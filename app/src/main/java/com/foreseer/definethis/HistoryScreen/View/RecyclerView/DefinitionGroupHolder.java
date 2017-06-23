package com.foreseer.definethis.HistoryScreen.View.RecyclerView;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foreseer.definethis.MainScreen.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.R;
import com.foreseer.definethis.Storage.Models.Word;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Konstantin "Foreseer" Buinak on 23.06.2017.
 */

public class DefinitionGroupHolder extends ChildViewHolder {

    @BindView(R.id.textView_partOfSpeech_history)
    TextView textViewPartOfSpeech;

    @BindView(R.id.textView_definition_history)
    TextView textViewDefinition;

    @BindView(R.id.view_separator_history)
    View separator;

    public DefinitionGroupHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void onBind(Definition definition){
        // old size : 34
        textViewPartOfSpeech.setText(definition.getPartOfSpeech());

        // old size : 22
        textViewDefinition.setText(definition.getDefinition());

    }
}
