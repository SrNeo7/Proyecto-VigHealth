package com.joseantonio.personalproject.proyectovighealth.workers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.joseantonio.personalproject.proyectovighealth.R;

import java.util.Random;

public class NotificacionesHidWorker extends Worker {
    public NotificacionesHidWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * doWork: lleva a cabo el trabajo solicitado mediante las WorkRequest
     * @return
     */
    @NonNull
    @Override
    public Result doWork() {


        String titulo = getInputData().getString("titulo");
        String descripcion = getInputData().getString("descripcion");
        int id = (int)getInputData().getLong("idRecordatorio",0);

        notificacionHid(titulo,descripcion);

        return Result.success();
    }

    /**
     * notificacionHid: crea y configura la notificacion para la funcion de recordatorios de Hidratacion
     * @param titulo
     * @param descripcion
     */
    private void notificacionHid(String titulo, String descripcion){

        String id ="message";
        NotificationManager notificationManager = (NotificationManager)getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),
                id);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(id,"nuevo",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Recordatorio Hidratación");
            notificationChannel.setShowBadge(true);
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{1000,1000,1000,1000,1000});
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        /*Intent intent = new Intent(getApplicationContext(), NuevoMedicamentoActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_ONE_SHOT);*/

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(titulo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setTicker("Recordatorio hidratación")
                .setSmallIcon(R.drawable.cup_water)
                .setContentText(descripcion)
                .setContentInfo("nuevo")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[]{1000,1000,1000,1000,1000});


        Random random = new Random();
        int idNotify = random.nextInt(8000);

        assert notificationManager != null;
        notificationManager.notify(idNotify,builder.build());
    }
}
