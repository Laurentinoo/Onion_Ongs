package br.univali.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.FirebaseFirestore;

import br.univali.application.R;
import br.univali.application.Inscricao;
import br.univali.application.databinding.FragmentInscriptionBinding;

public class InscriptionFragment extends Fragment {

    private EditText editTextName, editTextEmail, editTextCpf;
    private Button btnSubmit;
    private FirebaseFirestore db;
    private FragmentInscriptionBinding binding;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscription, container, false);
        binding = FragmentInscriptionBinding.inflate(inflater, container, false);

        // Inicializando os campos
        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextCpf = view.findViewById(R.id.editTextCpf);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        // Inicializando o Firestore
        db = FirebaseFirestore.getInstance();

        // Obtendo o ID do usuário logado do SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("USER_ID", null); // 'userId' é a chave que você usou para salvar o ID


        // Lógica para o botão de inscrição
        btnSubmit.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            String cpf = editTextCpf.getText().toString();

            // Validando os campos
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(cpf)) {
                Toast.makeText(getContext(), "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Criando um novo objeto Inscription com os dados preenchidos
            Inscricao Inscricao = new Inscricao(userId, name, email, cpf);

            // Salvando os dados na coleção 'inscricoes'
            db.collection("inscricoes")
                    .add(Inscricao)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(getContext(), "Inscrição realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Erro ao salvar dados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

            // Navegar para a próxima tela
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_inscricaoFragment_to_ongViewProfileFragment);
        });

        return view;
    }


}
