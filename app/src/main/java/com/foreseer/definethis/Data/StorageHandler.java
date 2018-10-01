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

public class StorageHandler {

    public static void save(Word word){
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmWord realmWord = getRealmWord(word);

            realm.copyToRealm(realmWord);
            realm.commitTransaction();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

    }

    @NonNull
    private static RealmWord getRealmWord(Word word) {
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
    private static Word getWord(RealmWord realmWord){
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

    public static List<Word> getAllWords(){
        Realm realm = null;
        List<Word> result = new ArrayList<>();
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<RealmWord> results = realm.where(RealmWord.class).findAll();
            for (RealmWord realmWord :
                    results) {
                result.add(getWord(realmWord));
            }
            realm.commitTransaction();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return result;
    }

    public static boolean wasWordPreviouslyRequested(String word){
//        if (Word.find(Word.class, "word = ?", word).size() == 0){
//            return false;
//        }
        return false;
    }

    public static Word getWord(String word){
//        if (!wasWordPreviouslyRequested(word)){
//            return null;
//        }
//
//        Word savedWord = Word.find(Word.class, "word = ?", word).get(0);
//        JsonObject object = new JsonParser().parse(savedWord.getJsonWord()).getAsJsonObject();
//        Word result =
//                new WordDeserializer().deserialize(object, null, null);
        return null;
    }

    public static void resetAllHistory(){
//        Word.deleteAll(Word.class);
    }
}
