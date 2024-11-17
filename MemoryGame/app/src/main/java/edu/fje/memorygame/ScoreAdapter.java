package edu.fje.memorygame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private List<ScoreItem> scoreList;

    public ScoreAdapter(List<ScoreItem> scoreList) {
        this.scoreList = scoreList;
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {
        ScoreItem scoreItem = scoreList.get(position);
        holder.scoreText.setText(String.valueOf(scoreItem.getScore()));
        holder.dateText.setText(scoreItem.getDate());
        holder.playTimeText.setText(scoreItem.getPlayTime());
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder{
        TextView scoreText;
        TextView dateText;
        TextView playTimeText;

        public ScoreViewHolder(View itemView){
            super(itemView);
            scoreText = itemView.findViewById(R.id.scoreTextView);
            dateText = itemView.findViewById(R.id.dateTextView);
            playTimeText = itemView.findViewById(R.id.timeTextView);
        }
    }
}
