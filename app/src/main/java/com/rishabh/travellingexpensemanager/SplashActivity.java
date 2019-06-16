package com.rishabh.travellingexpensemanager;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.rishabh.travellingexpensemanager.R;

public class SplashActivity extends AppCompatActivity
{
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        t=new Thread()
        {
            public void run()
            {

                try
                {
                    Thread.sleep(3000);


                }
                catch(Exception e)
                {
                }
                finally
                {
                    Intent i = new Intent(SplashActivity.this, Main2Activity.class);
                    startActivity(i);
                    finish();

                }
            }
        };
        t.start();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        System.exit(0);


    }
}
