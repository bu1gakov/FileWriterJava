import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DataRecordStep {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: Фамилия Имя Отчество дата_рождения номер_телефона пол");
        String input = scanner.nextLine();
        String[] data = input.split(" ");

        if (data.length != 6) {
            System.out.println("Error: incorrect number of parameters");
            System.exit(1);
        }

        String lastName = data[0];
        String firstName = data[1];
        String patronymic = data[2];
        String birthDate = data[3];
        String phoneNumber = data[4];
        char gender = data[5].charAt(0);

        try {
            validateBirthdate(birthDate);
            validatePhoneNumber(phoneNumber);
            validateGender(gender);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        try {
            writeToFile(lastName, firstName, patronymic, birthDate, phoneNumber, gender);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void validateBirthdate(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate.parse(birthDate, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid birthdate format. Correct format is dd.mm.yyyy");
        }
    }

    private static void validatePhoneNumber(String phoneNumber) {
        try {
            Long.parseUnsignedLong(phoneNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid phone number format. Phone number should be an unsigned integer");
        }
    }

    private static void validateGender(char gender) {
        if (gender != 'f' && gender != 'm') {
            throw new IllegalArgumentException("Invalid gender format. Gender should be either f or m");
        }
    }

    private static void writeToFile(String firstName, String lastName, String patronymic, String birthDate, String phoneNumber, char gender) throws IOException {
        File file = new File(firstName + ".txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(String.format("%s %s %s %s %s %c", firstName, lastName, patronymic, birthDate, phoneNumber, gender));
            writer.newLine();
        }
    }
}