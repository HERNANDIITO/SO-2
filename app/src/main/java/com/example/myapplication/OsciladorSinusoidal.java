package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;

public class OsciladorSinusoidal extends AppCompatActivity{

    private EditText rEdit, cEdit, r1Edit, freq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oscilador_sinusoidal);
        rEdit = (EditText) findViewById(R.id.r);
        r1Edit = (EditText) findViewById(R.id.r1);
        cEdit = (EditText) findViewById(R.id.c);
        freq = (EditText) findViewById(R.id.freq);

        rEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if ( rEdit.getText().toString().isEmpty() ) {
                    r1Edit.setText("");
                    return;
                }

                double r = Double.parseDouble(rEdit.getText().toString());
                r1Edit.setText(String.valueOf(r * 29));
            }
        });
    }

    public void Back (View view) {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void Clear(View view) {
        rEdit.setText("");
        r1Edit.setText("");
        cEdit.setText("");
        freq.setText("");
    }

    public void Suma(View view) {
        if ( rEdit.getText().toString().isEmpty() ) {
            MediaPlayer mpok= MediaPlayer.create(this,R.raw.nok);
            mpok.start();

            String text = "Introduce un valor en R";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();

            return;
        }

        if ( cEdit.getText().toString().isEmpty() ) {
            MediaPlayer mpok= MediaPlayer.create(this,R.raw.nok);
            mpok.start();

            String text = "Introduce un valor en C";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();

            return;
        }

        MediaPlayer mpok= MediaPlayer.create(this,R.raw.ok);
        mpok.start();

        double r = Double.parseDouble(rEdit.getText().toString());
        double r1 = Double.parseDouble(r1Edit.getText().toString());
        double c = Double.parseDouble(cEdit.getText().toString());
        double denom = 2*Math.PI * r * c * Math.sqrt(6);
        double res = 1 / denom;

        BigDecimal textRes = new BigDecimal(String.valueOf(res)).setScale(4, BigDecimal.ROUND_FLOOR);

        freq.setText(String.valueOf(textRes));

    }
}
