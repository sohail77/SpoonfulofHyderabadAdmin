package com.sohail.spoonfulofhyderabadadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    EditText name,email;
    Button findBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.nameEditTxt);
        email=(EditText)findViewById(R.id.emailEditTxt);
        findBtn=(Button) findViewById(R.id.findBtn);
            findBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, DetailActivity.class);
                    i.putExtra("code", name.getText().toString());
                    i.putExtra("email", email.getText().toString());
                    startActivity(i);
                }
            });

    }
}
