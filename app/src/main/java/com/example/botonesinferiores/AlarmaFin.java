package com.example.botonesinferiores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class AlarmaFin extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Crear una nueva instancia de Intent para iniciar el servicio de Notificación
        Intent service1 = new Intent(context, NotificacionFin.class);

        // Establecer datos en el Intent para evitar conflictos de reutilización
        service1.setData(Uri.parse("custom://" + System.currentTimeMillis()));

        // Iniciar el servicio en primer plano usando ContextCompat para la compatibilidad con versiones antiguas
        ContextCompat.startForegroundService(context, service1);

        // Registrar un mensaje de depuración indicando que la alarma fue recibida
        Log.d("JUAN", " ALARMA RECIBIDA!!!");
    }
}
