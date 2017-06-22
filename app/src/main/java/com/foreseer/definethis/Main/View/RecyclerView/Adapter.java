package com.foreseer.definethis.Main.View.RecyclerView;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.foreseer.definethis.Main.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.codetail.widget.RevealLinearLayout;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.DefinitionHolder> {

    private List<Definition> definitions;

    public Adapter(List<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public Adapter.DefinitionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_definition_item, parent, false);
        return new DefinitionHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(Adapter.DefinitionHolder holder, int position) {
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

            // Detached view error
            /*revealLayout(revealLinearLayoutPartOfSpeech);
            revealLayout(revealLinearLayoutDefinition);*/
        }

        // detached view error, find alternative or a solution?
        private void revealLayout(RevealLinearLayout layout){
            final int childCount = layout.getChildCount();
            for (int i = 0; i < childCount; i++){
                View view = layout.getChildAt(i);
                revealView(view);
            }
        }

        private void revealView(View view){
            // get the center for the clipping circle
            int cx = (view.getLeft() + view.getRight()) / 2;
            int cy = (view.getTop() + view.getBottom()) / 2;

            // get the final radius for the clipping circle
            int dx = Math.max(cx, view.getWidth());
            int dy = Math.max(cy, view.getHeight());
            float finalRadius = (float) Math.hypot(dx, dy);

            // Android native animator
            Animator animator =
                    ViewAnimationUtils.createCircularReveal(view, view.getLeft(), view.getTop(), 0, finalRadius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(500);
            animator.start();
        }
    }
}
