import com.google.common.io.Resources;
import com.sun.istack.internal.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Maksym on 03.04.2017.
 */
public class AthensDecryptor {

    private static ArrayList<Integer> keys = new ArrayList<>();
    private static int a;
    private static int b;

    public static void main(String[] args) {
        String encryptedText = readEncryptedFile();
        readEncryptionKeys();

        String decryptedText = decrypt(encryptedText);
        System.out.println("Decrypted text: " + decryptedText);
    }

    @Nullable
    private static String decrypt(String encryptedText) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            int module = 26;
            BigInteger aInverse = BigInteger.valueOf(a).modInverse(BigInteger.valueOf(module));

            char letter;

            for (int i = 0; i < encryptedText.length(); i++) {
                if (encryptedText.charAt(i) == ' ') {
                    letter = ' ';
                } else {
                    int decoded = aInverse.intValue() * (encryptedText.charAt(i) - 'a' - b + module);
                    letter = (char) (decoded % module + 'a');
                }
                stringBuilder.append(letter);
            }

            return stringBuilder.toString();
        } catch (ArithmeticException e) {
            System.out.println("This value is not inversable!");
            return "";
        }
    }

    private static String readEncryptedFile() throws NullPointerException {
        try {
            URL url = Resources.class.getClassLoader().getResource("encrypted_affine.txt");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getPath()));
            String currentLine, input = null;
            while ((currentLine = bufferedReader.readLine()) != null)
                input = currentLine;
            return input;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void readEncryptionKeys() {
        try {
            URL url = Resources.class.getClassLoader().getResource("keys.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getPath()));
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                keys.add(Integer.valueOf(currentLine));
            }

            if (keys.size() == 2) {
                a = keys.get(0);
                b = keys.get(1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
