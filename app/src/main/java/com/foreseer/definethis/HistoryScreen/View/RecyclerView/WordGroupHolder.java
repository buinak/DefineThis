package com.foreseer.definethis.HistoryScreen.View.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.foreseer.definethis.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Konstantin "Foreseer" Buinak on 23.06.2017.
 */

public class WordGroupHolder extends GroupViewHolder {

    @BindView(R.id.textView_word)
    TextView textViewWord;

    public WordGroupHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setWord(ExpandableGroup group){
        // this is a bullet string literal
        String text = "\u2022 ";

        text += group.getTitle();
        textViewWord.setText(text);
    }
}
