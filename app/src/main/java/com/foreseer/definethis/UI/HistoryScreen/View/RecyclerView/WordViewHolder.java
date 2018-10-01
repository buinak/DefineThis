package com.foreseer.definethis.UI.HistoryScreen.View.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_word)
    TextView textViewWord;

    @BindView(R.id.textView_date)
    TextView textViewDate;

    public WordViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bindWord(Word word){
        textViewWord.setText(word.getWord());
        textViewDate.setText(formatDate(word.getDate()));
    }

    private String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
        String newDate = format.format(date);
        return newDate;
    }
}
