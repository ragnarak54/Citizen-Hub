package com.example.citizenhub;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.maps.android.data.kml.KmlLayer;
import java.lang.Object;

import androidx.fragment.app.FragmentActivity;

//import android.support.v7.app.AppCompatActivity;

public class googleMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
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

        // Add a marker in Sydney and move the camera
        LatLng campus = new LatLng(33.423514, -111.937178);
        mMap.addMarker(new MarkerOptions().position(campus).title("Marker on campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(campus));

        showMap();
    }

    public void showMap(){

        try{

            KmlLayer layer1 = new KmlLayer( mMap, R.raw.cityhub_bus_routes, getApplicationContext());

            layer1.addLayerToMap();

            KmlLayer layer2 = new KmlLayer( mMap, R.raw.cityhub_except_bus, getApplicationContext());

            layer2.addLayerToMap();

        }
        catch (Exception e)
        {
            //Toast.makeText(getApplicationContext(),"Exception for Map", Toast.LENGTH_SHORT.show());
        }
    }

}
