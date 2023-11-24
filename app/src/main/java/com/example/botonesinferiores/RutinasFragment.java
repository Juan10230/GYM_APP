package com.example.botonesinferiores;// Importaciones necesarias
import static android.content.Context.ALARM_SERVICE;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.botonesinferiores.database.AppDatabase;
import com.example.botonesinferiores.databinding.FragmentRutinasBinding;
import com.example.botonesinferiores.models.Base;
import com.example.botonesinferiores.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

// Clase que representa el fragmento de Rutinas
public class RutinasFragment extends Fragment implements BasesAdapter.OnItemClicked {

    // Enlace de datos (binding) para el fragmento de Rutinas
    FragmentRutinasBinding binding;

    // Variables relacionadas con las notificaciones de tiempo
    private TextView notificationsTime;
    private int alarmID = 1;
    private SharedPreferences settings;

    // Lista de datos de tipo 'Base' y adaptador para el RecyclerView
    List<Base> listaBases = new ArrayList<>();
    BasesAdapter baseAdapter = new BasesAdapter(listaBases, this);

    // Instancia de la base de datos
    AppDatabase db;

    // Objeto de tipo 'Base'
    Base base = new Base();

    // Variables para la validación y edición de datos
    Boolean isValid = false;
    Boolean isEditando = false;

    public RutinasFragment() {
        // Required empty public constructor
    }

    // Método estático para crear una nueva instancia del fragmento de Rutinas
    public static RutinasFragment newInstance(String param1, String param2) {
        RutinasFragment fragment = new RutinasFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lógica relacionada con la creación del fragmento
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño del fragmento utilizando el enlace de datos (binding)
        binding = FragmentRutinasBinding.inflate(getLayoutInflater());
        View vista = binding.getRoot();

        // Establecer el título de la barra de acción
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Rutinas");

        // Obtener una instancia de la base de datos
        db = new Utils().getAppDatabase(getContext());

        // Configurar el menú de la barra de herramientas
        setupToolbarMenu();

        // Obtener y mostrar la lista de usuarios
        obtenerUsuarios();

        // Configurar el escuchador de cambios en el texto de búsqueda
        binding.svUsuario.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrar la lista de usuarios según el texto de búsqueda
                filtrarUsuario(newText);
                return false;
            }
        });

        // Devolver la vista inflada
        return vista;
    }

    // Método para filtrar la lista de usuarios según el texto de búsqueda
    private void filtrarUsuario(String texto) {
        // Crear una lista filtrada para almacenar los resultados de la búsqueda
        ArrayList<Base> listaFiltrada = new ArrayList<>();

        // Iterar a través de la lista original de bases de datos
        for (Base base : listaBases) {
            // Verificar si el nombre del usuario contiene el texto de búsqueda (ignorando mayúsculas y minúsculas)
            if (base.nomUsuario.toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(base);  // Agregar a la lista filtrada si hay coincidencia
            }
        }

        // Actualizar el adaptador del RecyclerView con la lista filtrada
        baseAdapter.filtrarUsuario(listaFiltrada);
    }

    // Método para configurar el RecyclerView y su adaptador
    private void setupRecyclerView() {
        // Crear un administrador de diseño lineal para el RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Establecer el administrador de diseño en el RecyclerView
        binding.rvUsuarios.setLayoutManager(layoutManager);

        // Inicializar y establecer el adaptador del RecyclerView
        baseAdapter = new BasesAdapter(listaBases, this);
        binding.rvUsuarios.setAdapter(baseAdapter);
    }

    // Método para configurar el menú de la barra de herramientas
    private void setupToolbarMenu() {
        // Agregar un proveedor de menú a la actividad actual
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                // Inflar el menú de la barra de herramientas desde el recurso de menú
                menuInflater.inflate(R.menu.menu_toolbar, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // Manejar eventos de clic en elementos del menú

                if (menuItem.getItemId() == R.id.action_agregar) {
                    lanzarAlertDialog(getActivity());  // Lanzar el cuadro de diálogo al hacer clic en "Agregar"
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    // Método para lanzar el cuadro de diálogo de agregar/actualizar registro
    private void lanzarAlertDialog(Activity activity) {
        // Crear un constructor de AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // Inflar el diseño del cuadro de diálogo desde el archivo XML
        LayoutInflater inflater = activity.getLayoutInflater();
        View vista = inflater.inflate(R.layout.alert_dialog_add_update, null);
        builder.setView(vista);
        builder.setCancelable(false);

        // Referencias a elementos de la vista del cuadro de diálogo
        EditText setNomUsuario, setPeso, setNota, setTipoE, setPesoPerfil, setRepeticiones, setSeries;
        TextView tvTituloAlert, tvHoraInicio, tvHoraFin;
        ImageButton ibtnHora, ibtnHora2;

        setNomUsuario = vista.findViewById(R.id.setNomUsuario);
        setPeso = vista.findViewById(R.id.setPeso);
        setNota = vista.findViewById(R.id.setNota);
        setTipoE = vista.findViewById(R.id.setTipo);
        setPesoPerfil = vista.findViewById(R.id.setPesocarga);
        setRepeticiones = vista.findViewById(R.id.setRepeticiones);
        setSeries = vista.findViewById(R.id.setSeries);

        tvTituloAlert = vista.findViewById(R.id.tvTituloAlert);
        tvHoraInicio = vista.findViewById(R.id.tvHoraInicio);
        tvHoraFin = vista.findViewById(R.id.tvHoraFin);
        ibtnHora = vista.findViewById(R.id.ibtnHora);
        ibtnHora2 = vista.findViewById(R.id.ibtnHora2);

        // Verificar si se está editando para establecer el título y llenar los campos
        if (isEditando) {
            tvTituloAlert.setText("ACTUALIZAR REGISTRO");
            setNomUsuario.setText(base.nomUsuario);
            setPeso.setText(base.pesoUsuario);
            setNota.setText(base.notaUsuario);
            setTipoE.setText(base.ejercicioUsuario);
            setPesoPerfil.setText(base.pesoacargarUsuario);
            setRepeticiones.setText(base.repeticionesUsuario);
            setSeries.setText(base.serieUsuario);

            tvHoraInicio.setText(base.horaInicioUsuario);
            tvHoraFin.setText(base.horaFinUsuario);
        }

        // Asignar eventos de clic a los botones para obtener la hora
        ibtnHora.setOnClickListener(v -> {
            obtenerHora(tvHoraInicio);
        });
        ibtnHora2.setOnClickListener(v -> {
            obtenerHora2(tvHoraFin);
        });

        // Configurar el botón "Aceptar" del cuadro de diálogo
        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            // Verificar si se está editando y asignar un ID único si no
            if (!isEditando) {
                base.idUsuario = String.valueOf(System.currentTimeMillis());
            }

            // Obtener los datos del cuadro de diálogo y asignarlos al objeto Base
            base.nomUsuario = setNomUsuario.getText().toString().trim();
            base.horaInicioUsuario = tvHoraInicio.getText().toString().trim();
            base.horaFinUsuario = tvHoraFin.getText().toString().trim();
            base.pesoUsuario = setPeso.getText().toString().trim();
            base.notaUsuario = setNota.getText().toString().trim();
            base.ejercicioUsuario = setTipoE.getText().toString().trim();
            base.pesoacargarUsuario = setPesoPerfil.getText().toString().trim();
            base.repeticionesUsuario = setRepeticiones.getText().toString().trim();
            base.serieUsuario = setSeries.getText().toString().trim();

            validarCampos();

            // Verificar la validez de los campos antes de agregar o actualizar
            if (isValid) {
                if (isEditando) {
                    actualizarDatos();
                    isEditando = false;
                } else {
                    agregarDatos();
                }
            } else {
                Toasty.error(getContext(), "Faltan por llenar datos obligatorios", Toasty.LENGTH_LONG, true).show();
            }
        });

        // Configurar el botón "Cancelar" del cuadro de diálogo
        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {
            Toast.makeText(activity, "CANCELAR", Toast.LENGTH_LONG).show();
        });

        // Crear y mostrar el cuadro de diálogo
        builder.create();
        builder.show();
    }

    // Método para validar la entrada de datos en los campos del cuadro de diálogo
    private void validarCampos() {
        if (
                base.nomUsuario.isEmpty()
                        || base.pesoUsuario.isEmpty()
                        || base.horaInicioUsuario.isEmpty()
                        || base.horaFinUsuario.isEmpty()
                        || base.ejercicioUsuario.isEmpty()
                        || base.pesoacargarUsuario.isEmpty()
                        || base.repeticionesUsuario.isEmpty()
                        || base.serieUsuario.isEmpty()
        ) {
            isValid = false;
        } else {
            isValid = true;
        }
    }

    // Método para mostrar un selector de hora y establecer la hora en un TextView
    private void obtenerHora(TextView tvHora) {
        TimePickerDialog recogerHora = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
            String horaFormateada = (hourOfDay < 10) ? "0" + hourOfDay : String.valueOf(hourOfDay);
            String minuteFormateado = (minute < 10) ? "0" + minute : String.valueOf(minute);
            tvHora.setText(horaFormateada + ":" + minuteFormateado);

            // Crear un objeto Calendar para establecer la hora seleccionada
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, hourOfDay);
            today.set(Calendar.MINUTE, minute);
            today.set(Calendar.SECOND, 0);

            // Mostrar un mensaje de cambio y configurar la alarma
            String mensaje = getString(R.string.changed_to, horaFormateada + ":" + minuteFormateado);
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
            setAlarm(alarmID, today.getTimeInMillis(), getContext());
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), false);

        recogerHora.show();
    }

    // Método similar al anterior para la segunda hora
    private void obtenerHora2(TextView tvHora) {
        TimePickerDialog recogerHora = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
            String horaFormateada = (hourOfDay < 10) ? "0" + hourOfDay : String.valueOf(hourOfDay);
            String minuteFormateado = (minute < 10) ? "0" + minute : String.valueOf(minute);
            tvHora.setText(horaFormateada + ":" + minuteFormateado);

            // Crear un objeto Calendar para establecer la hora seleccionada
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, hourOfDay);
            today.set(Calendar.MINUTE, minute);
            today.set(Calendar.SECOND, 0);

            // Mostrar un mensaje de cambio y configurar la segunda alarma
            String mensaje = getString(R.string.changed_to, horaFormateada + ":" + minuteFormateado);
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
            setAlarm2(alarmID, today.getTimeInMillis(), getContext());

        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), false);

        recogerHora.show();
    }

    // Método para obtener datos de la base de datos en segundo plano y actualizar la interfaz de usuario
    private void obtenerUsuarios() {
        AsyncTask.execute(() -> {
            listaBases = db.baseDao().obtenerDatos();
            getActivity().runOnUiThread(() -> {
                setupRecyclerView();
            });
        });
    }

    // Método para agregar datos a la base de datos en segundo plano y actualizar la interfaz de usuario
    private void agregarDatos() {
        AsyncTask.execute(() -> {
            db.baseDao().agregarDato(base);
            listaBases = db.baseDao().obtenerDatos();
            getActivity().runOnUiThread(() -> {
                setupRecyclerView();
            });
        });
    }

    // Método para actualizar datos en la base de datos en segundo plano y actualizar la interfaz de usuario
    private void actualizarDatos() {
        AsyncTask.execute(() -> {
            db.baseDao().actualizarDato(base);
            listaBases = db.baseDao().obtenerDatos();
            getActivity().runOnUiThread(() -> {
                setupRecyclerView();
            });
        });
    }

    // Método de la interfaz para editar un registro existente
    @Override
    public void editarR(Base base) {
        isEditando = true;
        this.base = base;
        lanzarAlertDialog(getActivity());
    }

    // Método de la interfaz para eliminar un registro existente
    @Override
    public void eliminarR(Base base) {
        AsyncTask.execute(() -> {
            db.baseDao().eliminarDato(base);
            listaBases = db.baseDao().obtenerDatos();
            getActivity().runOnUiThread(() -> {
                setupRecyclerView();
            });
        });

        Toasty.info(getContext(), "Se eliminó el registro exitosamente", Toasty.LENGTH_LONG).show();
    }

    // Método para configurar una alarma de inicio
    public static void setAlarm(int i, Long timestamp, Context ctx) {
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(ctx, AlarmaInicio.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(ctx, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }

    // Método para configurar una alarma de fin
    public static void setAlarm2(int i, Long timestamp, Context ctx) {
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(ctx, AlarmaFin.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(ctx, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }
}