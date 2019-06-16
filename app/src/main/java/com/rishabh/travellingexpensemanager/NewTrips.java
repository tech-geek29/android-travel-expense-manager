package com.rishabh.travellingexpensemanager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.rishabh.travellingexpensemanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class NewTrips extends AppCompatActivity implements View.OnClickListener
{
    EditText et1;
    AutoCompleteTextView et2;
    AutoCompleteTextView et3;
    EditText et4;
    EditText et5;
    EditText et6;
    String s[] = {"New Delhi","Noida","Kanpur","Agra","Lucknow","Shimla","Chandigarh","Mathura","Mumbai","Pune","Bengluru","Banglore","Indore","Ujjain","Bhopal","Ahemdabad","Baroda","Chennai","Sikkim","Nagda","Dewas","Andaman/Nicobar Islands","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chandigarh","Chhattisgarh","Dadra/Nagar Haveli","Daman/Diu","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu/Kashmir","Jharkhand","Karnataka","Kerala","Lakshadweep","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Orissa","Pondicherry","Punjab","Rajasthan","Sikkim","Tamil Nadu","Tripura","Uttaranchal","Uttar Pradesh","West Bengal"};

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trips);
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        for(String so : s){
            countries.add(so);
        }

        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countries);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, s);


        et1= (EditText) findViewById(R.id.editText1);
        et2= (AutoCompleteTextView) findViewById(R.id.editText2);
        et3= (AutoCompleteTextView) findViewById(R.id.editText3);
        et4= (EditText) findViewById(R.id.editText4);
        et5= (EditText) findViewById(R.id.editText5);
        et6= (EditText) findViewById(R.id.editText6);
        et2.setAdapter(adapter);


        et3.setAdapter(adapter);

        Intent i=getIntent();
        String s=i.getStringExtra("title");
        et1.setText(s);
        et1.setInputType(InputType.TYPE_NULL);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        et4.setInputType(InputType.TYPE_NULL);
        et4.requestFocus();
        et5.setInputType(InputType.TYPE_NULL);
        et4.setOnClickListener(this);
        et5.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et4.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                et5.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }



    @Override
    public void onClick(View view) {
        if(view == et4) {
            fromDatePickerDialog.show();
        } else if(view == et5) {
            toDatePickerDialog.show();
        }

    }
    public void addTrip(View v) {
        SQLiteDatabase db = openOrCreateDatabase("Trips", MODE_APPEND, null);
        db.execSQL("create table if not exists tripDetails(" +
                "TripId VARCHAR primary key ," +
                "Title VARCHAR," +
                "source VARCHAR," +
                "destination VARCHAR," +
                "Startdate date," +
                "todate date," +
                "ApprovedBudget decimal(10,2)," +
                "Balance decimal(10,2)" +
                ")"
        );
        String s = null;
        int a;
        Cursor c = db.rawQuery("select * from tripDetails", null);
        if (c.getCount() == 0) {
            a = 0;
            s = String.valueOf(a);
        } else {
            if (c.moveToLast()) {
                s = c.getString(0);
                a = Integer.parseInt(s) + 1;
                s = String.valueOf(a);

            }
        }
        if (et2.getText().length()<1)
        {
            Toast.makeText(this, "Enter the Source", Toast.LENGTH_SHORT).show();
        }
        else if (et3.getText().length()<1)
        {
            Toast.makeText(this, "Enter the Destination", Toast.LENGTH_SHORT).show();

        }
        else if (et4.getText().length()<1)
        {
            Toast.makeText(this, "Enter the StartDate", Toast.LENGTH_SHORT).show();

        }
        else if (et5.getText().length()<1)
        {
            Toast.makeText(this, "Enter the EndDate", Toast.LENGTH_SHORT).show();

        }
        else if (et6.getText().length()<1)
        {
            Toast.makeText(this, "Enter the Aprroved Budget", Toast.LENGTH_SHORT).show();

        }
        else
        {

            db.execSQL("INSERT INTO tripDetails VALUES('" + s + "','" + et1.getText() + "','" +
                    et2.getText() + "','" +
                    et3.getText() + "','" +
                    et4.getText() + "','" +
                    et5.getText() + "','" +
                    Double.parseDouble(et6.getText().toString()) + "','"
                    +Double.parseDouble(et6.getText().toString()) + "');");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Success");
            builder.setMessage("Trip Added");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(NewTrips.this, TripsList.class);
                    startActivity(i);
                }
            });
            AlertDialog ad = builder.create();
            ad.show();


        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}
