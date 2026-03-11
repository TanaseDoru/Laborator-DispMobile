package ro.ase.lab114a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Student> {

    private Context context;
    private int resource; // Id-ul fisierului de layout
    private List<Student> studentList;
    private LayoutInflater layoutInflater;

    public CustomAdapter(@NonNull Context context, int resource,
                         List<Student> list, LayoutInflater layoutInflater) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.studentList = list;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Student student = studentList.get(position);

        if(student != null)
        {
            TextView tvNume = view.findViewById(R.id.tvNume);
            tvNume.setText(student.getNume());

            TextView tvDataNasterii = view.findViewById(R.id.tvDataNasterii);
            tvDataNasterii.setText(student.getDataNasterii().toString());

            TextView tvMedie = view.findViewById(R.id.tvMedie);
            tvMedie.setText(String.valueOf(student.getMedie()));

            TextView tvFacultate = view.findViewById(R.id.tvFacultate);
            tvFacultate.setText(student.getFacultate());

            TextView tvTipScolarizare = view.findViewById(R.id.tvTipScolarizare);
            if(student.isTipScolarizare() == true)
            {
                tvTipScolarizare.setText("Buget");
            }
            else
                tvTipScolarizare.setText("Taxa");


        }

    }
}
