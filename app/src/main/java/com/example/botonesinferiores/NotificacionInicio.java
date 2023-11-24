package com.example.botonesinferiores;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

// Servicio de manejo de notificaciones
public class NotificacionInicio extends IntentService {

    // Manejador de notificaciones
    private NotificationManager notificationManager;
    // Intent para la actividad que se abrirá al hacer clic en la notificación
    private PendingIntent pendingIntent;
    // Identificador único para la notificación
    private static int NOTIFICATION_ID = 1;
    Notification notification;

    // Constructor que acepta un nombre para el servicio
    public NotificacionInicio(String name) {
        super(name);
    }

    // Constructor predeterminado sin argumentos
    public NotificacionInicio() {
        super("SERVICE");
    }

    // Método invocado al manejar la intención del servicio
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(Intent intent2) {
        // Identificador del canal de notificación
        String NOTIFICATION_CHANNEL_ID = getApplicationContext().getString(R.string.app_name1);
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, RutinasFragment.class);
        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);

        String message = getString(R.string.notification1);

        // Verificar la versión de Android y manejar las notificaciones de manera adecuada
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final int NOTIFY_ID = 0; // ID de la notificación
            String id = NOTIFICATION_CHANNEL_ID; // ID del canal de notificación
            String title = NOTIFICATION_CHANNEL_ID; // Nombre del canal por defecto
            PendingIntent pendingIntent;
            NotificationCompat.Builder builder;
            NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // Verificar si el NotificationManager es nulo y asignar uno nuevo si es necesario
            if (notifManager == null) {
                notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);

            // Crear un nuevo canal de notificación si aún no existe
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }

            builder = new NotificationCompat.Builder(context, id);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Construir la notificación para versiones de Android Oreo y superiores
            builder.setContentTitle(getString(R.string.app_name1)).setCategory(Notification.CATEGORY_SERVICE)
                    .setSmallIcon(R.drawable.ic_notification)   // requerido
                    .setContentText(message)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_notification))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setSound(soundUri)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            // Construir la notificación y mostrarla
            Notification notification = builder.build();
            notifManager.notify(NOTIFY_ID, notification);

            // Iniciar el servicio en primer plano para versiones de Android Oreo y superiores
            startForeground(1, notification);

        } else {
            pendingIntent = PendingIntent.getActivity(context, 1, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Construir la notificación para versiones anteriores a Android Oreo
            notification = new NotificationCompat.Builder(this)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_notification))
                    .setSound(soundUri)
                    .setAutoCancel(true)
                    .setContentTitle(getString(R.string.app_name1)).setCategory(Notification.CATEGORY_SERVICE)
                    .setContentText(message).build();

            // Mostrar la notificación
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
