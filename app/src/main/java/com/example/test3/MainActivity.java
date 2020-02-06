package com.example.test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity  {
    TextView edt;
    TextView edt1;
    Connection con;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=  findViewById(R.id.btn);
        edt = findViewById(R.id.edt);
        edt1= findViewById(R.id.edt1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK","");
                new Login().execute();
            }
        });
    }

    private class Login extends AsyncTask<Void, Void, Void> {
        String getName;
        String getPass;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getName = edt.getText().toString();
            getPass = edt1.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
               // StrictMode.enableDefaults();
                con = DriverManager.getConnection("jdbc:jtds:sqlserver://30.30.30.243:1433/Менеджеры;user=wsr-2;password=xxNTeL9FT2;");
                if (con == null){
                    Log.d("SHOTO NE TAK", "");
                   // Toast.makeText(this, "Ошибка подключения! /n Пожалуйста повторите попытку!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String query = "select * from Менеджеры where Name = '"+getName+"' and Parol='"+ getPass+"' ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()){
                        //Toast.makeText(MainActivity.this,"Вход успешен",Toast.LENGTH_LONG).show();
                        con.close();
                        Intent intent = new Intent(MainActivity.this, Tests.class);
                        intent.putExtra("NAME",getName);
                        startActivity(intent);
                    }
                    else{
                        Log.d("SQLLOGIN", "НЕТЬ");
                        //Toast.makeText(this, "Ошибка вводв данных!Повторите попытку", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (Exception ex){
                Log.d("SQLEX", ex.toString());
              //  Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }
}
