package Client.Formating;

import java.util.Objects;

public class UserData {
    private final String userPassword;
    private final String userName;

    public UserData(String userName, String userPassword){
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        UserData anotherClient = (UserData) obj;

        return this.userName.equals(anotherClient.getUserName()) &&
                this.userPassword.equals(anotherClient.getUserPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userName, this.userPassword);
    }
}
