package br.univali.application;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import br.univali.application.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            getSupportActionBar().setTitle("");

        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

            int currentDestinationId = navController.getCurrentDestination().getId();

            if (currentDestinationId == R.id.registerFragment) {
                navController.navigate(R.id.loginFragment);
            } else {
                navController.navigate(R.id.FirstFragment);
            }
            return true;
        }

        // Verifica se o item selecionado é o de configurações
        if (id == R.id.action_settings) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.ConfigFragment);
            return true;
        }

        // Verifica se o item selecionado é o de perfil
        if (id == R.id.action_profile) {
            SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
            int userType = preferences.getInt("USER_TYPE", 0);

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            int currentDestinationId = navController.getCurrentDestination().getId();

            if (currentDestinationId == R.id.loginFragment || currentDestinationId == R.id.registerFragment){
                navController.navigate(R.id.loginFragment);
             } else {
                    if (userType == 2) {
                        navController.navigate(R.id.ongProfileFragment);
                    } else if (userType == 1) {
                        navController.navigate(R.id.UserProfileFragment);
                    }
             }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}