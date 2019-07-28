package com.proyecto.marketdillo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/*Clase creada para poder usar un slider*/
public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    /*Aqui va la informacion que se va a mostrar en el slider*/
    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.registro,
            R.drawable.descubre,
            R.drawable.buscar,
            R.drawable.sube,
            R.drawable.vende,
            R.drawable.canasta,
            R.drawable.comunica2
    };

    public String[] slide_headings = {
            "Regístrate",
            "Descubre",
            "Busca",
            "Sube",
            "Vende",
            "Llena Tu Canasta",
            "Comunícate"
    };

    public  String[] slide_desc ={
            "Y únete al mercado virtual",
            "Productores, mercadillos y productos agrícolas",
            "Los productos que más consumas",
            "Los productos que ofreces",
            "Si eres agricultor, y encuentra nuevos clientes",
            "Con lo que más te gusta",
            "Con tu cliente o productor"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (ConstraintLayout) o;
    }
    /*Instanciar e inicializar los textview y el imageview de este slide adapter*/
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }
    /*Para ir borrando cada vista anterior del slider, y solo dejar la actual*/
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
