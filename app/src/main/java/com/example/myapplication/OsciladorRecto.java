package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class OsciladorRecto extends AppCompatActivity{

    private EditText r1Edit, cEdit, r2Edit, freq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oscilador_recto);

        r1Edit = (EditText) findViewById(R.id.r1);
        r2Edit = (EditText) findViewById(R.id.r2);
        cEdit = (EditText) findViewById(R.id.c);
        freq = (EditText) findViewById(R.id.freq);

        r1Edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if ( r1Edit.getText().toString().isEmpty() ) {
                    r2Edit.getText().clear();
                    return;
                }

                double r1 = Double.parseDouble(r1Edit.getText().toString());

                if ( r2Edit.getText().toString().isEmpty() ) {
                    r2Edit.setText(String.valueOf(r1 / 2));
                    return;
                }

                double r2 = Double.parseDouble(r2Edit.getText().toString());

                if ( r2 == r1 * 2 ) { return; }

                if ( r1 == r2 / 2 || r1 == r2 * 2 ) { return; }
            }
        });

        r2Edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if ( r2Edit.getText().toString().isEmpty() ) {
                    r1Edit.setText("");
                    return;
                }

                double r2 = Double.parseDouble(r2Edit.getText().toString());

                if ( r1Edit.getText().toString().isEmpty() ) {
                    r1Edit.setText(String.valueOf(r2 * 2));
                    return;
                }

                double r1 = Double.parseDouble(r1Edit.getText().toString());

                if ( r1 == r2 / 2 || r1 == r2 * 2 ) { return; }

                r1Edit.setText(String.valueOf(r2 * 2));
            }
        });

    }

    public void Back (View view) {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void Clear(View view) {
        r1Edit.setText("");
        r2Edit.setText("");
        cEdit.setText("");
        freq.setText("");
    }

    public void errorToast(String error) {
        MediaPlayer mpok= MediaPlayer.create(this,R.raw.nok);
        mpok.start();

        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, error, duration);
        toast.show();

        return;
    }

    public void Suma(View view) {
        if ( r1Edit.getText().toString().isEmpty() ) {
            errorToast("Introduce un valor en R1");
            return;
        }

        if ( cEdit.getText().toString().isEmpty() ) {
            errorToast("Introduce un valor en C");
            return;
        }

        double r1 = Double.parseDouble(r1Edit.getText().toString());
        double r2 = Double.parseDouble(r2Edit.getText().toString());
        double c = Double.parseDouble(cEdit.getText().toString());

        if ( r1 < 2000 || r1 > 1000000 ) {
            errorToast("R1 debe estar comprendido en el intervalo [2 kΩ, 1 MΩ]");
            return;
        }

        double denom = 2.5 * r1 * c;
        double res = 1 / denom;

        BigDecimal textRes = new BigDecimal(String.valueOf(res)).setScale(4, BigDecimal.ROUND_FLOOR);

        freq.setText(String.valueOf(textRes));

        MediaPlayer mpok= MediaPlayer.create(this,R.raw.ok);
        mpok.start();

    }
}
