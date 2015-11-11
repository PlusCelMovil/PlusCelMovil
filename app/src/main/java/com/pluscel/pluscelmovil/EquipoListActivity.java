package com.pluscel.pluscelmovil;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fasterxml.jackson.databind.DeserializationFeature;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.pluscel.pluscelmovil.serv.Equipos;
import com.pluscel.pluscelmovil.R;
import com.pluscel.pluscelmovil.dom.Equipo;
import com.pluscel.pluscelmovil.serv.Equipos;
import com.pluscel.pluscelmovil.dom.IsisService;
import com.pluscel.pluscelmovil.serv.Greeting;
import com.pluscel.pluscelmovil.serv.RestLink;
import com.pluscel.pluscelmovil.serv.RestLinks;
import com.pluscel.pluscelmovil.serv.Services;


public class EquipoListActivity extends Activity {
/**
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equipo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

**/
    String url;
    String user;
    String pass;
    Equipos equipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_list);

        ListView listview = (ListView) findViewById(R.id.listView_equipment);

        Intent intent = getIntent();
        url =  intent.getStringExtra("url")+ "services/EquipoRepositorio/actions/listarTodos/invoke";
        user =  intent.getStringExtra("user");
        pass =  intent.getStringExtra("pass");

        try {
            equipos = new FillListOfEquiposThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<RestLink> LinksAlumnosList = null;
        final List<String> listNombres = new ArrayList<String>();
        if (equipos !=null) {
            LinksAlumnosList = equipos.getResult().getValue();

            //tomar nombres de los alumnos

            for (RestLink alumnoLink : LinksAlumnosList) {
                listNombres.add(alumnoLink.getTitle());
            }
        }

        //llenar la lista
        final StableArrayAdapter adapter = new StableArrayAdapter(getBaseContext(),
                android.R.layout.simple_list_item_1, listNombres);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, final long id) {
                final String item = (String) parent.getItemAtPosition(position);

                Log.v("nombre",equipos.getResult().getValue().get(position).getTitle());
                Log.v("link", equipos.getResult().getValue().get(position).getHref());

                String urlEquipo = equipos.getResult().getValue().get(position).getHref();

                Intent newIntent = new Intent("android.intent.action.");
                newIntent.putExtra("user",user);
                newIntent.putExtra("pass",pass);
                newIntent.putExtra("url",urlEquipo);

                startActivity(newIntent);


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equipo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FillListOfEquiposThread extends AsyncTask<Void, Void, Equipos> {
        @Override
        protected Equipos doInBackground(Void...  params) {
            try {


                //Services services = null;
                Log.v("ingresando User y Pass", user + " : " + pass);
                // Set the username and password for creating a Basic Auth request
                HttpAuthentication authHeader = new HttpBasicAuthentication(user, pass);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                Log.v("ingresando URL",url);
                RestTemplate restTemplate = new RestTemplate();

                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                restTemplate.getMessageConverters().add(converter);
                String pep = pass;
               // Log.v("JSON leido", json);
                // Make the HTTP GET request to the Basic Auth protected URL
                ResponseEntity<Equipos> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Equipos.class);
               // Equipos equipos=restTemplate.getForObject(url,Equipos.class);
              //  Greeting greeting = restTemplate.getForObject("http://rest-service.guides.spring.io/greeting", Greeting.class);
                Equipos equipos = response.getBody();


                Log.v("listado Equipos contiene", equipos.getResult().getValue().size() +"");

                //

                int arraySize = equipos.getResult().getValue().size();
                RestLink[] equiposArray = new RestLink[arraySize];
                for (int i=0; i< arraySize;i++){
                    equiposArray[i] = equipos.getResult().getValue().get(i);
                    Log.v("Equipo Encontrado", equiposArray[i].getTitle());
                }
                return equipos;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
    public String loadJSON() {
        String json = null;
        try {
            FileInputStream fis = openFileInput("config.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            json = sb.toString();

        } catch (FileNotFoundException e) {
            json = "{ \"urlRestful\" : \"\",  \"user\" : \"\",  \"pass\" : \"\",  \"save\" : \"\"}";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
