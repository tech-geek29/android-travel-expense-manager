package com.rishabh.travellingexpensemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rishabh.travellingexpensemanager.R;

public class MainActivity extends AppCompatActivity
{
    EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=(EditText)findViewById(R.id.editText);
        et1.setText(null);

    }
    public void travel(View v)
    {
        String s=et1.getText().toString();
        if(s.equals(null) || s.equals(""))
        {
            Toast.makeText(this,"Enter The Title of Journey",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent i=new Intent(this,NewTrips.class);
            i.putExtra("title",s);
            startActivity(i);
        }
    }
}
