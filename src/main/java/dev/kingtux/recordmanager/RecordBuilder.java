package dev.kingtux.recordmanager;

public class RecordBuilder {
    private String title;
    private String artist;
    private int year;
    private Record.RecordType type;

    public RecordBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public RecordBuilder setArtist(String  artist) {
        this.artist = artist;
        return this;
    }

    public RecordBuilder setYear(int year) {
        this.year = year;
        return this;
    }

    public RecordBuilder setType(Record.RecordType type) {
        this.type = type;
        return this;
    }

    public Record createRecord() {
        return new Record(title, artist, year, type);
    }
}