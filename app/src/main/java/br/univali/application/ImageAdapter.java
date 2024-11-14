package br.univali.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final Context context;
    private final List<Integer> images;
    private final NavController navController;  // NavController para navegar entre os Fragments

    public ImageAdapter(Context context, NavController navController, List<Integer> images) {
        this.context = context;
        this.navController = navController;
        this.images = images;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(images.get(position));

        // Adicionando o clique na imagem para navegar
        holder.imageView.setOnClickListener(v -> {
            if (position == 0) {
                navController.navigate(R.id.action_firstFragment_to_ongViewProfileFragment);
            } else if (position == 1) {
                navController.navigate(R.id.action_firstFragment_to_ongViewProfileFragment);
            } else if (position == 2) {
                navController.navigate(R.id.action_firstFragment_to_ongViewProfileFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
