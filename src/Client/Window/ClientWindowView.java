package Client.Window;

import Client.Formating.Message;
import Client.ClientConnectDriver.ToServerCD;

public interface ClientWindowView {
    void AcceptMessage(Message message);
    void setConnectDriver(ToServerCD connectDriver);
}
