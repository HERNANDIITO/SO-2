package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;

public class OsciladorSinusoidal extends AppCompatActivity {

    private EditText rEdit, cEdit, r1Edit, freq;
    private Spinner rSpinner, cSpinner, r1Spinner, freqSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oscilador_sinusoidal);
        rEdit =  (EditText) findViewById(R.id.r);
        r1Edit = (EditText) findViewById(R.id.r1);
        cEdit =  (EditText) findViewById(R.id.c);
        freq =   (EditText) findViewById(R.id.freq);

        rSpinner =    (Spinner) findViewById(R.id.r_spinner);
        r1Spinner =   (Spinner) findViewById(R.id.r1_spinner);
        cSpinner =    (Spinner) findViewById(R.id.c_spinner);
        freqSpinner = (Spinner) findViewById(R.id.f_spinner);

        ArrayAdapter<CharSequence> resAdapter  = ArrayAdapter.createFromResource(this, R.array.resistencias,  R.layout.spinner);
        ArrayAdapter<CharSequence> cAdapter    = ArrayAdapter.createFromResource(this, R.array.condensadores, R.layout.spinner);
        ArrayAdapter<CharSequence> freqAdapter = ArrayAdapter.createFromResource(this, R.array.frecuencia,    R.layout.spinner);

        resAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        freqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        rSpinner.setAdapter(resAdapter);
        r1Spinner.setAdapter(resAdapter);
        cSpinner.setAdapter(cAdapter);
        freqSpinner.setAdapter(freqAdapter);

        rEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if ( rEdit.getText().toString().isEmpty() ) {
                    r1Edit.setEnabled(true);
                    r1Edit.setHintTextColor(getResources().getColor(R.color.white));

                } else {
                    r1Edit.setEnabled(false);
                    r1Edit.setHintTextColor(getResources().getColor(R.color.disabled));
                }
            }
        });

        r1Edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if ( r1Edit.getText().toString().isEmpty() ) {
                    rEdit.setEnabled(true);
                    rEdit.setHintTextColor(getResources().getColor(R.color.white));

                } else {
                    rEdit.setEnabled(false);
                    rEdit.setHintTextColor(getResources().getColor(R.color.disabled));
                }
            }
        });

    }

    public double getTotalValue( double currentValue, Spinner spinner ) {
        double result = currentValue;
        switch ( spinner.getSelectedItemPosition() ) {
            case 0:
                break;
            case 1:
                result = result * Math.pow(10, 3);
                break;
            case 2:
                result = result * Math.pow(10, 6);
                break;
        }
        return result;
    }

    public double getTotalC( double currentValue, Spinner spinner ) {
        double result = currentValue;
        switch (spinner.getSelectedItemPosition() ) {
            case 0:
                result = result * Math.pow(10, -12);
                break;
            case 1:
                result = result * Math.pow(10, -9);
                break;
            case 2:
                result = result * Math.pow(10, -6);
                break;
        }
        return result;
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

    public void errorToast(String error) {
        MediaPlayer mpok= MediaPlayer.create(this,R.raw.nok);
        mpok.start();

        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, error, duration);
        toast.show();

        return;
    }

    public void Suma(View view) {
        if ( rEdit.getText().toString().isEmpty() && r1Edit.getText().toString().isEmpty()) {
            errorToast("Introduce un valor en R o R1");
            return;
        }

        if ( cEdit.getText().toString().isEmpty() ) {
            errorToast("Introduce un valor en C");
            return;
        }

        double r;
        double c = Double.parseDouble(cEdit.getText().toString());
        c = getTotalC(c, cSpinner);

        if ( !rEdit.getText().toString().isEmpty() ) {
            r = Double.parseDouble(rEdit.getText().toString());
            r = getTotalValue(r, rSpinner);
        } else {
            r = Double.parseDouble(r1Edit.getText().toString());
            r = getTotalValue(r, r1Spinner);
            r = r / 29;
        }

        if ( r < Math.pow(10, 3) || r > Math.pow(10, 6) ) {
            errorToast("R debe estar comprendido en el intervalo [1 KΩ, 1 MΩ]");
            return;
        }

        MediaPlayer mpok= MediaPlayer.create(this,R.raw.ok);
        mpok.start();

        if ( c == 0 ) {
            errorToast("Introduce un valor de C");
            return;
        }

        double denom = 2 * Math.PI * r * c * Math.sqrt(6);
        double res = 1 / denom;

        res = transformValue(res, freqSpinner);

        BigDecimal textRes = new BigDecimal(String.valueOf(res)).setScale(4, BigDecimal.ROUND_FLOOR);

        freq.setText(String.valueOf(textRes));

    }

    private double transformValue(double totalValue, Spinner spinner) {
        double result = totalValue;
        switch (spinner.getSelectedItemPosition()) {
            case 0:
                break;
            case 1:
                result = result / Math.pow(10, 3);
                break;
            case 2:
                result = result / Math.pow(10, 6);
        }
        return result;
    }
}
