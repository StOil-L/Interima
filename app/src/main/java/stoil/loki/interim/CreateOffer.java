package stoil.loki.interim;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateOffer extends AppCompatActivity {

    private Offer offer;
    //Intent intent = new Intent(this, MainActivity.class);
    private DatabaseUpdateCreate<CreateOffer> dbCo;

    String titre, description, datedeb, datefin, urlsource, urlimg;
    float salaire_H; float geolong; float geolat;
    String[] motscles;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_offer);

        Button buttonDebut = findViewById(R.id.buttDebut);
        Button buttonFin = findViewById(R.id.buttFin);
        Button submit = findViewById(R.id.submit);

        buttonDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Calendar instance to get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog and set the initial date to the current date
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateOffer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to the TextView
                        TextView dateDebutTextView = findViewById(R.id.dateDebut);
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        dateDebutTextView.setText(selectedDate);
                        dateDebutTextView.setTextColor(Color.BLACK);
                    }
                }, year, month, dayOfMonth);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });

        buttonFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Calendar instance to get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog and set the initial date to the current date
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateOffer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to the TextView
                        TextView dateDebutTextView = findViewById(R.id.dateFin);
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        dateDebutTextView.setText(selectedDate);
                        dateDebutTextView.setTextColor(Color.BLACK);
                    }
                }, year, month, dayOfMonth);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validForm = false;

                //TODO: Check if form is valid here
                if (
                        !((EditText) findViewById(R.id.string_title)).getText().toString().isEmpty() &&
                                !((EditText) findViewById(R.id.string_desc)).getText().toString().isEmpty() &&
                                !((EditText) findViewById(R.id.string_keywords)).getText().toString().isEmpty() &&
                                !((TextView) findViewById(R.id.dateDebut)).getText().toString().equals("DATE NON CHOISIE") &&
                                !((TextView) findViewById(R.id.dateFin)).getText().toString().equals("DATE NON CHOISIE") &&
                                !((EditText) findViewById(R.id.string_img_url)).getText().toString().isEmpty() &&
                                !((EditText) findViewById(R.id.string_source)).getText().toString().isEmpty() &&
                                !((EditText) findViewById(R.id.float_wage)).getText().toString().isEmpty() &&
                                !((EditText) findViewById(R.id.float_long)).getText().toString().isEmpty() &&
                                !((EditText) findViewById(R.id.float_lat)).getText().toString().isEmpty()
                ) {
                    validForm = true;
                }

                if (validForm)
                {

                    titre = ((EditText) findViewById(R.id.string_title)).getText().toString();
                    description = ((EditText) findViewById(R.id.string_desc)).getText().toString();
                    motscles = ((EditText) findViewById(R.id.string_keywords)).getText().toString().split("\\P{L}+");
                    datedeb = ((TextView) findViewById(R.id.dateDebut)).getText().toString();
                    datefin = ((TextView) findViewById(R.id.dateFin)).getText().toString();
                    urlimg = ((EditText) findViewById(R.id.string_img_url)).getText().toString();
                    urlsource = ((EditText) findViewById(R.id.string_source)).getText().toString();


                    try {
                        salaire_H = Float.parseFloat(((EditText) findViewById(R.id.float_wage)).getText().toString());
                        geolong = Float.parseFloat(((EditText) findViewById(R.id.float_long)).getText().toString());
                        salaire_H = Float.parseFloat(((EditText) findViewById(R.id.float_lat)).getText().toString());
                    } catch (NumberFormatException e) {
                        //Do nothing lol
                        //throw new RuntimeException(e);
                    }

                    datedeb = convertDate(datedeb);
                    datefin = convertDate(datefin);

                    //TODO: OCEANE FAIS LA REQUETE POUR CREER L'OFFRE D'EMPLOI//
                    SQLSubmit();

                    Intent in = new Intent(CreateOffer.this, MainActivity.class);
                    Toast.makeText(CreateOffer.this, "Offre mise en ligne", Toast.LENGTH_SHORT).show();
                    startActivity(in);

                }
                else
                {
                    Toast.makeText(CreateOffer.this, "Veuillez compléter tous les champs pour soumettre le formulaire.", Toast.LENGTH_SHORT).show();
                }

                //Go back to Main//
                //startActivity(intent);


            }
        });

        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intenth);
                        return true;
                    case R.id.favoris:
                        if(((CreateOffer)menu.getContext()).getInfoTokenID() != null) {
                            Intent intentf = new Intent(getApplicationContext(), Bookmarks.class);
                            startActivity(intentf);
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Connectez-vous pour " +
                                    "accéder à cette fonctionnalité.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.recherche:
                        Intent intents = new Intent(getApplicationContext(), RecherchePage.class);
                        startActivity(intents);
                        return true;
                    case R.id.notifs:
                        if(((CreateOffer)menu.getContext()).getInfoTokenID() != null) {
                            Intent intentn = new Intent(getApplicationContext(), Notifications.class);
                            startActivity(intentn);
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Connectez-vous pour " +
                                    "accéder à cette fonctionnalité.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.profil:
                        Intent intentp = new Intent(getApplicationContext(), ProfilDisplay.class);
                        startActivity(intentp);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

    }


    private void SQLSubmit() {
        Log.d("SignIn.java", "Starting connection from CreateOffer.java");
        dbCo = new DatabaseUpdateCreate<>(CreateOffer.this, 1);
        dbCo.setContext(getApplicationContext());

        String SQL;
        //SQL = "SELECT mdp FROM interima.utilisateur WHERE id IN (SELECT idUti FROM interima.gestionnaire WHERE email = '"+email.getText().toString()+"');";
        SQL = "INSERT INTO offre (idEmp, titre, publication, fermeture, debut, fin, url, salaire, geolat, geolong, img, description) values ('"+getInfoTokenID()+"', '"+titre+"', '2023-05-01', '2023-06-02', '"+datedeb+"', '"+datefin+"', '"+urlsource+"', '77.5', '"+geolat+"', '"+geolong+"', '"+urlimg+"', '"+description+"');";


        Log.d("CreateOffer.java", "Requete :" + SQL);

        dbCo.setRequete(SQL);
        dbCo.execute("");

    }


    public void onQueryResult(String result) {
        // Traitez le résultat de la requête ici
        //Log.d("SignIn", "Résultat de la requête : " + result + " pw = " + pw.getText().toString() + " res = " + pw.getText().toString().trim().equals(result.trim()));
        String res = result;
        if (res!="1")
        {
             System.out.println("Successful insertion to database");
             Intent intent = new Intent(getApplicationContext(), MainActivity.class);
             startActivity(intent);
             finish();

        }
        else System.out.println("Something went wrong.");
    }

    public String convertDate(String dateOrigine)
    {
        String resConvertedDate = "";
        String outputFormat = "yyyy-MM-dd";
        String desiredOutputDate = null;

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormatObj = new SimpleDateFormat(outputFormat);

        try {
            Date date = inputFormat.parse(dateOrigine);
            desiredOutputDate = outputFormatObj.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("====== CONVERTDATE IN CREATEOFFER.JAVA =======");
        System.out.println("Input Date: " + dateOrigine);
        System.out.println("Desired Output Date: " + desiredOutputDate);
        resConvertedDate = desiredOutputDate;
        return resConvertedDate;
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.getMenu().findItem(R.id.profil).setChecked(true);
    }

    public ArrayList<String> getInfoToken() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("User DATA", Context.MODE_PRIVATE);
        ArrayList<String> value = new ArrayList<>();
        value.add(sharedPreferences.getString("role", null));
        value.add(sharedPreferences.getString("id", null));

        return value;
    }

    public String getInfoTokenID() {
        ArrayList<String> info = getInfoToken();
        return info.get(1);
    }

    public String getInfoTokenRole() {
        ArrayList<String> info = getInfoToken();
        return info.get(0);
    }
}