package ro.ase.lab114a;

import android.app.AlertDialog;
import android.app.ComponentCaller;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD = 100;

    public ListView listView;
    public int poz;
    public static final String EDIT_STUDENT = "editStudent";
    public static final int REQUEST_CODE_EDIT = 200;

    List<Student> listStudenti = new ArrayList<>();

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

        listView = findViewById(R.id.ListViewStudenti);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_ADD);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = listStudenti.get(position);

                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("Confirmare Stergere").setMessage("Doriti sa stergeti?")
                        .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listStudenti.remove(student);
                                adapter.notifyDataSetChanged();

                                StudentiDB database = StudentiDB.getInstanta(getApplicationContext());
                                database.getStudentDao().delete(student);

                                dialog.cancel();
                            }
                        })
                        .create();
                dialog.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                poz = position;
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                intent.putExtra(EDIT_STUDENT, listStudenti.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        StudentiDB database = StudentiDB.getInstanta(getApplicationContext());
        if (listStudenti.size() == 0)
        {
            listStudenti = database.getStudentDao().getAll();
        }

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                R.layout.elem_listview, listStudenti, getLayoutInflater())
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                Student student1 = listStudenti.get(position);

                TextView tvMedie = view.findViewById(R.id.tvMedie);
                if(student1.getMedie() >= 5)
                {
                    tvMedie.setTextColor(Color.GREEN);
                }
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

        if(item.getItemId() == R.id.optiune1)
        {
            Intent intent = new Intent(this, BNRActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.optiune2)
        {
            ExtractXML extractXML = new ExtractXML(){

                @Override
                protected void onPostExecute(InputStream inputStream) {
                    StudentiDB database = StudentiDB.getInstanta(getApplicationContext());
                    database.getStudentDao().insert(this.studentList);
                    listStudenti.addAll(this.studentList);

                    CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                            R.layout.elem_listview, listStudenti, getLayoutInflater())
                    {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            Student student1 = listStudenti.get(position);

                            TextView tvMedie = view.findViewById(R.id.tvMedie);
                            if(student1.getMedie() >= 5)
                            {
                                tvMedie.setTextColor(Color.GREEN);
                            }
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
        else if (item.getItemId() == R.id.optiune3)
        {
            ExtractJson extractJson= new ExtractJson(){
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
                    listStudenti.addAll(this.studentListJson);

                    StudentiDB database = StudentiDB.getInstanta(getApplicationContext());
                    database.getStudentDao().insert(this.studentListJson);

                    CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                            R.layout.elem_listview, listStudenti, getLayoutInflater())
                    {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            Student student1 = listStudenti.get(position);

                            TextView tvMedie = view.findViewById(R.id.tvMedie);
                            if(student1.getMedie() >= 5)
                            {
                                tvMedie.setTextColor(Color.GREEN);
                            }
                            else
                                tvMedie.setTextColor(Color.RED);

                            return view;
                        }
                    };
                    listView.setAdapter(adapter);
                }
            };
            try {
                extractJson.execute(new URL("https://pastebin.com/raw/AhW5SkFf"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        else if (item.getItemId() == R.id.optiune4)
        {
            // to do
            return true;
        }
        else if (item.getItemId() == R.id.optiune5)
        {
            // to do
            return true;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, @NonNull ComponentCaller caller) {
        super.onActivityResult(requestCode, resultCode, data, caller);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK && data != null)
        {
            Student student = (Student) data.getSerializableExtra(AddActivity.ADD_STUDENT);
            if(student != null)
            {
                Toast.makeText(getApplicationContext(), student.toString(), Toast.LENGTH_LONG).show();
                listStudenti.add(student);

                StudentiDB database = StudentiDB.getInstanta(getApplicationContext());
                database.getStudentDao().insert(student);

//                ArrayAdapter<Student> adapter = new ArrayAdapter<>(getApplicationContext(),
//                        android.R.layout.simple_list_item_1, listStudenti);
//
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                        R.layout.elem_listview, listStudenti, getLayoutInflater())
                {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        Student student1 = listStudenti.get(position);

                        TextView tvMedie = view.findViewById(R.id.tvMedie);
                        if(student1.getMedie() >= 5)
                        {
                            tvMedie.setTextColor(Color.GREEN);
                        }
                        else
                            tvMedie.setTextColor(Color.RED);


                        return view;
                    }
                };
                listView.setAdapter(adapter);
            }
        }
        else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null)
        {
            Student student = (Student) data.getSerializableExtra(AddActivity.ADD_STUDENT);
            if(student != null)
            {
                listStudenti.get(poz).setNume(student.getNume());
                listStudenti.get(poz).setDataNasterii(student.getDataNasterii());
                listStudenti.get(poz).setMedie(student.getMedie());
                listStudenti.get(poz).setFacultate(student.getFacultate());
                listStudenti.get(poz).setTipScolarizare(student.isTipScolarizare());

//                ArrayAdapter<Student> adapter = new ArrayAdapter<>(getApplicationContext(),
//                        android.R.layout.simple_list_item_1, listStudenti);
                CustomAdapter adapter = (CustomAdapter) listView.getAdapter();
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }
        }

    }


}