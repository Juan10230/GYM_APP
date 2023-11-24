package com.example.botonesinferiores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.botonesinferiores.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    // Enlace de datos para la vista de la actividad principal
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el diseño de la actividad principal utilizando el enlace de datos
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());

        // Configurar la navegación
        setupNavegacion();
    }

    // Método para configurar la navegación con la barra de navegación inferior
    private void setupNavegacion() {
        // Obtener la vista de la barra de navegación inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Obtener el fragmento de navegación del host
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);

        // Configurar la navegación con el controlador del fragmento de navegación
        NavigationUI.setupWithNavController(
                bottomNavigationView,
                navHostFragment.getNavController()
        );
    }
}
