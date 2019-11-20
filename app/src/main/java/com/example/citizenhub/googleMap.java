package com.example.citizenhub;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.kml.KmlLayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

public class googleMap extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mMapView;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    //private boolean busYes, metroYes, parkYes, transitYes = false;

    Button show_bus_only;
    Button show_metro_only;
    Button show_park_only;
    Button show_transit_only;
    Button show_all;
//    Button go_back;

    private KmlLayer bus_routes;
    private KmlLayer metro_station_lines;
    private KmlLayer park_and_rides;
    private KmlLayer transit_centers;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up button listeners
        show_bus_only();
        show_metro_only();
        show_park_only();
        show_transit_only();
        show_all();
//        go_back();

        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapEmbeded);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);



    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }



    @Override
    public void onMapReady(GoogleMap map) {
        //map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap = map;

        LatLng campus = new LatLng(33.423514, -111.937178);
        mMap.addMarker(new MarkerOptions().position(campus).title("Marker on campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(campus));

        float zoomLevel = 10.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campus, zoomLevel));


        try{



            park_and_rides = new KmlLayer( map, R.raw.park_and_rides, getApplicationContext());

            park_and_rides.addLayerToMap();

            //parkYes = true;

            transit_centers = new KmlLayer( map, R.raw.transit_centers, getApplicationContext());

            transit_centers.addLayerToMap();

            //transitYes = true;

            bus_routes = new KmlLayer( map, R.raw.bus_routes, getApplicationContext());

            bus_routes.addLayerToMap();

            //busYes = true;

            metro_station_lines = new KmlLayer( map, R.raw.metro_stations_lines, getApplicationContext());

            metro_station_lines.addLayerToMap();

            //metroYes = true;

        }
        catch (Exception e)
        {
            //Toast.makeText(getApplicationContext(),"Exception for Map", Toast.LENGTH_SHORT.show());
        }
    }

    public void show_bus_only(){
        show_bus_only = (Button) findViewById(R.id.bus);

        show_bus_only.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                try {
                    bus_routes.addLayerToMap();

                    //busYes = true;
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(),"Exception for Map", Toast.LENGTH_SHORT.show());
                }
            }
        });
    }
    public void show_metro_only(){
        show_metro_only = (Button) findViewById(R.id.metro);

        show_metro_only.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                try {
                    metro_station_lines.addLayerToMap();

                    //metroYes = true;
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(),"Exception for Map", Toast.LENGTH_SHORT.show());
                }

            }
        });
    }
    public void show_park_only(){
        show_park_only = (Button) findViewById(R.id.park);

        show_park_only.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                try {
                    park_and_rides.addLayerToMap();

                    //parkYes = true;
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(),"Exception for Map", Toast.LENGTH_SHORT.show());
                }

            }
        });
    }
    public void show_transit_only(){
        show_transit_only = (Button) findViewById(R.id.transit);

        show_transit_only.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                try {
                    transit_centers.addLayerToMap();

                    //transitYes = true;
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(),"Exception for Map", Toast.LENGTH_SHORT.show());
                }

            }
        });
    }

    public void show_all(){
        show_all = (Button) findViewById(R.id.all);

        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                try {
                    bus_routes.addLayerToMap();
                    metro_station_lines.addLayerToMap();
                    park_and_rides.addLayerToMap();
                    transit_centers.addLayerToMap();

                    //transitYes = true;
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(),"Exception for Map", Toast.LENGTH_SHORT.show());
                }

            }
        });
    }

//    public void go_back(){
//        go_back = (Button) findViewById(R.id.back);
//
//        go_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
//    }

    public void goHome(View view) {
        //goes home
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

}
