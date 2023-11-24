package com.example.botonesinferiores.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.botonesinferiores.dao.BaseDao;
import com.example.botonesinferiores.models.Base;

@Database(
        // Definimos las entidades (tablas) que forman parte de la base de datos
        entities = {Base.class},
        // Especificamos la versión de la base de datos
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {

    // Método abstracto para obtener el DAO asociado a la entidad 'Base'
    public abstract BaseDao baseDao();
}
