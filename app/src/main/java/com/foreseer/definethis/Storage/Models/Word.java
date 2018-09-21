package com.foreseer.definethis.Storage.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class Word extends SugarRecord implements Parcelable {
    String word;
    String jsonDefinitions;

    public Word(){}

    public Word(String word, String jsonDefinitions) {
        this.word = word;
        this.jsonDefinitions = jsonDefinitions;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getJsonDefinitions() {
        return jsonDefinitions;
    }

    public void setJsonDefinitions(String jsonDefinitions) {
        this.jsonDefinitions = jsonDefinitions;
    }

    protected Word(Parcel in) {
        word = in.readString();
        jsonDefinitions = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(jsonDefinitions);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}
