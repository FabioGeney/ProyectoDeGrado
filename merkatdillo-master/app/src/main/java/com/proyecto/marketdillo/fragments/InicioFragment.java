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
import com.proyecto.marketdillo.SliderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment{

    private ViewPager slideViewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;

    private SliderAdapter sliderAdapter;

    public InicioFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    public void addDotsIndicator(int position){

        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for(int i = 0; i < dots.length; i++){

            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            dotsLayout.addView(dots[i]);

        }

        if(dots.length > 0){

            dots[position].setTextColor(getResources().getColor(R.color.colorPrimary2));
        }
    }

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
