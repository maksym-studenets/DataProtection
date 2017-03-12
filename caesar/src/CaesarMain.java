import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by maksym on 11.03.17.
 */

public class CaesarMain {
    private static ArrayList<String> alphabet = new ArrayList<>();

    public static void main(String[] args) {
        readAlphabet();
        String input = readFile();

        System.out.print("Offset: ");
        Scanner in = new Scanner(System.in);
        int offset = in.nextInt();

        String encryptedText = encryptCaesar(input, offset);
        System.out.println("Input text: " + input);
        System.out.println("Encrypted text: " + encryptedText);

        System.out.println(" ------------------------------- ");
        System.out.println(" ------------------------------- ");
        System.out.println(" ------------------------------- ");

        String decryptedText = decryptCaesar(encryptedText, offset);
        System.out.println("Decrypted text: " + decryptedText);
        writeEncryptedToFile(encryptedText);
    }

    private static String encryptCaesar(String text, int offset) {
        text = text.toLowerCase();

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

    private static String decryptCaesar(String text, int offset) {
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

    private static void readAlphabet() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    "/home/maksym/PROGRAMS/Java/DataProtection/caesar/res/alphabet.txt"));
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                alphabet.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    "/home/maksym/PROGRAMS/Java/DataProtection/caesar/res/input.txt"));
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
                    "/home/maksym/PROGRAMS/Java/DataProtection/caesar/res/encrypted.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(encryptedText);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
