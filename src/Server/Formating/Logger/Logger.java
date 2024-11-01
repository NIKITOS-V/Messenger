package Server.Formating.Logger;

import Client.Formating.Message;

import java.util.ArrayList;

public interface Logger{

    void addMessage(int registryID, Message message);


    void addClientConnected(String userName, int registryID);

    void addClientDisconnected(String userName, int registryID);

     void addAnotherLog(String text);

     void saveLogs();

     ArrayList<String> getLogHistory();

    public String getLastLog();
}
