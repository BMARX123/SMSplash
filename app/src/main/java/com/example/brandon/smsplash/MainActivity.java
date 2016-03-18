package com.example.brandon.smsplash;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button contacts;
    private Button send;
    private EditText numberEntry;
    private final int REQUEST_CODE = 99;
    private SendTexts sendTexts;
    private String num;
    private EditText sendTime;
    private EditText messageField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("SMSplash");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        contacts = (Button) findViewById(R.id.contacts_button);
        send = (Button) findViewById(R.id.send_button);
        numberEntry = (EditText) findViewById(R.id.number_field);
        sendTime = (EditText) findViewById(R.id.time_field);
        messageField = (EditText) findViewById(R.id.message_field);


        contacts.setOnClickListener(this);
        send.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.contacts_button:
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
                break;


            case R.id.send_button:

                if(numberEntry.getText().toString().equals("")){
                    Toast.makeText(this, "Please Enter A Phone Number", Toast.LENGTH_SHORT).show();
                    break;
                }

                if(sendTime.getText().toString().equals("")){
                    Toast.makeText(this, "Please Enter The Number Of Messages", Toast.LENGTH_SHORT).show();
                    break;
                }

                if(messageField.getText().toString().equals("")){
                    Toast.makeText(this, "Please Enter A Message", Toast.LENGTH_SHORT).show();
                    break;
                }

                sendTexts = new SendTexts(num,sendTime, messageField, numberEntry);
                sendTexts.sendMessages();
                Toast.makeText(this, "Messages Sent", Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        num = "";
                        if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (numbers.moveToNext()) {
                                num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                //Toast.makeText(MainActivity.this, "Number=" + num, Toast.LENGTH_LONG).show();
                                numberEntry.setText(num);

                            }
                        }
                    }
                    break;
                }
        }
    }






    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


}
