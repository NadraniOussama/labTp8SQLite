package com.example.mysqlite_2;

import android.app.ListActivity;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {
    private ContactDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new ContactDataSource(this);
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Contact> values = dataSource.getAllContact();
        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
//        ListView = (ListView) findViewById(android.R.id.list);

        // TODO: get a the contact of the item selected from ListView so we could send it to secound activity to manage
    }
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Contact> adapter = (ArrayAdapter<Contact>)getListAdapter();
        Contact contact = null;

        switch (view.getId()) {
            case R.id.add:
                String[] names = new String[] { "oussama", "alaoui",
                        "ali","ahmed","omar" };
                int nextInt = new Random().nextInt(3);
                StringBuilder numString = new StringBuilder ("06");
                for (int i = 0; i <= 7 ;i++) {
                    int num = new Random().nextInt(9);
                   numString.append(num);
                }

                // save the new comment to the database
                contact =dataSource.createContact(names[nextInt], numString.toString()
                        ,names[nextInt]+"@usmba.ac.ma");
                adapter.add(contact);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    contact = (Contact) getListAdapter().getItem(0);
                    dataSource.deleteContact(contact);
                    adapter.remove(contact);
                }
                break;
            case android.R.id.list:
                System.out.println("test");
                break;
        }
        adapter.notifyDataSetChanged();
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