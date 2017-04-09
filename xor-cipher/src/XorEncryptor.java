import com.google.common.io.Resources;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Example application for XOR cipher encryption
 */
public class XorEncryptor {
    private static ArrayList<String> alphabet = new ArrayList<>();
    private static ArrayList<Integer> xorReplacement = new ArrayList<>();
    private static ArrayList<Integer> afterFirstReplacement = new ArrayList<>();
    private static int key1, key2, key3;
    private static String input;

    public static void main(String[] args) {
        input = readFile().toLowerCase();
        readAlphabet();
        readXorReplacementValues();

        afterFirstReplacement = replaceWithValues();
        System.out.println("Input: " + input);
        System.out.println("Encrypted sequence: " + afterFirstReplacement);

        Scanner in = new Scanner(System.in);
        System.out.print("Key 1: ");
        key1 = in.nextInt();
        System.out.print("Key 2: ");
        key2 = in.nextInt();
        System.out.print("Key 3: ");
        key3 = in.nextInt();
        System.out.println("Keys: " + key1 + "; " + key2 + "; " + key3);
        checkKeys();

        ArrayList<Integer> sequence = generateIntermediateSequence();
        System.out.println("Sequence: " + sequence);
        System.out.println("Input sequence length: " + input.length());
        System.out.println("Intermediate sequence size: " + sequence.size());

        ArrayList<Integer> encryptedSequence = encrypt();
        System.out.println("Encrypted sequence: " + encryptedSequence);

        writeCipherMessage(encryptedSequence);
    }

    public static ArrayList<String> getAlphabet() {
        readAlphabet();
        return alphabet;
    }

    private static ArrayList<Integer> encrypt() {
        ArrayList<Integer> sequence = new ArrayList<>();
        sequence.add(key1);
        sequence.add(key2);
        sequence.add(key3);

        for (int i = 3; i < input.length() + 1; i++) {
            int element = (sequence.get(i - 1) + sequence.get(i - 3)) % alphabet.size();
            sequence.add(element);
        }

        ArrayList<Integer> gamma = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            int element = (sequence.get(i) + sequence.get(i + 1)) % alphabet.size();
            gamma.add(element);
        }


        ArrayList<Integer> output = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                continue;
            }

            char character = input.charAt(i);
            int charIndex = alphabet.indexOf(String.valueOf(character));
            int element = (afterFirstReplacement.get(charIndex) + gamma.get(i)) % alphabet.size();
            output.add(element);
        }

        return output;
    }

    private static ArrayList<Integer> generateIntermediateSequence() {
        ArrayList<Integer> sequence = new ArrayList<>();
        sequence.add(key1);
        sequence.add(key2);
        sequence.add(key3);

        for (int i = 3; i < input.length() + 1; i++) {
            int element = (sequence.get(i - 1) + sequence.get(i - 3)) % alphabet.size();
            sequence.add(element);
        }

        return sequence;
    }

    private static ArrayList<Integer> replaceWithValues() {
        ArrayList<Integer> replacedSequence = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                continue;
            }

            int alphabetPosition = alphabet.indexOf(String.valueOf(input.charAt(i)));
            replacedSequence.add(xorReplacement.get(alphabetPosition));
        }
        return replacedSequence;
    }

    private static void checkKeys() {
        boolean isKey1Valid = key1 >= 0 && key1 <= alphabet.size() - 1;
        boolean isKey2Valid = key2 >= 0 && key2 <= alphabet.size() - 1;
        boolean isKey3Valid = key3 >= 0 && key3 <= alphabet.size() - 1;

        if (!isKey1Valid || !isKey2Valid || !isKey3Valid) {
            System.out.println("KEYS ARE NOT VALID");
            System.exit(-1);
        }

        writeKeys();
    }

    private static void writeKeys() {
        try {
            File file = new File("D:\\Progs\\JAVA\\2017\\1\\DataProtection" +
                    "\\xor-cipher\\res\\keys.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(String.valueOf(key1));
            fileWriter.write("\n");
            fileWriter.write(String.valueOf(key2));
            fileWriter.write("\n");
            fileWriter.write(String.valueOf(key3));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeCipherMessage(ArrayList<Integer> message) {
        try {
            File file = new File("D:\\Progs\\JAVA\\2017\\1\\DataProtection" +
                    "\\xor-cipher\\res\\encrypted.txt");
            FileWriter fileWriter = new FileWriter(file);

            for (int i = 0; i < message.size() - 1; i++) {
                fileWriter.write(String.valueOf(message.get(i)));
                fileWriter.write("\n");
            }
            fileWriter.write(String.valueOf(message.get(message.size() - 1)));

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile() {
        try {
            URL url = Resources.class.getClassLoader().getResource("input.txt");
            String input;
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getPath()))) {
                String currentLine;
                input = null;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    input = currentLine;
                }
                bufferedReader.close();
            }
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static void readAlphabet() {
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

    private static void readXorReplacementValues() {
        try {
            URL url = Resources.class.getClassLoader().getResource("xor_replacement.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getPath()));
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                xorReplacement.add(Integer.valueOf(currentLine));
                //xorReplacement.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
