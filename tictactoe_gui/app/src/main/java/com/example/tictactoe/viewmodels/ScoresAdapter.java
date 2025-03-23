package com.example.tictactoe.viewmodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tictactoe.R;
import com.example.tictactoe.models.Score;

import java.util.ArrayList;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder> {

    private final ArrayList<Score> scores;

    public ScoresAdapter(ArrayList<Score> scores) {
        this.scores = scores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Score score = scores.get(position);

        TextView dateView = holder.itemView.findViewById(R.id.cardViewDate);
        String dateText = "Date: " + score.date;
        dateView.setText(dateText);

        TextView playersView = holder.itemView.findViewById(R.id.cardVeiwPlayers);
        String playersText = "Players: " + score.players;
        playersView.setText(playersText);

        TextView winnerView = holder.itemView.findViewById(R.id.cardVeiwWhoWon);
        String winnerText = "Winner: " + score.winner;
        winnerView.setText(winnerText);

    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
