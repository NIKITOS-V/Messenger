package Server.Window;

import Server.AdminConnectDriver.ToServerCD;

public interface AdminWindowView {
    void acceptLog(String log);
    void setServerCD(ToServerCD serverCD);
}
