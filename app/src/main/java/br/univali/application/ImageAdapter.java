package br.univali.application;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.navigation.Navigation;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final Context context;
    private final List<ONG> ongs;
    private final NavController navController;

    public ImageAdapter(Context context, NavController navController, List<ONG> ongs) {
        this.context = context;
        this.navController = navController;
        this.ongs = ongs;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ONG ong = ongs.get(position);

        // Usar a imagem de placeholder diretamente
        int imagemResId = R.drawable.rock1;  // Imagem de placeholder para as ONGs
        holder.imageView.setImageResource(imagemResId);
        holder.nomeTextView.setText(ong.getNome());

        // Configuração do clique na imagem (pode ser para navegar ao perfil da ONG)
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("ongNome", ong.getNome());  // Passando o nome da ONG
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_firstFragment_to_ongViewProfileFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return ongs.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nomeTextView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nomeTextView = itemView.findViewById(R.id.textViewOngName);
        }
    }
}
