import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Main class for XOR cipher decryption
 */
public class XorDecryptor {
    private static ArrayList<String> alphabet = XorEncryptor.getAlphabet();
    private static ArrayList<Integer> keys = new ArrayList<>();
    private static ArrayList<Integer> decryptedMessage = new ArrayList<>();
    private static ArrayList<Integer> encryptedMessage = new ArrayList<>();
    private static ArrayList<Integer> gamma = new ArrayList<>();
    private static int key1, key2, key3;

    public static void main(String[] args) {
        readEncryptionKeys();
        System.out.println("Keys: 1) " + key1 + "; 2) " + key2 + "; 3) " + key3);
        readEncryptedMessage();
        System.out.println("Encrypted message: " + encryptedMessage);

        generateGamma();
        System.out.println("Gama: " + gamma);
        decryptMessage();
        System.out.println("Decrypted sequence: " + decryptedMessage);
    }

    private static void decryptMessage() {
        int newElement;
        for (int i = 0; i < encryptedMessage.size(); i++) {
            newElement = encryptedMessage.get(i) + (alphabet.size() - gamma.get(i));
            decryptedMessage.add(newElement % alphabet.size());
        }
    }

    private static void generateGamma() {
        ArrayList<Integer> sequence = new ArrayList<>();
        sequence.add(key1);
        sequence.add(key2);
        sequence.add(key3);

        for (int i = 3; i < encryptedMessage.size() + 1; i++) {
            int element = (sequence.get(i - 1) + sequence.get(i - 3)) % alphabet.size();
            sequence.add(element);
        }

        //ArrayList<Integer> gamma = new ArrayList<>();
        for (int i = 0; i < encryptedMessage.size(); i++) {
            int element = (sequence.get(i) + sequence.get(i + 1)) % alphabet.size();
            gamma.add(element);
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

            if (keys.size() == 3) {
                key1 = keys.get(0);
                key2 = keys.get(1);
                key3 = keys.get(2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readEncryptedMessage() {
        try {
            URL url = Resources.class.getClassLoader().getResource("encrypted.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getPath()));
            String currentLine = null;
            while ((currentLine = bufferedReader.readLine()) != null) {
                encryptedMessage.add(Integer.valueOf(currentLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
