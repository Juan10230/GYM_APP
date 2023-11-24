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

public class PesoCorporal extends AppCompatActivity {
    //Listas y variables necesarias
    List<Base> listaBases;
    ArrayList<BarEntry> pesos;

    AppDatabase db;

    Base base = new Base();

    TextView a;

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso_corporal);

        // Inicializa la BarChart y la ArrayList aquí, dentro del método onCreate
        barChart = findViewById(R.id.barChart);
        pesos = new ArrayList<>();
        //base = new ArrayList<>();

        // Llama a un método para cargar y mostrar los datos en la gráfica
        cargarDatosEnGrafica();
    }

    private void cargarDatosEnGrafica() {
        // Aquí deberías implementar la lógica para obtener datos desde tu base de datos
        AppDatabase appdatabase = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "dbGymAppV2.1"
        ).allowMainThreadQueries().build();

        listaBases = appdatabase.baseDao().obtenerDatos();
        for (int i = 0;i<listaBases.size();i++){

            pesos.add(new BarEntry((i + 1), Integer.parseInt(listaBases.get(i).pesoUsuario)));

        }

        // Configura el conjunto de datos
        BarDataSet barDataSet = new BarDataSet(pesos, "Estaditicas de peso corporal");
        barDataSet.setValueTextSize(30f);
        int colorHex = Color.parseColor("#8B158C2C");
        barDataSet.setColors(new int[]{colorHex});
        BarData barData = new BarData(barDataSet);

        // Configura la gráfica

        barChart.setData(barData);
        Legend legend = barChart.getLegend();
        legend.setTextSize(28f);

        //barChart.set(Color.MAGENTA);
        //barChart=(BarChart)getSameChart(barChart,"series")
        barChart.invalidate(); // Refresca la gráfica
    }
}