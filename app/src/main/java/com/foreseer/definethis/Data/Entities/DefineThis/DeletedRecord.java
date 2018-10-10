package com.foreseer.definethis.Data.Entities.DefineThis;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeletedRecord {
    private long id;
    private List<Word> words;

    public DeletedRecord(List<Word> words) {
        id = UUID.randomUUID().getMostSignificantBits();
        this.words = words;
    }

    public DeletedRecord(Word word){
        id = UUID.randomUUID().getMostSignificantBits();
        words = new ArrayList<>();
        words.add(word);
    }

    public DeletedRecord(long id, List<Word> words) {
        this.id = id;
        this.words = words;
    }

    public DeletedRecord() {
    }

    public List<Word> getWords() {
        return words;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
