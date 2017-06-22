package com.foreseer.definethis.HistoryScreen.View.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foreseer.definethis.Storage.Models.Word;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class Adapter /*extends RecyclerView.Adapter<Adapter.WordHolder> */{

    private List<Word> words;

    public Adapter(List<Word> words) {
        this.words = words;
    }

    /*@Override
    public Adapter.WordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View inflatedView = LayoutInflater.from(parent.getContext());
        return null;
    }

    @Override
    public void onBindViewHolder(Adapter.WordHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return words.size();
    }*/

    public class WordHolder {
    }
}
