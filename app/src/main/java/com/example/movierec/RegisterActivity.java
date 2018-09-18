package com.example.movierec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register_btn_submit = (Button) findViewById(R.id.register_btn_submit);
        register_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText register_edit_account = (EditText) findViewById(R.id.register_edit_account);
                EditText register_edit_pwd = (EditText) findViewById(R.id.register_edit_pwd);
                EditText register_confirm_pwd = (EditText) findViewById(R.id.register_confirm_pwd);
                String userName = register_edit_account.getText().toString();
                String pwd = register_edit_pwd.getText().toString();
                String confirmpwd = register_confirm_pwd.getText().toString();
                if(userName.isEmpty())
                    Toast.makeText(RegisterActivity.this, "User Name is Empty!", Toast.LENGTH_LONG).show();
                else if(pwd.isEmpty())
                    Toast.makeText(RegisterActivity.this, "Password is Empty!", Toast.LENGTH_LONG).show();
                else if(!pwd.equals(confirmpwd))
                    Toast.makeText(RegisterActivity.this, "Confirm your Password!", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_LONG).show();
                    Intent registerI = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(registerI);
                }
            }
        });
    }
}
