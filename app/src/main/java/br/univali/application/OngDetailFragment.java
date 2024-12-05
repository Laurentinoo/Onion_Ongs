package br.univali.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class OngDetailFragment extends Fragment {

    private TextView nomeOng;
    private TextView bioOng;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout do fragmento
        View rootView = inflater.inflate(R.layout.fragment_ong_detail, container, false);

        nomeOng = rootView.findViewById(R.id.nomeOng);
        bioOng = rootView.findViewById(R.id.bioOng);

        // Pega os dados passados do fragmento anterior (FirstFragment)
        Bundle bundle = getArguments();
        if (bundle != null) {
            String nome = bundle.getString("nomeOng");
            String bio = bundle.getString("bioOng");

            nomeOng.setText(nome);
            bioOng.setText(bio);
        }

        return rootView;
    }
}