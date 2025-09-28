package Task1;

import java.io.File;
import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) {
        File romeoAndJuliet = new File("romeo-and-juliet.txt");

        try (FileInputStream fis = new FileInputStream(romeoAndJuliet)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String romeoAndJulietText = new String(buffer);

            String cleanedText = cleanText(romeoAndJulietText);
            String[] words = cleanedText.split(" ");
            int maxLength = 0;
            String wordMaxLength = "";
            for (String word : words) {
                if (word.length() > maxLength) {
                    maxLength = word.length();
                    wordMaxLength = word;
                }
            }
            System.out.println(wordMaxLength);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String cleanText(String text) {
        return text.replaceAll("[^A-z\\s]", "").replaceAll("\\s+", " ");
    }
}
