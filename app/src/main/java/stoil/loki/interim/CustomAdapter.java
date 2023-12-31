package stoil.loki.interim;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int logos[];

    String types[];
    LayoutInflater inflter;

    private ArrayList<AbonnementData> abonnementData;
    public CustomAdapter(Context applicationContext, int[] logos, String[] types) {
        this.context = applicationContext;
        this.logos = logos;
        this.types = types;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setAbonnementData(ArrayList<AbonnementData> abonnementData) {
        this.abonnementData = abonnementData;
    }
    @Override
    public int getCount() {
        return logos.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_gridview, null); // inflate the layout

        ImageView icon = (ImageView) view.findViewById(R.id.icon); // get the reference of ImageView
        icon.setImageResource(logos[i]); // set logo images

        TextView titre = view.findViewById(R.id.textView);
        titre.setText(abonnementData.get(i).getNom());

        return view;
    }
}