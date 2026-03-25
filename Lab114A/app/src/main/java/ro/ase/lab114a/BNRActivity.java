package ro.ase.lab114a;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class BNRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bnr);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.e("lifecycle", "Apel metoda onCreate");

        TextView tvDate = findViewById(R.id.tvDate);

        EditText etEur = findViewById(R.id.editTextEUR);
        EditText etUSD = findViewById(R.id.editTextUSD);
        EditText etGBP = findViewById(R.id.editTextGBP);
        EditText etXAU = findViewById(R.id.editTextXAU);

        Button btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),
//                        "Click Pe Buton", Toast.LENGTH_LONG).show();
                Network network = new Network(){
                    @Override
                    protected void onPostExecute(InputStream inputStream) {
//                        Toast.makeText(getApplicationContext(),
//                                Network.rezultat, Toast.LENGTH_LONG).show();
                        tvDate.setText(cv.getDataCurs());
                        etEur.setText(cv.getCursEUR());
                        etUSD.setText(cv.getCursUSD());
                        etGBP.setText(cv.getCursGBP());
                        etXAU.setText(cv.getCursXAU());
                    }
                };
                try {
                    network.execute(new URL("https://www.bnr.ro/nbrfxrates.xml"));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void writeToFile(String fileName, CursValutar cv) throws IOException {
        FileOutputStream fileOutputStream = openFileOutput(fileName, BNRActivity.MODE_PRIVATE);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        dataOutputStream.writeUTF(cv.getDataCurs());
        dataOutputStream.writeUTF(cv.getCursEUR());
        dataOutputStream.writeUTF(cv.getCursGBP());
        dataOutputStream.writeUTF(cv.getCursUSD());
        dataOutputStream.writeUTF(cv.getCursXAU());
        dataOutputStream.flush();
        fileOutputStream.close();
    }

    private CursValutar readFromFile(String fileName) throws IOException
    {
        FileInputStream fileInputStream = openFileInput(fileName);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        String dataCurs = dataInputStream.readUTF();
        String cursEUR = dataInputStream.readUTF();
        String cursGBP = dataInputStream.readUTF();
        String cursUSD = dataInputStream.readUTF();
        String cursXAU = dataInputStream.readUTF();

        CursValutar cv = new CursValutar(dataCurs, cursEUR, cursGBP, cursUSD, cursXAU);
        fileInputStream.close();

        return cv;
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.e("lifecycle", "Apel metoda onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("lifecycle", "Apel metoda onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("lifecycle", "Apel metoda onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("lifecycle", "Apel metoda onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("lifecycle", "Apel metoda onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("lifecycle", "Apel metoda onDestroy");
    }
}
