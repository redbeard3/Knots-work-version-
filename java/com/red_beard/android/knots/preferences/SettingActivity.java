package com.red_beard.android.knots.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by red beard on 27.09.2018.
 */

public class SettingActivity extends Activity {



   @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);

       getFragmentManager().beginTransaction()
               .replace(android.R.id.content, new SettingFragment())
               .commit();
   }

    public static Intent newIntent (Context packageContext){
        Intent intent = new Intent(packageContext, SettingActivity.class);
        return intent;
    }

}
