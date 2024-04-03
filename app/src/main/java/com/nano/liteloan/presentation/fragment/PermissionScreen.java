package com.nano.liteloan.presentation.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.info.Location;
import com.nano.liteloan.presentation.activity.MainActivity;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class PermissionScreen extends Fragment {

    Button btOkay;
    MainActivity context;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    Location loc;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;


    public PermissionScreen(MainActivity context) {
        this.context = context;
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_permission_screen, container, false);



        btOkay = view.findViewById(R.id.okay);
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context , Manifest.permission.READ_CALL_LOG)
                        + ContextCompat.checkSelfPermission(
                        context , Manifest.permission.READ_SMS)
                        + ContextCompat.checkSelfPermission(
                        context , Manifest.permission.READ_CONTACTS)
                        + ContextCompat.checkSelfPermission(
                                context , Manifest.permission.ACCESS_FINE_LOCATION)
                        + ContextCompat.checkSelfPermission(
                        context , Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){

                    // Do something, when permissions not granted
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            context , Manifest.permission.READ_CALL_LOG)
                            || ActivityCompat.shouldShowRequestPermissionRationale(
                            context , Manifest.permission.READ_SMS)
                            || ActivityCompat.shouldShowRequestPermissionRationale(
                            context , Manifest.permission.READ_CONTACTS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                            context , Manifest.permission.ACCESS_COARSE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                            context , Manifest.permission.ACCESS_FINE_LOCATION)){
                        // If we should give explanation of requested permissions

                        // Show an alert dialog here with request explanation
                        AlertDialog.Builder builder = new AlertDialog.Builder(context,
                                R.style.MyDialogTheme);
                        builder.setMessage("Call Logs, Read Contacts" +
                                " Read SMS and Location permissions are required to do the task.");
                        builder.setTitle("Please grant these permissions");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(
                                        context,
                                        new String[]{
                                                Manifest.permission.READ_CALL_LOG,
                                                Manifest.permission.READ_SMS,
                                                Manifest.permission.READ_CONTACTS,
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION

                                        },
                                        MY_PERMISSIONS_REQUEST_CODE
                                );


                            }
                        });
                        builder.setNeutralButton("Cancel" , null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        // Directly request for required permissions, without explanation
                        ActivityCompat.requestPermissions(
                                context,
                                new String[]{
                                        Manifest.permission.READ_CALL_LOG,
                                        Manifest.permission.READ_SMS,
                                        Manifest.permission.READ_CONTACTS,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                } else {

                    context.homeFrag();
                }
            }
        });

        return view;
    }







    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:{
                // When request is cancelled, the results array are empty
                if (
                        (grantResults.length > 0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        + grantResults[3]
                                        + grantResults[4]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                ){
                    Toast.makeText(context , "Permissions granted." , Toast.LENGTH_SHORT).show();
                } else {
                    // Permissions are denied
                    Toast.makeText(context , "Permissions denied." , Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}