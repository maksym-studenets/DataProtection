import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Maksym on 03.04.2017.
 */
public class KeywordDecryptor {

    private static ArrayList<Object> keys = new ArrayList<>();
    private static String keyword;
    private static int shift;

    public static void main(String[] args) {
        initializeAlphabet();
        String encryptedText = readEncryptedFile();
        readEncryptionKeys();

        System.out.println("Encrypted: " + encryptedText);
        System.out.println("keyword: " + keyword);
        System.out.println("shift: " + shift);

        String decrypted = decrypt(encryptedText, keyword, shift);
        System.out.println("Decrypted: " + decrypted);
    }

    private static String decrypt(String encrypted, String key, int shift) {

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < encrypted.length(); i++) {
            if (encrypted.charAt(i) == ' ') {
                ans.append(' ');
            } else {
                int index = ((encrypted.charAt(i) - key.charAt(i % key.length()) + 26) % 26);
                if (index < 0)
                    index += 26;

                //num = ((encrypted.charAt(i)  - key.charAt(i % key.length()) + 26) % 26);
                //обратные преобразования с номером буквы в алфавите
                char c = KeywordEncryptor.alphabet.get((index + shift) % 26);
                ans.append(c);
            }


        }
        return ans.toString();

        /*
        StringBuilder stringBuilder = new StringBuilder();
        shift = (int) KeywordEncryptor.alphabet.get(shift);

        char letter;
        for (int i = 0; i < encrypted.length(); i++) {
            if (encrypted.charAt(i) == ' ') {
                letter = ' ';
            } else {
                int num = ((encrypted.charAt(i) - key.charAt(i % key.length()) + 26) % 26);
                int index = (num + shift) % 26;
                if (index < 0) {
                    index = -index;
                } else if (index > KeywordEncryptor.alphabet.size()) {
                    index %= KeywordEncryptor.alphabet.size();
                }
                    letter = KeywordEncryptor.alphabet.get(index);
            }
            stringBuilder.append(letter);
        }

        return stringBuilder.toString();
        */

        /*
        * public String decrypt(String shifr, String keyWord)
    {
        StringBuilder ans = new StringBuilder();
        for(int i = 0; i < shifr.length();i++)
        {
            int num = ((shifr.charAt(i)  - keyWord.charAt(i % keyWord.length()) + 26) % 26);
            //обратные преобразования с номером буквы в алфавите
            char c = (char)(num + smesh);
            ans.append(c);
        }
        return ans.toString();
    }
        * */
    }

    private static String readEncryptedFile() {
        try {
            URL url = Resources.class.getClassLoader().getResource("encrypted_vigenere.txt");

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
            URL url = Resources.class.getClassLoader().getResource("keys_vigenere.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getPath()));
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                keys.add(currentLine);
            }

            if (keys.size() == 2) {
                keyword = String.valueOf(keys.get(0));
                shift = Integer.valueOf(String.valueOf(keys.get(1)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeAlphabet() {
        KeywordEncryptor.readAlphabet();
    }
}
