package stoil.loki.interim;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class GridAbonnement extends AppCompatActivity {
    GridView simpleGrid;
    int logos[] = {R.drawable.abonnement_annee, R.drawable.abonnement_indeterminee, R.drawable.abonnement_mois, R.drawable.abonnement_ponctuel,
            R.drawable.abonnement_semestre, R.drawable.abonnement_trimestre};
    String types[] = {"Annuel", "Indéterminé", "Mensuel", "Ponctuel", "Semestriel", "Trimestriel"};

    ArrayList<AbonnementData> abonnementData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abonnements);
        Log.d("GridAbonnement.java", "debut co");

        Intent intentp = getIntent();

        DatabaseConnexion<GridAbonnement, GridAbonnement> dbCo = new DatabaseConnexion<>(GridAbonnement.this, GridAbonnement.this);
        dbCo.setContext(getApplicationContext());
        String SQL = "select * from interima.abonnement;";

        Log.d("GridAbonnement.java", "Requete :" + SQL);

        dbCo.setRequete(SQL);
        dbCo.execute("");

        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
//        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), logos, types);
//        simpleGrid.setAdapter(customAdapter);
//        implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(), GridAbView.class);

                intent.putExtra("role", intentp.getStringExtra("role"));
                intent.putExtra("email1", intentp.getStringExtra("email1") );
                intent.putExtra("nomEntreprise", intentp.getStringExtra("nomEntreprise"));
                intent.putExtra("adresse", intentp.getStringExtra("adresse"));
                intent.putExtra("departement", intentp.getStringExtra("departement"));
                intent.putExtra("nom1", intentp.getStringExtra("nom1"));
                intent.putExtra("telephone1", intentp.getStringExtra("telephone1"));
                intent.putExtra("siret", intentp.getStringExtra("siret"));
                intent.putExtra("sousService", intentp.getStringExtra("sousService"));
                intent.putExtra("nom2", intentp.getStringExtra("nom2"));
                intent.putExtra("email2", intentp.getStringExtra("email2"));
                intent.putExtra("tel2", intentp.getStringExtra("tel2"));

                if (!abonnementData.isEmpty()) {
                    intent.putExtra("id", abonnementData.get(position).getId());
                    intent.putExtra("titre", abonnementData.get(position).getNom());
                    intent.putExtra("prix", abonnementData.get(position).getPrix());
                    intent.putExtra("avantages", abonnementData.get(position).getAvantages());
                    intent.putExtra("conditions", abonnementData.get(position).getConditions());

                    Log.d("abonnement", "id = " + id + " titre = " + abonnementData.get(position).getNom() + " prix = " + abonnementData.get(position).getPrix() + " avantages = " + abonnementData.get(position).getAvantages() + " conditions = " + abonnementData.get(position).getConditions());

                    startActivity(intent);
                }

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
                        Intent intents = new Intent(getApplicationContext(), RecherchePage.class);
                        startActivity(intents);
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

    public void onQueryResult(String result, ArrayList<AbonnementData> abonnementData) {
        Log.d("customAdapter", "recup");
        Log.d("grid", "ab " + abonnementData.get(0).getNom());
        this.abonnementData = abonnementData;
        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), logos, types);
        customAdapter.setAbonnementData(abonnementData);
        simpleGrid.setAdapter(customAdapter);
    }
}