package stoil.loki.interim;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUpdateCreate<T> extends AsyncTask<String, Void, String> {

    private static final String url = "jdbc:mysql://interima.ddns.net:11006/interima";
    private static final String user = "dev_user";
    private static final String password = "dev_user_password";

    private String requete;

    private String res;

    private Context context;

    private T callingActivity;

    private int querynumber;

    public DatabaseUpdateCreate(T callingActivity, int querynb) {
        this.callingActivity = callingActivity;
        this.querynumber = querynb;
    }

    public void setRequete(String SQL) {
        this.requete = SQL;
    }

    public String getRequete() {
        return this.requete;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection coDb = DriverManager.getConnection(url, user, password);

            Log.d("DatabaseUpdateCreate.java", "Connexion ok");

            Statement st = coDb.createStatement();
            Log.d("DatabaseUpdateCreate.java", "requete : " + this.requete);

            int rs = st.executeUpdate(this.requete, Statement.RETURN_GENERATED_KEYS);

            if (rs > 0) {
                ResultSet generatedKeys = st.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int insertedId = generatedKeys.getInt(1);
                    res = String.valueOf(insertedId);
                    Log.d("DatabaseUpdateCreate.java", "id ligne = " + res);
                }
            }

            st.close();
            coDb.close();

        } catch (Exception e) {
            e.printStackTrace();
            res = "0";
        }

        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (callingActivity != null) {
            Log.d("DatabaseUpdateCreate.java", "callingActivity class: " + callingActivity.getClass().getSimpleName() + "result = " + result);
            if (callingActivity instanceof MdP && this.querynumber == 2) {
                Log.d("DatabaseUpdateCreate.java", "query 2, id = " + Integer.parseInt(result));
                ((MdP) callingActivity).dataAddQuery(Integer.parseInt(result));
            }
            if (callingActivity instanceof MdP && this.querynumber == 1) {
                Log.d("DatabaseUpdateCreate.java", "query 3, souscription");
                ((MdP) callingActivity).dataForSubscriptionQuery();
            }
            if (callingActivity instanceof Apply && this.querynumber == 2) {
                Log.d("DatabaseUpdateCreate.java", "query 2, id = " + Integer.parseInt(result));
                ((Apply) callingActivity).dataAddQuery(Integer.parseInt(result));
            }
            if (callingActivity instanceof UploadOffer && this.querynumber == 1) {
                ((UploadOffer) callingActivity).onQueryResult();
            }
            if (callingActivity instanceof UpdateMdP) {
                Log.d("DatabaseUpdateCreate.java", "Mise à jour du mot de passe");
                ((UpdateMdP) callingActivity).onQueryResult();
            }
            if (callingActivity instanceof EditProfile) {
                Log.d("DatabaseUpdateCreate.java", "Mise à jour des informations");
                ((EditProfile) callingActivity).onQueryResult();
            }
            if (callingActivity instanceof EditProfileCo) {
                Log.d("DatabaseUpdateCreate.java", "Mise à jour des informations");
                ((EditProfileCo) callingActivity).onQueryResult();
            }
        }
        res = result;
    }
}
