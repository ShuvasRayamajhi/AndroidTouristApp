package com.rayamajs.cwapp;
/*use google maps api to display users current location*/
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2000;
    private long FASTEST_INTERVAL = 5000;
    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (requestSinglePermission()) {

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationManager =(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            
            checkLocation();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private boolean checkLocation() {
        if (!isLocationEnabled()) {
            showAlert();
            
        }
        return isLocationEnabled();
        
    }

    //alert the user if their location is turned off
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Turn On Location") //i've named the alert dialog
                .setMessage("Your location setting is off. \nPlease turn it on use this app ")
                //i have two buttons first positive
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface paramDialogInterface, int paramInt){
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);

                    }

                })
                //nothing entered for negative as it will automatically dismiss
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show(); //enabling dialog

    }
    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); //initialised 2nd location manager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || //passing gps provider and network provider
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    private boolean requestSinglePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        isPermission = false;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
        return isPermission;
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
        //making sure the latitude and longitude isn't empty
        //if it's not null i will add marker, and set the camera and set zoom
        if(latLng!=null) {
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker In Current Location"));
            //above, added marker, position which is current location is passed and set title.
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14F));
            //above,  zoomed 14f is passed, show current location.

        }
    }

    @Override
    //method on connected
    public void onConnected(@Nullable Bundle bundle) {

        //
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) //check self permission declared
                != PackageManager.PERMISSION_DENIED && //check package manager
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return; //return permission when true
        }
        startLocationUpdate();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient); //location is initialised and is passed with google api client

        if (mLocation == null) {
            startLocationUpdate(); //start updates when location is empty

        }
        else  {
            //when the location isn't detected
            Toast.makeText(this, "Your location can not be detected. ", Toast.LENGTH_SHORT).show();

        }
    }

    private void startLocationUpdate() {
        mLocationRequest = LocationRequest.create() //requesting location
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)  //set high accuracy priority
                .setInterval(UPDATE_INTERVAL) //use interval declared above
                .setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this, //checking our  granted permission
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this,
                                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                PackageManager.PERMISSION_DENIED) {
            return;
        }

        //location services listener
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest,this);
    }
    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection suspended ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection failed ", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onLocationChanged(Location location) {

        //string variable and show latitude and latitude
        String msg = "UPDATE LOCATION: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        LocationHelper helper = new LocationHelper(
                location.getLongitude(), location.getLatitude()
        );

        FirebaseDatabase.getInstance().getReference("Current Location")
                .setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MapsActivity.this, "Location Saved", Toast.LENGTH_SHORT); //when location is saved on the database
                }
                else {
                    Toast.makeText(MapsActivity.this, "Location failed to save", Toast.LENGTH_SHORT);
                }
            }
        });

        latLng = new LatLng(location.getLatitude(), location.getLongitude()); //initialise latitude and longitude, and store

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onStart () {
        super.onStart();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

    }
}
