package com.foreseer.definethis.UI.MainScreen.View.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.foreseer.definethis.Data.Models.Definition;
import com.foreseer.definethis.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DefinitionHolder extends RecyclerView.ViewHolder{

    private Definition definition;

    @BindView(R.id.textView_definition)
    TextView textViewDefinition;

    @BindView(R.id.textView_partOfSpeech)
    TextView textViewPartOfSpeech;

    @BindView(R.id.textView_examples)
    TextView textViewExamples;

    public DefinitionHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindDefinition(Definition definition){
        this.definition = definition;


        textViewDefinition.setText(definition.getDefinition());

        if (definition.getExample() != null) {
            String exampleString = "\"" + definition.getExample() + "\"";
            textViewExamples.setText(exampleString);
        } else {
            textViewExamples.setVisibility(View.GONE);
        }

        if (definition.getPartOfSpeech() != null) {
            textViewPartOfSpeech.setText(definition.getPartOfSpeech());
        }
    }
}
