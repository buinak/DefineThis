package com.foreseer.definethis.Data;

import android.support.annotation.NonNull;

import com.foreseer.definethis.Data.Entities.DefineThis.DeletedRecord;
import com.foreseer.definethis.Data.Entities.Realm.RealmDeletedRecord;
import com.foreseer.definethis.Data.Entities.Realm.RealmWord;
import com.foreseer.definethis.Data.Entities.DefineThis.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class Repository implements DataSource{

    private static DataSource instance;

    private RealmConfiguration wordConfiguration;
    private RealmConfiguration deletedRecordsConfiguration;

    private Repository() {
    }

    public static DataSource getInstance(){
        if (instance == null){
            instance = new Repository();
        }
        return instance;
    }

    @Override
    public void setWordConfiguration(RealmConfiguration wordConfiguration) {
        this.wordConfiguration = wordConfiguration;
    }

    @Override
    public void setDeletedRecordsConfiguration(RealmConfiguration deletedRecordsConfiguration) {
        this.deletedRecordsConfiguration = deletedRecordsConfiguration;
    }

    @Override
    public void saveWord(Word word) {

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

    @Override
    public void saveDeletedRecord(DeletedRecord deletedRecord){
        try (Realm realm = getDeletedRecordsInstance()){
            realm.executeTransaction(r -> {
                RealmDeletedRecord record = RealmUtils.deletedRecordToRealmDeletedRecord(deletedRecord);
                realm.copyToRealm(record);
            });
        }
    }

    @Override
    public List<Word> getAllWords() {
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

    @Override
    public Stack<DeletedRecord> getAllDeletedRecords() {
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

    @Override
    public boolean wasWordPreviouslyRequested(String word) {
        return isWordCachedInDeletedRecordsDatabase(word) ||
                isWordCachedInWordDatabase(word);
    }

    private boolean isWordCachedInWordDatabase(String word) {
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

    private boolean isWordCachedInDeletedRecordsDatabase(String word) {
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

    @Override
    public Word getWord(String word) {
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
    private Word getWordFromWordDatabase(String word) {
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
    private Word getWordFromDeletedRecordsDatabase(String word) {
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

    @Override
    public void deleteAllWords() {
        try (Realm realm = getWordInstance()) {
            realm.executeTransaction(r -> {
                RealmResults<RealmWord> results = r.where(RealmWord.class).findAll();
                results.deleteAllFromRealm();
            });
        }
    }

    @Override
    public void deleteWord(String word){
        try (Realm realm = getWordInstance()){
            realm.executeTransaction(r -> {
                RealmResults<RealmWord> results = r.where(RealmWord.class).equalTo("word", word).findAll();
                results.deleteAllFromRealm();
            });
        }
    }

    @Override
    public void deleteDeletedRecord(long id){
        try (Realm realm = getDeletedRecordsInstance()){
            realm.executeTransaction(r -> {
                RealmResults<RealmDeletedRecord> results = r.where(RealmDeletedRecord.class).equalTo("id", id).findAll();
                results.deleteAllFromRealm();
            });
        }
    }

    @Override
    public void deleteAllDeletedRecords(){
        try (Realm realm = getDeletedRecordsInstance()){
            realm.executeTransaction(r -> {
//                RealmResults<RealmDeletedRecord> results = r.where(RealmDeletedRecord.class).findAll();
//                results.deleteAllFromRealm();
                r.deleteAll();
            });
        }
    }

    private Realm getWordInstance(){
        return Realm.getInstance(wordConfiguration);
    }

    private Realm getDeletedRecordsInstance(){
        return Realm.getInstance(deletedRecordsConfiguration);
    }
}
