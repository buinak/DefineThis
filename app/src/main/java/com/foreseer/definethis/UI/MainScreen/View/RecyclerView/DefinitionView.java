package com.foreseer.definethis.UI.MainScreen.View.RecyclerView;

/**
 * Created by Foreseer on 02-Oct-18.
 */

public interface DefinitionView {
    void setDefinitionText(String text);
    void setExamples(String text);
    void setPartOfSpeech(String text);

    void hideExamples();
}
