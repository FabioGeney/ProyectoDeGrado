package com.proyecto.marketdillo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proyecto.marketdillo.R;
import com.proyecto.marketdillo.SessionManager;
import com.proyecto.marketdillo.SliderAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Este fragment es el primer fragment, el cual va el slider para ver varias imagenes del inicio de la app,
 * que trata de explicar las funcionalidades que tiene y su uso general.
 */
public class InicioFragment extends Fragment{

    private ViewPager slideViewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;

    private SliderAdapter sliderAdapter;
    private SessionManager sessionManager;
    public InicioFragment() {
        // Required empty public constructor
    }


    /*En un fragment se inicializa diferente, toca usar un root para poder inflar, encontrar vistas, etc
    * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);
        slideViewPager = (ViewPager) root.findViewById(R.id.slideViewPager);
        dotsLayout = (LinearLayout) root.findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(getActivity());
        slideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        slideViewPager.addOnPageChangeListener(viewListener);

        return root;
    }
    /*Codigo para agregar los puntos y la posicion del slider en el que se encuentra en determinado momento*/
    public void addDotsIndicator(int position){

        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for(int i = 0; i < dots.length; i++){

            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226")); /*la fuente es para la forma de los puntos*/
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            dotsLayout.addView(dots[i]);

        }

        if(dots.length > 0){

            dots[position].setTextColor(getResources().getColor(R.color.colorPrimary2));
        }
    }
    /*Se usa este metodo para que escuche y se sepa en que pagina esta al seleccionar cada slide*/
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
