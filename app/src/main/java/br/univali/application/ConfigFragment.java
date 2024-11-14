package br.univali.application;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class ConfigFragment extends Fragment {

    private Switch switchNotifications;
    private Switch switchDarkMode;
    private Button buttonResetSettings;

    private static final String PREFS_NAME = "app_preferences";
    private static final String PREF_DARK_MODE = "dark_mode_enabled";

    public ConfigFragment() {
        // Construtor vazio obrigatório
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);

        // Inicializa os componentes da UI
        switchNotifications = view.findViewById(R.id.switch_notifications);
        switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        buttonResetSettings = view.findViewById(R.id.button_reset_settings);

        // Carrega o estado de tema escuro das preferências
        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, 0);
        boolean isDarkModeEnabled = preferences.getBoolean(PREF_DARK_MODE, false);
        switchDarkMode.setChecked(isDarkModeEnabled);

        // Configura os listeners
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Implementa lógica para ativar/desativar notificações
            Toast.makeText(getContext(), "Notificações " + (isChecked ? "ativadas" : "desativadas"), Toast.LENGTH_SHORT).show();
        });

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            // Salva a preferência do tema
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(PREF_DARK_MODE, isChecked);
            editor.apply();

            Toast.makeText(getContext(), "Tema " + (isChecked ? "escuro" : "claro") + " ativado", Toast.LENGTH_SHORT).show();
        });

        buttonResetSettings.setOnClickListener(v -> {
            // Implementa lógica para redefinir configurações
            Toast.makeText(getContext(), "Configurações redefinidas", Toast.LENGTH_SHORT).show();
            switchNotifications.setChecked(false);
            switchDarkMode.setChecked(false);

            // Reseta para o tema claro
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(PREF_DARK_MODE, false);
            editor.apply();
        });

        return view;
    }
}
