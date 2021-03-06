package encryption;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import jdk.internal.org.xml.sax.InputSource;

public class Des {

    public static void encrypt(String key, InputStream is, OutputStream os) throws Exception {
        encryptOrDecrypt(key, Cipher.ENCRYPT_MODE, is, os);

    }

    public static void decypt(String key, InputStream is, OutputStream os) throws Exception {
        encryptOrDecrypt(key, Cipher.DECRYPT_MODE, is, os);
    }

    public static void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os) throws Exception {
        
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");

        if (mode == Cipher.ENCRYPT_MODE) {
            
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            makeFile(cis, os);
            
        } else if (mode == Cipher.DECRYPT_MODE) {
            
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            CipherOutputStream cos = new CipherOutputStream(os, cipher);
            makeFile(is, cos);
        }

    }

    private static void makeFile(InputStream is, OutputStream os) throws IOException {
        byte[] bytes= new byte[64];
        int numBytes;
        while((numBytes = is.read(bytes)) != -1){
            os.write(bytes,0, numBytes);
        }
        os.flush();
        os.close();
        is.close();
    }

}
