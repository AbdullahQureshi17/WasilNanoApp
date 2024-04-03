package com.nano.liteloan.correspondant.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.correspondant.correspondantdialoguefragment.RejectFragmentDialogue;
import com.nano.liteloan.correspondant.correspondantdialoguefragment.VerificationUpdatedDialgoue;
import com.nano.liteloan.correspondant.correspondantdialoguefragment.VerifyDialogueFragment;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.businesscorrespondant.AppVerificationRequest;
import com.nano.liteloan.info.businesscorrespondant.ApplicationVerificationResponce;
import com.nano.liteloan.info.businesscorrespondant.PendingList;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.InfoDialogFragment;
import com.nano.liteloan.utils.alerts.AlertUtils;


public class VerificationFrag extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private MainActivity context;
    private LatLng latLng;
    private static final int REQUEST_LOCATION = 1;
    private GoogleMap mMap;
    private PendingList pendingList;
    private TextView tvAmount, tvName, tvCnic, tvEmail, tvPhone;
    private RelativeLayout rlEmail, rlGetDirection;
    private double latitude = 0.0, longitude = 0.0;
    private TextView tvLocationPoints;
    private RelativeLayout rlVerify, rlReject;
    private Button btUpdate;
    private FusedLocationProviderClient mFusedLocationClient;
    private double selectedLatitude = 0.0, selectedLongitude = 0.0;
    private String lon , lat;

    private static final long INTERVAL = 1000 * 15;
    private static final long FASTEST_INTERVAL = 1000 * 15;
    private CameraUpdate cameraUpdate = null;

    private SupportMapFragment mapFragment;


    public VerificationFrag(MainActivity context, PendingList pendingList) {
        this.context = context;
        this.pendingList = pendingList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verification, container, false);


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

        ImageView ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.getSupportFragmentManager().popBackStackImmediate();
            }
        });
        btUpdate = view.findViewById(R.id.update);
        tvAmount = view.findViewById(R.id.requestedAmount);
        tvName = view.findViewById(R.id.name);
        tvCnic = view.findViewById(R.id.cnic);
        tvEmail = view.findViewById(R.id.emial);
        tvPhone = view.findViewById(R.id.phone);

        rlVerify = view.findViewById(R.id.rlverify);
        rlVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyDialogueFragment infoDialogFragment = new VerifyDialogueFragment(pendingList , context);
                infoDialogFragment.show(context.getSupportFragmentManager(),
                        InfoDialogFragment.class.getName());

                infoDialogFragment.setOnValueSaved(new VerifyDialogueFragment.OnValueSaved() {
                    @Override
                    public void onSaveValue(String value) {
                        submitData(value, "0", "verified");
                    }
                });
            }
        });

        rlReject = view.findViewById(R.id.rlreject);
        rlReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RejectFragmentDialogue infoDialogFragment = new RejectFragmentDialogue(context);
                infoDialogFragment.show(context.getSupportFragmentManager(),
                        InfoDialogFragment.class.getName());

                infoDialogFragment.setOnReasonSaved(new RejectFragmentDialogue.OnReasonSaved() {
                    @Override
                    public void onSavereson(String value) {
                        submitData("0", value, "reject");
                    }
                });

            }
        });

        rlEmail = view.findViewById(R.id.rlemail);

//        rlGetDirection = view.findViewById(R.id.getDirection);
//        rlGetDirection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.verificationMapFragment(pendingList.latitude, pendingList.longitude, pendingList.fullname, pendingList.phoneno);
//            }
//        });

        tvLocationPoints = view.findViewById(R.id.tv_location_ponits);
//        tvLocationPoints.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
//                } else {
//                    tvLocationPoints.setClickable(false);
//                    launchLocationFragment(pendingList.latitude, pendingList.longitude);
//
//                    tvLocationPoints.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            tvLocationPoints.setClickable(true);
//                        }
//                    }, 500);
//
//                }
//            }
//        });

        btUpdate = view.findViewById(R.id.update);
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMap != null){
                    mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                        @Override
                        public void onMyLocationChange(Location location) {


                            LatLng ltlng = new LatLng(Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()));
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                    ltlng, 15f);
                            Marker maker = mMap.addMarker(new MarkerOptions().position(ltlng).title(String.valueOf(pendingList.fullname)).snippet(String.valueOf(pendingList.phoneno)));
                            mMap.animateCamera(cameraUpdate);
                            tvLocationPoints.setText("Lat: " + location.getLatitude() + " Long: " + location.getLongitude());
                        }
                    });


                } else {
                    tvLocationPoints.setText("Lat: " + selectedLatitude + " Long: " + selectedLongitude);
                }


            }
        });

        initView(view);
        setContent();

        return view;
    }


    public void initView(View view) {

        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        Button btnSave = view.findViewById(R.id.btn_save);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }


    private void setContent() {

        if (pendingList.longitude != null && !pendingList.longitude.isEmpty()
                && pendingList.latitude != null && !pendingList.latitude.isEmpty()) {

            longitude = Double.valueOf(pendingList.latitude);
            latitude = Double.valueOf(pendingList.longitude);

            selectedLatitude = Double.valueOf(pendingList.latitude);
            selectedLongitude = Double.valueOf(pendingList.longitude);


            tvLocationPoints.setText("Lat: " + pendingList.latitude + " Long: " + pendingList.longitude);
        }
        if (pendingList.fullname != null) {
            tvName.setText(pendingList.fullname);
        }
        if (pendingList.cnic != null) {
            tvCnic.setText(pendingList.cnic);
        }
        if (pendingList.phoneno != null) {
            tvPhone.setText(pendingList.phoneno);
        }
        rlEmail.setVisibility(View.GONE);
        tvAmount.setText(pendingList.requestedAmount + "");
    }

    private void submitData(String value, String reason, String status) {


        AppVerificationRequest appVerificationRequest = new AppVerificationRequest();

        appVerificationRequest.applicationid = pendingList.applicationid;
        appVerificationRequest.correspondentid = pendingList.correspondentid;
        appVerificationRequest.recommendamount = String.valueOf(value);
        appVerificationRequest.rejectreason = reason;
        appVerificationRequest.userid = pendingList.userid;
        appVerificationRequest.status = status;
        appVerificationRequest.longitude = String.valueOf(selectedLongitude);
        appVerificationRequest.latitude = String.valueOf(selectedLatitude);


        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }


        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
        userBusiness.applicationVerification(appVerificationRequest, new ResponseCallBack<ApplicationVerificationResponce>() {
            @Override
            public void onSuccess(ApplicationVerificationResponce body) {

                dialog.dismiss();
                if (appVerificationRequest.status.equalsIgnoreCase("verified")) {
                    VerificationUpdatedDialgoue popup = new VerificationUpdatedDialgoue(context, "Location has been verified and Loan Amount of Rs " + appVerificationRequest.recommendamount + " has been approved.");
                    popup.show(getChildFragmentManager(), VerificationUpdatedDialgoue.class.getName());
                } else {
                    AlertUtils.showAlert(context, "Loaction verification is rejected.");
                    context.dashBoardFragment();
                }


            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();


                AlertUtils.showAlert(context, message);
            }
        });

    }

//    @SuppressLint("SetTextI18n")
//    private void launchLocationFragment(String lon, String lat) {
//        LocationFragment dialogFragment = new LocationFragment(lat, lon);
//        dialogFragment.show(context.getSupportFragmentManager(),
//                LocationFragment.class.getName());
//
//        dialogFragment.setOnLocationSaved(new LocationFragment.OnLocationSaved() {
//
//            @Override
//            public void onSave(double lat, double longi) {
//
//                latitude = lat;
//                longitude = longi;
//
//
//                tvLocationPoints.setText("Lat: " + lat + " Long: " + longi);
//                tvLocationPoints.setClickable(true);
//
//            }
//        });
//    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GoogleApiAvailability.getInstance().getErrorDialog(context, 0, 1).show();
            return false;
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
//        getMyLocation();
        if (googleMap != null) {

            mMap = googleMap;
            mMap.getUiSettings().setScrollGesturesEnabled(false);


            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(),
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                if (selectedLongitude == 0.0 ||
                                        selectedLatitude == 0.0) {
                                    selectedLatitude = location.getLatitude();
                                    selectedLongitude = location.getLongitude();
                                } else {
                                    selectedLatitude = Double.valueOf(selectedLatitude);
                                    selectedLongitude = Double.valueOf(selectedLongitude);
                                }

                                latLng = new LatLng( selectedLatitude, selectedLongitude);
                                if (mMap != null) {

                                    mMap.clear();
                                    mMap.addMarker(new MarkerOptions()
                                            .position(latLng));
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
//                                    cameraUpdate = CameraUpdateFactory.newLatLngZoom(
//                                            latLng, 14f);
//                                    mMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(pendingList.fullname)).snippet(String.valueOf(pendingList.phoneno)));

                                }
                            }
                        }
                    });


//            if(cameraUpdate != null){
//                cameraUpdate = CameraUpdateFactory.newLatLngZoom(
//                        latLng, 14f);
//                Marker maker = mMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(pendingList.fullname)).snippet(String.valueOf(pendingList.phoneno)));
//                mMap.animateCamera(cameraUpdate);
//            }

            googleMap.setMyLocationEnabled(true);

//            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                @Override
//                public void onMapClick(LatLng latLng) {
//
//                    selectedLatitude = latLng.latitude;
//                    selectedLongitude = latLng.longitude;
//
//                    mMap.clear();
//                    mMap.addMarker(new MarkerOptions()
//                            .position(latLng).draggable(true));
//                }
//            });
//
            mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {

//                    mMap.clear();
//
//                    mMap.addMarker(new MarkerOptions()
//                            .position(mMap.getCameraPosition().target).draggable(true));
//
//                    latLng = mMap.getCameraPosition().target;
//                    selectedLatitude = mMap.getCameraPosition().target.latitude;
//                    selectedLongitude = mMap.getCameraPosition().target.longitude;

                }
            });
//
//            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
//                @Override
//                public void onCameraIdle() {
//
//                    mMap.clear();
//
//                    mMap.addMarker(new MarkerOptions()
//                            .position(mMap.getCameraPosition().target).draggable(true));
//                    latLng = mMap.getCameraPosition().target;
//
//                    selectedLatitude = mMap.getCameraPosition().target.latitude;
//                    selectedLongitude = mMap.getCameraPosition().target.longitude;
//                }
//            });


        }

    }





//
//    @SuppressLint("MissingPermission")
//    private void getMyLocation() {
//
//        mMap.setMyLocationEnabled(true);
//        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
//            @Override
//            public void onMyLocationChange(Location location) {
//
//
//
//                LatLng ltlng = new LatLng(Double.valueOf(selectedLatitude), Double.valueOf(selectedLongitude));
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
//                        ltlng, 12f);
//                Marker maker = mMap.addMarker(new MarkerOptions().position(ltlng).title(String.valueOf(pendingList.fullname)).snippet(String.valueOf(pendingList.phoneno)));
//                mMap.animateCamera(cameraUpdate);
//            }
//        });
//
//        //get destination location when user click on map
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//            }
//        });
//    }
}