package Model.Server;

import Model.Errors.ConnectError;
import Model.Formating.UserData;
import Presenter.ToClientConnectDriver;

public interface ReceivingServer {
    boolean isRunning();
    void AcceptMessage(int userID, String text) throws ConnectError;
    void connectClient(UserData userData, ToClientConnectDriver connectDriver) throws ConnectError;
    void disconnectClient(int registryID);
}
