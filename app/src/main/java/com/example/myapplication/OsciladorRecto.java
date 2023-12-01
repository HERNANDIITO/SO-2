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
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class OsciladorRecto extends AppCompatActivity{

    private EditText r1Edit, cEdit, r2Edit, freq;
    private Spinner r1Spinner, r2Spinner, cSpinner, freqSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oscilador_recto);

        r1Edit = (EditText) findViewById(R.id.r1);
        r2Edit = (EditText) findViewById(R.id.r2);
        cEdit = (EditText) findViewById(R.id.c);
        freq = (EditText) findViewById(R.id.freq);

        r1Spinner =   (Spinner) findViewById(R.id.r1_spinner);
        r2Spinner =   (Spinner) findViewById(R.id.r2_spinner);
        cSpinner =    (Spinner) findViewById(R.id.c_spinner);
        freqSpinner = (Spinner) findViewById(R.id.f_spinner);

        ArrayAdapter<CharSequence> resAdapter  = ArrayAdapter.createFromResource(this, R.array.resistencias,  R.layout.spinner);
        ArrayAdapter<CharSequence> cAdapter    = ArrayAdapter.createFromResource(this, R.array.condensadores, R.layout.spinner);
        ArrayAdapter<CharSequence> freqAdapter = ArrayAdapter.createFromResource(this, R.array.frecuencia,    R.layout.spinner);

        resAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        freqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        r1Spinner.setAdapter(resAdapter);
        r2Spinner.setAdapter(resAdapter);
        cSpinner.setAdapter(cAdapter);
        freqSpinner.setAdapter(freqAdapter);

        r1Edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if ( r1Edit.getText().toString().isEmpty() ) {
                    r2Edit.setEnabled(true);
                    r2Edit.setHintTextColor(getResources().getColor(R.color.white));

                } else {
                    r2Edit.setEnabled(false);
                    r2Edit.setHintTextColor(getResources().getColor(R.color.disabled));
                }
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
                    r1Edit.setEnabled(true);
                    r1Edit.setHintTextColor(getResources().getColor(R.color.white));

                } else {
                    r1Edit.setEnabled(false);
                    r1Edit.setHintTextColor(getResources().getColor(R.color.disabled));
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
        if ( r1Edit.getText().toString().isEmpty() && r2Edit.getText().toString().isEmpty()) {
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

        if ( !r1Edit.getText().toString().isEmpty() ) {
            r = Double.parseDouble(r1Edit.getText().toString());
            r = getTotalValue(r, r1Spinner);
        } else {
            r = Double.parseDouble(r2Edit.getText().toString());
            r = getTotalValue(r, r2Spinner);
            r = r / 2;
        }

        if ( r < 2 * Math.pow(10, 3) || r > Math.pow(10, 6) ) {
            errorToast("R debe estar comprendido en el intervalo [1 KΩ, 1 MΩ]");
            return;
        }

        MediaPlayer mpok= MediaPlayer.create(this,R.raw.ok);
        mpok.start();

        if ( c == 0 ) {
            errorToast("Introduce un valor de C");
            return;
        }

        double denom = 2.5 * r * c;
        double res = 1 / denom;

        res = transformValue(res, freqSpinner);

        BigDecimal textRes = new BigDecimal(String.valueOf(res)).setScale(4, BigDecimal.ROUND_FLOOR);

        freq.setText(String.valueOf(textRes));
    }
}
