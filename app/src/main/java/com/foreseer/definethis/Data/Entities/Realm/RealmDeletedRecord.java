package com.foreseer.definethis.Data.Entities.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmDeletedRecord extends RealmObject {

    @PrimaryKey
    long id;

    private RealmList<RealmWord> words;

    public RealmDeletedRecord(long id, RealmList<RealmWord> words) {
        this.id = id;
        this.words = words;
    }

    public RealmDeletedRecord() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RealmList<RealmWord> getWords() {
        return words;
    }

    public void setWords(RealmList<RealmWord> words) {
        this.words = words;
    }
}
