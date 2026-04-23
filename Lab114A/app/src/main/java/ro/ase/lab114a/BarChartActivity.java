package ro.ase.lab114a;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarChartActivity extends AppCompatActivity {

    ArrayList<Student> list;
    LinearLayout layout;
    Map<String, Integer> source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bar_chart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        list = (ArrayList<Student>) intent.getSerializableExtra("list");

        source = getSource(list);

        layout = findViewById(R.id.layoutBar);

        layout.addView(new BarChartView(getApplicationContext(), source));
    }

    private Map<String, Integer> getSource(List<Student> studentList)
    {
        if(studentList == null || studentList.isEmpty())
            return new HashMap<>();
        Map<String, Integer> source = new HashMap<>();
        for(Student student : studentList)
            if(source.containsKey(student.getFacultate()))
                source.put(student.getFacultate(), source.get(student.getFacultate()) + 1);
            else
                source.put(student.getFacultate(), 1);
        return source;
    }

}