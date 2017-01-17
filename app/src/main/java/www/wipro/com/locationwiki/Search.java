package www.wipro.com.locationwiki;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class Search extends AppCompatActivity {
    SearchView search;
    double latsearch;
    double lngsearch;
    String title,lat,lng;
    int image;
    TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent inn1=getIntent();
        Bundle b=inn1.getExtras();

        title=b.getString("title");
        image=b.getInt("image");

       /* //----from Listactivty------//
        lat=b.getString("latitude");
        lng=b.getString("longitude");*/

        search=(SearchView) findViewById(R.id.searchView1);
        tv= (TextView) findViewById(R.id.textView);
        search.setQueryHint("Find " + title + "'s in- Area");

        //*** setOnQueryTextFocusChangeListener ***
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                //Toast.makeText(getBaseContext(), String.valueOf(hasFocus),Toast.LENGTH_SHORT).show();
            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                if (query.length() == 0) {
                    Toast.makeText(Search.this, "Enter Area Name", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getBaseContext(), query,
                        Toast.LENGTH_SHORT).show();

                getLatLng(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                //   Toast.makeText(getBaseContext(), newText,
                //Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void getLatLng(String query) {

        List list=new List();
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(query, 50);
            for(Address add : adresses){
                lngsearch = add.getLongitude();
                latsearch = add.getLatitude();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       // Toast.makeText(Search.this,""+latsearch,Toast.LENGTH_SHORT).show();
        tv.setText("" + latsearch + "," + lngsearch);
        if(latsearch==0.0 && lngsearch==0.0)
        {
            Toast.makeText(Search.this,"Enter Area Name Correctly or check your Internet Connection",Toast.LENGTH_SHORT).show();
        }

        else {
            Intent intent = new Intent(Search.this, ListActivity.class);

            //--------------searching place------------------//
            intent.putExtra("latitude", "" + latsearch);
            intent.putExtra("longitude", "" + lngsearch);

            intent.putExtra("title", title);
            intent.putExtra("ImageID", image);
            startActivity(intent);
        }
    }
}