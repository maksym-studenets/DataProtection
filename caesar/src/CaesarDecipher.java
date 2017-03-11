import com.sun.istack.internal.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Main class for the deciphering application
 */
public class CaesarDecipher {
    private static HashMap<Character, Integer> letterStats = new HashMap<>();

    public static void main(String[] args) {
        String encryptedText = readEncryptedFile();
        generateCharOccurrenceStats(encryptedText);
    }

    private static String decipherText(String encryptedText) {
        return null;
    }

    @Nullable
    private static String readEncryptedFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    "/home/maksym/PROGRAMS/Java/DataProtection/caesar/res/encrypted.txt"));
            String currentLine, input = null;
            while ((currentLine = bufferedReader.readLine()) != null)
                input = currentLine;
            return input;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void generateCharOccurrenceStats(String text) {
        char[] textChars = text.toCharArray();
        for (char c : textChars) {
            if (letterStats.containsKey(c)) {
                letterStats.put(c, letterStats.get(c) + 1);
            } else {
                letterStats.put(c, 1);
            }
        }

        System.out.println("Letter statistics");
        System.out.println(letterStats);
    }
}
