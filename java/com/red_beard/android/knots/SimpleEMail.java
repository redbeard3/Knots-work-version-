package com.red_beard.android.knots;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SimpleEMail extends AppCompatActivity {
    Button send;
    EditText emailtext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_email);

        send = (Button) findViewById(R.id.emailsendbutton);
        emailtext = (EditText) findViewById(R.id.emailtext);

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                emailIntent.setType("plain/text");

                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[] { "red.beard@bk.ru" });

                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        emailtext.getText().toString());

                SimpleEMail.this.startActivity(Intent.createChooser(emailIntent,
                        "Отправка письма..."));
            }
        });
    }

    public static Intent newIntent (Context packageContext){
        Intent intent = new Intent(packageContext, SimpleEMail.class);
        return intent;
    }
}
