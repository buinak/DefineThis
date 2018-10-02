package com.foreseer.definethis.UI.MainScreen.View.RecyclerView;

import com.foreseer.definethis.Data.Models.Definition;

import java.util.List;

/**
 * Created by Foreseer on 02-Oct-18.
 */

public class DefinitionModelImpl implements DefinitionModel {

    private List<Definition> definitions;

    public DefinitionModelImpl(List<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public int getNumberOfElements() {
        return definitions.size();
    }

    @Override
    public Definition getElement(int position) {
        return definitions.get(position);
    }
}
