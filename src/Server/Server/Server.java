package Server.Server;

import Common.Errors.ConnectError;
import Server.AdminConnectDriver.ToAdminCD;
import Server.Formating.ConnectedUserData;
import Server.Formating.Logger.Logger;
import Client.Formating.UserData;
import Client.Formating.Message;
import Client.ClientConnectDriver.ToClientCD;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Database{
    public final ArrayList<UserData> database;

    public Database(){
        this.database = new ArrayList<>(List.of(
                new UserData("user1", "password1"),
                new UserData("user2", "password2"),
                new UserData("user3", "password3"),
                new UserData("user4", "password4"),
                new UserData("user5", "password5")
        ));
    }

    public boolean contains(UserData userData){
        return this.database.contains(userData);
    }
}

public class Server implements ReceivingServer, ManagedServer {
    private ToAdminCD adminCD;

    private final ArrayList<Message> chatHistory;
    private final Logger logger;
    private final Database database;

    private final ArrayList<ConnectedUserData> connectedClients;
    private boolean serverIsRunning;

    public Server(Logger logger){
        this.logger = logger;
        this.chatHistory = new ArrayList<>();
        this.database = new Database();
        this.connectedClients = new ArrayList<>();

        this.serverIsRunning = false;
    }
    @Override
    public boolean isRunning(){
        return this.serverIsRunning;
    }

    @Override
    public void AcceptMessage(int userID, String text) throws ConnectError {
        boolean isConnected = false;
        String userName = "default_user_name";

        for (ConnectedUserData connectedUserData: this.connectedClients){
            if (connectedUserData.getConnectDriver().getRegistryID() == userID){
                isConnected = true;
                userName = connectedUserData.getUserName();
            }
        }

        if (!isConnected){
            throw new ConnectError("User is not registered");
        }

        Message message = new Message(
                userName,
                text
        );

        this.chatHistory.add(message);

        this.logger.addMessage(
                userID, message
        );

        sendLastLog();

        sendMessages(message);
    }

    @Override
    public void connectClient(UserData userData, ToClientCD connectDriver) throws ConnectError {
        if (!this.database.contains(userData)){
            throw new ConnectError("Uncorrected user`s name or password");
        }

        for (ConnectedUserData connectedUserData: this.connectedClients){
            if (connectedUserData.getUserName().equals(userData.getUserName())){
                throw new ConnectError("User is already connected");
            }
        }

        int registryID = Objects.hash(userData.getUserName());

        connectDriver.setRegistryID(
                registryID
        );

        this.connectedClients.add(
                new ConnectedUserData(
                        userData.getUserName(),
                        connectDriver
                )
        );

        for (Message message: this.chatHistory){
            connectDriver.sendMessageToClient(message);
        }

        this.logger.addClientConnected(
                userData.getUserName(),
                registryID
        );

        sendLastLog();
    }

    @Override
    public void disconnectClient(int registryID) {
        for (ConnectedUserData connectedUserData: this.connectedClients){
            if (connectedUserData.getConnectDriver().getRegistryID() == registryID){

                this.connectedClients.remove(connectedUserData);

                this.logger.addClientDisconnected(
                        connectedUserData.getUserName(),
                        connectedUserData.getConnectDriver().getRegistryID()
                );

                sendLastLog();

                break;
            }
        }
    }

    private void sendMessages(Message message){
        for (ConnectedUserData connectedUserData: this.connectedClients){
            connectedUserData.getConnectDriver().sendMessageToClient(message);
        }
    }

    private void sendLastLog(){
        if (this.adminCD != null){
            this.adminCD.acceptLog(
                    this.logger.getLastLog()
            );
        }
    }

    public void setAdminCD(ToAdminCD admin){
        this.adminCD = admin;

        this.logger.addAnotherLog("Window of server has been connected");

        sendLastLog();
    }

    @Override
    public void startServer() {
        if (!this.serverIsRunning){
            this.serverIsRunning = true;

            this.logger.addAnotherLog(
                    "Server has been started"
            );

        } else {
            this.logger.addAnotherLog(
                    "Server is already started"
            );
        }

        sendLastLog();
    }

    @Override
    public void stopServer() {
        if (this.serverIsRunning){
            for (ConnectedUserData connectedUserData: this.connectedClients) {
                this.logger.addClientDisconnected(
                        connectedUserData.getUserName(),
                        connectedUserData.getConnectDriver().getRegistryID()
                );

                sendLastLog();
            }
            this.connectedClients.clear();

            this.chatHistory.clear();

            this.logger.addAnotherLog(
                "Server has been stop"
            );

            sendLastLog();

            this.logger.saveLogs();

            this.serverIsRunning = false;
        }
    }
}
