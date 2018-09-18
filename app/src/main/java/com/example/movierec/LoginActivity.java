package com.example.movierec;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movierec.Helper.DataBaseHelper;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        Button login_btn_register = (Button) findViewById(R.id.login_btn_register);
        Button login_btn_login = (Button) findViewById(R.id.login_btn_login);

        login_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logI =new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(logI);
            }
        });


        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText login_edit_account = (EditText) findViewById(R.id.login_edit_account);
                EditText login_edit_pwd = (EditText) findViewById(R.id.login_edit_pwd);
                String userID = login_edit_account.getText().toString();
                String userPwd = login_edit_pwd.getText().toString();

                if(userID.equals("mg")) {
                    Intent intent = new Intent(LoginActivity.this, MgMainActivity.class);
                    startActivity(intent);
                }

                DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());
                try {
                    myDbHelper.createDataBase();
                } catch (IOException e) {
                    throw new Error("Unable to create database");
                }
                try {
                    myDbHelper.openDataBase();
                } catch(SQLException e) {
                    throw e;
                }
                String table = "cunemf";
                String selection = "userID=?";
                String[] selectionArgs = new String[]{userID};
                Log.d("MainActivity", userID);
                Cursor cursor = myDbHelper.getCursor(table, null, selection,
                        selectionArgs, null, null, null);
                if (cursor.moveToFirst() == false) {
                    Toast.makeText(getApplicationContext(), "Logon Failure", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent logI = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle logB = new Bundle();
                    logB.putString("userOnline", userID);
                    logI.putExtras(logB);
                    startActivity(logI);
                }
                cursor.close();
            }
        });
    }
}
