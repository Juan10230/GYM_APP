package com.example.botonesinferiores.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "base")
public class Base {

    // Identificador único para cada usuario (clave primaria)
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id_usuario")
    public String idUsuario;

    // Nombre del usuario
    @ColumnInfo(name = "nombre_usuario")
    public String nomUsuario;

    // Hora de inicio para el usuario
    @ColumnInfo(name = "hora_inicio")
    public String horaInicioUsuario;

    // Hora de fin para el usuario
    @ColumnInfo(name = "hora_fin")
    public String horaFinUsuario;

    // Peso asociado al usuario
    @ColumnInfo(name = "peso_usuario")
    public String pesoUsuario;

    // Nota asociada al usuario
    @ColumnInfo(name = "nota_usuario")
    public String notaUsuario;


    // Tipo de ejercicio para el usuario
    @ColumnInfo(name = "tipo_ejercicio")
    public String ejercicioUsuario;

    // Peso a cargar para el usuario
    @ColumnInfo(name = "pesocarga_usuario")
    public String pesoacargarUsuario;

    // Número de repeticiones para el usuario
    @ColumnInfo(name = "repeticiones_usuario")
    public String repeticionesUsuario;

    // Número de series para el usuario
    @ColumnInfo(name = "serie_usuario")
    public String serieUsuario;

    @ColumnInfo(name = "nombre_perfil")
    public String nombrePerfil;
    @ColumnInfo(name = "apellidos_perfil")
    public String apellidosPerfil;

    @ColumnInfo(name = "correo_perfil")
    public String correoPerfil;

    @ColumnInfo(name = "edad_perfil")
    public String edadPerfil;

    @ColumnInfo(name = "pesoInicial_perfil")
    public String pesoInicialPerfil;

    @ColumnInfo(name = "motivacion_perfil")
    public String motivacionPerfil;
}
