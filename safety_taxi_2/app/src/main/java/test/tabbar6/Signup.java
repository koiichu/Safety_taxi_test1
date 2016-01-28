package test.tabbar6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.ParseObject;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    ProgressDialog dialog;

    EditText username;
    EditText password;
    EditText conPass;
    EditText email;
    EditText tel;
    Button regis;
    Button bt_back;

    String username_a;
    String password_a;
    String conP;
    String Email;
    String Tel;

    String pwd;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        username = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        conPass = (EditText)findViewById(R.id.con_pass);
        email = (EditText)findViewById(R.id.email);
        tel = (EditText)findViewById(R.id.tel);
        regis = (Button)findViewById(R.id.bt_Regis);

        bt_back = (Button)findViewById(R.id.bt_back);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(Signup.this);
                dialog.setMessage("กำลังลงทะเบียน...");
                dialog.setCancelable(false);
                dialog.show();

                username_a = username.getText().toString();
                username_a = username_a.trim();
                password_a = password.getText().toString();
                conP = conPass.getText().toString();
                Email = email.getText().toString();
                Email = Email.trim();
                Tel = tel.getText().toString();
                Tel = Tel.trim();

                if (password_a.equals(conP)) {
                    pwd = password_a;
                } else {
                    dialog.dismiss();
                    password.setText("");
                    conPass.setText("");
                    Toast.makeText(Signup.this,
                            "กรุณากรอกรหัสผ่านอีกครั้ง",
                            Toast.LENGTH_SHORT).show();
                }

                int lengthPass = pwd.length();//count pwd
                if (lengthPass < 8) {
                    dialog.dismiss();
                    Toast.makeText(Signup.this,
                            "กรุณากรอกรหัสผ่านมากกว่า 8 ตัวอักษร",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (EMAIL_PATTERN.matcher(Email).matches()) {

                        if ("".equals(username_a) || "".equals(Tel)) {
                            dialog.dismiss();
                            Toast.makeText(Signup.this,
                                    "กรุณากรอกแบบฟอร์มลงทะเบียนให้สมบูรณ์",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            ParseUser user = new ParseUser();
                            user.setUsername(username_a);
                            user.setPassword(pwd);
                            user.setEmail(Email);
                            user.put("Tel", Tel);
                            user.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        dialog.dismiss();
                                        Toast.makeText(Signup.this,
                                                "ลงทะเบียนเรียบร้อย",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Signup.this, login.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(Signup.this,
                                                "ลงทะเบียนไม่สำเร็จ ชื่อผู้ใช้งานหรืออีเมล์นี่ได้ลงทะเบียนแล้ว",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    } else {
                        dialog.dismiss();
                        Toast.makeText(Signup.this,
                                "กรอกอีเมล์ไม่ถูกต้อง",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
