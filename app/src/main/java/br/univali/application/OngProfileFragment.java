package br.univali.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import br.univali.application.databinding.FragmentOngProfileBinding;

public class OngProfileFragment extends Fragment {

    private FragmentOngProfileBinding binding;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOngProfileBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance();

        // Recuperar o ID da ONG armazenado nas preferências
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
        String ongId = preferences.getString("USER_ID", ""); // Pega o ID da ONG armazenado

        // Exibir o nome da ONG na interface
        if (!ongId.isEmpty()) {
            db.collection("usuarios").document(ongId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String ongName = document.getString("nome");
                                String ongAbout = document.getString("bio");

                                // Preencher os campos do perfil da ONG
                                binding.ongName.setText(ongName);
                                binding.ongAbout.setText(ongAbout);
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        binding.ongAbout.setText("Erro ao carregar perfil da ONG.");
                    });
        }

        Button btnEditarPerfil = binding.btnEditProfile;
        btnEditarPerfil.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_ongProfileFragment_to_editOngProfileFragment);
        });

        Button btnVerCandidatos = binding.btnViewCandidates;
        btnVerCandidatos.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_ongProfileFragment_to_candidatesListFragment);
        });

        Button btnCriarCampanha = binding.btnCreateCampaign;
        btnCriarCampanha.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_ongProfileFragment_to_createCampaignFragment);
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
