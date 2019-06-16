package com.rishabh.travellingexpensemanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.rishabh.travellingexpensemanager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Update extends AppCompatActivity implements View.OnClickListener
{
    EditText et1,et2,et3,et4,et5,et6;
    SQLiteDatabase db;
    String s;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        et1=(EditText)findViewById(R.id.editText1);
        et2=(EditText)findViewById(R.id.editText2);
        et3=(EditText)findViewById(R.id.editText3);
        et4=(EditText)findViewById(R.id.editText4);
        et5=(EditText)findViewById(R.id.editText5);
        et6=(EditText)findViewById(R.id.editText6);
        Intent i=getIntent();
        s=i.getStringExtra("id");
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        et3.setInputType(InputType.TYPE_NULL);
        et3.requestFocus();
        et4.setInputType(InputType.TYPE_NULL);
        et3.setOnClickListener(this);
        et4.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et3.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et4.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        db=openOrCreateDatabase("Trips", MODE_APPEND, null);
        Cursor c=db.rawQuery("Select * from tripDetails where TripId='"+s+"'",null);
        String title=null;
        String sou=null;
        String des=null;
        String startdate=null;
        String enddate=null;
        String ab=null;
        while(c.moveToNext())
        {
            title = c.getString(1);
            sou = c.getString(2);
            des = c.getString(3);
            startdate = c.getString(4);
            enddate = c.getString(5);
            ab = String.valueOf(c.getDouble(6));
        }
        et6.setText(title);
        et1.setText(sou);

        et2.setText(des);
        et3.setText(startdate);
        et4.setText(enddate);
        et5.setText(ab);

    }
    @Override
    public void onClick(View view) {
        if (view == et3) {
            fromDatePickerDialog.show();
        } else if (view == et4) {
            toDatePickerDialog.show();
        }
    }
    public void update(View v)
    {
        db.execSQL("Update tripDetails set Title="+"'"+et6.getText()+"'"+
                    ",source="+"'"+et1.getText()+"'"+
                    ",destination="+"'"+et2.getText()+"'"+
                    ",Startdate="+"'"+et3.getText()+"'"+
                    ",todate="+"'"+et4.getText()+"'"+
                    ",ApprovedBudget="+Double.parseDouble(et5.getText().toString())
                    +" where TripId="+"'"+s+"'");
        Intent i=new Intent(this,TripsList.class);
        startActivity(i);
    }
}
