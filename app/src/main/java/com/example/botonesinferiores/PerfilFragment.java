package com.example.botonesinferiores;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.botonesinferiores.databinding.FragmentEstadisticasBinding;
import com.example.botonesinferiores.databinding.FragmentPerfilBinding;

import es.dmoral.toasty.Toasty;

// Definición de la clase PerfilFragment que extiende Fragment
public class PerfilFragment extends Fragment {
    FragmentPerfilBinding binding;

    // Constructor predeterminado
    public PerfilFragment() {
        // Constructor vacío requerido por Fragment
    }

    // Método estático para crear una nueva instancia del fragmento
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // Método llamado cuando se crea el fragmento
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verifica si hay argumentos, aunque en este caso no se utilizan
        if (getArguments() != null) {
            // Puedes manejar argumentos aquí si es necesario
        }
    }

    // Método llamado para crear y devolver la vista asociada al fragmento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del fragmento usando el enlace de datos (binding)
        binding = FragmentPerfilBinding.inflate(getLayoutInflater());
        View vista1 = binding.getRoot();

        // Establecer el título de la barra de acción
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(" ");
        setupToolbarMenu();

        // Obtener referencias a los elementos de la vista
        TextView tvNombrePerfil = vista1.findViewById(R.id.TvwnombreP);
        TextView tvApellidosPerfil = vista1.findViewById(R.id.Papellidos);
        TextView tvCorreoPerfil = vista1.findViewById(R.id.TvcorreoP);
        TextView tvEdadPerfil = vista1.findViewById(R.id.TvedadP);
        TextView tvPesoInicialPerfil = vista1.findViewById(R.id.TvpesoIP);
        TextView tvMotivacionPerfil = vista1.findViewById(R.id.tvMotivacionP);

        // Devolver la vista inflada
        return vista1;
    }

    // Configurar el menú de la barra de herramientas
    private void setupToolbarMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar_perfil, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_agregarPerfil) {
                    lanzarAlertDialogPerfil(getActivity());
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    // Método para lanzar un cuadro de diálogo de perfil
    private void lanzarAlertDialogPerfil(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View vista = inflater.inflate(R.layout.alert_dialog_add_update_perfil, null);
        builder.setView(vista);
        builder.setCancelable(false);

        // Obtener referencias a los elementos del cuadro de diálogo
        EditText etNomPerfil, etApellidoPerfil, etCorreoPerfil, etEdadPerfil, etPesoPerfil, etMotivacion;
        TextView tvTituloAlert;

        etNomPerfil = vista.findViewById(R.id.setNomUsuarioPerfil);
        etApellidoPerfil = vista.findViewById(R.id.setApellidosPerfil);
        etCorreoPerfil = vista.findViewById(R.id.setCorreoPerfil);
        etEdadPerfil = vista.findViewById(R.id.setEdadPerfil);
        etPesoPerfil = vista.findViewById(R.id.setPesoInicialPerfil);
        etMotivacion = vista.findViewById(R.id.setMotivacion);

        // Obtener referencias a los elementos de la vista principal
        TextView tvNombrePerfil = activity.findViewById(R.id.TvwnombreP);
        TextView tvApellidoPerfil = activity.findViewById(R.id.Papellidos);
        TextView tvCorreoPerfil = activity.findViewById(R.id.TvcorreoP);
        TextView tvEdadPerfil = activity.findViewById(R.id.TvedadP);
        TextView tvPesoInicial = activity.findViewById(R.id.TvpesoIP);
        TextView tvMotivacion = activity.findViewById(R.id.tvMotivacionP);

        // Configurar el botón "Aceptar" del cuadro de diálogo
        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            // Mostrar mensaje de aceptación
            Toast.makeText(activity, "ACEPTAR", Toast.LENGTH_SHORT).show();

            // Obtener datos del cuadro de diálogo
            String nombrePerfil = etNomPerfil.getText().toString().trim();
            String apellidosPerfil = etApellidoPerfil.getText().toString().trim();
            String correoPerfil = etCorreoPerfil.getText().toString().trim();
            String edadPerfil = etEdadPerfil.getText().toString().trim();
            String pesoPerfil = etPesoPerfil.getText().toString().trim();
            String motivacionPerfil = etMotivacion.getText().toString().trim();

            // Actualizar los elementos de la vista principal con los nuevos datos
            tvNombrePerfil.setText(nombrePerfil);
            tvApellidoPerfil.setText(apellidosPerfil);
            tvCorreoPerfil.setText(correoPerfil);
            tvEdadPerfil.setText(edadPerfil);
            tvPesoInicial.setText(pesoPerfil);
            tvMotivacion.setText(motivacionPerfil);
        });

        // Configurar el botón "Cancelar" del cuadro de diálogo
        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {
            // Mostrar mensaje de cancelación
            Toast.makeText(activity, "CANCELAR", Toast.LENGTH_LONG).show();
        });

        // Crear y mostrar el cuadro de diálogo
        builder.create();
        builder.show();
    }
}
