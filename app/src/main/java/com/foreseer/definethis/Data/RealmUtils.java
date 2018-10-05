package com.foreseer.definethis.Data;

import android.support.annotation.NonNull;

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

import io.realm.RealmList;

public class RealmUtils {

    private RealmUtils () {}

    @NonNull
    public static RealmWord modelWordToRealmWord(Word word) {
        RealmWord realmWord = new RealmWord();
        realmWord.setWord(word.getWord());
        realmWord.setDate(word.getDate());

        RealmList<RealmDefinition> realmDefinitions = new RealmList<>();
        for (Definition definition :
                word.getDefinitions()) {
            RealmDefinition realmDefinition = new RealmDefinition();
            realmDefinition.setDefinition(definition.getDefinition());
            realmDefinition.setExample(definition.getExample());
            realmDefinition.setPartOfSpeech(definition.getPartOfSpeech());
            RealmList<RealmSynonym> synonyms = new RealmList<>();
            if (definition.getSynonymList() != null) {
                for (String synonym :
                        definition.getSynonymList()) {
                    synonyms.add(new RealmSynonym(synonym));
                }
            }
            realmDefinition.setSynonymRealmList(synonyms);
            realmDefinitions.add(realmDefinition);
        }
        realmWord.setDefinitionRealmList(realmDefinitions);

        RealmList<RealmPhonetic> realmPhonetics = new RealmList<>();
        for (String phonetic :
                word.getPhonetics()) {
            realmPhonetics.add(new RealmPhonetic(phonetic));
        }
        realmWord.setPhoneticRealmList(realmPhonetics);
        return realmWord;
    }

    @NonNull
    public static Word realmWordToModelWord(RealmWord realmWord){
        Word result = new Word();
        result.setWord(realmWord.getWord());
        result.setDate(realmWord.getDate());

        List<String> phonetics = new ArrayList<>();
        for (RealmPhonetic realmPhonetic :
                realmWord.getPhoneticRealmList()) {
            phonetics.add(realmPhonetic.getPhonetic());
        }
        result.setPhonetics(phonetics);

        List<Definition> definitions = new ArrayList<>();
        for (RealmDefinition realmDefinition :
                realmWord.getDefinitionRealmList()){
            Definition definition = new Definition();
            definition.setPartOfSpeech(realmDefinition.getPartOfSpeech());
            definition.setDefinition(realmDefinition.getDefinition());
            definition.setExample(realmDefinition.getExample());
            List<String> synonyms = new ArrayList<>();
            for (RealmSynonym realmSynonym :
                    realmDefinition.getSynonymRealmList()) {
                synonyms.add(realmSynonym.getSynonym());
            }
            definition.setSynonymList(synonyms);
            definitions.add(definition);
        }
        result.setDefinitions(definitions);
        return result;
    }

    @NonNull
    public static DeletedRecord realmDeletedRecordToRecord(RealmDeletedRecord record){
        List<Word> words = new ArrayList<>(record.getWords().size());
        for (RealmWord word :
                record.getWords()) {
            words.add(realmWordToModelWord(word));
        }
        DeletedRecord result = new DeletedRecord(record.getId(), words);
        return result;
    }

    @NonNull
    public static RealmDeletedRecord deletedRecordToRealmDeletedRecord(DeletedRecord record){
        RealmList<RealmWord> realmList = new RealmList<>();
        for (Word word :
                record.getWords()) {
            realmList.add(modelWordToRealmWord(word));
        }
        RealmDeletedRecord realmRecord = new RealmDeletedRecord(record.getId(), realmList);
        return realmRecord;
    }
}
