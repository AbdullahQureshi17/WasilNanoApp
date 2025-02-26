package com.nano.liteloan.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nano.liteloan.info.User;
import com.nano.liteloan.info.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "applicants";
    private static final String TABLE_CONTACTS = "applicant";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    private static final String EMAIL = "email";
    private static final String IMAGE = "image";
    private static final String GENDER = "gender";
    private static final String PARENTAGE = "parentage";
    private static final String CNIC = "cnic";
    private static final String MARTIAL_STATUS = "matrialstatus";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addContact(UserProfile contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhone());
        values.put(IMAGE , contact.getImage());
        values.put(GENDER , contact.getGender());
        values.put(PARENTAGE, contact.getParentage());
        values.put(MARTIAL_STATUS, contact.getMaritalStatus());
        values.put(EMAIL, contact.getEmail());

        // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    UserProfile getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

//        UserProfile contact = new UserProfile(
//                String.valueOf(cursor.getString(1)),
//                String.valueOf(cursor.getString(2)),
//                String.valueOf(cursor.getString(3)),
//                String.valueOf(cursor.getString(4)),
//                String.valueOf(cursor.getString(5)),
//                String.valueOf(cursor.getString(6)),
//                String.valueOf(cursor.getString(7)));
        // return contact
        return null;
    }

    // code to get all contacts in a list view
    public List<UserProfile> getAllContacts() {
        List<UserProfile> contactList = new ArrayList<UserProfile>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                UserProfile contact = new UserProfile();
                contact.setCnic(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                contact.setImage(cursor.getString(3));
                contact.setGender(cursor.getString(4));
                contact.setParentage(cursor.getString(5));
                contact.setMaritalStatus(cursor.getString(6));
                contact.setEmail(cursor.getString(7));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }



    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
