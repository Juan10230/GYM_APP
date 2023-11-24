package com.example.botonesinferiores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import com.example.botonesinferiores.database.AppDatabase;
import com.example.botonesinferiores.models.Base;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class HoraInicio extends AppCompatActivity {
    // Lista para almacenar objetos Base (datos de entrenamiento)
    List<Base> listaBases;

    // Lista de BarEntry para las horas de inicio
    ArrayList<BarEntry> hora;

    // Instancia de la base de datos Room
    AppDatabase db;

    // Objeto Base para manipulación de datos individuales
    Base base = new Base();

    // Elementos de la interfaz de usuario
    TextView a;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hora_inicio);

        // Inicializa la BarChart y la ArrayList aquí, dentro del método onCreate
        barChart = findViewById(R.id.barChart);
        hora = new ArrayList<>();

        // Llama a un método para cargar y mostrar los datos en la gráfica
        cargarDatosEnGrafica();
    }

    // Método para cargar y mostrar los datos en la gráfica de barras
    private void cargarDatosEnGrafica() {
        // Inicializa la base de datos Room
        AppDatabase appdatabase = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "dbGymAppV2.1"
        ).allowMainThreadQueries().build();

        // Obtiene la lista de bases de datos
        listaBases = appdatabase.baseDao().obtenerDatos();

        // Itera sobre la lista de bases para calcular las diferencias y llenar el conjunto de datos de la gráfica
        for (int i = 0; i < listaBases.size(); i++) {
            // Obtiene la hora de inicio de cada registro
            String horaString = listaBases.get(i).horaInicioUsuario;

            // Convierte la hora directamente a minutos si ya está en formato de 24 horas
            Float horaTotal = convertirHoraAMinutos(horaString);

            // Agrega la hora como una BarEntry a la lista de horas
            hora.add(new BarEntry((i + 1), horaTotal));
        }

        // Configura el conjunto de datos para la gráfica de barras
        BarDataSet barDataSet = new BarDataSet(hora, "Estadísticas de 'Hora de inicio'");
        barDataSet.setValueTextSize(30f);

        // Cambia el color de las barras a tu color deseado
        int colorHex = Color.parseColor("#8B158C2C");
        barDataSet.setColors(new int[]{colorHex});
        BarData barData = new BarData(barDataSet);

        // Configura la gráfica de barras
        barChart.setData(barData);
        Legend legend = barChart.getLegend();
        legend.setTextSize(28f);

        // Ajusta el rango del eje Y
        barChart.invalidate(); // Refresca la gráfica
    }

    // Método para convertir la hora en formato HH:mm a minutos
    private Float convertirHoraAMinutos(String hora) {
        // Divide la cadena de hora en partes (horas y minutos)
        String[] partes = hora.split(":");
        int horas = Integer.parseInt(partes[0]);
        float minutos = Float.parseFloat(partes[1]);
        // Devuelve la hora total en minutos, considerando fracciones de hora
        return horas + (minutos / 100);
    }
}
