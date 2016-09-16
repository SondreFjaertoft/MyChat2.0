package com.example.e_e_s.mychat20;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity

{
    ArrayList<ContactsActivity> contacts;
    public ListView Fornavn;
    private static final int PERMISSION_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.contacts = new ArrayList<contacts>()

        this.Fornavn = (ListView) findViewById(R.id.Fornavn);
        showContacts();
    }

    public void showContacts()
    {

        //Skjekker om SDK versonen har allerede gitt tilatelse eller ikke.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_READ_CONTACTS);
        }
        else
        {
            List<String> contacts = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
            Fornavn.setAdapter(adapter);
        }
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        if(requestCode == PERMISSION_REQUEST_READ_CONTACTS)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //WE HAVE A LIF OF BOYS!
                showContacts();
            }
            else
            {
                Toast.makeText(this, "Me treng tilatelsen din bro.. ok? ty", Toast.LENGTH_SHORT).show();
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }

        }
    }

    private List<String> getContactNames()
    {
        List<String> contacts = new ArrayList<>();

        // FInner contentresolver
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        //skjekker om cursor er tom og flytter den til første.
        if (cursor.moveToFirst())
        {
            //ittererer gjennom cursor
            do
            {
                //Finner kontakt navnene
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            }

            while(cursor.moveToNext());

        }
        // avslutter cursor
        cursor.close();
        return contacts;

    }

    public void loadContactInfo()
    {
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if(cursor.getCount() > 0)
        {
            while (cursor.moveToNext())
            {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                if(photoUri == null)
                {
                    photoUri = "";
                }
                String phoneNumb ="";
                if(cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    if(pCur.moveToFirst())
                    {
                        phoneNumb = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    pCur.close();
                }
                Log.d("Contact: ", id + " - " + name + " - " + phoneNumb + " - " + photoUri);
                // LEGG TIL CONTACTS UNDER HER SÅÅNDRÆ
                //OKTY??

                contacts.add(new ContactsActivity(id, name, phoneNumb, photoUri));
            }
        }

    }

}



