package com.example.googlemaplocations;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends Activity implements OnMapReadyCallback {

    private LatLng LOCATION_UNIV = new LatLng(33.783768, -118.114336);
    private LatLng LOCATION_ECS = new LatLng(33.782777, -118.111868);

    private GoogleMap map;

    LocationsDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new LocationsDB(getApplicationContext());
        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    public void OnClick_ECS(View v) {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_ECS, 16);
        map.animateCamera(update);
    }

    public void OnClick_LongBeach(View v) {
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 14);
        map.animateCamera(update);
    }

    public void OnClick_City(View v) {
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 9);
        map.animateCamera(update);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(LOCATION_ECS).title("Find me here!"));
        loadAllMarkers();
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.addMarker(new MarkerOptions().position(latLng));
                db.insertNewLocation(latLng.latitude, latLng.longitude, Math.round(map.getCameraPosition().zoom));
                Toast.makeText(getApplicationContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();
            }
        });
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                map.clear();
                db.deleteAllLocations();
                Toast.makeText(getApplicationContext(), "All Markers are removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadAllMarkers() {
        List<LocationModel> locations = db.getAllLocations();
        for (int i = 0; i < locations.size(); i++) {
            map.addMarker(new MarkerOptions().position(new LatLng(locations.get(i).getLatitude(), locations.get(i).getLongitude())));
        }
    }
}