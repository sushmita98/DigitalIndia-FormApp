package com.digitalIndiaLeaders.formapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText name,email,phoneNo,address;
    private FormDatabase db;

    String gen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new FormDatabase(this);
        name= findViewById(R.id.name);
        email= findViewById(R.id.email);
        phoneNo= findViewById(R.id.phoneNo);
        address= findViewById(R.id.address);
        TextView userDetails = findViewById(R.id.userDetails);
        TextView gender = findViewById(R.id.gender);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameInput = name.getText().toString();
                String emailInput = email.getText().toString();
                validateEmail(emailInput);
                String phoneInput = phoneNo.getText().toString();
               validatePhone(phoneInput);
                String genderInput = gen;
                String addressInput = address.getText().toString();
                if(nameInput.isEmpty() || emailInput.isEmpty() || phoneInput.isEmpty() || genderInput == null || addressInput.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }else {
                    boolean emailChk = validateEmail(emailInput);
                    boolean phoneChk = validatePhone(phoneInput);
                    boolean ChkemailInput = db.chkemail(emailInput);
                    if (ChkemailInput && emailChk && phoneChk) {
                        db.insert(nameInput,emailInput,phoneInput,genderInput,addressInput);
                        Toast.makeText(getApplicationContext(), "Form submitted!!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, FormSubmission.class);
                        startActivity(i);
                    } else {
                        if(!phoneChk){
                            phoneNo.setError("Please enter valid phone number");
                        }else{
                            Toast.makeText(getApplicationContext(), "email already exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.maleButton:
                if (checked)
                    gen = "Male";
                    break;
            case R.id.femaleButton:
                if (checked)
                   gen = "Female";
                    break;
            case R.id.othersButton:
                if (checked)
                    gen = "Others";
                    break;
            default:
                    gen = "";
        }
    }

    private  boolean validateEmail(String emailInput){
        if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            email.setError("Please enter valid email address");
            return false;
        }else{
            email.setError(null);
            return true;
        }
    }
    private  boolean validatePhone(String phoneInput){
        if(!Patterns.PHONE.matcher(phoneInput).matches() || phoneInput.length() != 10){
            phoneNo.setError("Please enter valid phone number");
            return false;
        }else{
            phoneNo.setError(null);
            return true;
        }
    }
}
