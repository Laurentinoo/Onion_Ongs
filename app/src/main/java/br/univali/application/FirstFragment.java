package br.univali.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import br.univali.application.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance();

        List<ONG> ongs = new ArrayList<>();
        List<Campanha> campanhas = new ArrayList<>();

        // Buscar as ONGs no banco de dados
        db.collection("usuarios")
                .whereEqualTo("tipo", 2) // Filtra apenas as ONGs
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ONG ong = new ONG(
                                    document.getString("nome"), // Nome da ONG
                                    document.getString("bio")   // Descrição da ONG
                            );
                            ongs.add(ong);
                        }

                        // Se não houver ONGs no banco, adicione uma ONG de fallback
                        if (ongs.isEmpty()) {
                            ongs.add(new ONG("ONG Default", "Descrição da ONG Default"));
                        }

                        // Configura o adapter para o ViewPager com a lista de imagens
                        NavController navController = NavHostFragment.findNavController(this);
                        ImageAdapter ongAdapter = new ImageAdapter(requireContext(), navController, ongs);
                        binding.viewPager.setAdapter(ongAdapter);
                    }
                })
                .addOnFailureListener(e -> {
                    // Tratamento de erro, caso a consulta falhe
                    e.printStackTrace();
                });

        // Buscar as campanhas no banco de dados
        // Configura o adapter para Campanhas
        db.collection("campanhas")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String nomeCampanha = document.getString("nome");
                            String descricaoCampanha = document.getString("descricao");
                            String ongId = document.getString("ongId");

                            db.collection("usuarios").document(ongId).get().addOnSuccessListener(ongDocument -> {
                                if (ongDocument.exists()) {
                                    String nomeOng = ongDocument.getString("nome");
                                    String bioOng = ongDocument.getString("bio");

                                    Campanha campanha = new Campanha(
                                            nomeCampanha,
                                            descricaoCampanha,
                                            new ONG(nomeOng, bioOng),
                                            R.drawable.paris
                                    );
                                    campanhas.add(campanha);

                                    NavController navController = NavHostFragment.findNavController(this);
                                    CampanhaAdapter campanhaAdapter = new CampanhaAdapter(requireContext(), navController, campanhas);
                                    binding.viewPagerCampanhas.setAdapter(campanhaAdapter);
                                    // Configura o clique nos itens
                                    campanhaAdapter.setOnItemClickListener(campanhaItem -> {
                                        // Quando um item é clicado, navegue até o fragmento de detalhes da ONG
                                        Bundle bundle = new Bundle();
                                        bundle.putString("nomeOng", campanhaItem.getOng().getNome());
                                        bundle.putString("bioOng", campanhaItem.getOng().getDescricao());

                                        navController.navigate(R.id.action_firstFragment_to_ongDetailFragment, bundle);
                                    });
                                }
                            });
                        }
                    }
                })
                .addOnFailureListener(Throwable::printStackTrace);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
