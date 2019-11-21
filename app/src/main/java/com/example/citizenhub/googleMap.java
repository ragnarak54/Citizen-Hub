package com.example.citizenhub;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class googleMap extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_google_map, container, false);
        container.removeAllViews();
        //set up button listeners
        show_bus_only(rootView);
        show_metro_only(rootView);
        show_park_only(rootView);
        show_transit_only(rootView);
        show_all(rootView);
//        go_back();

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

    public void show_bus_only(View rootView){
        show_bus_only = (Button) rootView.findViewById(R.id.bus);

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
    public void show_metro_only(View rootView){
        show_metro_only = (Button) rootView.findViewById(R.id.metro);

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
    public void show_park_only(View rootView){
        show_park_only = (Button) rootView.findViewById(R.id.park);

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
    public void show_transit_only(View rootView){
        show_transit_only = (Button) rootView.findViewById(R.id.transit);

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

    public void show_all(View rootView){
        show_all = (Button) rootView.findViewById(R.id.all);

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
