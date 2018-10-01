package com.foreseer.definethis.Storage.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class Word extends SugarRecord implements Parcelable {

    String word;
    String jsonWord;
    Date date;

    public Word(){}

    public Word(String word, String jsonWord, Date date) {
        this.word = word;
        this.jsonWord = jsonWord;
        this.date = date;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getJsonWord() {
        return jsonWord;
    }

    public void setJsonWord(String jsonWord) {
        this.jsonWord = jsonWord;
    }

    protected Word(Parcel in) {
        word = in.readString();
        jsonWord = in.readString();
        date = new Date(Date.parse(in.readString()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(jsonWord);
        dest.writeString(date.toString());
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
