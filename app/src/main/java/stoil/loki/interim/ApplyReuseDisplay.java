package stoil.loki.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ApplyReuseDisplay extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_reuse);

        ArrayList<CandidatureData> candidatures = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            candidatures.add(new CandidatureData("Candidature Developpeur FullStack"));
        }

        RecyclerView list_candidatures = findViewById(R.id.apply_list);
        list_candidatures.setLayoutManager(layoutManager);
        list_candidatures.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        ApplyReuseAdapter adapter = new ApplyReuseAdapter(candidatures);
        list_candidatures.setAdapter(adapter);

        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Gérez la redirection ici
                switch (item.getItemId()) {
                    case R.id.home:
                        // Redirigez vers l'écran d'accueil

                        Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intenth);
                        return true;

                    case R.id.favoris:

                        Intent intentf = new Intent(getApplicationContext(), Bookmarks.class);
                        startActivity(intentf);
                        return true;
                    case R.id.recherche:

                        return true;
                    case R.id.notifs:

                        Intent intentn = new Intent(getApplicationContext(), Notifications.class);
                        startActivity(intentn);
                        return true;

                    case R.id.profil:
                        // si connecter donner la page du profil
                        // sinon on demande la co ou inscription

                        if(true) {
                            Intent intentp = new Intent(getApplicationContext(), ProfilDisplay.class);
                            startActivity(intentp);
                        } else {
                            Intent intentp = new Intent(getApplicationContext(), SignIn.class);
                            startActivity(intentp);
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });

    }
}