package com.example.mysqlite_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {
    private Contact contact;
    private EditText editName,editNumber,editEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editName = (EditText) findViewById(R.id.editTextTextPersonName);
        editNumber = (EditText) findViewById(R.id.editTextTextPersonName3);
        editEmail = (EditText) findViewById(R.id.editTextTextPersonName5);
        contact = (Contact) getIntent().getSerializableExtra("contact");
        editName.setText(contact.getName());
        editEmail.setText(contact.getEmail());
        editNumber.setText(contact.getNumber());

    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add:
                // save the new comment to the database
                contact.setEmail(editEmail.getText().toString());
                contact.setName(editName.getText().toString());
                contact.setNumber(editNumber.getText().toString());
                 new ContactDataSource(this).updateContact(contact);
                 Intent i = new Intent(MainActivity2.this,MainActivity.class);
                 startActivity(i);
                break;
            case R.id.delete:
                    new ContactDataSource(this).deleteContact(contact);
                break;
        }
    }

}