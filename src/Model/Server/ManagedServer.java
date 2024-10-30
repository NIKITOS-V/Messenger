package Model.Server;

public interface ManagedServer {
    String getLog();
    void startServer();
    void stopServer();
}
