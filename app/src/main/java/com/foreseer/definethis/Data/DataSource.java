package com.foreseer.definethis.Data;

import com.foreseer.definethis.Data.Entities.DefineThis.DeletedRecord;
import com.foreseer.definethis.Data.Entities.DefineThis.Word;

import java.util.List;
import java.util.Stack;

import io.realm.RealmConfiguration;

public interface DataSource {
    void setWordConfiguration(RealmConfiguration wordConfiguration);
    void setDeletedRecordsConfiguration(RealmConfiguration deletedRecordsConfiguration);

    void saveWord(Word word);
    void saveDeletedRecord(DeletedRecord deletedRecord);

    Word getWord(String word);
    List<Word> getAllWords();
    Stack<DeletedRecord> getAllDeletedRecords();

    boolean wasWordPreviouslyRequested(String word);

    void deleteWord(String word);
    void deleteAllWords();
    void deleteDeletedRecord(long id);
    void deleteAllDeletedRecords();
}
