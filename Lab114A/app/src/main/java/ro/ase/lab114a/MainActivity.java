package ro.ase.lab114a;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ComponentCaller;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD = 100;

    List<Student> listaStudenti = new ArrayList<>();

    public ListView listView;

    public int poz;

    public static final int REQUEST_CODE_EDIT = 200;

    public static final String EDIT_STUDENT = "editStudent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listViewStudenti);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                Student student = listaStudenti.get(position);

                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirmare stergere")
                        .setMessage("Doriti sa stergeti?")
                        .setNegativeButton("NU", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listaStudenti.remove(student);
                                adapter.notifyDataSetChanged();

                                StudentiDB database = StudentiDB.getInstanta(getApplicationContext());
                                database.getStudentDao().delete(student);

                                dialogInterface.cancel();
                            }
                        }).create();

                dialog.show();

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                poz = position;
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                intent.putExtra(EDIT_STUDENT, listaStudenti.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        StudentiDB database = StudentiDB.getInstanta(getApplicationContext());
        if(listaStudenti.size() == 0)
            listaStudenti = database.getStudentDao().getAll();

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                R.layout.elem_listview, listaStudenti, getLayoutInflater())
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                Student student1 = listaStudenti.get(position);

                TextView tvMedie = view.findViewById(R.id.tvMedie);
                if(student1.getMedie()>=5)
                    tvMedie.setTextColor(Color.GREEN);
                else
                    tvMedie.setTextColor(Color.RED);

                return view;
            }
        };

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.meniu_principal, menu);

         return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.optiune1)
        {
            Intent intent = new Intent(this, BNRActivity.class);
            startActivity(intent);

            return true;
        }
        else
            if(item.getItemId()==R.id.optiune2)
            {
                ExtractXML extractXML = new ExtractXML()
                {
                    @Override
                    protected void onPostExecute(InputStream inputStream) {

                        listaStudenti.addAll(this.studentList);

                        StudentiDB database = StudentiDB.getInstanta(getApplicationContext());
                        database.getStudentDao().insert(this.studentList);

                        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                                R.layout.elem_listview, listaStudenti, getLayoutInflater())
                        {
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);

                                Student student1 = listaStudenti.get(position);

                                TextView tvMedie = view.findViewById(R.id.tvMedie);
                                if(student1.getMedie()>=5)
                                    tvMedie.setTextColor(Color.GREEN);
                                else
                                    tvMedie.setTextColor(Color.RED);

                                return view;
                            }
                        };
                        listView.setAdapter(adapter);
                    }
                };
                try {
                    extractXML.execute(new URL("https://pastebin.com/raw/NywvHXck"));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
            else
            if(item.getItemId()==R.id.optiune3)
            {
                ExtractJSON extractJSON = new ExtractJSON()
                {
                  ProgressDialog progressDialog;

                    @Override
                    protected void onPreExecute() {
                        progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        progressDialog.cancel();

                        listaStudenti.addAll(this.studentListJSON);

                        StudentiDB database = StudentiDB.getInstanta(getApplicationContext());
                        database.getStudentDao().insert(this.studentListJSON);

                        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                                R.layout.elem_listview, listaStudenti, getLayoutInflater())
                        {
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);

                                Student student1 = listaStudenti.get(position);

                                TextView tvMedie = view.findViewById(R.id.tvMedie);
                                if(student1.getMedie()>=5)
                                    tvMedie.setTextColor(Color.GREEN);
                                else
                                    tvMedie.setTextColor(Color.RED);

                                return view;
                            }
                        };
                        listView.setAdapter(adapter);
                    }
                };
                try {
                    extractJSON.execute(new URL("https://pastebin.com/raw/AhW5SkFf"));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
            else
            if(item.getItemId()==R.id.optiune4)
            {
                Intent intent = new Intent(this, ViewFirebaseActivity2.class);
                startActivity(intent);

                return true;
            }
            else
            if(item.getItemId()==R.id.optiune5)
            {
                Intent intent = new Intent(this, BarChartActivity.class);
                intent.putExtra("list", (ArrayList<Student>)listaStudenti);
                startActivity(intent);

                return true;
            }
            else
            if(item.getItemId()==R.id.optiune6)
            {
                Intent intent = new Intent(this, GraficActivity.class);
                intent.putExtra("list", (ArrayList<Student>)listaStudenti);
                startActivity(intent);

                return true;
            }
            else
            if(item.getItemId()==R.id.optiune7)
            {
                Intent intent = new Intent(this, FragmentActivity.class);
                startActivity(intent);

                return true;
            }
            else
            if(item.getItemId()==R.id.optiune8)
            {
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);

                return true;
            }
            else
            if(item.getItemId()==R.id.optiune9)
            {
                Intent intent = new Intent(this, SensorsActivity.class);
                startActivity(intent);

                return true;
            }
            else
            if(item.getItemId()==R.id.optiune10)
            {
                Intent intent = new Intent(this, SendSMSActivity.class);
                startActivity(intent);

                return true;
            }
            else
            if(item.getItemId()==R.id.optiune11)
            {
                PIM pim = new PIM(this);
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]
                            {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 0);

                }

                pim.addContact();

                return true;
            }
            

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, @NonNull ComponentCaller caller) {
        super.onActivityResult(requestCode, resultCode, data, caller);

        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK && data != null) {
            Student student = (Student) data.getSerializableExtra(AddActivity.ADD_STUDENT);
            if (student != null) {
                //Toast.makeText(getApplicationContext(), student.toString(), Toast.LENGTH_LONG).show();
                listaStudenti.add(student);

                StudentiDB database = StudentiDB.getInstanta(getApplicationContext());
                database.getStudentDao().insert(student);

               /* ArrayAdapter<Student> adapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, listaStudenti);*/
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                        R.layout.elem_listview, listaStudenti, getLayoutInflater())
                {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        Student student1 = listaStudenti.get(position);

                        TextView tvMedie = view.findViewById(R.id.tvMedie);
                        if(student1.getMedie()>=5)
                            tvMedie.setTextColor(Color.GREEN);
                        else
                            tvMedie.setTextColor(Color.RED);

                        return view;
                    }
                };
                listView.setAdapter(adapter);
            }
        } else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            Student student = (Student) data.getSerializableExtra(AddActivity.ADD_STUDENT);
            if (student != null) {
                listaStudenti.get(poz).setNume(student.getNume());
                listaStudenti.get(poz).setDataNasterii(student.getDataNasterii());
                listaStudenti.get(poz).setMedie(student.getMedie());
                listaStudenti.get(poz).setFacultate(student.getFacultate());
                listaStudenti.get(poz).setTipScolarizare(student.isTipScolarizare());

                StudentiDB database = StudentiDB.getInstanta(getApplicationContext());
                database.getStudentDao().update(listaStudenti.get(poz));

               /* ArrayAdapter<Student> adapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, listaStudenti);*/
                CustomAdapter adapter = (CustomAdapter)listView.getAdapter();
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }
        }
    }
}