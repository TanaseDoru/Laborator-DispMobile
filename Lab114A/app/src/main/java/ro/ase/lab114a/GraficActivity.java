package ro.ase.lab114a;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.ArrayList;
import java.util.List;

public class GraficActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grafic);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        ArrayList<Student> list = (ArrayList<Student>) intent.getSerializableExtra("list");

        XYPlot plot = findViewById(R.id.myXYplot);

        List<Double> listaMediiBuget = new ArrayList<>();
        List<Double> ListaMediiTaxa = new ArrayList<>();

        for(Student student : list) {
            if(student.isTipScolarizare())
                listaMediiBuget.add(Double.valueOf(student.getMedie()));
            else {
                ListaMediiTaxa.add(Double.valueOf(student.getMedie()));
            }
        }

        plot.setTitle("Grafic medii studenti");

        XYSeries series1 = new SimpleXYSeries(listaMediiBuget,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Buget");

        XYSeries series2 = new SimpleXYSeries(ListaMediiTaxa,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Taxa");

//        plot.addSeries(series1, new LineAndPointFormatter(getApplicationContext(), R.layout.f2));
//        plot.addSeries(series2, new LineAndPointFormatter(getApplicationContext(), R.layout.f3));


    }
}