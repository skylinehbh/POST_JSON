package com.skyline.post_json;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends  AppCompatActivity {

    TextView tvIsConnected;
    EditText url_send;
    EditText etName;
    EditText etCountry;
    EditText etTwitter;
    TextView tvResult;
    TextView showjson ;
    Button Get ;
    JSONArray Var_array= new JSONArray() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        url_send = (EditText) findViewById(R.id.url_id);
        etName = findViewById(R.id.etName);
        etCountry = findViewById(R.id.etCountry);
        etTwitter = findViewById(R.id.etTwitter);
        tvResult = (TextView) findViewById(R.id.tvResult);
        showjson = (TextView)  findViewById(R.id.show_json);
        Get = (Button) findViewById(R.id.btnGet);
        checkNetworkConnection();

    }

    // check network connection
    public boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            // show "Connected" & type of network "WIFI or MOBILE"
            tvIsConnected.setText("Connected "+networkInfo.getTypeName());
            // change background color to red
            tvIsConnected.setBackgroundColor(0xFF7CCC26);


        } else {
            // show "Not Connected"
            tvIsConnected.setText("Not Connected");
            // change background color to green
            tvIsConnected.setBackgroundColor(0xFFFF0000);
        }

        return isConnected;
    }


    private String httpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);

        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // 2. build JSON object
        JSONObject jsonObject = buidJsonObject();
        //  Var_array.put(jsonObject);
        // Toast.makeText(this,Var_array.toString(),Toast.LENGTH_LONG).show();
        // show_text(Var_array.toString());

        // 3. add JSON content to POST request body
        setPostRequestContent(conn, jsonObject);

        // 4. make POST request to the given URL
        conn.connect();

        // 5. return response message
        return conn.getResponseMessage()+"";

    }


    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return httpPost(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            tvResult.setText(result);
        }
    }


    public void send(View view) throws JSONException {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        Var_array.put(buidJsonObject());
        show_text(Var_array.toString());
        String add_url = url_send.getText().toString();
        // perform HTTP POST request
        if(checkNetworkConnection()) {
           // new HTTPAsyncTask().execute("http://hmkcode.appspot.com/jsonservlet");
            // new  HTTPAsyncTask().execute("http://192.168.5.113:8220/api/QRCode/");
            new  HTTPAsyncTask().execute(add_url);
            //  showjson.setText(Var_array.toString());
        }
        else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();

        //  showjson.setText(Var_array.toString());
    }

    public void Get(View view) throws JSONException {
        Toast.makeText(this, "Get json ", Toast.LENGTH_SHORT).show();
        Intent get = new Intent(MainActivity.this,Get_json.class);
        startActivity(get);

    }

    private JSONObject buidJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("name", etName.getText().toString());
        jsonObject.accumulate("country",  etCountry.getText().toString());
        jsonObject.accumulate("twitter",  etTwitter.getText().toString());
        //  Var_array.put(jsonObject);
        //  jsonObject.accumulate("ID","10");
        // jsonObject.accumulate("Name","afsasa-3223j-dsdd");
        // showjson.setText(Var_array.toString());
        //  if(Var_array!=null)
        // show_text(Var_array.toString());
        //  Toast.makeText(this, Var_array.toString(),Toast.LENGTH_LONG).show();
        return jsonObject;

    }

    private void setPostRequestContent(HttpURLConnection conn, JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        //   showjson.setText(jsonObject.toString());
        Log.i(MainActivity.class.toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }
    public void show_text( String data)
    {
        TextView showjson ;
        showjson = (TextView)  findViewById(R.id.show_json);
        showjson.setText(data);
        return;
    }

}