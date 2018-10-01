package com.foreseer.definethis.Data.Models.Realm;

import io.realm.RealmObject;

public class RealmPhonetic extends RealmObject {

    private String phonetic;

    public RealmPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public RealmPhonetic() {
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }
}
