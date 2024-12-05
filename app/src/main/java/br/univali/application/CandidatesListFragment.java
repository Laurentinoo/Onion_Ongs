package br.univali.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class CandidatesListFragment extends Fragment {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private CandidateAdapter adapter;
    private List<String> candidateNames;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidates_list, container, false);

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewCandidates);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        candidateNames = new ArrayList<>();
        adapter = new CandidateAdapter(candidateNames);
        recyclerView.setAdapter(adapter);

        // Inicializar o Firestore
        db = FirebaseFirestore.getInstance();

        // Buscar dados de 'inscricoes'
        fetchCandidates();

        return view;
    }

    private void fetchCandidates() {
        db.collection("inscricoes")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    candidateNames.clear(); // Limpa lista antes de adicionar novos dados
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String name = document.getString("name"); // Pega o campo 'name'
                        if (name != null) {
                            candidateNames.add(name);
                        }
                    }
                    adapter.notifyDataSetChanged(); // Atualiza o adapter com os dados
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Erro ao carregar candidatos", Toast.LENGTH_SHORT).show();
                });
    }
}
