package View;

import Model.Errors.ConnectError;
import Model.Formating.UserData;
import Presenter.ToServerConnectDriver;
import Model.Formating.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ClientWindow extends JFrame implements Client {
    private ToServerConnectDriver connectDriver;

    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;
    private static final int BIG_TEXT_FONT_SIZE = 16;
    private static final int SMALL_TEXT_FONT_SIZE = 14;

    private final JPanel registryPanel;
    private final JPanel sendMessagePanel;

    private final JTextField IPTextField;
    private final JTextField portTextField;
    private final JTextField userNameTextField;
    private final JPasswordField userPasswordTextField;
    private final JButton loginButton;

    private final JTextArea userMessageField;
    private final JButton sendMessageButton;

    private final JTextArea allUsersMessagesField;
    private final JPanel allUsersMessagesPanel;

    public ClientWindow(){
        this.registryPanel = new JPanel();
        this.sendMessagePanel = new JPanel();

        this.IPTextField = new JTextField();
        this.portTextField = new JTextField(5);
        this.userNameTextField = new JTextField();
        this.userPasswordTextField = new JPasswordField();
        this.loginButton = new JButton();

        this.userMessageField = new JTextArea();
        this.sendMessageButton = new JButton();

        this.allUsersMessagesField = new JTextArea();
        this.allUsersMessagesPanel = new JPanel();

        createWindow();
        createRegistryPanel();
        createSendMessagePanel();
        createAllUsersMessagesFiled();

        addPanels();

        setVisible(true);
    }

    private void createWindow(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Client window");
        setResizable(false);
        setSize(WIDTH, HEIGHT);
    }

    private void createRegistryPanel(){
        GridLayout gridLayout = new GridLayout(2, 3);

        gridLayout.setHgap(
                getRelativeSize(HEIGHT, 0.005f)
        );

        gridLayout.setVgap(
                getRelativeSize(WIDTH, 0.005f)
        );

        this.registryPanel.setLayout(
                gridLayout
        );

        this.registryPanel.setPreferredSize(
                new Dimension(100, getRelativeSize(HEIGHT, 0.2f))
        );

        this.userPasswordTextField.setEchoChar('*');

        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        this.loginButton.setText("Login");

        Font font = new Font(
                "Arial",
                Font.PLAIN,
                SMALL_TEXT_FONT_SIZE
        );

        this.IPTextField.setFont(font);
        this.portTextField.setFont(font);
        this.loginButton.setFont(font);
        this.userNameTextField.setFont(font);
        this.userPasswordTextField.setFont(font);

        this.registryPanel.add(this.IPTextField);
        this.registryPanel.add(this.portTextField);
        this.registryPanel.add(this.loginButton);
        this.registryPanel.add(this.userNameTextField);
        this.registryPanel.add(this.userPasswordTextField);
    }

    private void createSendMessagePanel(){
        this.sendMessagePanel.setLayout(
                new BoxLayout(this.sendMessagePanel, BoxLayout.X_AXIS)
        );

        this.sendMessagePanel.setPreferredSize(
                new Dimension(100, getRelativeSize(HEIGHT, 0.1f))
        );

        this.userMessageField.setLineWrap(true);
        this.userMessageField.setWrapStyleWord(true);
        this.userMessageField.setFont(
                new Font("Arial", Font.PLAIN, SMALL_TEXT_FONT_SIZE)
        );

        this.sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendUserMessage();
            }
        });
        this.sendMessageButton.setText("Send");

        JPanel sendMessageButtonPanel = new JPanel(new GridLayout(1, 1));
        sendMessageButtonPanel.add(sendMessageButton);

        this.sendMessageButton.setPreferredSize(
                new Dimension(getRelativeSize(WIDTH, 0.15f), HEIGHT)
        );

        this.sendMessagePanel.add(this.userMessageField);

        this.sendMessagePanel.add(new JScrollPane(
                this.userMessageField,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        this.sendMessagePanel.add(sendMessageButtonPanel);
    }

    private void createAllUsersMessagesFiled(){
        this.allUsersMessagesPanel.setLayout(
                new GridLayout(1, 1)
        );

        this.allUsersMessagesField.setWrapStyleWord(true);
        this.allUsersMessagesField.setLineWrap(true);
        this.allUsersMessagesField.setEditable(false);
        this.allUsersMessagesField.setFont(
                new Font("Arial", Font.PLAIN, BIG_TEXT_FONT_SIZE)
        );

        this.allUsersMessagesPanel.add(this.allUsersMessagesField);
        this.allUsersMessagesPanel.add(
            new JScrollPane(
                    this.allUsersMessagesField,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
            )
        );
    }

    private int getRelativeSize(int SideSize, float Hint){
        return (int) (SideSize * Hint);
    }

    private void addPanels(){
        add(this.registryPanel, BorderLayout.NORTH);
        add(this.sendMessagePanel, BorderLayout.SOUTH);
        add(this.allUsersMessagesPanel, BorderLayout.CENTER);
    }

    private void sendUserMessage(){
        if (connectDriver == null){
            showError(
                    new ConnectError("Client has not the Connect driver")
            );
        }

        try {
            this.connectDriver.sendMessageToServer(
                this.userMessageField.getText()
            );

        } catch (ConnectError connectError){
            showError(connectError);
        }

    }

    private void login(){
        if (connectDriver == null){
            showError(
                    new ConnectError("Client has not the Connect driver")
            );
        }

        this.allUsersMessagesField.setText("");

        try {
            this.connectDriver.connect(
                    new UserData(
                            this.userNameTextField.getText(),
                            this.userPasswordTextField.getText()
                    )
            );

            showSuccessful("Connect status");

        } catch (ConnectError connectError){
            showError(connectError);
        }
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            this.connectDriver.disconnected();
        }

        super.processWindowEvent(e);
    }

    private void showError(IOException exception){
        JOptionPane.showMessageDialog(
                new JFrame(),
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showSuccessful(String title){
        JOptionPane.showMessageDialog(
                new JFrame(),
                "Successful",
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void AcceptMessage(Message message) {
       this.allUsersMessagesField.setText(
               String.format("%s%s: %s\n",
                       allUsersMessagesField.getText(),
                       message.getUserName(),
                       message.getText()
               )
       );
    }

    @Override
    public void setConnectDriver(ToServerConnectDriver connectDriver) {
        this.connectDriver = connectDriver;
    }
}