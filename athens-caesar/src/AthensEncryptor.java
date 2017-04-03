import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * AthensEncryptor cipher implementation
 */
public class AthensEncryptor {

    private static String input;

    public static void main(String[] args) {
        input = CaesarMain.readFile();
        System.out.println("Input text: " + input);

        System.out.print("a key: ");
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        System.out.print("b key: ");
        int b = in.nextInt();
        in.close();

        String encrypted = encryptAffine(a, b);
        writeKeysToFile(a, b);

        System.out.print("Encrypted text: " + encrypted);
        writeEncryptedToFile(encrypted);
    }

    private static String encryptAffine(int a, int b) {
        input = input.toLowerCase();
        StringBuilder decryptedBuilder = new StringBuilder();

        char letter;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                letter = ' ';
            } else {
                //letter = (char) ((((a * input.charAt(i)) + b) % 26) + 65);
                letter = (char) ((a * (input.charAt(i) - 'a') + b) % 26 + 'a');
            }
            decryptedBuilder.append(letter);
        }
        return decryptedBuilder.toString();
    }

    private static void writeKeysToFile(int a, int b) {
        try {
            File file = new File("D:\\Progs\\JAVA\\2017\\1\\DataProtection" +
                    "\\athens-caesar\\res\\keys.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(String.valueOf(a));
            fileWriter.write("\n");
            fileWriter.write(String.valueOf(b));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeEncryptedToFile(String encryptedText) {
        try {
            File file = new File(
                    "D:\\Progs\\JAVA\\2017\\1\\DataProtection\\athens-caesar\\res\\encrypted_affine.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(encryptedText);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
