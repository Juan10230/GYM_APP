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

public class DuracionEntrenamiento extends AppCompatActivity {
    // Lista para almacenar objetos Base (datos de entrenamiento)
    List<Base> listaBases;

    // Lista de BarEntry para las diferencias de tiempo
    ArrayList<BarEntry> diferencias;

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
        setContentView(R.layout.activity_duracion_entrenamiento);

        // Inicializa la BarChart y la ArrayList aquí, dentro del método onCreate
        barChart = findViewById(R.id.barChart);
        diferencias = new ArrayList<>();

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
            // Obtiene las horas de inicio y fin de cada registro
            String horaInicioString = listaBases.get(i).horaInicioUsuario;
            String horaFinString = listaBases.get(i).horaFinUsuario;

            // Calcula la diferencia de horas en minutos
            float diferenciaMinutos = calcularDiferenciaHoras(horaInicioString, horaFinString);

            // Agrega la diferencia como una BarEntry a la lista de diferencias
            diferencias.add(new BarEntry((i + 1), diferenciaMinutos));
        }

        // Configura el conjunto de datos para la gráfica de barras
        BarDataSet barDataSet = new BarDataSet(diferencias, "Diferencia de Horas (minutos)");
        barDataSet.setValueTextSize(30f);

        // Cambia el color de las barras a tu color deseado (puedes proporcionar un array de colores)
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

    // Método para calcular la diferencia de tiempo entre dos horas en minutos
    private float calcularDiferenciaHoras(String horaInicio, String horaFin) {
        // Parsea las horas y minutos de las cadenas de tiempo
        String[] partesInicio = horaInicio.split(":");
        String[] partesFin = horaFin.split(":");

        int horasInicio = Integer.parseInt(partesInicio[0]);
        int minutosInicio = Integer.parseInt(partesInicio[1]);

        int horasFin = Integer.parseInt(partesFin[0]);
        int minutosFin = Integer.parseInt(partesFin[1]);

        // Ajusta las horas y minutos si es necesario para asegurar un cálculo preciso
        if (horasFin < horasInicio) {
            horasFin = horasFin + 24;
        }
        if (minutosFin < minutosInicio) {
            minutosFin = minutosFin + 60;
        }

        // Calcula la diferencia en minutos y devuelve el resultado en formato de horas y fracciones de horas
        int diferenciaHoras = horasFin - horasInicio;
        int diferenciaMinutos = minutosFin - minutosInicio;
        return diferenciaHoras + (diferenciaMinutos / 100f);
    }
}
