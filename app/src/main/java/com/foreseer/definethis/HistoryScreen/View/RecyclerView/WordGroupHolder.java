package com.foreseer.definethis.HistoryScreen.View.RecyclerView;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foreseer.definethis.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Konstantin "Foreseer" Buinak on 23.06.2017.
 */

public class WordGroupHolder extends GroupViewHolder {

    @BindView(R.id.layout_history_word)
    ConstraintLayout layout;

    @BindView(R.id.textView_word)
    TextView textViewWord;

    @BindView(R.id.textView_date)
    TextView textViewDate;

    public WordGroupHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setWord(ExpandableWord word) {
        // this is a bullet string literal
        String text = "\u2022 ";

        text += word.getTitle();
        textViewWord.setText(text);

        String newDate = formatDate(word.getWord().getDate());
        textViewDate.setText(newDate.toString());
    }

    private String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
        String newDate = format.format(date);
        return newDate;
    }
}
