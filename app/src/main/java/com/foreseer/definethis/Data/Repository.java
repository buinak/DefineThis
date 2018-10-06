package com.foreseer.definethis.Data;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.foreseer.definethis.Application.DefineThisApplication;
import com.foreseer.definethis.Data.Models.Definition;
import com.foreseer.definethis.Data.Models.DeletedRecord;
import com.foreseer.definethis.Data.Models.Realm.RealmDefinition;
import com.foreseer.definethis.Data.Models.Realm.RealmDeletedRecord;
import com.foreseer.definethis.Data.Models.Realm.RealmPhonetic;
import com.foreseer.definethis.Data.Models.Realm.RealmSynonym;
import com.foreseer.definethis.Data.Models.Realm.RealmWord;
import com.foreseer.definethis.Data.Models.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class Repository {

    private static RealmConfiguration wordConfiguration;
    private static RealmConfiguration deletedRecordsConfiguration;

    public static void setWordConfiguration(RealmConfiguration wordConfiguration) {
        Repository.wordConfiguration = wordConfiguration;
    }

    public static void setDeletedRecordsConfiguration(RealmConfiguration deletedRecordsConfiguration) {
        Repository.deletedRecordsConfiguration = deletedRecordsConfiguration;
    }

    public static void saveWord(Word word) {

        if (isWordCachedInWordDatabase(word.getWord())) {
            return;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> {
                RealmWord realmWord = RealmUtils.modelWordToRealmWord(word);
                realm.copyToRealm(realmWord);
            });
        }
    }

    public static void saveDeletedRecord(DeletedRecord deletedRecord){
        try (Realm realm = getDeletedRecordsInstance()){
            realm.executeTransaction(r -> {
                RealmDeletedRecord record = RealmUtils.deletedRecordToRealmDeletedRecord(deletedRecord);
                realm.copyToRealm(record);
            });
        }
    }

    public static List<Word> getAllWords() {
        Realm realm = null;
        List<Word> result = new ArrayList<>();
        try {
            realm = getWordInstance();
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
    public static Stack<DeletedRecord> getAllDeletedRecords() {
        Realm realm = null;
        List<DeletedRecord> result = new ArrayList<>();
        try {
            realm = getDeletedRecordsInstance();
            realm.beginTransaction();
            RealmResults<RealmDeletedRecord> results = realm.where(RealmDeletedRecord.class).findAll();
            for (RealmDeletedRecord realmDeletedRecord :
                    results) {
                result.add(RealmUtils.realmDeletedRecordToRecord(realmDeletedRecord));
            }
            realm.commitTransaction();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        Stack<DeletedRecord> resultStack = new Stack();
        resultStack.addAll(result);
        return resultStack;
    }

    public static boolean wasWordPreviouslyRequested(String word) {
        return isWordCachedInDeletedRecordsDatabase(word) ||
                isWordCachedInWordDatabase(word);
    }

    private static boolean isWordCachedInWordDatabase(String word) {
        boolean result = false;
        Realm realm = null;
        try {
            realm = getWordInstance();
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

    private static boolean isWordCachedInDeletedRecordsDatabase(String word) {
        boolean result = false;
        Realm realm = null;
        try {
            realm = getDeletedRecordsInstance();
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

        if (isWordCachedInWordDatabase(word)){
            return getWordFromWordDatabase(word);
        } else {
            return getWordFromDeletedRecordsDatabase(word);
        }
    }

    @NonNull
    private static Word getWordFromWordDatabase(String word) {
        Realm realm = null;
        Word resultWord;
        try {
            realm = getWordInstance();
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

    @NonNull
    private static Word getWordFromDeletedRecordsDatabase(String word) {
        Realm realm = null;
        Word resultWord;
        try {
            realm = getDeletedRecordsInstance();
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

    public static void deleteAllWords() {
        try (Realm realm = getWordInstance()) {
            realm.executeTransaction(r -> {
                RealmResults<RealmWord> results = r.where(RealmWord.class).findAll();
                results.deleteAllFromRealm();
            });
        }
    }

    public static void deleteWord(String word){
        try (Realm realm = getWordInstance()){
            realm.executeTransaction(r -> {
                RealmResults<RealmWord> results = r.where(RealmWord.class).equalTo("word", word).findAll();
                results.deleteAllFromRealm();
            });
        }
    }

    public static void removeDeletedRecord(long id){
        try (Realm realm = getDeletedRecordsInstance()){
            realm.executeTransaction(r -> {
                RealmResults<RealmDeletedRecord> results = r.where(RealmDeletedRecord.class).equalTo("id", id).findAll();
                results.deleteAllFromRealm();
            });
        }
    }

    public static void deleteAllDeletedRecords(){
        try (Realm realm = getDeletedRecordsInstance()){
            realm.executeTransaction(r -> {
//                RealmResults<RealmDeletedRecord> results = r.where(RealmDeletedRecord.class).findAll();
//                results.deleteAllFromRealm();
                r.deleteAll();
            });
        }
    }

    private static Realm getWordInstance(){
        return Realm.getInstance(wordConfiguration);
    }

    private static Realm getDeletedRecordsInstance(){
        return Realm.getInstance(deletedRecordsConfiguration);
    }
}
