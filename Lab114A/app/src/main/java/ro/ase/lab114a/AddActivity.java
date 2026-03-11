package ro.ase.lab114a;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    public static final String ADD_STUDENT = "addStudent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        String[] facultati = {"Calculatoare", "Comunicatii", "Constructii"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                facultati);
        Spinner spinnerFacultati = findViewById(R.id.spinnerFacultate);
        spinnerFacultati.setAdapter(adapter);

        EditText etNume = findViewById(R.id.editTextNume);
        EditText etDataNasterii = findViewById(R.id.editTextDate);
        EditText etMedie = findViewById(R.id.editTextMedie);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        if(intent.hasExtra(MainActivity.EDIT_STUDENT))
        {
            Student student = (Student) intent.getSerializableExtra(MainActivity.EDIT_STUDENT);
            etNume.setText(student.getNume());
            etDataNasterii.setText(new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(student.getDataNasterii()));
            etMedie.setText(String.valueOf(student.getMedie()));
            
        }

        Button btnCreare = findViewById(R.id.btnCreare);
        btnCreare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(etNume.getText().toString().isEmpty()){
                    etNume.setError("Introduceti numele!!!!!");
                }
                else if (etDataNasterii.getText().toString().isEmpty()) {
                    etDataNasterii.setError("Introduceti data nasterii!");
                }
                else if (etMedie.getText().toString().isEmpty()) {
                    etMedie.setError("Introduceti media!");
                }
                else
                {
                    SimpleDateFormat sdf = new
                            SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    try {
                        sdf.parse(etDataNasterii.getText().toString());
                        Date dataNasterii = new Date(etDataNasterii.getText().toString());
                        String numeStudent = etNume.getText().toString();
                        float medie = Float.parseFloat(etMedie.getText().toString());
                        String facultate = spinnerFacultati.getSelectedItem().toString();
                        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                        boolean tipScolarizare = false;
                        if(radioButton.getText().equals("Buget")) {
                            tipScolarizare = true;
                        }

                        Student student = new Student(numeStudent, dataNasterii, medie, facultate, tipScolarizare);
//                        Toast.makeText(getApplicationContext(), student.toString(), Toast.LENGTH_LONG).show();
                        intent.putExtra(ADD_STUDENT, student);
                        setResult(RESULT_OK, intent);
                        finish();

                    } catch (Exception e) {
                        Log.e("AddActivity", e.getMessage().toString());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}