package com.foreseer.definethis.Data.Entities.DefineThis;

import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Word {

    private String word;

    private List<String> phonetics;
    private List<Definition> definitions;

    private Date date;

    public Word(String word, List<String> phonetics, List<Definition> definitions, Date date) {
        this.word = word;
        this.phonetics = phonetics;
        this.definitions = definitions;
        this.date = date;
    }

    public Word() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<String> phonetics) {
        this.phonetics = phonetics;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Word word1 = (Word) o;

        return Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {

        return Objects.hash(word);
    }
}
