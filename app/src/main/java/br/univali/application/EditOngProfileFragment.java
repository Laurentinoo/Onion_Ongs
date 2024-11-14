package br.univali.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditOngProfileFragment extends Fragment {

    private FirebaseFirestore db;
    private EditText editUserName, editBiography;
    private String userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar o layout
        View view = inflater.inflate(R.layout.fragment_edit_user_profile, container, false);

        // Inicializando os componentes
        editUserName = view.findViewById(R.id.editUserName);
        editBiography = view.findViewById(R.id.editBiography);
        db = FirebaseFirestore.getInstance();

        // Recuperando o ID do usuário das SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
        userId = preferences.getString("USER_ID", "");

        // Carregar os dados atuais do usuário do Firestore
        if (!userId.isEmpty()) {
            db.collection("usuarios").document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Preencher os campos com os dados atuais
                                String currentName = document.getString("nome");
                                String currentBio = document.getString("bio");

                                editUserName.setText(currentName);
                                editBiography.setText(currentBio != null ? currentBio : "");
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
                    });
        }

        // Configurar o listener do botão de salvar
        view.findViewById(R.id.saveButton).setOnClickListener(v -> {
            String newName = editUserName.getText().toString();
            String newBio = editBiography.getText().toString();

            if (!newName.isEmpty() && !newBio.isEmpty()) {
                // Atualizar os dados no Firestore
                db.collection("usuarios").document(userId)
                        .update("nome", newName, "bio", newBio)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getActivity(), "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                            // Voltar para o fragmento anterior ou para o perfil de usuário
                            Navigation.findNavController(v).navigateUp();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getActivity(), "Erro ao atualizar perfil", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(getActivity(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
