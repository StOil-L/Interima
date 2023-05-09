package stoil.loki.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChooseApply extends AppCompatActivity {

    private Offer offer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_apply);

        Button reuse = findViewById(R.id.button5);
        Button newOne = findViewById(R.id.button6);

        reuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // changer la classe a lancer pour le choix de la candidature a donne
                Intent intent = new Intent(view.getContext(), ApplyReuseDisplay.class);
                view.getContext().startActivity(intent);
            }
        });

        newOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // changer la classe a lancer pour le choix de la candidature a donne
                Intent intent = new Intent(view.getContext(), Apply.class);
                view.getContext().startActivity(intent);
            }
        });

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