package Server.AdminConnectDriver;

import Server.Server.ManagedServer;
import Server.Window.AdminWindowView;

public class AdminCD implements ToServerCD, ToAdminCD{
    private final ManagedServer server;
    private final AdminWindowView admin;

    public AdminCD(AdminWindowView admin, ManagedServer server){
        this.admin = admin;
        this.server = server;

        this.server.setAdminCD(this);
        this.admin.setServerCD(this);
    }


    @Override
    public void startServer() {
        this.server.startServer();
    }

    @Override
    public void stopServer() {
        this.server.stopServer();
    }

    @Override
    public void acceptLog(String log) {
        this.admin.acceptLog(log);
    }
}
