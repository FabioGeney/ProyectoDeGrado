package com.proyecto.marketdillo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.support.constraint.Constraints.TAG;

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
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //click_action indica que activity abrir
        Intent intent = new Intent(click_action);
        //determina si la notificacion es de mensajes o de pedidos
        if(tipo.equals("Pedido")){
            //notificacion de pedidos
            String idPedido = remoteMessage.getData().get("pedidoID");
            getMercadillo(new PedidosToRealm(idPedido) );
            if(sizePedidos() == 1){
                intent.putExtra("idPedido", idPedido);
            }else{
                intent.putExtra("fragment", "pedidos");
                if(click_action.equals("com.proyecto.marketdillo.NOTIFICACIONCAMPESINO")){

                    click_action = "com.proyecto.marketdillo.NOTIFICACIONVISTACAMPESINO";
                }else {
                    click_action = "com.proyecto.marketdillo.NOTIFICACIONVISTAUSUARIO";
                }
            }

        }else{
            //notificacion de mensajes
            String id = remoteMessage.getData().get("remitenteID");
            intent.putExtra("nombreDestinatario", titulo);
            intent.putExtra("idDestinatario", id);

        }
        //al hacer clilck en la noificacion
        PendingIntent intentFilter = PendingIntent.getActivity(  this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intentFilter);

        int mNotificationID = (int)System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationID, builder.build());

    }
    //Este metodo busca en la base de datos local de Realm la id del pedido, si no esta lo agrega.
    //Esto se hace para evitar que las nuevas notificaciones de diferentes pedidos sobre escriban las notificaciones antiguas no abiertas por el usuario
    //Al haber notificaciones de mas de un pedido llevara al FragmentPedidos para mostras mejor la informacion de los nuevos pedidos
    private void getMercadillo(PedidosToRealm pedidosToRealm){
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<PedidosToRealm> res = realm.where(PedidosToRealm.class).equalTo("idPedido", String.valueOf(pedidosToRealm.getIdPedido()))
                .findAll();
        if(res.isValid() && !res.isEmpty()) {

        }else {
            agregarPedidoss( pedidosToRealm);
        }
    }
    //agrega la id de pedidos a la base de datos local
    private void agregarPedidoss(PedidosToRealm pedidosToRealm){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(pedidosToRealm);
        realm.commitTransaction();
    }
    //devulve la cantidad de pedidos agregados a la base de datos local
    private int sizePedidos(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<PedidosToRealm> query = realm.where(PedidosToRealm.class).findAll();
        return query.size();
    }


}
