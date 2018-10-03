package com.foreseer.definethis.UI.WordInformationScreen;

import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.UI.WordInformationScreen.RecyclerView.DefinitionAdapter;
import com.foreseer.definethis.UI.WordInformationScreen.RecyclerView.DefinitionModelImpl;
import com.foreseer.definethis.UI.WordInformationScreen.RecyclerView.DefinitionPresenterImpl;

public class WordInformationPresenterImpl implements WordInformationContract.WordInformationPresenter,
        WordInformationContract.WordInformationModel.Listener {


    private WordInformationContract.WordInformationModel model;
    private WordInformationContract.WordInformationView view;

    public WordInformationPresenterImpl(WordInformationContract.WordInformationView view, String data) {
        this.view = view;
        model = new WordInformationModelImpl(this);

        model.requestWord(data);
    }

    @Override
    public void onWordReceived(Word word) {
        view.setWordViewText(word.getWord());
        view.setPhoneticsViewText(word.getPhonetics().get(0));
        view.setUpRecyclerView();
        view.setRecyclerViewAdapter(new DefinitionAdapter(
                new DefinitionPresenterImpl(
                        new DefinitionModelImpl(
                                word.getDefinitions()))));
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
        view = null;
        model = null;
    }
}
