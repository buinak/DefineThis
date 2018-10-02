package com.foreseer.definethis.UI.HistoryScreen.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.R;

import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<WordViewHolder> {


    private List<Word> wordList;

    public HistoryRecyclerViewAdapter(List<Word> wordList) {
        this.wordList = wordList;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_history_word_item, parent, false);

        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.setOnClickListener();
        holder.bindWord(wordList.get(position));
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }
}
