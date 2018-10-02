package com.foreseer.definethis.UI.WordInformationScreen.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.foreseer.definethis.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DefinitionHolder extends RecyclerView.ViewHolder implements DefinitionRecyclerViewContract.DefinitionView {

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

    @Override
    public void setDefinitionText(String text) {
        textViewDefinition.setText(text);
    }

    @Override
    public void setExamples(String text) {
        textViewExamples.setText(text);
    }

    @Override
    public void setPartOfSpeech(String text) {
        textViewPartOfSpeech.setText(text);
    }

    @Override
    public void hideExamples() {
        textViewExamples.setVisibility(View.GONE);
    }
}
