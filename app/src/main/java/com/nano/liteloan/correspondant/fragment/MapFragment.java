package com.nano.liteloan.correspondant.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.directions.route.AbstractRouting;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, RoutingListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    protected LatLng start = null;
    protected LatLng end = null;
    SupportMapFragment mMapp;
    String longitude, latitude;
    //current and destination location objects
    Location myLocation = null;
    Location destinationLocation = null;
    boolean locationPermission = false;
    private MainActivity context;
    private GoogleMap googleMap;
    private GoogleMap mMap;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    private String name ;
    String phoneno;
    //polyline object
    private List<Polyline> polylines = null;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;

    }

    public MapFragment(String lon, String lat , String name , String phoneno) {
        longitude = lat;
        latitude = lon;
        this.name = name;
        this.phoneno = phoneno;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mMapp = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        return view;
    }

    private void getLocation() {

        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            android.location.Location locationGps = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            android.location.Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            android.location.Location locationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (locationGps != null) {

            } else if (locationNetwork != null) {

            } else if (locationPassive != null) {

            } else {
//                AlertUtils.showAlert(context, "Can't get your location");
            }
        }
    }


    @SuppressLint("MissingPermission")
    private void getMyLocation() {

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                myLocation = location;

                LatLng ltlng = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        ltlng, 12f);
                Marker maker = mMap.addMarker(new MarkerOptions().position(ltlng).title(String.valueOf(name)).snippet(String.valueOf(phoneno)));
                mMap.animateCamera(cameraUpdate);
            }
        });

        //get destination location when user click on map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                end = latLng;

                mMap.clear();

                start = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                //start route finding
                findroutes(start, end);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                            @Override
                            public void onMyLocationChange(Location location) {

                                myLocation = location;
                                LatLng ltlng = new LatLng(location.getLatitude(), location.getLongitude());
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                        ltlng, 16f);
                                mMap.animateCamera(cameraUpdate);
                            }
                        });

                        //get destination location when user click on map
                        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {

                                end = latLng;

                                mMap.clear();

                                start = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                                //start route finding
                                findroutes(start, end);
                            }
                        });

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }
        }
    }


    // function to find Routes.
    public void findroutes(LatLng Start, LatLng End) {
        if (Start == null || End == null) {
            Toast.makeText(context, "Unable to get location", Toast.LENGTH_LONG).show();
        } else {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("Your Api Key")  //also define your api key here.
                    .build();
            routing.execute();
        }
    }


    @Override
    public void onRoutingFailure(com.directions.route.RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<com.directions.route.Route> arrayList, int shortestRouteIndex) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if (polylines != null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng = null;
        LatLng polylineEndLatLng = null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i < arrayList.size(); i++) {

            if (i == shortestRouteIndex) {
                polyOptions.color(getResources().getColor(R.color.colorPrimary));
                polyOptions.width(7);
                polyOptions.addAll(arrayList.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng = polyline.getPoints().get(0);
                int k = polyline.getPoints().size();
                polylineEndLatLng = polyline.getPoints().get(k - 1);
                polylines.add(polyline);

            } else {

            }
        }
    }


    @Override
    public void onRoutingCancelled() {
        findroutes(start, end);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        findroutes(start, end);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getMyLocation();
    }
}
