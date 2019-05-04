package com.proyecto.marketdillo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.proyecto.marketdillo.fragments.EmpezarFragment;
import com.proyecto.marketdillo.fragments.InicioFragment;
import com.proyecto.marketdillo.fragments.VitrinaFragment;

/*Esta es la clase donde va el fragment manager, que da soporte a
los fragment InicioFragment, VitrinaFragment y EmpezarFragment, a su
vez se utiliza un bottom navigation y se utilizan 3 botones, cada uno
para un fragment diferente
* */

public class BottomActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new InicioFragment());
    }
    /*llama al fragment manager, el cual empieza la transaccion y reemplaza el fragment
    * deseado a usar*/
    private boolean loadFragment (Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit();

            return true;
        }
        return false;

    }
    /*Usa un switch dependiendo del fragment que se escoja y crea una instancia del fragment que se clickeo
    * */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()){

            case R.id.navigation_home:
                fragment = new InicioFragment();
                break;

            case R.id.navigation_vitrina:
                fragment = new VitrinaFragment();
                break;

            case R.id.navigation_start:
                fragment = new EmpezarFragment();
                break;

        }

        return loadFragment(fragment);
    }
}
