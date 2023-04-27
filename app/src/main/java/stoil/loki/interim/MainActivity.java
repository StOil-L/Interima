package stoil.loki.interim;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
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
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

class ViewHolder {
    TextView title;
    TextView company;
    Button applyButton;
}

public class MainActivity extends AppCompatActivity implements Serializable {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private ArrayList<Offer> offers = new ArrayList<Offer>();
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = new File(getFilesDir(), "first_time_launch.txt");
        if (!file.exists()) {
            // This is the first launch
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Intent intent = new Intent(this, Geolocalisation.class);
            startActivity(intent);
            finish(); //Kill the activity to avoid force backout
        }
        else //This isn't the first launch
        {
            file.delete();
            for (int i = 0; i < 10; i++) {
                offers.add(new Offer(1, "Developpeur Fullstack", "capgemini.com"));
            }

            RecyclerView recyclerView = findViewById(R.id.offersList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            OfferAdapter adapter = new OfferAdapter(offers);
            recyclerView.setAdapter(adapter);

            BottomNavigationView menu = findViewById(R.id.navigation);
            menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Gérez la redirection ici
                    switch (item.getItemId()) {
                        case R.id.home:
                            // Redirigez vers l'écran d'accueil

                            Intent intenth = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intenth);
                            return true;

                        case R.id.favoris:

                            return true;
                        case R.id.recherche:

                            return true;
                        case R.id.notifs:

                            Intent intentn = new Intent(MainActivity.this, Notifications.class);
                            startActivity(intentn);
                            return true;

                        case R.id.profil:
                            // si connecter donner la page du profil
                            // sinon on demande la co ou inscription

                            Intent intentp = new Intent(MainActivity.this, SignIn.class);
                            startActivity(intentp);
                            return true;

                        default:
                            return false;
                    }
                }
            });

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
            // Handle other permissions here if needed
        }

    }
}