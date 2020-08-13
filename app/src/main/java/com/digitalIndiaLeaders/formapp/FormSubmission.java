package com.digitalIndiaLeaders.formapp;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class FormSubmission extends AppCompatActivity {
    private FormDatabase db;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_submission);
        tableLayout = findViewById(R.id.displayLinear) ;
        TableRow rowHeader = new TableRow(this);
         rowHeader.setBackgroundColor(Color.parseColor("#9874a7"));
         rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        rowHeader.setPadding(5,0,5,0);
        String[] headerText={"NAME","EMAIL","PHONE_NO","ACTION"};

        for(String c:headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(16);
             tv.setPadding(5, 5, 5, 5);
             tv.setText(c);

            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);
        db = new FormDatabase(this);
        try
        {
            Cursor cursor = db.getData();
            if(cursor.getCount() >0)
            {
                while (cursor.moveToNext()) {
                    // Read columns data
                    String user_name= cursor.getString(cursor.getColumnIndex("name"));
                    final String email= cursor.getString(cursor.getColumnIndex("email"));
                    String phone_no = cursor.getString(cursor.getColumnIndex("mobile_no"));
                    // dara rows
                    TableRow row = new TableRow(this);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText={user_name,email,phone_no};
                    for(String text:colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(14);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    Button deleteBtn = new Button(this);
                    deleteBtn.setText("DELETE");
                    deleteBtn.setTextSize(14);
                    deleteBtn.setPadding(5, 0, 5, 5);
                    deleteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // row is your row, the parent of the clicked button
                            View row = (View) v.getParent();
                            // container contains all the rows, you could keep a variable somewhere else to the container which you can refer to here
                            ViewGroup container = ((ViewGroup)row.getParent());
                            // delete the row and invalidate your view so it gets redrawn
                            DeleteData(email);
                            container.removeView(row);
                            container.invalidate();
                        }
                    });
                    row.addView(deleteBtn);
                    tableLayout.addView(row);
                }
            }else{
                Toast.makeText(getApplicationContext(),"No records exist!!",Toast.LENGTH_SHORT).show();
            }
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.close();
            // Close database
        }
    }
    public void  DeleteData(String email){
        Integer deletedRows = db.deleteData(email);
        if(deletedRows > 0){
            Toast.makeText(getApplicationContext(),"Data deleted",Toast.LENGTH_SHORT).show();
        }
    }
}
