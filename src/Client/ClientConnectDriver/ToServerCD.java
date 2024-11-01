package Client.ClientConnectDriver;

import Common.Errors.ConnectError;
import Client.Formating.UserData;

public interface ToServerCD {
    void connect(UserData userData) throws ConnectError;
    void sendMessageToServer(String text) throws ConnectError;
    void disconnected();
}
