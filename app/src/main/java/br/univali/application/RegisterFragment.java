package br.univali.application;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import br.univali.application.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance(); // Inicializando o Firestore

        // Acesse os botões através do binding
        Button btnOng = binding.btnOng;
        Button btnVolunteer = binding.btnVolunteer;

        // Inicialmente, nenhum botão está ativo
        btnOng.setBackgroundTintList(getResources().getColorStateList(R.color.primary_light));
        btnVolunteer.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));

        // Lógica para alternar os estados de seleção
        btnOng.setOnClickListener(v -> {
            btnOng.setBackgroundTintList(getResources().getColorStateList(R.color.primary_light));
            btnVolunteer.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
        });

        btnVolunteer.setOnClickListener(v -> {
            btnVolunteer.setBackgroundTintList(getResources().getColorStateList(R.color.primary_light));
            btnOng.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
        });

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForColorStateLists")
            @Override
            public void onClick(View v) {
                String nome = binding.usernameEditText.getText().toString();
                String senha = binding.passwordEditText.getText().toString();

                // Definir tipo com base no botão selecionado
                int tipo;
                if (btnOng.getBackgroundTintList().equals(getResources().getColorStateList(R.color.primary_light))) {
                    tipo = 2; // ONG
                } else if (btnVolunteer.getBackgroundTintList().equals(getResources().getColorStateList(R.color.primary_light))) {
                    tipo = 1; // VOLUNTÁRIO
                } else {
                    Toast.makeText(getActivity(), "Selecione um tipo de usuário", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!nome.isEmpty() && !senha.isEmpty()) {
                    // Verifica se o usuário já existe no Firestore
                    db.collection("usuarios")
                            .whereEqualTo("nome", nome)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                    // O usuário já existe
                                    Toast.makeText(getActivity(), "Usuário já registrado!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Usuário não existe, então registra
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("nome", nome);
                                    user.put("senha", senha);
                                    user.put("tipo", tipo);
                                    user.put("bio", "");

                                    // Adiciona o novo usuário ao Firestore
                                    db.collection("usuarios")
                                            .add(user)
                                            .addOnSuccessListener(documentReference -> {
                                                Toast.makeText(getActivity(), "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show();

                                                // Navegar de volta para o LoginFragment
                                                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_registerFragment_to_LoginFragment);
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(getActivity(), "Erro ao registrar usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            });
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getActivity(), "Erro ao verificar usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(getActivity(), "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
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