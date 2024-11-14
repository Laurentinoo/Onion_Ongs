package br.univali.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.univali.application.databinding.FragmentOngViewProfileBinding;

public class OngViewProfileFragment extends Fragment {

    private FragmentOngViewProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOngViewProfileBinding.inflate(inflater, container, false);

        // Exemplo de como configurar a imagem e os textos de forma fixa
        binding.ongProfileImage.setImageResource(R.drawable.rock1);  // Imagem fixa da ONG
        binding.ongName.setText("Nome da ONG");
        binding.ongAbout.setText("Resumo sobre a ONG");

        Button btnInscreverSe = binding.btn;
        btnInscreverSe.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_ongViewProfileFragment_to_inscricaoFragment);
        });

        RecyclerView recyclerView = binding.recyclerViewComments;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("João", 4.5f, "Ótima ONG, ajudam bastante!"));
        comments.add(new Comment("Maria", 5f, "Muito comprometidos com o meio ambiente."));
        comments.add(new Comment("Lucas", 3f, "Trabalho bom, mas pode melhorar em alguns aspectos."));
        comments.add(new Comment("Victor", 0.5f, "Péssima ONG, me fizeram esperar numa fila infinita!"));

        CommentAdapter adapter = new CommentAdapter(comments);
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
