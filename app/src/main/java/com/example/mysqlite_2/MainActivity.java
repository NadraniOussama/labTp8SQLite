package com.example.mysqlite_2;

import android.app.ListActivity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    }
    @Override
    protected void onListItemClick(ListView listView, View v, int position, long id) {
        Intent i = new Intent(MainActivity.this,MainActivity2.class);
        i.putExtra("contact",(Contact) getListAdapter().getItem(position));
        startActivityForResult(i,0);
    }
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Contact> adapter = (ArrayAdapter<Contact>)getListAdapter();
        Contact contact = null;

        switch (view.getId()) {
            case R.id.add:
//                adapter.add(createRandomContact()); // uncomment si vous voulez generer avec un click des contact random
                Intent i = new Intent(MainActivity.this,MainActivity2.class);
                startActivityForResult(i,0);
//                contact = new Contact(229,"Nadrani","Hey","nadrani@something.jk");
//                dataSource.updateContact(contact);
//                adapter.remove(dataSource.getContact(229));
//                adapter.insert(contact,0);
//                adapter.add(contact);

                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    contact = (Contact) getListAdapter().getItem(0);
                    dataSource.deleteContact(contact);
                    adapter.remove(contact);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }
    private Contact createRandomContact(){
        Contact contact = new Contact();
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
        return contact;
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