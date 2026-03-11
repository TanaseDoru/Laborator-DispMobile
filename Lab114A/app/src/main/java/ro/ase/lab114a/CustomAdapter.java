package ro.ase.lab114a;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

public class CustomAdapter extends ArrayAdapter<Student> {
    
    public CustomAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
