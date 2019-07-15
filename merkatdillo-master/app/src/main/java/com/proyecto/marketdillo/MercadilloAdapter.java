package com.proyecto.marketdillo;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class MercadilloAdapter extends RecyclerView.Adapter<MercadilloAdapter.ViewHolder> {

    private List<Mercadillo> mercadillos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;
    private Realm realm;

    public  MercadilloAdapter(List<Mercadillo> mercadillos, int layout, OnItemClickListener itemClickListener ){
        this.mercadillos = mercadillos;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        context = viewGroup.getContext();
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(mercadillos.get(i),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return mercadillos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView favorito;
        public ImageView imagen;
        public TextView nombre ;
        public TextView costoEnvio ;
        public TextView tiempoEnvio;
        public TextView calificacion ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             favorito = (ImageView) itemView.findViewById(R.id.favorito);
             imagen = (ImageView) itemView.findViewById(R.id.imagen);
             nombre = (TextView) itemView.findViewById(R.id.nombre);
             costoEnvio = (TextView)itemView.findViewById(R.id.costoEnvio);
             tiempoEnvio = (TextView)itemView.findViewById(R.id.tiempoEnvio);
             calificacion = (TextView) itemView.findViewById(R.id.calificacion);
        }
        public void bind( final Mercadillo mercadillo, final OnItemClickListener listener){
            Picasso.with(context).load(mercadillo.getImagen()).fit().into(imagen);
            nombre.setText( mercadillo.getNombre());
            costoEnvio.setText("Envio $ " +mercadillo.getCostoEnvio() );
            tiempoEnvio.setText( mercadillo.getTiempoEntrega());
            calificacion.setText(  mercadillo.getCalificacion());

            realm = Realm.getDefaultInstance();

            final boolean[] click = {true};

            if(getMercadillo(mercadillo)){
                favorito.setImageResource(R.drawable.ic_fav);
            }
            favorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!getMercadillo(mercadillo) && click[0]){
                        favorito.setImageResource(R.drawable.ic_fav);
                        click[0] = false;
                        agregarFavoritos(mercadillo);
                    }else {
                        favorito.setImageResource(R.drawable.ic_favorito);
                        eliminarFavorito(mercadillo);
                        click[0] = true;
                        notifyDataSetChanged();
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(mercadillo, getAdapterPosition());
                }
            });


        }
    }

    private void agregarFavoritos(Mercadillo mercadillo){
        realm.beginTransaction();
        realm.copyToRealm(mercadillo);
        realm.commitTransaction();
    }

    private boolean getMercadillo(Mercadillo mercadillo){
        final RealmResults<Mercadillo> res = realm.where(Mercadillo.class).equalTo("id", String.valueOf(mercadillo.getId()))
                .findAll();
        if(res.isValid() && !res.isEmpty()) {

            return true;
        }else {
            return false;
        }
    }

    private void eliminarFavorito(Mercadillo mercadillo){
        final RealmResults<Mercadillo> res = realm.where(Mercadillo.class).equalTo("id", String.valueOf(mercadillo.getId()))
                .findAll();
        if(res.isValid() && !res.isEmpty()) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    res.deleteAllFromRealm();
                }
            });
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(Mercadillo mercadillo, int posicion);

    }
}

