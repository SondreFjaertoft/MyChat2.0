package com.example.e_e_s.mychat20;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by e-e-s on 16.09.2016.
 */

public class ContactsActivity implements Parcelable {

    private String id;
    private String name;
    private String phoneNumb;
    private Uri photoUri;

    private ArrayList<String> message;
    String lastMessage;


    public ContactsActivity(String id, String name, String phoneNumb, String photoUri)
    {
        this.id = id;
        this.name = name;
        this.phoneNumb = phoneNumb;
        this.photoUri = Uri.parse(photoUri);


    }





    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
