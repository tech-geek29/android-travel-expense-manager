package com.rishabh.travellingexpensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.rishabh.travellingexpensemanager.R;
public class DayWiseExpense extends AppCompatActivity
{
    String id;
    SQLiteDatabase db1;
    SQLiteDatabase db2;
    Cursor c1;
    Cursor c2;
    TextView tv1,tv2,tv3,tv4,tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_wise_expense);
        Intent i=getIntent();
        id=i.getStringExtra("id");
        db1=openOrCreateDatabase("Trips",MODE_APPEND, null);
        db2=openOrCreateDatabase("TripDatabase",MODE_APPEND, null);
        tv1=(TextView)findViewById(R.id.textView1);
        tv2=(TextView)findViewById(R.id.textView2);
        tv3=(TextView)findViewById(R.id.textView3);
        tv4=(TextView)findViewById(R.id.textView4);
        tv5=(TextView)findViewById(R.id.textView5);
        Cursor c1=db1.rawQuery("Select * from tripDetails where TripId="+"'"+id+"'",null);
        while(c1.moveToNext())
        {
            tv1.append(c1.getString(1));
            tv2.append("\n"+c1.getString(2));
            tv3.append("\n"+c1.getString(3));

        }
        Cursor c2=db2.rawQuery("select Date, Amount from expenses where TripId="+"'"+id+"'",null);
        while(c2.moveToNext())
        {
            tv4.append("\n\n"+c2.getString(0));
            tv5.append("\n\n"+c2.getString(1));
        }

    }
}
