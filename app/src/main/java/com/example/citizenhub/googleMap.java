package com.example.citizenhub;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.kml.KmlLayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class googleMap extends Fragment {

    private MapView mMapView;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    //private boolean busYes, metroYes, parkYes, transitYes = false;

    CheckBox add_bus;
    CheckBox add_metro;
    CheckBox add_park;
    CheckBox add_transit;
    Button refresh;
//    Button go_back;

    private KmlLayer bus_routes;
    private KmlLayer metro_station_lines;
    private KmlLayer park_and_rides;
    private KmlLayer transit_centers;

    private GoogleMap mMap;

    private boolean busYes;
    private boolean metroYes;
    private boolean parkYes;
    private boolean transitYes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_google_map, container, false);
        container.removeAllViews();
        //set up button listeners
        add_bus(rootView);
        add_metro(rootView);
        add_park(rootView);
        add_transit(rootView);
        refresh(rootView);
//      go_back();

        //set up all booleans
        busYes = false;
        metroYes = false;
        parkYes = false;
        transitYes = false;

        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) rootView.findViewById(R.id.mapEmbeded);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
                mMap = googleMap;

                LatLng campus = new LatLng(33.423514, -111.937178);
                mMap.addMarker(new MarkerOptions().position(campus).title("Marker on campus"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(campus));

                float zoomLevel = 10.0f; //This goes up to 21
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campus, zoomLevel));


                try{

                    park_and_rides = new KmlLayer( mMap, R.raw.park_and_rides, getContext());

                    //park_and_rides.data.setStyle({fillColor: red});
                    park_and_rides.addLayerToMap();

                    //parkYes = true;

                    transit_centers = new KmlLayer( mMap, R.raw.transit_centers, getContext());

                    transit_centers.addLayerToMap();

                    //transitYes = true;

                    bus_routes = new KmlLayer( mMap, R.raw.bus_routes, getContext());

                    bus_routes.addLayerToMap();

                    //busYes = true;

                    metro_station_lines = new KmlLayer( mMap, R.raw.metro_stations_lines, getContext());

                    metro_station_lines.addLayerToMap();

                    //metroYes = true;

                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(),"Exception for Map", Toast.LENGTH_SHORT.show());
                }
            }
        });
        return rootView;
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

    public void add_bus(View rootView){
        add_bus = (CheckBox) rootView.findViewById(R.id.check_bus);

        add_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add_bus.isChecked())
                    busYes = true;
                else
                    busYes = false;
            }
        });
    }
    public void add_metro(View rootView){
        add_metro = (CheckBox) rootView.findViewById(R.id.check_metro);

        add_metro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(add_metro.isChecked())
                    metroYes = true;
                else
                    metroYes = false;

            }
        });
    }
    public void add_park(View rootView){
        add_park = (CheckBox) rootView.findViewById(R.id.check_park);

        add_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(add_park.isChecked())
                    parkYes = true;
                else
                    parkYes = false;
            }
        });
    }
    public void add_transit(View rootView){
        add_transit = (CheckBox) rootView.findViewById(R.id.check_transit);

        add_transit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(add_transit.isChecked())
                    transitYes = true;
                else
                    transitYes = false;
            }
        });
    }

    public void refresh(View rootView){
        refresh = (Button) rootView.findViewById(R.id.all);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                try {
                    if(busYes)
                    {
                        bus_routes.addLayerToMap();
                    }
                    if(metroYes)
                    {
                        metro_station_lines.addLayerToMap();
                    }
                    if(parkYes)
                    {
                        park_and_rides.addLayerToMap();
                    }
                    if(transitYes)
                    {
                        transit_centers.addLayerToMap();
                    }
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
        Intent homePage= new Intent(getActivity(),MainActivity.class);
        getActivity().startActivity(homePage);
    }




    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

}
