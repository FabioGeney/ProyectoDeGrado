package com.proyecto.marketdillo;

import android.app.Application;


import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        setUpRealConfig();
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        realm.close();
    }

    private void setUpRealConfig(){
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

}