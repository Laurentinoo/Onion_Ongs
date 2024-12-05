package br.univali.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class CampaignFragment extends Fragment {

    private ImageView campaignImage;
    private TextView campaignName;
    private TextView campaignDescription;

    private FirebaseFirestore db;
    private ListenerRegistration listenerRegistration;

    public CampaignFragment() {
        // Requer um construtor vazio
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout do fragmento
        View rootView = inflater.inflate(R.layout.fragment_campaign, container, false);

        // Vincula os elementos da interface
        campaignImage = rootView.findViewById(R.id.campanha_imagem);  // Usando o ID correto do XML
        campaignName = rootView.findViewById(R.id.campanha_nome);
        campaignDescription = rootView.findViewById(R.id.campanha_descricao);

        // Inicializa o Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Obtém a referência do documento de campanhas no Firestore
        DocumentReference campaignRef = db.collection("campanhas").document("campanha_de_natal");

        // Carrega os dados do Firestore
        listenerRegistration = campaignRef.addSnapshotListener((documentSnapshot, e) -> {
            if (e != null) {
                Toast.makeText(getContext(), "Erro ao carregar os dados", Toast.LENGTH_SHORT).show();
                return;
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                // Recupera os dados da campanha
                String nome = documentSnapshot.getString("nome");
                String descricao = documentSnapshot.getString("descricao");

                // Atualiza a interface com os dados da campanha
                campaignName.setText(nome);
                campaignDescription.setText(descricao);

                // Define a imagem placeholder (sem carregar a imagem de URL)
                campaignImage.setImageResource(R.drawable.paris); // Imagem placeholder sempre
            } else {
                Toast.makeText(getContext(), "Campanha não encontrada", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listenerRegistration != null) {
            listenerRegistration.remove(); // Remover o ouvinte para evitar vazamentos de memória
        }
    }
}
