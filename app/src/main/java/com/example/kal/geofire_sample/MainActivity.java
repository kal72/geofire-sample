package com.example.kal.geofire_sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    GeoQuery geoQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = firebaseDatabase.getReference();
        final GeoFire geoFire = new GeoFire(myRef.child("driver"));
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    List<User> userList = new ArrayList<User>();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        User user = snapshot.getValue(User.class);
//                        userList.add(user);
//                        System.out.println(user.toString());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("cancel", databaseError.getMessage());
//            }
//        });

        geoQuery = geoFire.queryAtLocation(new GeoLocation(65.9674534, -18.52936506), 2);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                System.out.println(String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude));
            }

            @Override
            public void onKeyExited(String key) {
                System.out.println(String.format("Key %s is no longer in the search area", key));
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                System.out.println(String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady() {
                System.out.println("All initial data has been loaded and events have been fired!");
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                System.err.println("There was an error with this query: " + error);
            }
        });
        Button buttonPush = (Button)findViewById(R.id.btn_push);
        buttonPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                User user = new User("adi", 65.9597534, -18.53822708);
//                User user1 = new User("adi2", 65.96775052, -18.52932215);
//                myRef.child(user.getNama()).setValue(user);
//                myRef.child(user1.getNama()).setValue(user1);
                geoFire.setLocation(UUID.randomUUID().toString(), new GeoLocation(65.96775052, -18.52932215), new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        if (error != null) {
                            System.err.println("There was an error saving the location to GeoFire: " + error);
                        } else {
                            System.out.println("Location saved on server successfully! : "+key);
                            User user = new User("adi3");
                            myRef.child("Users").child(key).setValue(user);
                        }
                    }
                });

//                geoFire.setLocation(user.getNama(), new GeoLocation(65.96665816, -18.53084564), new GeoFire.CompletionListener() {
//                    @Override
//                    public void onComplete(String key, DatabaseError error) {
//                        if (error != null) {
//                            System.err.println("There was an error saving the location to GeoFire: " + error);
//                        } else {
//                            System.out.println("Location saved on server successfully! : "+key);
//                        }
//                    }
//                });
            }
        });

        Button buttonGet = (Button)findViewById(R.id.btn_getlocation);
        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoQuery.setCenter(new GeoLocation(65.9551, -18.5328));
            }
        });

        final EditText editTextName = (EditText)findViewById(R.id.name);
        final EditText editTextLat = (EditText)findViewById(R.id.lat);
        final EditText editTextLng = (EditText)findViewById(R.id.lng);

        Button buttonSave = (Button)findViewById(R.id.btn_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = Double.valueOf(editTextLat.getText().toString());
                double lng = Double.valueOf(editTextLng.getText().toString());
                geoFire.setLocation(editTextName.getText().toString(), new GeoLocation(lat, lng), new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        if (error != null) {
                            System.err.println("There was an error saving the location to GeoFire: " + error);
                        } else {
                            System.out.println("Location saved on server successfully! : "+key);
                        }
                    }
                });
            }
        });
    }
}
