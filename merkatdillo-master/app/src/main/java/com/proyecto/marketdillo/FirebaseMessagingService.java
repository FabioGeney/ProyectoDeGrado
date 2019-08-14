package com.proyecto.marketdillo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;

import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //extra la información de la notificacion y la almacena en variables
        String titulo = remoteMessage.getData().get("title");
        String cuerpo = remoteMessage.getData().get("body");
        String click_action = remoteMessage.getData().get("click_action");
        String tipo = remoteMessage.getData().get("tipo");

        //construye la notificacion
        NotificationCompat.Builder builder = new NotificationCompat.Builder(FirebaseMessagingService.this, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(cuerpo)
                .setAutoCancel(true)
                .setLights(Color.GREEN, 1000, 2000)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        //click_action indica que activity abrir
        Intent intent = new Intent(click_action);
        //determina si la notificacion es de mensajes o de pedidos
        if(tipo.equals("Pedido")){
            //notificacion de pedidos
            String idPedido = remoteMessage.getData().get("pedidoID");
            intent.putExtra("idPedido", idPedido);

        }else{
            //notificacion de mensajes
            String id = remoteMessage.getData().get("remitenteID");
            String nombre = remoteMessage.getData().get("nombre");
            intent.putExtra("nombreDestinatario", nombre);
            intent.putExtra("idDestinatario", id);

        }
        //al hacer clilck en la noificacion
        PendingIntent intentFilter = PendingIntent.getActivity(  this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intentFilter);

        int mNotificationID = (int)System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationID, builder.build());

    }



}
