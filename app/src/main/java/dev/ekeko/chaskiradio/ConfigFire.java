package dev.ekeko.chaskiradio;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class ConfigFire extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //Esto es para tener los datos localmente aunque se reinicie el dispositivo
    }
}
