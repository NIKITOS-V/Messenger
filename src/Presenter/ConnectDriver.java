package Presenter;

import Model.Errors.ConnectError;
import Model.Formating.Message;
import Model.Server.ReceivingServer;
import Model.Formating.UserData;
import View.Client;

public class ConnectDriver implements ToClientConnectDriver, ToServerConnectDriver {
    private final ReceivingServer server;
    private final Client client;
    private int registryID = -1;

    public ConnectDriver(Client client, ReceivingServer server){
        this.server = server;
        this.client = client;

        this.client.setConnectDriver(this);
    }

    @Override
    public void connect(UserData userData) throws ConnectError {
        if (!server.isRunning()){
            throw new ConnectError("Server is not running");
        }

        this.server.connectClient(
                userData,
                this
        );
    }

    @Override
    public void sendMessageToClient(Message message){
        this.client.AcceptMessage(message);
    }

    @Override
    public void sendMessageToServer(String text) throws ConnectError {
         if (!server.isRunning()){
            throw new ConnectError("Server is not running");
         }

         this.server.AcceptMessage(this.registryID, text);
    }

    @Override
    public void disconnected() {
        this.server.disconnectClient(this.registryID);
    }

    public void setRegistryID(int registryID) {
        this.registryID = registryID;
    }

    public int getRegistryID() {
        return registryID;
    }
}
