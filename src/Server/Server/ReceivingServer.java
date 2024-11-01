package Server.Server;

import Common.Errors.ConnectError;
import Client.Formating.UserData;
import Client.ClientConnectDriver.ToClientCD;

public interface ReceivingServer {
    boolean isRunning();
    void AcceptMessage(int userID, String text) throws ConnectError;
    void connectClient(UserData userData, ToClientCD connectDriver) throws ConnectError;
    void disconnectClient(int registryID);
}
