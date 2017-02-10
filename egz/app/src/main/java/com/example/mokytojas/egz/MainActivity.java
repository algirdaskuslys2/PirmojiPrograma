package com.example.mokytojas.egz;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mokytojas.egz.DB.KITM;
import com.example.mokytojas.egz.DB.StorageIOException;

public class MainActivity extends Activity {

    private EditText vardas, grupe, data;
    private Button saugoti, ieskoti, trinti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vardas = (EditText) findViewById(R.id.vardas);
        grupe = (EditText) findViewById(R.id.grupe);
        data = (EditText) findViewById(R.id.data);

        saugoti = (Button) findViewById(R.id.saugoti);
        trinti = (Button) findViewById(R.id.trinti);
        ieskoti = (Button) findViewById(R.id.ieskoti);

        this.saugoti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                saugotiIrasa();
            }

        });

        this.trinti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                trintiIrasa();
            }

        });

        this.ieskoti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ieskotiIrasu();
            }

        });

    }

    void saugotiIrasa() {

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(vardas.toString())) {
            vardas.setError(getString(R.string.klaida));
            focusView = vardas;
            cancel = true;
        }

        ModelClass model = new ModelClass(null, vardas.toString(), grupe.toString(), data.toString());

        try {
            KITM.getDB().saveModel(model);
        } catch (StorageIOException e) {
            e.printStackTrace();
        }

    }

    void trintiIrasa() {

    }

    void ieskotiIrasu() {

    }
}
