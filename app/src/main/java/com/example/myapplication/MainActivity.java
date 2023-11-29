package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Sinusoidal (View view) {
        Intent ActividadSinusoidal = new Intent(this, OsciladorSinusoidal.class);
        startActivity(ActividadSinusoidal);
    }

    public void Recto (View view) {
        Intent ActividadRecto = new Intent(this, OsciladorRecto.class);
        startActivity(ActividadRecto);
    }
}
