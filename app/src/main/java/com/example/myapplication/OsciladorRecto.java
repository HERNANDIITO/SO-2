package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        r1Edit = (EditText) findViewById(R.id.r1);
        r2Edit = (TextView) findViewById(R.id.r2);
        cEdit = (EditText) findViewById(R.id.c);
        freq = (TextView) findViewById(R.id.freq);

        r1Edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if ( r1Edit.getText().toString().isEmpty() ) {
                    r2Edit.setText("");
                    return;
                }

                double r = Double.parseDouble(r1Edit.getText().toString());
                r2Edit.setText(String.valueOf(r * 2));
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

    public void Suma(View view) {
        double r1 = Double.parseDouble(r1Edit.getText().toString());
        double r2 = Double.parseDouble(r2Edit.getText().toString());
        double c = Double.parseDouble(cEdit.getText().toString());
        double denom = 2.5 * r1 * c;
        double res = 1 / denom;

        BigDecimal textRes = new BigDecimal(String.valueOf(res)).setScale(4, BigDecimal.ROUND_FLOOR);

        freq.setText(String.valueOf(textRes));

    }
}
