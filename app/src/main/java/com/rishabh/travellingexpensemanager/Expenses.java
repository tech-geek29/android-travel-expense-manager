package com.rishabh.travellingexpensemanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rishabh.travellingexpensemanager.Main2Activity;
import com.rishabh.travellingexpensemanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Expenses extends AppCompatActivity implements View.OnClickListener
{

    String s1[] = {"Food","Travel","Lodging","Miscellaneous"};

    String s2[] = {"BreakFast","Lunch","Dinner"};
    String s3[] = {"Train","Public Transport","Flight","Private Transport"};
    String s4[] = {"Hotel","Room on Rent","Other"};
    String s5[] = {"Recharge","Shopping","Other"};

    ArrayAdapter adapter,adapter1,adapter2,adapter3,adapter4,adapter5;
    Spinner sp1,sp2;
    TextView tv1,tv2,tv3,tv33;
    EditText et1;
    EditText et2;
    Button bt1;
    String id,sd1,sd2,title,budget;
    SQLiteDatabase db;
    SQLiteDatabase db1;
    Date d1,d2;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog date;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title=intent.getStringExtra("tit");
        sp1 = (Spinner) findViewById(R.id.spinner);
        sp2 = (Spinner) findViewById(R.id.spinner2);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView3);
        tv33 = (TextView) findViewById(R.id.textView33);
        tv3 = (TextView) findViewById(R.id.textView4);
        et2=(EditText)findViewById(R.id.editText7) ;
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        et2.setInputType(InputType.TYPE_NULL);
        et2.requestFocus();
        et2.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                db = openOrCreateDatabase("TripDatabase",MODE_APPEND, null);
                db1 = openOrCreateDatabase("Trips", MODE_APPEND, null);
                Date dNow = new Date( );
                SimpleDateFormat ft =
                        new SimpleDateFormat ("dd-MM-yyyy");

                Cursor cb = db1.rawQuery("Select Startdate,todate from tripdetails where TripId='"+id+"'",null);
                if(cb.moveToNext()){
                if(dateFormatter.format(newDate.getTime()).compareTo(cb.getString(0))>=0 && dateFormatter.format(newDate.getTime()).compareTo(cb.getString(1))<=0 && dateFormatter.format(newDate.getTime()).compareTo(ft.format(dNow))<=0){
                et2.setText(dateFormatter.format(newDate.getTime()));}
                else if(ft.format(dNow).compareTo(cb.getString(0))<0){
                   // showMessage("Invalid Date","Trip not yet started...Scheduled Trip is From "+cb.getString(0));
                    AlertDialog.Builder builder=new AlertDialog.Builder(Expenses.this);
                    builder.setCancelable(true);
                    builder.setTitle("Invalid Date");
                    builder.setMessage("Trip not yet started...Scheduled Trip is From "+cb.getString(0));
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    AlertDialog ad = builder.create();
                    ad.show();

                }
                else if (dateFormatter.format(newDate.getTime()).compareTo(ft.format(dNow))>0){
                    showMessage("Date not yet arrived","Enter date within range from "+cb.getString(0)+" to "+ft.format(dNow));
                }
                else{
                    showMessage("Date out of range of scheduled trip","Enter date within range from "+cb.getString(0)+" to "+cb.getString(1));
                }}
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        tv1.append(" "+title);
        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,s1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,s2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,s3);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,s4);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter4 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,s5);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        sp1.setAdapter(adapter);
        et1 = (EditText) findViewById(R.id.editText1);
        bt1 = (Button) findViewById(R.id.button2);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                sd1 = s1[i];
                if(i == 0)
                {
                    sp2.setAdapter(adapter1);
                }
                else if(i == 1)
                {
                    sp2.setAdapter(adapter2);
                }
                else if(i == 2)
                {
                    sp2.setAdapter(adapter3);
                }
                else if(i == 3)
                {
                    sp2.setAdapter(adapter4);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });



        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                sd2 = s3[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        db = openOrCreateDatabase("TripDatabase",MODE_APPEND, null);
        db1 = openOrCreateDatabase("Trips", MODE_APPEND, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS expenses"+"(" +
                "ExpenseId VARCHAR primary key"+
                ",Category VARCHAR"+
                ",Particulars VARCHAR"+
                ",Amount VARCHAR"+
                ",Date date"+
                ",TripId VARCHAR references tripDetails(TripId))");

    }
    @Override
    public void onClick(View view) {
        if(view == et2) {
            date.show();
        }

    }

    public void addExpense(View view)
    {
        int a;
        String bd=null;
        String s = null;
        String str = et1.getText().toString();
        //  int bdg=Integer.parseInt(str);
        // Cursor cd =db.rawQuery("SELECT * FROM tripd where TripId = '"+id+"'", null);
        // if(cd.moveToNext())
        //     bd = cd.getString(6);

        //int amt = Integer.parseInt(bd) - bdg;

        Cursor c=db.rawQuery("SELECT * FROM expenses", null);
        if(c.getCount()==0){
            a=0;
            s = String.valueOf(a);

        }
        else {

            if (c.moveToLast()) {
                s = c.getString(0);
                a=Integer.parseInt(s)+1;
                s=String.valueOf(a);

            }
        }
        if (et2.getText().length()<1)
        {
            Toast.makeText(this, "Enter the Date", Toast.LENGTH_SHORT).show();
        }

        else if (et1.getText().length()<1)
        {
            Toast.makeText(this, "Enter the Amount", Toast.LENGTH_SHORT).show();

        }
        else {
            db.execSQL("INSERT INTO expenses VALUES('" + s + "','" + sd1 +
                    "','" + sd2 + "','" + et1.getText() + "','" + et2.getText() + "','" + id + "');");
            Cursor cc = db1.rawQuery("Select * from tripDetails where TripId='" + id + "'", null);
            while (cc.moveToNext()) {
                budget = cc.getString(7);
            }

            double i = Double.parseDouble(et1.getText().toString());
            double b = Double.parseDouble(budget);
            if (b >= i) {
                b = b - i;
                db1.execSQL("Update tripDetails set Balance=" + b + " Where TripId=" + "'" + id + "'");
                showMessage("Success", "Expenses Added");
            } else {
                Toast.makeText(this, "Expenses Exceeded the Budget", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void showMessage(final String title1, String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title1);
        builder.setMessage(message);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Intent i = new Intent(Expenses.this,Expenses.class);
                //i.putExtra("id",id);
                //i.putExtra("tit",title);

                //startActivity(i);
            }
        });
        AlertDialog ad = builder.create();
        ad.show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent in = new Intent(this,TripsList.class);
        startActivity(in);
    }
}