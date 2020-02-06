package com.example.test3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tests extends  AppCompatActivity{
        ListView LV;
    TextView txt;
        Connection con;
        LinearLayout ll;
        @Override
        protected void  onCreate(Bundle saveInstanceState){
            super.onCreate(saveInstanceState);
            getSupportActionBar().setTitle("Напоминалка");
            setContentView(R.layout.activity_tests);
            Intent intent = getIntent();
            txt = (TextView) findViewById(R.id.textView1);
            //txt.setText(intent.getStringExtra("text"));
            LV = findViewById(R.id.List);
            ll = findViewById(R.id.llNotify);
            new GetList().execute();


        }

        private class GetList extends AsyncTask<Void, Void, Void>{

           // ArrayList<String> alst = new ArrayList<>();

            ArrayList<HashMap<String,String>> table = new ArrayList<>();
            HashMap<String, String> hashMap;

            //ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
            //android.R.layout.simple_list_item_1, alst);
            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), table, R.layout.list_item,new String[]{"Дата", "Событие", "Комментарий"},
                    new int[]{
                            R.id.text_view_id, R.id.text_view_name, R.id.text_view_phone});

            ResultSet res;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //LV.setAdapter(adapter);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try{

                    con= DriverManager.getConnection("jdbc:jtds:sqlserver://30.30.30.243:1433/Менеджеры;user=wsr-2;password=xxNTeL9FT2;");
                    Statement statement= con.createStatement();
                    res = statement.executeQuery("Select * from Напоминания");


                }
                catch (Exception ex){
                    Log.d("SQLEX", ex.toString());
                    //Toast.makeText(this,"Ошибка подключения!",Toast.LENGTH_LONG).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {


                    while (res.next()) {
                        View notifyItem = LayoutInflater.from(getApplicationContext()).inflate(R.layout.notify_item, null, false);
                        TextView twType = (TextView)findViewById(R.id.twType);
                        TextView twDate = (TextView)findViewById(R.id.twDate);
                        TextView twCom = (TextView)findViewById(R.id.twCom);

                        twType.setText(res.getString("Событие"));
                        twDate.setText(res.getString("Дата"));
                        twCom.setText(res.getString("Комментарий"));

                        notifyItem.setOnClickListener(new View.OnClickListener() {
                            final int notifyid = res.getInt("id");
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        /*hashMap = new HashMap<>();
                        hashMap.put("Дата", res.getString("Дата"));
                        hashMap.put("Событие",res.getString("Событие"));
                        hashMap.put("Комментарий",res.getString("Комментарий"));
                        table.add(hashMap);
                        */
                        ll.addView(notifyItem);

                    }
                    //LV.setAdapter(adapter);
                }catch (Exception e){
                    Log.d("SQLEX", e.toString());
                }
            }
        }


}
