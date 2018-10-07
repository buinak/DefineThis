package com.foreseer.definethis.Data.Entities.Realm;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmWord extends RealmObject {

    private String word;

    private RealmList<RealmPhonetic> phoneticRealmList;
    private RealmList<RealmDefinition> definitionRealmList;

    private Date date;

    public RealmWord(String word, RealmList<RealmPhonetic> phonetic, RealmList<RealmDefinition> definitions) {
        this.word = word;
        this.phoneticRealmList = phonetic;
        this.definitionRealmList = definitions;
    }

    public RealmWord(String word, RealmList<RealmPhonetic> phonetic, RealmList<RealmDefinition> definitions, Date date) {
        this.word = word;
        this.phoneticRealmList = phonetic;
        this.definitionRealmList = definitions;
        this.date = date;
    }

    public RealmWord() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public RealmList<RealmPhonetic> getPhoneticRealmList() {
        return phoneticRealmList;
    }

    public void setPhoneticRealmList(RealmList<RealmPhonetic> phoneticRealmList) {
        this.phoneticRealmList = phoneticRealmList;
    }

    public RealmList<RealmDefinition> getDefinitionRealmList() {
        return definitionRealmList;
    }

    public void setDefinitionRealmList(RealmList<RealmDefinition> definitionRealmList) {
        this.definitionRealmList = definitionRealmList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}