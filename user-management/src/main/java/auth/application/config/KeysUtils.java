package auth.application.config;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;

@Component
public class KeysUtils {
    private KeyPair keyPair;

    public KeyPair generateRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    public Optional<RSAPublicKey> getPublicKey() {
        if (keyPair != null) {
            return Optional.of((RSAPublicKey) keyPair.getPublic());
        } else {
            return Optional.empty();
        }
    }
}
