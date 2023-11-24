package com.example.botonesinferiores.utils;

import android.content.Context;

import androidx.room.Room;

import com.example.botonesinferiores.database.AppDatabase;

public class Utils {
    // Nombre de la base de datos
    final private String DB_NAME = "dbGymAppV2.1";
    /*Version2.0*/

    // Instancia de la base de datos
    AppDatabase db;

    // Método para obtener la instancia de la base de datos
    public AppDatabase getAppDatabase(Context context) {
        // Construir la base de datos utilizando el Builder de Room
        db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).build();
        return db;
    }
}
