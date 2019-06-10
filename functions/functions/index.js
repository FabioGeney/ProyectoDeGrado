'use-strict'
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.notificaCreacionPedido = functions.firestore.document('Pedidos/{pedidosId}')
.onCreate((snap, context) => {
 

  const idPedidos = context.params.pedidosId;

  return admin.firestore().collection("Pedidos").doc(idPedidos).get().then((dataSnap)=>{
    const compradorId = dataSnap.data().idConsumidor;
    const campesinoId = dataSnap.data().idCampesino;
    

    const compradorDatos = admin.firestore().collection("Consumidor").doc(compradorId).get();
    const campesinoDatos = admin.firestore().collection("Campesino").doc(campesinoId).get();

    return Promise.all([compradorDatos, campesinoDatos]).then(result=>{
      const nombreComprador = result[0].data().nombre;
      const tokenCampesino = result[1].data().token_id;
      
      const payload = {
        notification: {
          title : "Tienes un nuevo pedido ",
          body : "el usuario " + nombreComprador + " te ha hecho un pedido" ,
          icon: "default",
          click_action: "com.proyecto.marketdillo.NOTIFICACIONCAMPESINO"
        },
        data: {
          pedidoID : idPedidos
        }
      };

      return admin.messaging().sendToDevice( tokenCampesino, payload).then(result => {
        console.log("Notificacion enviada");
        return;
      });
    });
  });

  
});

exports.notificacionPedidoConsumidor = functions.firestore.document('Pedidos/{pedidosId}').onWrite((snap, context) =>{
  const idPedido = context.params.pedidosId;

  return admin.firestore().collection("Pedidos").doc(idPedido).get().then((dataSnap)=>{
    const compradorId = dataSnap.data().idConsumidor;
    const campesinoId = dataSnap.data().idCampesino;
    const estadoPedido = dataSnap.data().estado;

    const compradorDatos = admin.firestore().collection("Consumidor").doc(compradorId).get();
    const mercadilloDatos = admin.firestore().collection("Mercadillo").doc(campesinoId).get();

    return Promise.all([compradorDatos, mercadilloDatos]).then(result=>{
      const tokenComprador = result[0].data().token_id;
      const nombreMercadillo = result[1].data().nombre;
      
      
      const payload = {
        notification: {
          title : nombreMercadillo,
          body : "Tu pedido ha sido " + estadoPedido + " por el campesino" ,
          icon: "default",
          click_action: "com.proyecto.marketdillo.NOTIFICACIONCONSUMIDOR"
        },
        data: {
          pedidoID : idPedido
        }
      };

      return admin.messaging().sendToDevice( tokenComprador, payload).then(result => {
        console.log("Notificacion enviada");
        return;
      });
    });
  });


})
