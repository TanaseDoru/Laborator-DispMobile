package ro.ase.lab114a;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class SensorsActivity extends AppCompatActivity {

    TextView tvLog;
    SensorManager sm = null;
    Sensor senzor = null;
    SenzoriListener sl = null;

    LocationManager lm = null;
    GPSListener gl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sensors);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnSenzori = findViewById(R.id.btnSenzori);
        Button btnGPS = findViewById(R.id.btnGPS);
        tvLog = findViewById(R.id.tvLog);

        btnSenzori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.unregisterListener(sl);
                sm.registerListener(sl, senzor, SensorManager.SENSOR_DELAY_UI);
                tvLog.setText("");
            }
        });


        sl = new SenzoriListener();
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
//        List<Sensor> sensorList = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        List<Sensor> sensorList = sm.getSensorList(Sensor.TYPE_PROXIMITY);
        senzor = sensorList.get(0);


        gl = new GPSListener();
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.unregisterListener(sl);
                lm.removeUpdates(gl);

                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(SensorsActivity.this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

                }
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0 , gl);
                tvLog.setText("");
            }
        });

    }



    class SenzoriListener implements SensorEventListener {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
//            tvLog.append("Coordonatele pe cele 3 axe: \r\n");
//            tvLog.append("X: "+ event.values[0]);
//            tvLog.append("Y: "+ event.values[1]);
//            tvLog.append("Z: "+ event.values[2]);
            tvLog.append(("Distanta: \r\n"));
            tvLog.append(event.values[0]+"");
            if(event.values[0] < 5)
                Toast.makeText(getApplicationContext(), "Esti prea aproape", Toast.LENGTH_LONG).show();
        }
    }

    class GPSListener implements LocationListener
    {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            if(location != null)
            {
                String coordonate = "Latitudine: " + location.getLatitude() +
                        ", Altitudine: " + location.getAltitude();
                tvLog.append("Pozitie GPS: \r\n");
                tvLog.append(coordonate);
                tvLog.append("\r\n");

            }
        }
    }
}