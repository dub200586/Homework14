package Task2;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        File docNumbers;
        String path = "";
        String validNumbersText = "";
        String invalidNumbersText = "";

        while (true) {
            try {
                System.out.println("Введите путь к файлу:");
                String filePath = sc.nextLine();
                docNumbers = new File(filePath);
                path = docNumbers.getParent();

                try (FileInputStream fis = new FileInputStream(docNumbers)) {
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    byte[] buffer = new byte[fis.available()];
                    bis.read(buffer);
                    String docNumbersText = new String(buffer);

                    String[] numbers = docNumbersText.replaceAll("\r", "").split("\\n");
                    String[] validNumbers = new String[numbers.length];
                    String[] invalidNumbers = new String[numbers.length];
                    Pattern startWithPattern = Pattern.compile("^docnum|contract");
                    Pattern otherSymbolsPattern = Pattern.compile("[^A-z\\d]+");

                    for (int i = 0; i < numbers.length; i++) {
                        if (numbers[i].length() > 15) {
                            invalidNumbers[i] = numbers[i] + " - строка больше 15 символов";
                        } else if (numbers[i].length() < 15) {
                            invalidNumbers[i] = numbers[i] + " - строка меньше 15 символов";
                        } else if (otherSymbolsPattern.matcher(numbers[i]).find()) {
                            invalidNumbers[i] = numbers[i] + " - строка содержит символы кроме букв и цифр";
                        } else if (!startWithPattern.matcher(numbers[i]).find()) {
                            invalidNumbers[i] = numbers[i] + " - строка не начинается со слова docnum или contract";
                        } else {
                            validNumbers[i] = numbers[i];
                        }
                    }

                    StringJoiner validJoiner = new StringJoiner("\n");
                    StringJoiner invalidJoiner = new StringJoiner("\n");

                    for (String num : validNumbers) {
                        if (num != null) {
                            validJoiner.add(num);
                        }
                    }

                    for (String num : invalidNumbers) {
                        if (num != null) {
                            invalidJoiner.add(num);
                        }
                    }

                    validNumbersText = validJoiner.toString();
                    invalidNumbersText = invalidJoiner.toString();
                } catch (IOException e) {
                    throw new Exception(e);
                }

                break;
            } catch (Exception e) {
                System.out.println("Файл не найден");
            }
        }

        File validNumbersFile = new File(path + "\\validNumbers.txt");
        File invalidNumbersFile = new File(path + "\\invalidNumbers.txt");

        try (FileOutputStream fos = new FileOutputStream(validNumbersFile)) {
            fos.write(validNumbersText.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileOutputStream fos = new FileOutputStream(invalidNumbersFile)) {
            fos.write(invalidNumbersText.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        sc.close();
    }
}
