package com.foreseer.definethis.Data.Entities.Realm;

import android.os.Parcel;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmDefinition extends RealmObject{
    private String partOfSpeech;
    private String definition;
    private String example;

    private RealmList<RealmSynonym> synonymRealmList = null;

    public RealmDefinition(String partOfSpeech, String definition, RealmList<RealmSynonym> synonyms, String example) {
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
        this.synonymRealmList = synonyms;
        this.example = example;
    }

    public RealmDefinition() {
    }

    protected RealmDefinition(Parcel in) {
        partOfSpeech = in.readString();
        definition = in.readString();
        example = in.readString();
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public RealmList<RealmSynonym> getSynonymRealmList() {
        return synonymRealmList;
    }

    public void setSynonymRealmList(RealmList<RealmSynonym> synonymRealmList) {
        this.synonymRealmList = synonymRealmList;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

}
