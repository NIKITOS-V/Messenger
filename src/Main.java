import Model.Server.Server;
import Presenter.ConnectDriver;
import View.ClientWindow;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.createSeverWindow();

        ClientWindow clientWindow1 = new ClientWindow();
        ClientWindow clientWindow2 = new ClientWindow();

        new ConnectDriver(
                clientWindow1,
                server
        );

        new ConnectDriver(
                clientWindow2,
                server
        );
    }
}