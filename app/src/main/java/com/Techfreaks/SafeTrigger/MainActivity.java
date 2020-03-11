package com.Techfreaks.SafeTrigger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static boolean MasterSwitchVal,Contact_BVal,Local_BVal,Media_BVal,Contact_SVal,Local_SVal,Media_SVal;
    Map<String,?> settings;
    SharedPreferences preferences;
    Set<String> ContactNames,ContactNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSettings();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Switch MasterSwitch = findViewById(R.id.Master_switch);

        final CheckBox Contact_B = findViewById(R.id.contact_B);
        final CheckBox Local_B = findViewById(R.id.local_auth_B);
        final CheckBox Local_S = findViewById(R.id.local_auth_S);
        final CheckBox Media_B = findViewById(R.id.media_push_B);
        final CheckBox Media_S = findViewById(R.id.media_push_S);
        final CheckBox Contact_S = findViewById(R.id.contact_S);
        final Button AddContact = findViewById(R.id.AddContact);

        MasterSwitch.setChecked(MasterSwitchVal);

        Contact_B.setChecked(Contact_BVal);
        Local_B.setChecked(Local_BVal);
        Media_B.setChecked(Media_BVal);

        Contact_S.setChecked(Contact_SVal);
        Local_S.setChecked(Local_SVal);
        Media_S.setChecked(Media_SVal);

        final ScrollView MainScroll = findViewById(R.id.MainScroll);
        if(MasterSwitchVal){
            MainScroll.setVisibility(View.VISIBLE);
        }
        else{
            MainScroll.setVisibility(View.GONE);
        }

        MasterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences = getApplicationContext().getSharedPreferences("safeTriggerSettings",MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putInt("MasterSwitchVal",(isChecked?1:0));
                edit.apply();
                MasterSwitchVal = isChecked;
                if(MasterSwitchVal) MainScroll.setVisibility(View.VISIBLE);
                else                MainScroll.setVisibility(View.GONE);
            }
        });
        Contact_B.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences = getApplicationContext().getSharedPreferences("safeTriggerSettings",MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putInt("Contact_BVal",(isChecked?1:0));
                edit.apply();
                Contact_BVal = isChecked;
            }
        });
        Local_B.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences = getApplicationContext().getSharedPreferences("safeTriggerSettings",MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putInt("Local_BVal",(isChecked?1:0));
                edit.apply();
                Local_BVal = isChecked;
            }
        });
        Media_B.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences = getApplicationContext().getSharedPreferences("safeTriggerSettings",MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putInt("Media_BVal",(isChecked?1:0));
                edit.apply();
                Media_BVal = isChecked;
            }
        });
        Contact_S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences = getApplicationContext().getSharedPreferences("safeTriggerSettings",MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putInt("Contact_SVal",(isChecked?1:0));
                edit.apply();
                Contact_SVal = isChecked;
            }
        });
        Local_S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences = getApplicationContext().getSharedPreferences("safeTriggerSettings",MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putInt("Local_SVal",(isChecked?1:0));
                edit.apply();
                Local_SVal = isChecked;
            }
        });
        Media_S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences = getApplicationContext().getSharedPreferences("safeTriggerSettings",MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putInt("Media_SVal",(isChecked?1:0));
                edit.apply();
                Media_SVal = isChecked;
            }
        });

        AddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectContact();
            }
        });
    }

    /*code for getting contact */
    static final int REQUEST_SELECT_CONTACT = 1;
    static final int REQUEST_SELECT_PHONE_NUMBER = 1;

    public void selectContact() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_CONTACT);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            try{
                ContactNames.add( contactUri.toString());
            }catch(NullPointerException e){
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    /*End of code for getting contact*/

    void getSettings(){
        preferences = getApplicationContext().getSharedPreferences("safeTriggerSettings",MODE_PRIVATE);
        settings = preferences.getAll();

        MasterSwitchVal = ((Object)1 == settings.get("MasterSwitchVal"));
        Contact_BVal    = ((Object)1 == settings.get("Contact_BVal"));
        Local_BVal      = ((Object)1 == settings.get("Local_BVal"));
        Media_BVal      = ((Object)1 == settings.get("Media_BVal"));
        Contact_SVal    = ((Object)1 == settings.get("Contact_SVal"));
        Local_SVal      = ((Object)1 == settings.get("Local_SVal"));
        Media_SVal      = ((Object)1 == settings.get("Media_SVal"));
        ContactNames = preferences.getStringSet("ContactNames",new HashSet<String>());
        ContactNumbers = preferences.getStringSet("ContactNumbers",new HashSet<String>());
    }
}
