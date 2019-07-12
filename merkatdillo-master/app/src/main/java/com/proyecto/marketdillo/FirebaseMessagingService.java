package com.proyecto.marketdillo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.RemoteMessage;

import static android.support.constraint.Constraints.TAG;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String titulo = remoteMessage.getData().get("title");
        String cuerpo = remoteMessage.getData().get("body");
        String click_action = remoteMessage.getData().get("click_action");
        String tipo = remoteMessage.getData().get("tipo");



        NotificationCompat.Builder builder = new NotificationCompat.Builder(FirebaseMessagingService.this, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(cuerpo)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent intent = new Intent(click_action);

        if(tipo.equals("Pedido")){
            String idPedido = remoteMessage.getData().get("pedidoID");
            getPedido(idPedido);
            SingletonPedido singletonPedido = SingletonPedido.getInstance();
            Pedidos pedido=singletonPedido.getPedido();


            intent.putExtra("pedido", pedido);
            if(click_action.equals("com.proyecto.marketdillo.NOTIFICACIONCONSUMIDORFIN")){
                intent.putExtra("visible", "si");
            }else {
                intent.putExtra("visible", "no");
            }

        }else{


        }

        PendingIntent intentFilter = PendingIntent.getActivity(  this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intentFilter);



        int mNotificationID = (int)System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationID, builder.build());

    }

    private void getPedido(String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Pedidos").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 if(task.isSuccessful()){
                     DocumentSnapshot document = task.getResult();
                     Pedidos pedido = document.toObject(Pedidos.class);
                     pedido.setIdDocument(document.getId());
                     SingletonPedido singletonPedido = SingletonPedido.getInstance();
                     singletonPedido.setPedido(pedido);
                 }
             }
        });
    }
}
