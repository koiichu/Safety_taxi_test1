package test.tabbar6.Tab3_fake_call;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import test.tabbar6.R;

/**
 * Created by KMITL on 06/01/2016.
 */
public class Fakecall1 extends Activity {

    //TextView nameView;

    MediaPlayer mpBgm; /*ประกาศ class*/
    ToggleButton tbBGM; /*Toggle Button ปิดเปิดเสียง*/


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_three);

       /* Bundle userName = getIntent().getExtras();
        String text = userName.getString("Name");
        TextView textView = (TextView)findViewById(R.id.textView);*/


        Intent _inboundIndex = getIntent();
        String userName = _inboundIndex.getStringExtra("name");
        //Toast.makeText(getApplicationContext(),userName ,Toast.LENGTH_SHORT).show();


        //String userName = getIntent().getStringExtra("name");
        //nameView = (TextView)findViewById(R.id.nameView);

        TextView nameView = new TextView(this);
        nameView.setText(userName);
        //setContentView(nameView);


        //---------------------------------------------------------------------
        mpBgm = MediaPlayer.create(Fakecall1.this, R.raw.ringtone);
        mpBgm.setLooping(true); /*กำหนดให้วนลูปตลอด*/
        mpBgm.start();/*สั่งให้เริ่มเล่นไฟล์เสียง bgm ทันทีเมื่อผู้ใช้เปิดแอปฯ*/

        tbBGM = (ToggleButton) findViewById(R.id.bt_Sound);
        tbBGM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /*กำหนดค่าให้กับ tbBGM ของคลาส ToggleButton และเรียกใช้ Listener
            เมื่อกดให้ปุ่ม Toggle Button เป็น ON (True) ก็จะให้เล่นเสียง BGM*/
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1)
                    mpBgm.start();
                else
                    mpBgm.stop();
            }


        });

        tbBGM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Fakecall1.this, Fakecall.class);
                startActivity(i);
            }
        });

    }

    public void onResume() {
        super.onResume();
        if (tbBGM.isChecked())
            mpBgm.start();
    }
        /* ฟังก์ชัน onResume ที่จะทำงานเมื่อเปิดแอปพลิเคชันขึ้นมา
        หรือว่าย่อแอปพลิเคชันไว้ แล้วเปิดเข้ามาใหม่อีกครั้ง
        โดยฟังก์ชันนี้ก็จะให้เช็คว่าปุ่ม tbBGM ที่เป็น Toggle Button
        ว่าปุ่มดังกล่าวเป็น ON หรือ OFF ถ้ามีสถานะเป็น ON
        ก็จะให้เล่นไฟล์เสียง BGM ต่อจากของเดิม
        */

    public void onPause() {
        super.onPause();
        mpBgm.pause();
    }
    /*onPause หยุดเล่นเมื่อออกจากแอป เปิดแอปเข้ามาก็เล่นต่อด้วย onResume*/

    public void onDestroy() {
        super.onDestroy();
        mpBgm.stop();
        mpBgm.release();
        mpBgm = null;
    }
     /*หยุดเล่นเมื่อออกจากแอป แล้วเครียร์ข้อมูลใน mpBgm ทิ้ง เป็นการยกเลิกการเล่นเสียงไปเลย*/
}
