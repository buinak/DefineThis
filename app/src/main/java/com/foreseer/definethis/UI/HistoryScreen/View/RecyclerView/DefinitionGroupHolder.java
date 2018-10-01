package com.foreseer.definethis.UI.HistoryScreen.View.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.foreseer.definethis.Data.Models.Definition;
import com.foreseer.definethis.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Konstantin "Foreseer" Buinak on 23.06.2017.
 */

public class DefinitionGroupHolder extends ChildViewHolder {

    @BindView(R.id.textView_partOfSpeech)
    TextView textViewPartOfSpeech;

    @BindView(R.id.textView_definition)
    TextView textViewDefinition;

    @BindView(R.id.textView_examples)
    TextView textViewExamples;

//
//    @BindView(R.id.view_separator_history)
//    View separator;

    public DefinitionGroupHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void onBind(Definition definition){
        // old size : 34
        textViewPartOfSpeech.setText(definition.getPartOfSpeech());

        // old size : 22
        textViewDefinition.setText(definition.getDefinition());

        String exampleString = "\"" + definition.getExample() + "\"";
        textViewExamples.setText(exampleString);

    }
}
