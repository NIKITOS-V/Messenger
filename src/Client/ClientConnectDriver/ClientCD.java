package Client.ClientConnectDriver;

import Common.Errors.ConnectError;
import Client.Formating.Message;
import Server.Server.ReceivingServer;
import Client.Formating.UserData;
import Client.Window.ClientWindowView;

public class ClientCD implements ToClientCD, ToServerCD {
    private final ReceivingServer server;
    private final ClientWindowView clientWindowView;
    private int registryID = -1;

    public ClientCD(ClientWindowView clientWindowView, ReceivingServer server){
        this.server = server;
        this.clientWindowView = clientWindowView;

        this.clientWindowView.setConnectDriver(this);
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
        this.clientWindowView.AcceptMessage(message);
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
