package ro.ase.lab114a;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import ro.ase.lab114a.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);





        // Add a marker in Sydney and move the camera
        LatLng ATM = new LatLng(44.41837058451412, 26.086387339407167);
        mMap.addMarker(new MarkerOptions().position(ATM).title("Marker in ATM"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ATM));


        //44.44755214828657, 26.097889309668595
        LatLng ASE = new LatLng(44.44755214828657, 26.097889309668595);
        mMap.addMarker(new MarkerOptions().position(ASE).title("Marker in ASE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ASE));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(ASE).title("Academia de Stiinte Economice")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        Marker marker = mMap.addMarker(markerOptions);
        marker.showInfoWindow();

        //44.426915437226604, 26.102276564543416
        LatLng Unirii = new LatLng(44.426915437226604, 26.102276564543416);
        mMap.addMarker(new MarkerOptions().position(Unirii).title("Marker in Unirii"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Unirii));
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions.position(Unirii).title("Unirii")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        Marker marker2 = mMap.addMarker(markerOptions);
        marker2.showInfoWindow();






        PolylineOptions plo = new PolylineOptions();
        plo.add(ATM);
        plo.add(ASE);
        plo.color(Color.RED);
        plo.width(20);

        PolylineOptions plo2 = new PolylineOptions();
        plo2.add(ATM);
        plo2.add(Unirii);
        plo2.color(Color.GREEN);
        plo2.width(20);


        PolylineOptions plo3 = new PolylineOptions();
        plo3.add(Unirii);
        plo3.add(ASE);
        plo3.color(Color.BLUE);
        plo3.width(20);

        mMap.addPolyline(plo);
        mMap.addPolyline(plo2);
        mMap.addPolyline(plo3);



        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

    }
}