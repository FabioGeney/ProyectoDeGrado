package com.proyecto.marketdillo.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.AdapterView;

import com.proyecto.marketdillo.BottomActivity;
import com.proyecto.marketdillo.CrearCuentaActivity;
import com.proyecto.marketdillo.MainActivity;
import com.proyecto.marketdillo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmpezarFragment extends Fragment{

    private TextView txtunete;


    public EmpezarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Gets parámetros
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_empezar, container, false);
        txtunete = (TextView) root.findViewById(R.id.txtunete);

        txtunete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        return root;
    }

}
