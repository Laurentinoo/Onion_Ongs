package br.univali.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import br.univali.application.databinding.FragmentUserProfileBinding;

public class UserProfileFragment extends Fragment {

    private FragmentUserProfileBinding binding;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance(); // Inicializando o Firestore

        // Recuperar o nome do usuário logado
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
        String userName = preferences.getString("USER_NAME", "");
        String userId = preferences.getString("USER_ID", ""); // Pega o ID do usuário armazenado

        // Exibir o nome do usuário na interface
        binding.userNameTextView.setText(userName); // Supondo que você tenha um TextView para o nome

        if (!userId.isEmpty()) {
            db.collection("usuarios").document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String bio = document.getString("bio");
                                binding.biographyTextView.setText(bio != null ? bio : "Biografia não disponível.");
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        binding.biographyTextView.setText("Erro ao carregar biografia.");
                    });
        }


        // Configurar botão de editar perfil
        binding.editProfileButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_userProfileFragment_to_editUserProfileFragment);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
