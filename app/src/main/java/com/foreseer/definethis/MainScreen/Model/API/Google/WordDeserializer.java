package com.foreseer.definethis.MainScreen.Model.API.Google;

import com.foreseer.definethis.MainScreen.Model.API.Google.JSONSchemaGoogle.Definition;
import com.foreseer.definethis.MainScreen.Model.API.Google.JSONSchemaGoogle.Word;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordDeserializer implements JsonDeserializer<Word> {

    @Override
    public Word deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String jsonString = json.toString();

        JsonObject objectWord = json.getAsJsonObject();
        String wordString = objectWord.get("word").getAsString();
        JsonArray phoneticArray = objectWord.get("phonetic").getAsJsonArray();
        List<String> phonetics = new ArrayList<>(phoneticArray.size());
        for (int i = 0; i < phoneticArray.size(); i++) {
            phonetics.add(phoneticArray.get(i).getAsString());
        }
        List<Definition> definitions = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : objectWord.getAsJsonObject("meaning").entrySet()){
            String partOfSpeech = entry.getKey();
            JsonArray array = entry.getValue().getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                Definition definition = new Definition();
                JsonObject object = array.get(i).getAsJsonObject();
                definition.setDefinition(object.get("definition").getAsString());
                JsonElement example = object.get("example");
                if (example != null){
                    definition.setExample(object.get("example").getAsString());
                }
                definition.setPartOfSpeech(partOfSpeech);
                JsonElement synonymElement = object.get("synonyms");
                if (synonymElement != null) {
                    JsonArray synonyms = synonymElement.getAsJsonArray();
                    List<String> synonymList = new ArrayList<>(synonyms.size());
                    for (int j = 0; j < synonyms.size(); j++) {
                        synonymList.add(synonyms.get(j).getAsString());
                    }
                    definition.setSynonyms(synonymList);
                }
                definitions.add(definition);
            }
        }
        return new Word(jsonString, wordString, phonetics, definitions);
    }
}