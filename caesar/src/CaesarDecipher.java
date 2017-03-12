import com.sun.istack.internal.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Main class for the deciphering application
 */
public class CaesarDecipher {
    private static ArrayList<String> alphabet = new ArrayList<>();
    private static HashMap<Character, Integer> letterStats = new HashMap<>();
    private static Set<Map.Entry<Character, Integer>> sortedLetterOccurrence;
    //private static Multiset<Character> occurrences = HashMultiset.create();

    public static void main(String[] args) {
        String encryptedText = readEncryptedFile();
        try {
            generateCharOccurrenceStats(encryptedText);
            //multiSetCharOccurrence(encryptedText);
            sortLetterMap();
            decipherText(encryptedText);
        } catch (NullPointerException e) {
            System.out.println("File exception!");
        }
    }

    private static String decipherText(String encryptedText) {
        readAlphabet();
        ArrayList<Character> ukrainianLettersSorted = readUkrainianLetterStatistics();
        ArrayList<Character> letters = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : sortedLetterOccurrence) {
            letters.add(entry.getKey());
        }

        StringBuilder decryptedText = new StringBuilder("");

        for (int i = 0; i < letters.size(); i++) {
            int encryptedLetterIndex = alphabet.indexOf(String.valueOf(letters.get(i)));
            int generalAlphabetIndex = alphabet.indexOf(String.valueOf(ukrainianLettersSorted.get(i)));
            int offset = encryptedLetterIndex - generalAlphabetIndex;


            for (int j = 0; j < encryptedText.length(); j++) {
                //char character = encryptedText.charAt(j);
                int index = alphabet.indexOf(String.valueOf(encryptedText.charAt(j)));
                int newIndex = index - offset;
                decryptedText.append(alphabet.get(newIndex));
            }

            System.out.println("Decrypted Text: " + decryptedText.toString());
        }

        return decryptedText.toString();
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

    private static ArrayList<Character> readUkrainianLetterStatistics() {
        try {
            ArrayList<Character> letterOccurrence = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    "/home/maksym/PROGRAMS/Java/DataProtection/caesar/res/ua_letter_stats.txt"));
            String currentLine, input = null;
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

        System.out.println("Letter statistics");
        System.out.println(letterStats);
    }

    private static void sortLetterMap() {
        Set<Map.Entry<Character, Integer>> letters = letterStats.entrySet();

        Comparator<Map.Entry<Character, Integer>> valueComparator = (o1, o2) -> {
            Integer value1 = o1.getValue();
            Integer value2 = o2.getValue();
            return value2.compareTo(value1);
        };

        ArrayList<Map.Entry<Character, Integer>> entryArrayList = new ArrayList<>(letters);
        Collections.sort(entryArrayList, valueComparator);
        LinkedHashMap<Character, Integer> sortedByValue = new LinkedHashMap<>(entryArrayList.size());
        for (Map.Entry<Character, Integer> entry : entryArrayList) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        sortedLetterOccurrence = sortedByValue.entrySet();
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
}
