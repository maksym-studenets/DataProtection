import com.google.common.io.Resources;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by maksym on 11.03.17.
 */

public class CaesarMain {
    static ArrayList<String> alphabet = new ArrayList<>();

    public static void main(String[] args) {
        readAlphabet();
        String input = readFile();

        System.out.println("Input text: " + input);

        System.out.print("Offset: ");
        Scanner in = new Scanner(System.in);
        int offset = in.nextInt();

        String encryptedText = encryptCaesar(input, offset);
        System.out.println("Encrypted text: " + encryptedText);

        System.out.println(" --------------------------------- ");
        System.out.println(" --------------------------------- ");
        System.out.println(" --------------------------------- ");

        String decryptedText = decryptCaesar(encryptedText, offset);
        System.out.println("Decrypted text: " + decryptedText);
        writeEncryptedToFile(encryptedText);
    }

    static void readAlphabet() {
        try {
            URL url = Resources.class.getClassLoader().getResource("alphabet.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getPath()));
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                alphabet.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String decryptCaesar(String text, int offset) {
        String plainText = "";
        String replacementValue;
        for (int i = 0; i < text.length(); i++) {
            int charPosition = alphabet.indexOf(String.valueOf(text.charAt(i)));
            int keyValue = (charPosition - offset) % 32;
            if (keyValue < 0)
                keyValue = alphabet.size() + keyValue;
            if (text.charAt(i) != ' ')
                replacementValue = alphabet.get(keyValue);
            else replacementValue = " ";
            plainText += replacementValue;
        }
        return plainText;
    }

    private static String encryptCaesar(String text, int offset) {
        text = text.toLowerCase();

        if (offset < 0)
            offset = 32 + offset;

        String encryptedText = "";
        String replacementValue;
        for (int i = 0; i < text.length(); i++) {
            int charPosition = alphabet.indexOf(String.valueOf(text.charAt(i)));
            int keyValue = (charPosition + offset) % 32;
            if (text.charAt(i) != ' ')
                replacementValue = alphabet.get(keyValue);
            else replacementValue = " ";
            encryptedText += replacementValue;
        }
        return encryptedText;
    }


    public static String readFile() {
        try {
            URL url = Resources.class.getClassLoader().getResource("input.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getPath()));
            String currentLine, input = null;
            while ((currentLine = bufferedReader.readLine()) != null) {
                input = currentLine;
            }
            bufferedReader.close();
            return input;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeEncryptedToFile(String encryptedText) {
        try {
            File file = new File(
                    "D:\\Progs\\JAVA\\2017\\1\\DataProtection\\caesar\\res\\encrypted.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(encryptedText);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeEncryptionKey(int key) {
        try {
            File file = new File(
                    "D:\\Progs\\JAVA\\2017\\1\\DataProtection\\caesar\\res\\key.txt");
            FileWriter fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
