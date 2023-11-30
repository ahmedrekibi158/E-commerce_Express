package com.are_commerce.ecommercewebsite;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

public class GenerateKeyPair {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        class JwtTokenProvider{
            private KeyPair keyPair;

            public JwtTokenProvider() {
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(2048);
                this.keyPair = keyPairGenerator.generateKeyPair();
                System.out.println(keyPair);
            } catch (NoSuchAlgorithmException e) {
                // Handle exception
            }
        }}

        /*KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        byte[] pub = keyPair.getPublic().getEncoded();
        byte[] pri = keyPair.getPrivate().getEncoded();

        // Writing public key to pub.pem
        try (PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream("pub.pem")))) {
            PemObject pemObject = new PemObject("PUBLIC KEY", pub);
            pemWriter.writeObject(pemObject);
        }

        // Writing private key to pri.pem
        try (PemWriter pemWriter2 = new PemWriter(new OutputStreamWriter(new FileOutputStream("pri.pem")))) {
            PemObject pemObject2 = new PemObject("PRIVATE KEY", pri);
            pemWriter2.writeObject(pemObject2);
        }*/
    }
}
