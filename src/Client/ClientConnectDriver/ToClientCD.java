package Client.ClientConnectDriver;

import Client.Formating.Message;

public interface ToClientCD {
    void sendMessageToClient(Message message);
    void setRegistryID(int registryID);
    int getRegistryID();
}
