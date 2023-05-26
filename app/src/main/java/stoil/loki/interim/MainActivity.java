package stoil.loki.interim;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

class ViewHolder {
    TextView title;
    TextView company;
    Button applyButton;
}

// TODO: Océane fais en sorte que les valeurs de GEOLAT et GEOLONG dans la classe Offre correspondent à la BDD

public class MainActivity extends AppCompatActivity implements Serializable, LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    private static final int PERMISSION_REQUEST_CODE = 1993;
    private LocationManager locationManager;
    private ArrayList<Offer> offers = new ArrayList<Offer>();
    OfferAdapter adapter;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    //Location Variables//
    double curLong = 2.349014;
    double curLat = 48.864716;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = new File(getFilesDir().toString() + "/first_time_launch.txt");
        System.out.println(getFilesDir());
        if (!file.exists()) {
            // This is the first launch
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.getBaseContext().openFileOutput("first_time_launch.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write("notified");
                outputStreamWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Intent intent = new Intent(this, Geolocalisation.class);
            startActivity(intent);
            finish(); //Kill the activity to avoid force backout
        } else //This isn't the first launch
        {
            for (int i = 0; i < 10; i++) {
                offers.add(new Offer(1, "Developpeur Fullstack", "capgemini.com"));
            }

            RecyclerView recyclerView = findViewById(R.id.offersList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            this.adapter = new OfferAdapter(offers);
            recyclerView.setAdapter(adapter);


            //LOCATION MANAGER
            // Get the location manager
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // Check for location permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                // Do something if perm isnt granted
                //#############################################
                return;
            }


            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, this);


            BottomNavigationView menu = findViewById(R.id.navigation);
            menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Gérez la redirection ici
                    switch (item.getItemId()) {
                        case R.id.home:
                            //Nous sommes déjà à l'accueil.
                            return true;

                        case R.id.recherche:
                            return true;

                        case R.id.favoris:
                            Intent intentb = new Intent(MainActivity.this, Bookmarks.class);
                            startActivity(intentb);
                            return true;

                        case R.id.notifs:
                            Intent intentn = new Intent(MainActivity.this, Notifications.class);
                            startActivity(intentn);
                            return true;

                        case R.id.profil:
                            // si connecter donner la page du profil
                            // sinon on demande la co ou inscription
                            if (true) {
                                Intent intentp = new Intent(getApplicationContext(), ProfilDisplay.class);
                                startActivity(intentp);
                            } else {
                                Intent intentp = new Intent(MainActivity.this, SignIn.class);
                                startActivity(intentp);
                            }
                            return true;

                        default:
                            return false;
                    }
                }
            });

            // Display Lat and Long

            // NOTE : THERES A SIGNIFICANT PROBLEM WITH THIS APPROACH //

            Thread thread1 = new Thread(() -> {
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.err.println("Device currently standing at Lo: " + curLong + " La:" + curLat);
            });

            thread1.start();


            // search bar
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Location permission granted, do your work here
                } else {
                    // Location permission denied
                }
                break;
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("sms:"));
                    String smsbody = "Partagé via Interima: TITRE, DUREE, " +
                            "REMUNERATION, LIEN SI SOURCE DISPONIBLE";
                    sendIntent.putExtra("sms_body", smsbody);
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Activez l'accès à vos messages pour " +
                            "partager cette annonce par SMS.", Toast.LENGTH_LONG).show();
                }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.getMenu().findItem(R.id.home).setChecked(true);
    }

    //Location Listener BS here
    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        System.err.println("OnLocationChanged() called");

        // Use the latitude and longitude values as needed
        // Example: Log the values
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);

        this.curLat = latitude;
        this.curLong = longitude;

        sortOffers(); //THIS TRIGGERS THE SORTING BASED ON GEOLOCALISATION//

        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, this); */
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }


            public void sortOffers()
            {
                for (Offer off : offers) {
                    off.calculateDiff((float) curLong, (float) curLat);
                }

                Collections.sort(offers, new Comparator<Offer>() {
                    @Override
                    public int compare(Offer o1, Offer o2) {
                        // Compare based on DIST from User
                        return Double.compare(o1.getDistFromUser(), o2.getDistFromUser());
                    }});
                //This calculates the distance

                //offers.clear();
                System.out.println("Elements in list offers : "+offers.size());
                adapter.notifyDataSetChanged();
            }
}