package com.proyecto.marketdillo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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

    private boolean twice = false;
    private final String TAG = this.getClass().getName();
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //sessionManager = new SessionManager(this);
        //sessionManager.checkLogin();
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

    @Override
    public void onBackPressed() {

        Log.d(TAG, "click");

        if(twice){
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            System.exit(0);
        }
        twice = true;
        Log.d(TAG, "twice" + twice);

        Toast.makeText(BottomActivity.this, "Vuelva a oprimir para salir", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
                Log.d(TAG, "twice" + twice);
            }
        }, 2000);
    }
}
