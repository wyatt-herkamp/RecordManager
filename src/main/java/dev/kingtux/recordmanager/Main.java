package dev.kingtux.recordmanager;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("records");
        if (!file.exists()) {
            file.mkdir();
        }
        if (!file.isDirectory()) {
            System.out.println("records is not a directory");
            System.exit(1);
        }
        List<Record> records = new ArrayList<>();
        for (File f : Objects.requireNonNull(file.listFiles())) {
            try {
                records.add(Record.load(f));
            } catch (Exception e) {
                System.out.println("Failed to load " + f.getName());
                e.printStackTrace();
            }
        }
        Scanner scanner = new Scanner(System.in);
        printMenu();
        String input = scanner.nextLine().toLowerCase();
        while (!input.equals("exit")) {
            switch (input.toLowerCase()) {
                case "add" -> {
                    addRecord(scanner, records);
                }
                case "remove" -> {
                    removeRecord(scanner, records);
                }
                case "list" -> {
                    listRecords(records);
                }
                case "find_by_artist" -> {
                    findByArtist(scanner, records);
                }
                default -> {
                    System.out.println("Invalid input");
                }
            }
            printMenu();
            input = scanner.nextLine().toLowerCase();


        }


    }

    private static void findByArtist(Scanner scanner, List<Record> records) {

        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();
        records.stream().filter(record -> record.artist().equalsIgnoreCase(artist)).forEach(System.out::println);
    }

    private static void removeRecord(Scanner scanner, List<Record> records) {
        System.out.print("Enter the title of the record you want to remove: ");
        String title = scanner.nextLine();
        System.out.print("Records " + records.size());
        for (Record record : records) {
            if (record.title().equalsIgnoreCase(title)) {
                try {
                    if (record.delete()) {
                        records.remove(record);
                        System.out.println("Record removed");
                    } else {
                        System.out.println("Failed to remove record");
                    }
                } catch (IOException e) {
                    System.out.println("Failed to delete " + record.title());
                    e.printStackTrace();
                }
                return;
            }
        }
        System.out.println("No record with that title");
    }

    private static void listRecords(List<Record> records) {
        for (Record record : records) {
            System.out.println(record);
        }
    }

    public static void addRecord(Scanner scanner, List<Record> records) throws IOException {
        RecordBuilder builder = new RecordBuilder();
        System.out.print("Enter the title of the record: ");
        builder.setTitle(scanner.nextLine());
        System.out.print("Enter the artist of the record: ");
        builder.setArtist(scanner.nextLine());
        System.out.print("Enter the year of the record: ");
        builder.setYear(Integer.parseInt(scanner.nextLine()));
        System.out.print("Enter the type of the record " + Record.RecordType.options() + ": ");
        builder.setType(Record.RecordType.valueOf(scanner.nextLine()));
        Record record = builder.createRecord();
        File file = record.file();
        record.save(file);

        records.add(record);
    }

    public static void printMenu() {
        System.out.println("Add: Add a record");
        System.out.println("List: List Records");
        System.out.println("Remove: Remove a record");
        System.out.println("Find_by_artist: Find a record by artist");
        System.out.println("Exit: Exits the program");
        System.out.print("Enter a command: ");
    }
}
