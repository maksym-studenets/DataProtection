import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Maksym on 03.04.2017.
 */
public class KeywordEncryptor {

    private static ArrayList<Character> alphabet = new ArrayList<>();
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

        String encrypted = encryptVigere(keyword, shift);
        System.out.println("Encrypted: " + encrypted);
    }


    private static String encryptVigere(String key, int shift) {
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
                letter = (char) (letterIndex + shift);
            }
            stringBuilder.append(letter);
        }

        return stringBuilder.toString();
    }

    private static String readInput() {
        try {
            URL url = Resources.class.getClassLoader().getResource("input_vigere.txt");
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

    private static void readAlphabet() {
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
