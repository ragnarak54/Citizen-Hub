package com.example.citizenhub;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Sample extends Fragment {
    private MapView sampleMap;
    private GoogleMap googleMap;
    private Marker customMarker;

    public Sample() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createMapView(savedInstanceState);
        defineMapBehavior(new LatLng(33.423729, -111.939185)); // Brickyard coords
    }

    private void createMapView(Bundle savedInstanceState) {
        sampleMap = (MapView) getView().findViewById(R.id.map);
        sampleMap.onCreate(savedInstanceState);
        sampleMap.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1000));
        sampleMap.setBackgroundColor(Color.RED);
        sampleMap.setPadding(30, 30, 30, 30);
    }

    private void defineMapBehavior(final LatLng latLong) {
        sampleMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                setGoogleMap(googleMap);
                googleMap.addMarker(new MarkerOptions()
                        .position(latLong)
                        .title("Customer marker!"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 15));

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        if(customMarker != null) {
                            customMarker.remove();
                        }
                        customMarker = googleMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("Custom Location!")
                                .draggable(true)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_2_512)));
                    }
                });

                sampleMap.onResume();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);

        return inflater.inflate(R.layout.fragment_sample, container, false);
    }
    @Override
    public final void onResume() {
        super.onResume();
        this.getMapView().onResume();
    }

    @Override
    public final void onPause() {
        super.onPause();
        this.getMapView().onPause();
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        this.getMapView().onDestroy();
    }
    @Override
    public final void onLowMemory() {
        super.onLowMemory();
        this.getMapView().onLowMemory();
    }

    private final MapView getMapView() {
        return this.sampleMap;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
