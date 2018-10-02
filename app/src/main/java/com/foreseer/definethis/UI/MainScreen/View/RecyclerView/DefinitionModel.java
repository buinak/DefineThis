package com.foreseer.definethis.UI.MainScreen.View.RecyclerView;

import com.foreseer.definethis.Data.Models.Definition;

/**
 * Created by Foreseer on 02-Oct-18.
 */


public interface DefinitionModel {
    int getNumberOfElements();
    Definition getElement(int position);
}
