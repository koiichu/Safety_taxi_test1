package test.tabbar6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

/**
 * Created by KMITL on 05/01/2016.
 */
public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())){

            Intent intent = new Intent(Main.this, login.class);
            startActivity(intent);
            finish();
        }
        else{
            ParseUser currntUser = ParseUser.getCurrentUser();
            if(currntUser != null){
                Intent intent = new Intent(Main.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(Main.this, login.class);
                startActivity(intent);
                finish();
            }

        }

    }
}
