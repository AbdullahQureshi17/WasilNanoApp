package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.MainActivity;

import java.util.Objects;

/**
 * Created by Muhammad Ahmad.
 */
public class LocationFragment extends DialogFragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Marker marker;
    private static final long INTERVAL = 1000 * 15;
    private static final long FASTEST_INTERVAL = 1000 * 15;
    private SupportMapFragment mapFragment;
    private MainActivity context;
    private FusedLocationProviderClient mFusedLocationClient;
    private double selectedLatitude = 0.0, selectedLongitude = 0.0;
    private LatLng latLng;
    private GoogleMap mMap;
    private OnLocationSaved onLocationSaved;
    private static View view;
    private String lon , lat;


    public LocationFragment(String lon , String lat) {
        this.lon = lon;
        this.lat = lat;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        try {
            view = inflater.inflate(R.layout.fragment_location, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }
        initView(view);


        if (isGooglePlayServicesAvailable()) {
            createLocationRequest();

            GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            mGoogleApiClient.connect();

        } else {
            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
            int result = googleAPI.isGooglePlayServicesAvailable(getActivity());
            if (googleAPI.isUserResolvableError(result)) {

                int playServicesResulotionRequest = 1011;
                googleAPI.getErrorDialog(getActivity(), result,
                        playServicesResulotionRequest).show();

            }
        }

        Button button = view.findViewById(R.id.btn_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onLocationSaved != null) {

                    onLocationSaved.onSave(selectedLatitude, selectedLongitude);

                    LocationFragment.this.dismiss();
                }
            }
        });

        return view;
    }


    private boolean isGooglePlayServicesAvailable() {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GoogleApiAvailability.getInstance().getErrorDialog(context, 0, 1).show();
            return false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mapFragment != null) {
            this.getChildFragmentManager()
                    .beginTransaction()
                    .remove(mapFragment).commitAllowingStateLoss();
        }
    }

    private void createLocationRequest() {

        mFusedLocationClient = LocationServices
                .getFusedLocationProviderClient(context);
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void initView(View view) {

        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationFragment.this.dismiss();
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        if (googleMap != null) {

            mMap = googleMap;

            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(),
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                if(lon.equalsIgnoreCase("0") ||
                                lat.equalsIgnoreCase("0")){
                                    selectedLatitude = location.getLatitude();
                                    selectedLongitude = location.getLongitude();
                                } else {
                                    selectedLatitude = Double.valueOf(lat);
                                    selectedLongitude = Double.valueOf(lon);
                                }

                                latLng = new LatLng( selectedLatitude, selectedLongitude);
                                if (mMap != null) {

                                    mMap.clear();
                                    mMap.addMarker(new MarkerOptions()
                                            .position(latLng));
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
                                }
                            }
                        }
                    });

            googleMap.setMyLocationEnabled(true);

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    selectedLatitude = latLng.latitude;
                    selectedLongitude = latLng.longitude;

                    mMap.clear();
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng).draggable(true));
                }
            });

            mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {

                    mMap.clear();

                    mMap.addMarker(new MarkerOptions()
                            .position(mMap.getCameraPosition().target).draggable(true));

                    latLng = mMap.getCameraPosition().target;
                    selectedLatitude = mMap.getCameraPosition().target.latitude;
                    selectedLongitude = mMap.getCameraPosition().target.longitude;

                }
            });

            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {

                    mMap.clear();

                    mMap.addMarker(new MarkerOptions()
                            .position(mMap.getCameraPosition().target).draggable(true));
                    latLng = mMap.getCameraPosition().target;

                    selectedLatitude = mMap.getCameraPosition().target.latitude;
                    selectedLongitude = mMap.getCameraPosition().target.longitude;
                }
            });


        }


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialogfragmentsettings);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(d.getWindow()).setLayout(width, height);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        windowDimensions();

    }

    /**
     * Applying fragment window dimensions.
     */
    private void windowDimensions() {
        Window window = getDialog().getWindow();
        Point size = new Point();

        assert window != null;
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        window.setLayout((int) (width * 0.9), (int) (height * 0.7));
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void setOnLocationSaved(OnLocationSaved onLocationSaved) {
        this.onLocationSaved = onLocationSaved;
    }

    /**
     * Created by Muhammad Ahmad.
     */
    public interface OnLocationSaved {

        void onSave(double latitude, double longitude);
    }
}

