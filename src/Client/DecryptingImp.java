package Client;

import Commands.SerializedCommands.*;
import Commands.SerializedResAuth;
import Interfaces.Decrypting;
import com.google.inject.Singleton;

@Singleton
public class DecryptingImp implements Decrypting {
    @Override
    public void decrypt(Object o) {
        if (o instanceof SerializedMessage) {
            SerializedMessage serializedMessage = (SerializedMessage) o;
            System.out.println(serializedMessage.getMessage());
        }
        if (o instanceof SerializedResAuth) {
            SerializedResAuth serializedResAuth = (SerializedResAuth) o;
            System.out.println(serializedResAuth.getRes());
        }
    }
}
