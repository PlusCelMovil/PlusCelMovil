package com.pluscel.pluscelmovil;

/**
 * Created by Pablo Pincheira on 11/10/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pluscel.pluscelmovil.serv.Services;

public class ServicesActivity  extends Activity  {

    String url;
    String user;
    String pass;
    Services services;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        //ListView listview = (ListView) findViewById(R.id.listView_service);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        user = intent.getStringExtra("user");
        pass = intent.getStringExtra("pass");
/*
        //Textview Titulo
        TextView title = (TextView) findViewById(R.id.textView_title);
        title.setText( title.getText() + ": " + user);
*/
        //Captura del evento Click del boton
         Button button_Equipment = (Button) findViewById(R.id.button_Equipment);
    }



    public void onClickButton_Equipment(View  view) {

                Intent intent = new Intent("android.intent.action.EQUIPO_lIST");

                intent.putExtra("url", url);
                intent.putExtra("user", user);
                intent.putExtra("pass", pass);

                startActivity(intent);
            }



    public void onClickButton_prueba(View  view) {

        Intent intent = new Intent("android.intent.action.EQUIPOS2");

        intent.putExtra("url", url);
        intent.putExtra("user", user);
        intent.putExtra("pass", pass);

        startActivity(intent);
    }

}


