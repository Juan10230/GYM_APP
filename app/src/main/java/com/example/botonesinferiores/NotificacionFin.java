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

// Servicio de notificación para la finalización
public class NotificacionFin extends IntentService {

    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private static int NOTIFICATION_ID = 1;
    Notification notification;

    // Constructor con nombre para el servicio de notificación de finalización
    public NotificacionFin(String name) {
        super(name);
    }

    // Constructor predeterminado requerido por IntentService
    public NotificacionFin() {
        super("SERVICE");
    }

    // Método para manejar la intención de servicio
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(Intent intent2) {
        // ID del canal de notificación
        String NOTIFICATION_CHANNEL_ID = getApplicationContext().getString(R.string.app_name2);
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, RutinasFragment.class);
        Resources res = this.getResources();
        // URI del sonido de la notificación
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);

        String message = getString(R.string.notification2);

        // Verifica la versión de Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final int NOTIFY_ID = 0; // ID de la notificación
            String id = NOTIFICATION_CHANNEL_ID; // ID del canal predeterminado
            String title = NOTIFICATION_CHANNEL_ID; // Canal predeterminado
            PendingIntent pendingIntent;
            NotificationCompat.Builder builder;
            NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // Comprueba si el administrador de notificaciones es nulo y lo crea si es necesario
            if (notifManager == null) {
                notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);

            // Crea un canal de notificación si aún no existe
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }

            builder = new NotificationCompat.Builder(context, id);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Configuración de la notificación para versiones de Android superiores a Oreo
            builder.setContentTitle(getString(R.string.app_name2))
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentText(message)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_notification))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setSound(soundUri)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            // Construir y mostrar la notificación
            Notification notification = builder.build();
            notifManager.notify(NOTIFY_ID, notification);

            // Iniciar el servicio en primer plano con la notificación
            startForeground(1, notification);

        } else {
            // Configuración de la notificación para versiones de Android inferiores a Oreo
            pendingIntent = PendingIntent.getActivity(context, 1, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification = new NotificationCompat.Builder(this)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_notification))
                    .setSound(soundUri)
                    .setAutoCancel(true)
                    .setContentTitle(getString(R.string.app_name2)).setCategory(Notification.CATEGORY_SERVICE)
                    .setContentText(message).build();

            // Mostrar la notificación
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
