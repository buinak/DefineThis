package com.foreseer.definethis.Data;

import android.support.annotation.NonNull;

import com.foreseer.definethis.Data.Models.Definition;
import com.foreseer.definethis.Data.Models.Realm.RealmDefinition;
import com.foreseer.definethis.Data.Models.Realm.RealmPhonetic;
import com.foreseer.definethis.Data.Models.Realm.RealmSynonym;
import com.foreseer.definethis.Data.Models.Realm.RealmWord;
import com.foreseer.definethis.Data.Models.Word;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class Repository {

    public static void save(Word word) {

        if (wasWordPreviouslyRequested(word.getWord())) {
            return;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> {
                RealmWord realmWord = RealmUtils.modelWordToRealmWord(word);
                realm.copyToRealm(realmWord);
            });
        }
    }

    public static List<Word> getAllWords() {
        Realm realm = null;
        List<Word> result = new ArrayList<>();
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<RealmWord> results = realm.where(RealmWord.class).findAll();
            for (RealmWord realmWord :
                    results) {
                result.add(RealmUtils.realmWordToModelWord(realmWord));
            }
            realm.commitTransaction();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return result;
    }

    public static boolean wasWordPreviouslyRequested(String word) {
        boolean result = false;
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<RealmWord> words = realm.where(RealmWord.class)
                    .equalTo("word", word)
                    .findAll();
            if (words.size() > 0) {
                result = true;
            }
            realm.commitTransaction();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return result;
    }

    public static Word getWord(String word) {
        if (!wasWordPreviouslyRequested(word)) {
            return null;
        }

        Realm realm = null;
        Word resultWord;
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<RealmWord> results = realm.where(RealmWord.class)
                    .equalTo("word", word)
                    .findAll();
            resultWord = RealmUtils.realmWordToModelWord(results.get(0));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return resultWord;
    }

    public static void resetAllHistory() {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> {
                r.deleteAll();
            });
        }
    }
}
