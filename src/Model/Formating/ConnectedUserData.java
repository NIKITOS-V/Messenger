package Model.Formating;

import Presenter.ToClientConnectDriver;

public class ConnectedUserData {
    private final String userName;
    private final ToClientConnectDriver connectDriver;

    public ConnectedUserData(String userName, ToClientConnectDriver connectDriver){
        this.connectDriver = connectDriver;
        this.userName = userName;
    }

    public ToClientConnectDriver getConnectDriver() {
        return connectDriver;
    }

    public String getUserName() {
        return userName;
    }
}
