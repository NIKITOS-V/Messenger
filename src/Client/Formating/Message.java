package Client.Formating;

public class Message {
    private final String text;
    private final String userName;

    public Message(String userName, String text){
        this.userName = userName;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getUserName() {
        return userName;
    }
}
