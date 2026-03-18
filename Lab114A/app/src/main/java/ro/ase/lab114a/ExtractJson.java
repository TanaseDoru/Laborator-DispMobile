package ro.ase.lab114a;

import android.os.AsyncTask;
import android.system.StructUtsname;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ExtractJson extends AsyncTask<URL, Void, String> {

    public List<Student> studentListJson = new ArrayList<>();


    @Override
    protected String doInBackground(URL... urls) {

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) urls[0].openConnection();
            connection.setRequestMethod("GET");
            java.io.InputStream ist = connection.getInputStream();

            // var 1 - Conversie in String
            InputStreamReader isr = new InputStreamReader(ist);
            BufferedReader br = new BufferedReader(isr);
            String linie = null;
            String rezultat = "";
            while((linie = br.readLine()) != null)
                rezultat += linie;


            // parsare json

            parsareJSON(rezultat);


            return rezultat;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void parsareJSON(String jsonStr)
    {
        if(jsonStr != null)
        {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonStr);
                JSONArray studenti = jsonObject.getJSONArray("studenti");
                for (int i = 0; i < studenti.length(); i++) {
                    JSONObject obj = studenti.getJSONObject(i);
                    String nume = obj.getString("Nume");
                    Date dataNasterii = new Date(obj.getString("DataNasterii"));
                    float medie = Float.parseFloat(obj.getString("Medie"));
                    String facultate = obj.getString("Facultate");
                    Boolean tipScolarizare = Boolean.parseBoolean(obj.getString("TipScolarizare"));

                    Student student = new Student(nume, dataNasterii, medie, facultate, tipScolarizare);
                    studentListJson.add(student);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }


}
