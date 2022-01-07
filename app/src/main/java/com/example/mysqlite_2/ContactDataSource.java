package com.example.mysqlite_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactDataSource {
        private SQLiteDatabase db;
        private MySQLiteHelper dbHelper;
        private String [] allColumns= { MySQLiteHelper.COLUMN_ID , MySQLiteHelper.COLUMN_NAME ,
                MySQLiteHelper.COLUMN_NUMBER, MySQLiteHelper.COLUMN_EMAIL};
        public ContactDataSource(Context context){
            dbHelper = new MySQLiteHelper(context);
        }

        public void open() throws SQLException {
            db = dbHelper.getWritableDatabase();
        }

        public Contact updateContact(Contact contact){
            ContentValues values = new ContentValues();
            db.updateWithOnConflict(MySQLiteHelper.TABLE_CONTACT_LIST,values,MySQLiteHelper.COLUMN_ID+ " = "+contact.getId()
                    ,null,0);
            return contact;
        }
        public Contact createContact(String contact,String number,String email){
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_NAME,contact);
            values.put(MySQLiteHelper.COLUMN_NUMBER,number);
            values.put(MySQLiteHelper.COLUMN_EMAIL,email);
            long insertId = db.insert(MySQLiteHelper.TABLE_CONTACT_LIST,null , values);
            Cursor cursor = db.query(MySQLiteHelper.TABLE_CONTACT_LIST, allColumns,
                    MySQLiteHelper.COLUMN_ID + " = " + insertId
                    ,null,null,null,null);
            cursor.moveToFirst();
            Contact newContact = cursorToContact(cursor);
            cursor.close();
            return newContact ;
        }

        public void deleteContact(Contact contact){
            long id = contact.getId();
            System.out.println("contact_service delete with id:"+ id );
            db.delete(MySQLiteHelper.TABLE_CONTACT_LIST,MySQLiteHelper.COLUMN_ID+" = " + id
                    ,null );
        }

        public List<Contact> getAllContact(){
            List<Contact> contactList = new ArrayList<Contact>();
            Cursor cursor = db.query(MySQLiteHelper.TABLE_CONTACT_LIST,
                    allColumns,null,null,null,null,null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Contact contact = cursorToContact(cursor);
                contactList.add(contact);
                cursor.moveToNext();
            }
            return contactList;
        }

        private Contact cursorToContact(Cursor cursor){
            Contact contact = new Contact();
            contact.setId(cursor.getLong(0));
            contact.setName(cursor.getString(1));
            contact.setNumber(cursor.getString(2));
            contact.setEmail(cursor.getString(3));

            return contact;
        }

        public void close() {
            dbHelper.close();
        }
}
