'use-strict'
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.notificaCreacionPedido = functions.firestore.document('Campesino/{campesinoId}/Pedidos/{pedidosId}')
.onCreate((snap, context) => {
 

  const idPedidos = context.params.pedidosId;
  const idCampesino = context.params.campesinoId;
  return admin.firestore().collection("Campesino").doc(idCampesino).collection("Pedidos").doc(idPedidos).get().then((dataSnap)=>{
    const compradorId = dataSnap.data().idConsumidor;
    const campesinoId = dataSnap.data().idCampesino;
    

    const compradorDatos = admin.firestore().collection("Consumidor").doc(compradorId).get();
    const campesinoDatos = admin.firestore().collection("Campesino").doc(campesinoId).get();

    return Promise.all([compradorDatos, campesinoDatos]).then(result=>{
      const nombreComprador = result[0].data().nombre;
      const tokenCampesino = result[1].data().token_id;
      
      const payload = {
        data: {
          title : "Tienes un nuevo pedido ",
          body : "el usuario " + nombreComprador + " te ha hecho un pedido" ,
          icon: "default",
          click_action: "com.proyecto.marketdillo.NOTIFICACIONVISTACAMPESINO",
          pedidoID : idPedidos,
          tipo : "Pedido"
        },
       
      };

      return admin.messaging().sendToDevice( tokenCampesino, payload).then(result => {
        console.log("Notificacion enviada");
        return;
      });
    });
  });

  
});

exports.notificacionPedidoConsumidor = functions.firestore.document('Consumidor/{idConsumidor}/Pedidos/{pedidosId}').onWrite((snap, context) =>{
  const idPedido = context.params.pedidosId;
  const idConsumidor = context.params.idConsumidor;
  return admin.firestore().collection("Consumidor").doc(idConsumidor).collection("Pedidos").doc(idPedido).get().then((dataSnap)=>{
    const compradorId = dataSnap.data().idConsumidor;
    const campesinoId = dataSnap.data().idCampesino;
    const estadoPedido = dataSnap.data().estado;
    const cal = dataSnap.data().calificacion;

    const compradorDatos = admin.firestore().collection("Consumidor").doc(compradorId).get();
    const mercadilloDatos = admin.firestore().collection("Mercadillo").doc(campesinoId).get();

    if(cal === 0 && estadoPedido !== "Creado"){
      return Promise.all([compradorDatos, mercadilloDatos]).then(result=>{
        const tokenComprador = result[0].data().token_id;
        const nombreMercadillo = result[1].data().nombre;
  
        var mensaje = "Tu pedido ha sido rechazado por el campesino";
        var check = "com.proyecto.marketdillo.NOTIFICACIONCONSUMIDOR";
  
        if(estadoPedido === "Confirmado"){
          mensaje = "Tu pedido ha sido confirmado por el campesino";
        }
        if(estadoPedido === "Enviado"){
          mensaje = "Tu pedido ha sido enviado por el campesino";
        }
        if(estadoPedido === "Finalizado"){
          mensaje = "Pedido finalizado, no olvides calificarlo";
          //check = "com.proyecto.marketdillo.NOTIFICACIONCONSUMIDORFIN"
        }
        
        
        const payload = {
          data: {
            title : nombreMercadillo,
            body : mensaje,
            icon: "default",
            click_action: check,
            pedidoID : idPedido,
            tipo : "Pedido"
          },
                  

        };
  
        return admin.messaging().sendToDevice( tokenComprador, payload).then(result => {
          console.log("Notificacion enviada");
          return;
        });
      });
    }else{
      return;
    }


  });


});

exports.notificacionMensaje = functions.database.ref('/{idDestino}/{idRemintente}/Mensajes/{idMensaje}').
onWrite((snap, context) => {
  const idDestinatario = context.params.idDestino;
  const idRemintente = context.params.idRemintente;
  const idMensaje = context.params.idMensaje;

  const queryRemintente = admin.database()
  .ref(`/${idDestinatario}/${idRemintente}/Mensajes/${idMensaje}`).once('value');
  const queryTokenId = admin.database()
  .ref(`/${idDestinatario}/TokenId`).once('value');
  const queryDatos = admin.database()
  .ref(`/${idDestinatario}/${idRemintente}/Datos/`).once('value');

  return Promise.all([queryRemintente, queryTokenId, queryDatos]).then(result => {
    const de = result[0].val().de;
    const token = result[1].val().token_id;
    const nombreRemintente = result[2].val().nombre;
    const mensaje = result[2].val().ultimoMensaje;
    const check = "com.proyecto.marketdillo.NOTIFICACIONMENSAJE"
      if(de !== idDestinatario){
        const payload = {
          data: {
            title : nombreRemintente,
            body : mensaje,
            icon: "default",
            click_action: check,
            remitenteID : de,
            nombre : nombreRemintente,
            tipo : "Mensaje"
          },
        };
        return admin.messaging().sendToDevice( token, payload).then(result => {
          console.log("Notificacion enviada");
          return;
        });
  
      }else{
        return;
      }
    
  });
  

});

exports.calcularPormedio = functions.firestore.document('Mercadillo/{mercadilloId}/Calificacion/{calificacionId}').onWrite((snap, context) =>{

  const mercadilloId = context.params.mercadilloId;

  const calificaciones = admin.firestore().collection("Mercadillo").doc(mercadilloId).collection("Calificacion").get().then( (dataSnap) => {
    let contador = 0;
    let suma = 0;
   
    //const calificacionesResult = dataSnap.data().promedio;
    dataSnap.forEach(data =>{
        suma = suma + parseFloat(data.data().promedio);
        contador = contador + 1;
          
    });

    const promedio = (suma/contador).toFixed(1);
    console.log("suma: " + suma + " contador: " + contador + " promedio: " + (suma/contador).toFixed(1) );


    return admin.firestore().collection("Mercadillo").doc(mercadilloId).update("calificacion", promedio); 
    
  });

 
});