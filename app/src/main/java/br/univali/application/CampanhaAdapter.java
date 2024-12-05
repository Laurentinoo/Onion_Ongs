package br.univali.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CampanhaAdapter extends RecyclerView.Adapter<CampanhaAdapter.CampanhaViewHolder> {

    private final Context context;
    private final NavController navController;
    private final List<Campanha> campanhas;
    private OnItemClickListener onItemClickListener;

    // Interface para gerenciar o clique
    public interface OnItemClickListener {
        void onItemClick(Campanha campanha);
    }

    // Construtor do adapter
    public CampanhaAdapter(Context context, NavController navController, List<Campanha> campanhas) {
        this.context = context;
        this.navController = navController;
        this.campanhas = campanhas;
    }

    // Configura o listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public CampanhaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_campanha, parent, false);
        return new CampanhaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CampanhaViewHolder holder, int position) {
        Campanha campanha = campanhas.get(position);

        // Configura os dados na interface
        holder.nomeTextView.setText(campanha.getNome());
        holder.descricaoTextView.setText(campanha.getDescricao());
        holder.imagemView.setImageResource(campanha.getImagem());

        // Configura a navegação (caso necessário)
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(campanha);
            }
        });
    }

    @Override
    public int getItemCount() {
        return campanhas.size();
    }

    static class CampanhaViewHolder extends RecyclerView.ViewHolder {
        TextView nomeTextView;
        TextView descricaoTextView;
        ImageView imagemView;

        public CampanhaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.campanha_nome);
            descricaoTextView = itemView.findViewById(R.id.campanha_descricao);
            imagemView = itemView.findViewById(R.id.campanha_imagem);
        }
    }
}