package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

public class OsciladorRecto extends AppCompatActivity{

    private EditText r1Edit, cEdit;
    private TextView r2Edit, freq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oscilador_recto);
    }

    public void Back (View view) {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void Clear(View view) {
        r1Edit = (EditText) findViewById(R.id.r1);
        r2Edit = (TextView) findViewById(R.id.r2);
        cEdit = (EditText) findViewById(R.id.c);
        freq = (TextView) findViewById(R.id.freq);

        r1Edit.setText("");
        r2Edit.setText("");
        cEdit.setText("");
        freq.setText("");
    }

    public void Suma(View view) {
        r1Edit = (EditText) findViewById(R.id.r1);
        r2Edit = (TextView) findViewById(R.id.r2);
        cEdit = (EditText) findViewById(R.id.c);
        freq = (TextView) findViewById(R.id.freq);

        double r1 = Double.parseDouble(r1Edit.getText().toString());
        double r2 = Double.parseDouble(r2Edit.getText().toString());
        double c = Double.parseDouble(cEdit.getText().toString());
        double res;

        res = 1 / (2 * 3.14 * r1 * c * (6^(1/2)) );
        BigDecimal textRes = new BigDecimal(String.valueOf(res)).setScale(4, BigDecimal.ROUND_FLOOR);

        freq.setText(String.valueOf(textRes));

    }
}
