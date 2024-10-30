package View;

import Model.Formating.Message;
import Presenter.ToServerConnectDriver;

public interface Client {
    void AcceptMessage(Message message);
    void setConnectDriver(ToServerConnectDriver connectDriver);
}
