package Server.Formating;

import Client.ClientConnectDriver.ToClientCD;

public class ConnectedUserData {
    private final String userName;
    private final ToClientCD connectDriver;

    public ConnectedUserData(String userName, ToClientCD connectDriver){
        this.connectDriver = connectDriver;
        this.userName = userName;
    }

    public ToClientCD getConnectDriver() {
        return connectDriver;
    }

    public String getUserName() {
        return userName;
    }
}
