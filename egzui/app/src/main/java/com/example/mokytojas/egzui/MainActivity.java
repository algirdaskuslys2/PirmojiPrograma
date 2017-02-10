package com.example.mokytojas.egzui;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Student> studentList;
    private ViewGroup studentGroup;

    EditText vardas;
    EditText grupe;
    Button saugoti;
    Button ieskoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vardas = (EditText) findViewById(R.id.vardas);
        grupe = (EditText) findViewById(R.id.grupė);

        saugoti = (Button) findViewById(R.id.saugoti);
        ieskoti = (Button) findViewById(R.id.ieskoti);
/*
        this.studentGroup = (ViewGroup) findViewById(R.id.saved_students_container);
        this.studentGroup.removeAllViews();


        for (Student student : MainActivity.this.studentList) {
            final View locationItem = getLayoutInflater().inflate(
                    R.layout.user_item, MainActivity.this.studentGroup, false);
            final TextView title = (TextView) locationItem.findViewById(R.id.title);
            title.setText(student.getVardas());
            title.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                }
            });
            this.studentGroup.addView(locationItem);
        }

*/
        saugoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaugoti();
            }
        });
    }

    void onSaugoti()
    {
        boolean cancel = false;

        if (TextUtils.isEmpty(vardas.toString())) {
            vardas.setError("reikia užpildyti");
            cancel = true;
        }

        if (TextUtils.isEmpty(grupe.toString())) {
            grupe.setError("reikia užpildyti");
            cancel = true;
        }

        if (cancel == false) {// vartotojas užpildė privalomus laukelius
            Response.Listener<String> responseListener = new Response.Listener<String>() { //Kreipimasis iš/į Register.php
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            Toast.makeText(MainActivity.this, "Įrašas išsaugotas", Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Įrašo išsaugoti nepavyko.")
                                    .setNegativeButton("Bandykite dar kartą.", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {

                        Writer writer = new StringWriter();
                        PrintWriter printWriter = new PrintWriter(writer);
                        e.printStackTrace(printWriter);
                        String s = writer.toString();
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                }
            };

            RegisterRequest registerRequest = new RegisterRequest(vardas.toString(), grupe.toString(), responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(registerRequest);
        }

    }

}
