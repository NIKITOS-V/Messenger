package Server.Server;

import Server.AdminConnectDriver.ToAdminCD;

public interface ManagedServer {
    void startServer();
    void stopServer();
    void setAdminCD(ToAdminCD adminCD);
}
