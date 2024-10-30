package Presenter;

import Model.Errors.ConnectError;
import Model.Formating.UserData;

public interface ToServerConnectDriver {
    void connect(UserData userData) throws ConnectError;
    void sendMessageToServer(String text) throws ConnectError;
    void disconnected();
}
