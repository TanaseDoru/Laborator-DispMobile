package ro.ase.lab114a;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigInteger;

public class SendSMSActivity extends AppCompatActivity {


    BigInteger cipherText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_smsactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etText = findViewById(R.id.editTextMessage);
        EditText etPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        Spinner spinnerContacts = findViewById(R.id.spinnerContacts);
        Button btnSend = findViewById(R.id.btnSend);

        String[] contacts = {"1 Gigel 0721345678", "2 Dorel 0712346587"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                contacts);
        spinnerContacts.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = etPhoneNumber.getText().toString();
                if(phoneNumber.equals(""))
                {
                    String[] arr = spinnerContacts.getSelectedItem().toString().split(" ");
                    phoneNumber = arr[arr.length - 1];
                    etPhoneNumber.setText(phoneNumber);
                }

//                RSA rsa = new RSA(1024);
                String mesaj = etText.getText().toString();
                String MD5message = MD5.getMd5(mesaj);
                if(mesaj.equals(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Mesajul nu este completat!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    BigInteger plaintext = new BigInteger(mesaj.getBytes());
//                    cipherText = rsa.encrypt(plaintext);
                    Toast.makeText(getApplicationContext(), /*cipherText.toString()*/MD5message
                            , Toast.LENGTH_LONG).show();
                }

                if(ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(SendSMSActivity.this,
                            new String[]{Manifest.permission.SEND_SMS}, 0);
                }

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, /*cipherText.toString()*/
                        MD5message,
                        null, null);
                Toast.makeText(getApplicationContext(), "Sms Sent!", Toast.LENGTH_LONG).show();

            }
        });
    }
}