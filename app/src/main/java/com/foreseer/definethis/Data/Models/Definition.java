package com.foreseer.definethis.Data.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Definition implements Parcelable {

    private String partOfSpeech;
    private String definition;
    private String example;

    private List<String> synonymList;

    public Definition() {
    }

    public Definition(String partOfSpeech, String definition, String example, List<String> synonymList) {
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
        this.example = example;
        this.synonymList = synonymList;
    }

    protected Definition(Parcel in) {
        partOfSpeech = in.readString();
        definition = in.readString();
        example = in.readString();
        synonymList = in.createStringArrayList();
    }

    public static final Creator<Definition> CREATOR = new Creator<Definition>() {
        @Override
        public Definition createFromParcel(Parcel in) {
            return new Definition(in);
        }

        @Override
        public Definition[] newArray(int size) {
            return new Definition[size];
        }
    };

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
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

    public List<String> getSynonymList() {
        return synonymList;
    }

    public void setSynonymList(List<String> synonymList) {
        this.synonymList = synonymList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(partOfSpeech);
        parcel.writeString(definition);
        parcel.writeString(example);
        parcel.writeValue(synonymList);
    }
}
