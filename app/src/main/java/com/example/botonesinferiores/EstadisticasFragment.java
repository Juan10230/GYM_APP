package com.example.botonesinferiores;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.botonesinferiores.databinding.FragmentEstadisticasBinding;
import com.example.botonesinferiores.databinding.FragmentRegistroBinding;

public class EstadisticasFragment extends Fragment {
    // Enlace de datos para la vista del fragmento
    FragmentEstadisticasBinding binding;

    // Constructor vacío requerido por Fragment
    public EstadisticasFragment() {
        // Required empty public constructor
    }

    // Método estático para crear una nueva instancia del fragmento
    public static EstadisticasFragment newInstance(String param1, String param2) {
        EstadisticasFragment fragment = new EstadisticasFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Elementos de la interfaz de usuario
    Button btnPesoCorporal;
    Button btnDuracionEntrenamiento;
    Button btnHoraInicio;
    Button btnHoraTermino;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflar el diseño del fragmento utilizando el enlace de datos
        binding = FragmentEstadisticasBinding.inflate(getLayoutInflater());
        View vista1 = binding.getRoot();

        // Establecer el título de la barra de acciones en la actividad principal
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(" ");

        // Inicializar los botones
        btnPesoCorporal = vista1.findViewById(R.id.button8);
        btnDuracionEntrenamiento = vista1.findViewById(R.id.button5);
        btnHoraInicio = vista1.findViewById(R.id.button6);
        btnHoraTermino = vista1.findViewById(R.id.button7);

        // Configurar los listeners de los botones para iniciar actividades específicas al hacer clic
        btnPesoCorporal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad PesoCorporal al hacer clic en el botón
                Intent i = new Intent(getActivity(), PesoCorporal.class);
                startActivity(i);
            }
        });
        btnDuracionEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad DuracionEntrenamiento al hacer clic en el botón
                Intent i = new Intent(getActivity(), DuracionEntrenamiento.class);
                startActivity(i);
            }
        });
        btnHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad HoraInicio al hacer clic en el botón
                Intent i = new Intent(getActivity(), HoraInicio.class);
                startActivity(i);
            }
        });
        btnHoraTermino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad HoraTermino al hacer clic en el botón
                Intent i = new Intent(getActivity(), HoraTermino.class);
                startActivity(i);
            }
        });

        return vista1;
    }
}
