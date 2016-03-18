package com.example.brandon.smsplash;

import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Brandon on 3/16/2016.
 */
public class SendTexts extends AppCompatActivity {


    private String num;
    private TextView timesToSend;
    private TextView message;
    private SmsManager smsManager;
    private TextView pNum;

    public SendTexts(String num, EditText timesToSend, EditText message, EditText pNum) {
        this.num = num;
        this.timesToSend = timesToSend;
        this.message = message;
        this.pNum = pNum;
        smsManager = SmsManager.getDefault();
    }

    public void sendMessages() {
        int max = Integer.parseInt(timesToSend.getText().toString());
        String phoneNumber = formatMessage(pNum.getText().toString());

        for (int i = 0; i < max; i++) {
            smsManager.sendTextMessage(phoneNumber, null, message.getText().toString(), null, null);
        }

    }

    private String formatMessage(String mes) {
        String finalMes = "";
        for (int i = 0; i < mes.length(); i++) {
            if (Character.isDigit(mes.charAt(i))) {
                finalMes = finalMes.concat(mes.charAt(i) + "");
            }
        }


        return finalMes;
    }


}
