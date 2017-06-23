package com.foreseer.definethis.MainScreen.Model.API.JSONSchema;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Foreseer on 25/05/2017.
 * For any questions, feel free to reach me using any of my contacts.
 * Contacts:
 * e-mail (preferred): fforeseer@gmail.com
 */

public class Definition implements Parcelable {
    String definition;
    String partOfSpeech;

    public Definition(String definition, String partOfSpeech) {
        this.definition = definition;
        this.partOfSpeech = partOfSpeech;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    protected Definition(Parcel in) {
        definition = in.readString();
        partOfSpeech = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(definition);
        dest.writeString(partOfSpeech);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Definition> CREATOR = new Parcelable.Creator<Definition>() {
        @Override
        public Definition createFromParcel(Parcel in) {
            return new Definition(in);
        }

        @Override
        public Definition[] newArray(int size) {
            return new Definition[size];
        }
    };
}
