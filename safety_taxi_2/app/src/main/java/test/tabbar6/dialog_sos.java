package test.tabbar6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class dialog_sos extends AppCompatActivity {

    Button sos_ok;
    Button sos_cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sos);

        sos_ok = (Button) findViewById(R.id.dialog_ok);
        sos_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"เรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
            }
        });

        sos_cc = (Button) findViewById(R.id.dialog_cancle);
        sos_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish
            }
        });
    }
}
