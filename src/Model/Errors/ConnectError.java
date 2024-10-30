package Model.Errors;

import java.io.IOException;

public class ConnectError extends IOException {
    public ConnectError(String message){
        super(message);
    }
}
