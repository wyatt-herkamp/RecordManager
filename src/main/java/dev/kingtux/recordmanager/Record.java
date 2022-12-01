package dev.kingtux.recordmanager;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public record Record(String title, String artist, int year, dev.kingtux.recordmanager.Record.RecordType type) {

    @Override
    public String toString() {
        return title + " by " + artist + " (" + year + ") " + type;
    }

    public void save(Path recordDir) throws IOException {
        File file = file(recordDir).toFile();
        Properties properties = new Properties();
        properties.setProperty("title", title);
        properties.setProperty("artist", artist);
        properties.setProperty("year", String.valueOf(year));
        properties.setProperty("type", type.name());

        if (!file.createNewFile()) {
            throw new IOException("File already exists");
        }
        try (FileWriter writer = new FileWriter(file)) {
            properties.store(writer, "Record");
        }
    }

    public static Record load(File file) throws IOException, MissingPropertyException {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(file)) {
            properties.load(reader);
            RecordBuilder builder = new RecordBuilder();

            builder.setTitle(getProperty(properties, "title"));
            builder.setArtist(getProperty(properties, "artist"));
            builder.setYear(Integer.parseInt(getProperty(properties, "year")));
            builder.setType(RecordType.valueOf(getProperty(properties, "type")));
            reader.close();
            return builder.createRecord();
        }

    }

    private static String getProperty(Properties properties, String key) throws MissingPropertyException {
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        } else {
            throw new MissingPropertyException(key);
        }
    }

    public Path file(Path recordsDir) {
        return recordsDir.resolve(title + ".properties");
    }

    public boolean delete(Path recordsDir) throws IOException {
        File file = file(recordsDir).toFile();
        if (file.exists()) {
            return file.delete();
        } else {
            throw new FileNotFoundException("File not found");
        }
    }

    public enum RecordType {
        Album, Single;

        public static String options() {
            StringBuilder builder = new StringBuilder("[");
            RecordType[] iterator = RecordType.values();
            for (int i = 0; i < iterator.length; i++) {
                builder.append(iterator[i].name());
                if (i != iterator.length - 1) {
                    builder.append(", ");
                }
            }
            builder.append("]");
            return builder.toString();
        }
    }
}

