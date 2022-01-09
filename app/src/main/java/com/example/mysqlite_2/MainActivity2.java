package com.example.mysqlite_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {
    private Contact contact = null;
    private EditText editName,editNumber,editEmail;
    private Button buttonAddUp;
    private ContactDataSource dataSource ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editName = (EditText) findViewById(R.id.editTextTextPersonName);
        editNumber = (EditText) findViewById(R.id.editTextTextPersonName3);
        editEmail = (EditText) findViewById(R.id.editTextTextPersonName5);
        buttonAddUp = (Button) findViewById(R.id.update);
        contact = (Contact) getIntent().getSerializableExtra("contact");
        dataSource = new ContactDataSource(this);
//        dataSource.createContact("Test","0678947589","test@email.com");
//        contact = dataSource.getContact(229);
        if(contact!=null) {
            buttonAddUp.setText("Update");
            editName.setText(contact.getName());
            editEmail.setText(contact.getEmail());
            editNumber.setText(contact.getNumber());
        }else {
            buttonAddUp.setText("Ajouter");
        }
    }

    public void onClick(View view) {
        Intent i = new Intent(MainActivity2.this,MainActivity.class);
        switch (view.getId()) {
            case R.id.update:
                String email = editEmail.getText().toString();
                String name = editName.getText().toString();
                String number= editName.getText().toString();
                if(buttonAddUp.getText().equals("Ajouter")){
                    // save the new comment to the database
                    dataSource.createContact(name,number,email);
                }else if (buttonAddUp.getText().equals("Update")){
                    contact.setEmail(email);
                    contact.setName(name);
                    contact.setNumber(number);
                    dataSource.updateContact(contact);
                }
                // close datasource is always an option
                break;
            case R.id.deleteThis:
                dataSource.deleteContact(contact);
                break;
        }
        startActivity(i);
    }
    @Override
    protected void onResume() {
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
}