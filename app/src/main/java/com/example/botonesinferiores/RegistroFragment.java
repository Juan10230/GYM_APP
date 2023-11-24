// Importaciones necesarias
package com.example.botonesinferiores;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.botonesinferiores.databinding.FragmentRegistroBinding;

// Definición de la clase RegistroFragment que extiende de Fragment
public class RegistroFragment extends Fragment {
    FragmentRegistroBinding binding;

    // Constructor por defecto
    public RegistroFragment() {
        // Constructor vacío requerido por la clase Fragment
    }

    // Método estático para crear una nueva instancia del fragmento
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
        // Lógica para configurar fragmento con argumentos si es necesario
        return fragment;
    }

    // Método llamado cuando se crea el fragmento
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lógica de inicialización del fragmento
    }

    // Método llamado al crear la vista del fragmento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del fragmento utilizando el enlace de datos
        binding = FragmentRegistroBinding.inflate(getLayoutInflater());
        // Obtener la vista raíz del diseño inflado
        View vista1 = binding.getRoot();

        // Configuración del título de la barra de acción
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(" ");

        // Configuración del evento de clic del botón
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navegar al fragmento de Rutinas al hacer clic en el botón
                Navigation.findNavController(view).navigate(R.id.pagina_rutinas);
            }
        });

        // Retornar la vista inflada
        return vista1;
    }
}
