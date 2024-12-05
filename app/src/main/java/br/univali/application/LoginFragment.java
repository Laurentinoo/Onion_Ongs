package br.univali.application;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.navigation.Navigation;
import android.content.Context;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import br.univali.application.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance(); // Inicializando o Firestore

        // Configurar o listener do botão de login
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = binding.usernameEditText.getText().toString();
                String senha = binding.passwordEditText.getText().toString();

                if (!nome.isEmpty() && !senha.isEmpty()) {
                    // Buscar o usuário no Firestore
                    db.collection("usuarios")
                            .whereEqualTo("nome", nome)
                            .whereEqualTo("senha", senha)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                    int tipo = document.getLong("tipo").intValue();
                                    String userId = document.getId(); // Pega o ID do documento (ID do usuário)

                                    Toast.makeText(getActivity(), "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                                    // Salvar o tipo do usuário nas SharedPreferences
                                    SharedPreferences preferences = getActivity().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("USER_NAME", nome);  // Salva o nome do usuário
                                    editor.putString("USER_ID", userId);  // Salva o ID do usuário (ID do documento no Firestore)
                                    editor.putInt("USER_TYPE", tipo);    // Salva o tipo de usuário retornado do Firestore
                                    editor.apply();

                                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_FirstFragment);
                                } else {
                                    Toast.makeText(getActivity(), "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getActivity(), "Erro ao acessar o Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(getActivity(), "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Configurar o listener do botão de registro
        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar para o fragmento de registro
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
