package View;

import Model.Server.ManagedServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ServerWindow extends JFrame{
    private ManagedServer server;

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int BIG_TEXT_FONT_SIZE = 14;
    private static final int SMALL_TEXT_FONT_SIZE = 12;

    private final JPanel controlPanel;
    private final JButton startServerButton;
    private final JButton stopServerButton;

    private final JPanel logsPanel;
    private final JTextArea logsTextField;

    private int numberLine;

    public ServerWindow(ManagedServer server){
        this.server = server;

        this.controlPanel = new JPanel();
        this.startServerButton = new JButton();
        this.stopServerButton = new JButton();
        this.logsPanel = new JPanel();
        this.logsTextField = new JTextArea();

        createWindow();
        createControlPanel();
        createLogsPanel();

        addPanels();

        setVisible(true);

        numberLine = 1;
    }

    private void createWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Server Window");
        setResizable(false);
        setSize(WIDTH, HEIGHT);
    }

    private void createControlPanel(){
        this.controlPanel.setLayout(
                new GridLayout(1, 2)
        );
        this.controlPanel.setPreferredSize(
               new Dimension(
                       WIDTH, getRelativeSize(HEIGHT, 0.09f)
               )
        );

        this.startServerButton.setText("Start");
        this.startServerButton.setFont(
                new Font("Arial", Font.PLAIN, SMALL_TEXT_FONT_SIZE)
        );
        this.startServerButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startServer();
                    }
                }
        );

        this.stopServerButton.setText("Stop");
        this.stopServerButton.setFont(
                new Font("Arial", Font.PLAIN, SMALL_TEXT_FONT_SIZE)
        );
        this.stopServerButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        stopServer();
                    }
                }
        );

        this.controlPanel.add(this.startServerButton);
        this.controlPanel.add(this.stopServerButton);
    }

    private void createLogsPanel(){
        this.logsPanel.setLayout(
                new GridLayout(1, 1)
        );

        this.logsTextField.setFont(
                new Font("Arial", Font.PLAIN, BIG_TEXT_FONT_SIZE)
        );
        this.logsTextField.setLineWrap(true);
        this.logsTextField.setWrapStyleWord(true);
        this.logsTextField.setEditable(false);

        this.logsPanel.add(this.logsTextField);
        this.logsPanel.add(
                new JScrollPane(
                        this.logsTextField,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
                )
        );
    }

    private void addPanels(){
        add(this.controlPanel, BorderLayout.SOUTH);
        add(this.logsPanel, BorderLayout.CENTER);
    }

    private int getRelativeSize(int SideSize, float Hint){
        return (int) (SideSize * Hint);
    }

    private void startServer(){
        this.server.startServer();
    }

    private void stopServer(){
        this.server.stopServer();
    }

    public void acceptLog(String log){
        this.logsTextField.setText(
                String.format(
                        "%s%d) %s\n",
                        this.logsTextField.getText(),
                        numberLine++,
                        log
                )
        );
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            this.server.stopServer();
        }

        super.processWindowEvent(e);
    }
}
