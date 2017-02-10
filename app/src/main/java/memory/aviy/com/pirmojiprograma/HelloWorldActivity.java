package memory.aviy.com.pirmojiprograma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class HelloWorldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world); // kodas sujungiamas su sablonu

        Button mygtukas = (Button) findViewById(R.id.mygtukas);

        mygtukas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HelloWorldActivity.this, "Paspaudei mygtuka",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


}
