package Model.Server;

import Model.Errors.ConnectError;
import Model.Formating.ConnectedUserData;
import Model.Formating.Logger;
import Model.Formating.UserData;
import Model.Formating.Message;
import Presenter.ToClientConnectDriver;
import View.ServerWindow;

import java.util.ArrayList;
import java.util.Objects;

public class Server implements ReceivingServer, ManagedServer {
    private ServerWindow serverWindow;

    private final ArrayList<Message> chatHistory;
    private final Logger logger;

    private final ArrayList<UserData> dataBase;
    private final ArrayList<ConnectedUserData> connectedClients;
    private boolean serverIsRunning;

    public Server(){
        this.logger = new Logger();
        this.chatHistory = new ArrayList<>();
        this.dataBase = new ArrayList<>();
        this.connectedClients = new ArrayList<>();

        fillDatabase();

        this.serverIsRunning = false;
    }

    private void fillDatabase(){
        this.dataBase.add(
                new UserData("Lilia Cornaval", "Kirill2012")
        );

        this.dataBase.add(
                new UserData("Nikolay Cornaval", "180@alfa")
        );
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
    public void connectClient(UserData userData, ToClientConnectDriver connectDriver) throws ConnectError {
        if (!this.dataBase.contains(userData)){
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
        if (this.serverWindow != null){
            this.serverWindow.acceptLog(
                    this.logger.getLastLog()
            );
        }
    }

    public void createSeverWindow(){
        this.serverWindow = new ServerWindow(this);

        this.logger.addAnotherLog("Window of server has been created");

        sendLastLog();
    }

    @Override
    public String getLog() {
        return this.logger.getLastLog();
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
