package com.rishabh.travellingexpensemanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.rishabh.travellingexpensemanager.R;

public class Viewdetails extends AppCompatActivity
{
    EditText et1,et2,et3,et4,et5,et6;
    String s;
    String title=null;
    SQLiteDatabase db1;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdetails);
        et1=(EditText)findViewById(R.id.editText1);
        et2=(EditText)findViewById(R.id.editText2);
        et3=(EditText)findViewById(R.id.editText3);
        et4=(EditText)findViewById(R.id.editText4);
        et5=(EditText)findViewById(R.id.editText5);
        et6=(EditText)findViewById(R.id.editText6);
        et1.setInputType(InputType.TYPE_NULL);
        et2.setInputType(InputType.TYPE_NULL);
        et3.setInputType(InputType.TYPE_NULL);
        et4.setInputType(InputType.TYPE_NULL);
        et5.setInputType(InputType.TYPE_NULL);
        et6.setInputType(InputType.TYPE_NULL);
        Intent i=getIntent();
        s=i.getStringExtra("id");
        db1= openOrCreateDatabase("TripDatabase",Context.MODE_PRIVATE, null);
        db=openOrCreateDatabase("Trips",MODE_APPEND,null);

        String q="Select * from tripDetails where TripId='"+s+"'";
        Cursor c=db.rawQuery(q,null);

        String sou=null;
        String des=null;
        String startdate=null;
        String enddate=null;
        String ab=null;
        String Bal=null;
        while(c.moveToNext())
        {
            title=c.getString(1);
            sou=c.getString(2);
            des=c.getString(3);
            startdate=c.getString(4);
            enddate=c.getString(5);
            ab=String.valueOf(c.getDouble(6));
            Bal=String.valueOf(c.getDouble(7));
        }
        et1.setText(sou);
        et2.setText(des);

        et3.setText(startdate);
        et4.setText(enddate);
        et5.setText(ab);
        et6.setText(Bal);



    }
    public void addExpense(View v)
    {
        Intent i=new Intent(this,Expenses.class);
        i.putExtra("id",s);
        i.putExtra("tit",title);
        startActivity(i);

    }
    public void delete(View v)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete");
        builder.setMessage("Are You Sure You Want To delete The Trip");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                db1.execSQL("delete from expenses where TripId="+"'"+s+"'");
                db.execSQL("delete from tripDetails where TripId="+"'"+s+"'");

                //AlertDialog.Builder builder=new AlertDialog.Builder(Viewdetails.this);
                //builder.setCancelable(true);
                //builder.setTitle("Delete");
                //builder.setMessage("Trip Deleted");
                Intent i = new Intent(Viewdetails.this, Main2Activity.class);
                startActivity(i);
            }
        });
        AlertDialog ad = builder.create();
        ad.show();
       
    }
    public void update(View v)
    {
        Intent i=new Intent(this,Update.class);
        i.putExtra("id",s);
        startActivity(i);

    }

}
