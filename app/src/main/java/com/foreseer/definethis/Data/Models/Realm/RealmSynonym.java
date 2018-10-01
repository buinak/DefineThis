package com.foreseer.definethis.Data.Models.Realm;

import io.realm.RealmObject;

public class RealmSynonym extends RealmObject {

    private String synonym;

    public RealmSynonym(String synonym) {
        this.synonym = synonym;
    }

    public RealmSynonym() {
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }
}
