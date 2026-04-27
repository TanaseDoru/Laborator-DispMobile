package ro.ase.lab114a;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SendSMSActivity extends AppCompatActivity {

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

    }
}