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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import br.univali.application.databinding.FragmentOngViewProfileBinding;

public class OngViewProfileFragment extends Fragment {

    private FragmentOngViewProfileBinding binding;
    private String ongNome;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOngViewProfileBinding.inflate(inflater, container, false);

        // Receber o ID da ONG do argumento
        if (getArguments() != null) {
            ongNome = getArguments().getString("ongNome", "Nome não disponível");
        }

        // Verificar se o nome da ONG foi recebido corretamente
        if (ongNome != null) {
            // Atualiza a UI com o nome da ONG
            binding.ongName.setText(ongNome);
        } else {
            mostrarErro();  // Exibir erro caso o nome não seja válido
        }

        // Exemplo de como configurar a imagem e os textos de forma fixa
        binding.ongProfileImage.setImageResource(R.drawable.rock1);  // Imagem fixa da ONG
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

    private void carregarDadosDaOng(String documentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Busca o documento diretamente pelo ID fornecido (agora tratado como String)
        db.collection("usuarios")
                .document(documentId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot document = task.getResult();

                        if (document.exists()) {
                            // Obtém os dados do documento
                            String nome = document.getString("nome");
                            String bio = document.getString("bio");

                            // Atualiza os elementos da interface
                            binding.ongName.setText(nome != null ? nome : "Nome não disponível");
                            binding.ongAbout.setText(bio != null ? bio : "Descrição não disponível");

                            // Usa uma imagem placeholder fixa
                            binding.ongProfileImage.setImageResource(R.drawable.rock1);
                        } else {
                            // Caso o documento não exista, exibe informações padrão
                            mostrarDadosPadrao();
                        }
                    } else {
                        // Caso a consulta falhe, exibe informações padrão
                        mostrarDadosPadrao();
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    // Exibe informações padrão em caso de erro
                    mostrarDadosPadrao();
                });
    }

    private void mostrarErro() {
        binding.ongName.setText("Erro: ONG não encontrada");
        binding.ongAbout.setText("Descrição não disponível");
        binding.ongProfileImage.setImageResource(R.drawable.rock1);  // Imagem placeholder de erro
    }

    private void mostrarDadosPadrao() {
        binding.ongName.setText("ONG não encontrada");
        binding.ongAbout.setText("Descrição não disponível");
        binding.ongProfileImage.setImageResource(R.drawable.rock1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
