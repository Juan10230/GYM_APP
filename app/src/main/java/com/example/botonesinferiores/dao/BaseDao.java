package com.example.botonesinferiores.dao;

/* Se crea la interfaz de la base de datos */
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.botonesinferiores.models.Base;

import java.util.List;

@Dao
public interface BaseDao {

    /* Definimos métodos para realizar operaciones en la base de datos */

    // Método para obtener todos los datos de la tabla 'base'
    @Query("SELECT * FROM base")
    List<Base> obtenerDatos();

    // Método para agregar datos a la tabla 'base'
    @Insert
    void agregarDato(Base... base);

    // Método para actualizar datos en la tabla 'base'
    @Update
    void actualizarDato(Base... base);

    // Método para eliminar datos de la tabla 'base'
    @Delete
    void eliminarDato(Base base);
}
