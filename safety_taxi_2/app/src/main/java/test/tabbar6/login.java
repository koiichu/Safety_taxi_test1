package test.tabbar6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.ParseObject;

public class login extends AppCompatActivity {

    Button homebt;

    EditText username;
    EditText password;
    Button login;
    Button sign_up;
    TextView forget;

    ProgressDialog dialog;

    String username_a;
    String password_a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.bt_login);
        sign_up = (Button) findViewById(R.id.bt_signup);
        forget = (TextView) findViewById(R.id.forgetPwd);

        homebt = (Button) findViewById(R.id.button_home);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(login.this);
                dialog.setMessage("กำลังเข้าสู่ระบบ...");
                dialog.setCancelable(false);
                dialog.show();

                username_a = username.getText().toString();
                password_a = password.getText().toString();

                if(username_a.equals("") && password_a.equals("")){
                    dialog.dismiss();
                    Toast.makeText(login.this,
                            "กรุณากรอกชื่อผู้ใช้งาน และรหัสผ่าน",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    ParseUser.logInInBackground(username_a, password_a, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, com.parse.ParseException e) {

                            if (parseUser != null) {
                                Intent intent = new Intent(login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(login.this,
                                        "กรอกชื่อผู้ใช้งาน หรือรหัสผ่านผิด",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(login.this, Signup.class);
                startActivity(intent2);
                finish();
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(login.this, Resetpwd.class);
                startActivity(intent2);
                finish();
            }
        });

        homebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(login.this, MainActivity.class);
                startActivity(intent3);
                finish();
            }
        });
    }
}
