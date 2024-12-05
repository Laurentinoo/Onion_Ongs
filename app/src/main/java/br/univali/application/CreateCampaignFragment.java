package br.univali.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateCampaignFragment extends Fragment {

    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_campaign, container, false);
        db = FirebaseFirestore.getInstance();

        EditText etCampaignName = view.findViewById(R.id.etCampaignName);
        EditText etCampaignDescription = view.findViewById(R.id.etCampaignDescription);
        Button btnSaveCampaign = view.findViewById(R.id.btnSaveCampaign);

        // Recuperar o ID da ONG do SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);
        String ongId = preferences.getString("USER_ID", "");

        btnSaveCampaign.setOnClickListener(v -> {
            String campaignName = etCampaignName.getText().toString().trim();
            String campaignDescription = etCampaignDescription.getText().toString().trim();

            if (campaignName.isEmpty() || campaignDescription.isEmpty()) {
                Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Criar os dados para salvar no Firestore
            Map<String, Object> campaignData = new HashMap<>();
            campaignData.put("nome", campaignName);
            campaignData.put("descricao", campaignDescription);
            campaignData.put("ongId", ongId);

            // Salvar no Firestore
            db.collection("campanhas")
                    .add(campaignData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(getContext(), "Campanha criada com sucesso!", Toast.LENGTH_SHORT).show();
                        etCampaignName.setText("");
                        etCampaignDescription.setText("");
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Erro ao salvar campanha: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        return view;
    }
}