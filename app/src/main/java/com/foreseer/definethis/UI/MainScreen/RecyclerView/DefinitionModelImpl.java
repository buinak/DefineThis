package com.foreseer.definethis.UI.MainScreen.RecyclerView;

import com.foreseer.definethis.Data.Models.Definition;
import com.foreseer.definethis.UI.MainScreen.RecyclerView.DefinitionRecyclerViewContract;

import java.util.List;

/**
 * Created by Foreseer on 02-Oct-18.
 */

public class DefinitionModelImpl implements DefinitionRecyclerViewContract.DefinitionModel {

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
