package test.tabbar6.Tab3_fake_call;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import test.tabbar6.R;

/**
 * Created by KMITL on 06/01/2016.
 */
public class Fakecall extends Activity {

    // TextView nameView;
    private TextView calltext;
    private EditText edit_NameCall;
    private Button bt_Call, bt_Call2, bt_Call3, bt_Call4, bt_Call5;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //nameView = (TextView)findViewById(R.id.nameView);
        //userName = (EditText)findViewById(R.id.edit_NameCall);
        //userName.setText("Your name...");

        bt_Call = (Button) findViewById(R.id.bt_Call);
        bt_Call2 = (Button) findViewById(R.id.bt_Call2);
        bt_Call3 = (Button) findViewById(R.id.bt_Call3);
        bt_Call4 = (Button) findViewById(R.id.bt_Call4);
        bt_Call5 = (Button) findViewById(R.id.bt_Call5);

        bt_Call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Fakecall.this, Fakecall1.class);
                EditText userName = (EditText)findViewById(R.id.edit_NameCall);
                i.putExtra("Name",userName.getText().toString());
                startActivity(i);
            }


        });

        bt_Call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Fakecall.this, Fakecall1.class);

                startActivity(i);
            }
        });

    }

}

