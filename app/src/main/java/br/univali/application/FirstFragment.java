package br.univali.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import br.univali.application.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        // Lista de imagens para o carrossel
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.rock1);
        images.add(R.drawable.paris);
        images.add(R.drawable.cow);

        NavController navController = NavHostFragment.findNavController(this);
        ImageAdapter adapter = new ImageAdapter(requireContext(), navController, images);
        binding.viewPager.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
