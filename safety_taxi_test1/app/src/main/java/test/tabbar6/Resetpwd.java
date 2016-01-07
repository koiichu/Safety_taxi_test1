package test.tabbar6;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.ParseObject;


public class Resetpwd extends AppCompatActivity {

    EditText email;
    Button reset;
    ProgressDialog dialog;
    Button bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpwd);

        email = (EditText)findViewById(R.id.email);
        reset = (Button)findViewById(R.id.bt_reset);
        bt_back = (Button)findViewById(R.id.bt_back);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Resetpwd.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(Resetpwd.this);
                dialog.setMessage("กำลังส่งข้อความไปยังอีเมล์...");
                dialog.setCancelable(false);
                dialog.show();

                final String email_n = email.getText().toString();
                ParseUser.requestPasswordResetInBackground(email_n, new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // An email was successfully sent with reset instructions.
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(Resetpwd.this);
                            builder.setTitle("ส่งข้อความสำเร็จ");
                            builder.setMessage("กรุณาตรวจสอบอีเมล์")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            email.setText("");
                                            return;
                                        }
                                    });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        } else {
                            // Something went wrong. Look at the ParseException to see what's up.
                            dialog.dismiss();
                            Toast.makeText(Resetpwd.this,
                                    "เกิดข้อผิดพลาด โปรดลองอีกครั้ง",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
