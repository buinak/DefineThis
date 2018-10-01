package com.foreseer.definethis.UI.MainScreen.View.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foreseer.definethis.Data.Models.Definition;
import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.DefinitionHolder> {

    private Word word;

    private List<Definition> definitions;

    public DefinitionAdapter(Word word) {
        this.word = word;
        definitions = word.getDefinitions();
    }

    @Override
    public DefinitionAdapter.DefinitionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_definition_item, parent, false);
        return new DefinitionHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(DefinitionAdapter.DefinitionHolder holder, int position) {
        Definition definition = definitions.get(position);
        holder.bindDefinition(definition);
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    public class DefinitionHolder extends RecyclerView.ViewHolder{

        private Definition definition;

        @BindView(R.id.textView_definition)
        TextView textViewDefinition;

        @BindView(R.id.textView_partOfSpeech)
        TextView textViewPartOfSpeech;

        public DefinitionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindDefinition(Definition definition){
            this.definition = definition;

            textViewDefinition.setText(definition.getDefinition());

            if (definition.getPartOfSpeech() != null) {
                textViewPartOfSpeech.setText(definition.getPartOfSpeech());
            } else if (definition.getDefinition().contains("abbreviation")){
                textViewPartOfSpeech.setText("abbreviation");
            } else {
                textViewPartOfSpeech.setText("unknown");
            }
        }
    }
}
