package br.univali.application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder> {

    private final List<String> candidateNames;

    public CandidateAdapter(List<String> candidateNames) {
        this.candidateNames = candidateNames;
    }

    @NonNull
    @Override
    public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new CandidateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateViewHolder holder, int position) {
        holder.nameTextView.setText(candidateNames.get(position));
    }

    @Override
    public int getItemCount() {
        return candidateNames.size();
    }

    static class CandidateViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public CandidateViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}
