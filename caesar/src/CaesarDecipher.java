import com.google.common.io.Resources;
import com.sun.istack.internal.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Main class for the deciphering application
 */
public class CaesarDecipher {
    private static HashMap<Character, Integer> letterStats = new HashMap<>();
    private static Set<Map.Entry<Character, Integer>> sortedLetterOccurrence;
    private static ArrayList<Character> letters = new ArrayList<>();

    public static void main(String[] args) {
        String encryptedText = readEncryptedFile();
        try {
            generateCharOccurrenceStats(encryptedText);
            sortLetterMap();
            decipherText(encryptedText);
        } catch (NullPointerException e) {
            System.out.println("File exception!");
        }
    }

    private static void decipherText(String encryptedText) {
        CaesarMain.readAlphabet();
        ArrayList<Character> ukrainianLettersSorted = readUkrainianLetterStatistics();
        for (Map.Entry<Character, Integer> entry : sortedLetterOccurrence) {
            letters.add(entry.getKey());
        }

        System.out.println("Letter statistics in ciphered text: " + sortedLetterOccurrence);

        //StringBuilder decryptedText = new StringBuilder("");
        //Scanner in = new Scanner(System.in);

        for (Character letter : letters) {
            try {
                for (int j = 0; j < 2; j++) {
                    int encryptedLetterIndex = CaesarMain.alphabet.indexOf(String.valueOf(letter));
                    int generalAlphabetIndex = CaesarMain.alphabet.indexOf(String.valueOf(ukrainianLettersSorted
                            .get(j)));
                    int offset = encryptedLetterIndex - generalAlphabetIndex;

                    String decrypted = CaesarMain.decryptCaesar(encryptedText, offset);
                    System.out.println("Decrypted sequence: " + decrypted + "\n -- Offset: " + offset);
                    System.out.println("------------");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    private static String readEncryptedFile() throws NullPointerException {
        try {
            URL url = Resources.class.getClassLoader().getResource("encrypted.txt");

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

    private static ArrayList<Character> readUkrainianLetterStatistics() {
        try {
            ArrayList<Character> letterOccurrence = new ArrayList<>();

            URL url = Resources.class.getClassLoader().getResource("ua_letter_stats.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getPath()));
            String currentLine, input;
            while ((currentLine = bufferedReader.readLine()) != null) {
                input = currentLine;
                letterOccurrence.add(input.charAt(0));
            }
            return letterOccurrence;
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
        letterStats.remove(' ');
    }

    private static void sortLetterMap() {
        Set<Map.Entry<Character, Integer>> letters = letterStats.entrySet();

        Comparator<Map.Entry<Character, Integer>> valueComparator = (o1, o2) -> {
            Integer value1 = o1.getValue();
            Integer value2 = o2.getValue();
            return value2.compareTo(value1);
        };

        ArrayList<Map.Entry<Character, Integer>> entryArrayList = new ArrayList<>(letters);
        entryArrayList.sort(valueComparator);
        LinkedHashMap<Character, Integer> sortedByValue = new LinkedHashMap<>(entryArrayList.size());
        for (Map.Entry<Character, Integer> entry : entryArrayList) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        sortedLetterOccurrence = sortedByValue.entrySet();
    }
}
