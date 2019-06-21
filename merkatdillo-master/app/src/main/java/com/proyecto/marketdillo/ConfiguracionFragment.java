package com.proyecto.marketdillo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfiguracionFragment extends Fragment {

    private EditText nombre;
    private EditText apellido;
    private TextView correo;
    private EditText celular;
    private EditText documentoidentidad;
    private EditText fechanacimiento;
    private EditText direccion;

    public ConfiguracionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_configuracion, container, false);
        nombre = (EditText) root.findViewById(R.id.nnnnombre);
        apellido = (EditText) root.findViewById(R.id.aaaapellido);
        correo = (TextView) root.findViewById(R.id.ccorreoespacio);
        cargar();
        ((VistaCampesino) getActivity()).hideFloatingActionButton();
        return root;
    }

    public void cargar(){
        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
        Usuario usuario = singletonUsuario.getUsuario();
        nombre.setText(usuario.getNombre());
        apellido.setText(usuario.getApellidos());
        correo.setText(usuario.getEmail());

    }

}
