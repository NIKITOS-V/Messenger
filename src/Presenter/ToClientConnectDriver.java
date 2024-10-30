package Presenter;

import Model.Formating.Message;

public interface ToClientConnectDriver {
    void sendMessageToClient(Message message);
    void setRegistryID(int registryID);
    int getRegistryID();
}
