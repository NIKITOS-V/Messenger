package Server.Formating.Logger;

import Client.Formating.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Logger {
    private final String PATH = "src/Server/Formating/Logger/Logs/LastLogs.txt";
    private final ArrayList<String> logHistory;

    public Logger(){
        this.logHistory = new ArrayList<>();
    }

    public void addMessage(int registryID, Message message){
        this.logHistory.add(
                getFormatString(
                        message.getUserName(),
                        registryID,
                        message.getText()
                )
        );
    }

    public void addClientConnected(String userName, int registryID){
        this.logHistory.add(
                String.format(
                        "%s (%d) has been connected",
                        userName,
                        registryID
                )
        );
    }

    public void addClientDisconnected(String userName, int registryID){
        this.logHistory.add(
            String.format(
                    "%s (%d) has been disconnected",
                    userName,
                    registryID
            )
        );
    }

    public void addAnotherLog(String text){
        this.logHistory.add(text);
    }

    private String getFormatString(String userName, int registryID, String text){
        return String.format(
                "%s (%d) : %s",
                userName,
                registryID,
                text
        );
    }

    public void saveLogs(){
        try (FileWriter fileWriter = new FileWriter(PATH, false)){
            for (String text: this.logHistory){
                fileWriter.append(text);
                fileWriter.append("\n");
            }

            fileWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getLogHistory() {
        return logHistory;
    }

    public String getLastLog(){
        return this.logHistory.getLast();
    }
}
