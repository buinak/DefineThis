package com.foreseer.definethis.MainScreen.Model.API.JSONSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Foreseer on 25/05/2017.
 * For any questions, feel free to reach me using any of my contacts.
 * Contacts:
 * e-mail (preferred): fforeseer@gmail.com
 */

public class Result {
    @SerializedName("datasets")
    @Expose
    private List<String> datasets = null;
    @SerializedName("headword")
    @Expose
    private String headword;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("part_of_speech")
    @Expose
    private String partOfSpeech;
    @SerializedName("senses")
    @Expose
    private List<Sense> senses = null;
    @SerializedName("url")
    @Expose
    private String url;

    public List<String> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<String> datasets) {
        this.datasets = datasets;
    }

    public String getHeadword() {
        return headword;
    }

    public void setHeadword(String headword) {
        this.headword = headword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Sense> getSenses() {
        return senses;
    }

    public void setSenses(List<Sense> senses) {
        this.senses = senses;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
