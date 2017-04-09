import com.google.common.io.Resources;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Maksym on 03.04.2017.
 */
public class KeywordEncryptor {

    static ArrayList<Character> alphabet = new ArrayList<>();
    private static String input;

    public static void main(String[] args) {
        readAlphabet();
        System.out.println(alphabet);

        input = readInput();
        System.out.println("Input: \n" + input);

        String keyword;
        int shift;

        System.out.println("Key word: ");
        Scanner in = new Scanner(System.in);
        keyword = in.next();
        System.out.println("Shift: ");
        shift = in.nextInt();

        writeKeys(keyword, shift);

        String encrypted = encryptVigenere(keyword, shift);
        System.out.println("Encrypted: " + encrypted);
        writeEncrypted(encrypted);
    }


    private static String encryptVigenere(String key, int shift) {
        shift = (int) alphabet.get(shift);
        StringBuilder stringBuilder = new StringBuilder();
        if (shift < 0) {
            shift += 26;
        }

        char letter;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                letter = ' ';
            } else {
                int letterIndex = ((input.charAt(i) + key.charAt(i % key.length()) - 2 * shift) % 26);
                //letter = (char) (letterIndex + shift);
                int index = (letterIndex + shift) % alphabet.size();
                letter = alphabet.get(index);
            }
            stringBuilder.append(letter);
        }

        return stringBuilder.toString();
    }

    private static void writeEncrypted(String encrypted) {
        try {
            File file = new File(
                    "D:\\Progs\\JAVA\\2017\\1\\DataProtection\\athens-caesar\\" +
                            "res\\encrypted_vigenere.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(encrypted);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeKeys(String key, int shift) {
        try {
            File file = new File("D:\\Progs\\JAVA\\2017\\1\\DataProtection" +
                    "\\athens-caesar\\res\\keys_vigenere.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(key);
            fileWriter.write("\n");
            fileWriter.write(String.valueOf(shift));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readInput() {
        try {
            URL url = Resources.class.getClassLoader().getResource("input_vigenere.txt");
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

    static void readAlphabet() {
        try {
            URL url = Resources.class.getClassLoader().getResource("alphabet.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getPath()));
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                alphabet.add(currentLine.charAt(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
