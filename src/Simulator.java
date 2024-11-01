import Client.ClientConnectDriver.ClientCD;
import Client.Window.ClientWindow;
import Server.AdminConnectDriver.AdminCD;
import Server.Formating.Logger.FileHandler;
import Server.Server.Server;
import Server.Window.AdminWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulator extends JFrame {
    /*
        ПРОШУ ВНИМАНИЯ !!!!
        Данный класс не относится ни к серверной части, ни к клиентской части проекта.
        Он написан с целью создавать клиентские окна во время работы приложения,
        тем самым имитировать работу этого приложения, будто это настоящий мессенджер.
    */

    private final Server server;

    private final JButton createClientWindowButton;

    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;

    public Simulator(){
        this.createClientWindowButton = new JButton();

        this.server = new Server(
                new FileHandler()
        );

        createWindow();
        createButton();

        createAdminWindow();

        setVisible(true);
    }

    private void createWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setTitle("Creator client window");
        setResizable(false);
    }

    private void createButton(){
        this.createClientWindowButton.setText(
                "Create client window"
        );

        this.createClientWindowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createClientWindow();
            }
        });

        add(this.createClientWindowButton);
    }

    private void createAdminWindow(){
        new AdminCD(
                new AdminWindow(),
                this.server
        );
    }

    private void createClientWindow(){
        new ClientCD(
                new ClientWindow(),
                this.server
        );
    }
}
