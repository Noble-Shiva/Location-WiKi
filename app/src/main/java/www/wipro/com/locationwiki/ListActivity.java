package www.wipro.com.locationwiki;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import angtrim.com.fivestarslibrary.FiveStarsDialog;

public class ListActivity extends AppCompatActivity {

    SearchView search;
    double longSearch;
    double latSearch;
    TextView tv;
    private ProgressDialog dlg;
    ListView listView;
    String title;
    int image;
    private TextView stickyView;
    private ImageView heroImageView;
    double dist,latsrc,lngsrc;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.coordinatelayout);
        stickyView= (TextView) findViewById(R.id.stickyView);

        Intent in=getIntent();
        Bundle b=in.getExtras();

        title=b.getString("title");

        //------Mainactivity--||--- coordinaaates from search------//
        String latitude=b.getString("latitude");
        String longitude=b.getString("longitude");

        latsrc = Double.parseDouble(latitude);
        lngsrc = Double.parseDouble(longitude);
        Toast.makeText(this,title,Toast.LENGTH_SHORT).show();

        heroImageView= (ImageView) findViewById(R.id.heroImageView);

        image=getIntent().getIntExtra("ImageID",0);
        heroImageView.setImageResource(image);

        stickyView.setText(title);

        listView = (ListView) findViewById(R.id.listView);

        stickyView = (TextView) findViewById(R.id.stickyView);


        //---------------checking whether network is available or not-------------//
        if(isNetworkAvailable())
        {
            //large display      //small display
            dlg=ProgressDialog.show(this,"Searching places info","Please Wait");
            dlg.setCanceledOnTouchOutside(true);
            CityTask task=new CityTask();

            task.execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latsrc+","+lngsrc+"&rankby=distance&type="+title+"&key=AIzaSyBo3xNKTgX_aoFvQHe8TerO9Nr8rHgKFIo");

        }
        else
        {
            dlg.cancel();
            Toast.makeText(this, "Check Network Settings", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isConnected());
        else
        {
           // dlg=ProgressDialog.show(this,"Searching places info","Please Wait");

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
           //dlg.cancel();
        }
        return true;
    }


    //--------------- Thread to fetch data from JSON-----------------//
    private class CityTask extends AsyncTask<String,Void,JSONObject>
    {
        @Override                          //params is array of strings ...=variable objects
        protected JSONObject doInBackground(String... params) {

            //access web services
            JSONObject result=null;
            String urls=params[0];

            try {
                URL url=new URL(urls);

                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));

                String line=reader.readLine();
                String response="";
                while(line!=null)
                {
                    response+=line;
                    line=reader.readLine();
                }
                result=new JSONObject(response);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            JSONObject resultObject;
            if(result!=null)
            {
                try {

                    JSONArray array=result.getJSONArray("results");
                    final ArrayList<List> arrayList=new ArrayList<List>();

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);

                        List list=new List();

                        JSONObject geometry = object.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");
                        String latjson = location.get("lat").toString();
                        String lngjson = location.get("lng").toString();

                        String icon=object.getString("icon");

                        list.icon=icon;

                        //  JSONObject opening_hours=object.getJSONObject("opening_hours");

                        list.name = object.getString("name");
                        list.vicinity=object.getString("vicinity");
                        list.latitude=latjson;
                        list.longitude=lngjson;


                        //---------calculating distance-----------//

                        Location locationA = new Location("Source Current Location");
                        locationA.setLatitude(latsrc);
                        locationA.setLongitude(lngsrc);

                        Location locationB = new Location("Destination");
                        locationB.setLatitude(Double.parseDouble(list.latitude));
                        locationB.setLongitude(Double.parseDouble(list.longitude));
                        dist = locationA.distanceTo(locationB);
                        dist=(dist/1000);
                        DecimalFormat format = new DecimalFormat("#.##");
                        dist=Double.valueOf(format.format(dist));

                        String dis=""+dist;

                        list.distance=dis;

                        arrayList.add(list);
                    }
                    ArrayAdapter<List> adapter = new ArrayAdapter<List>(getApplicationContext(), R.layout.list_row, arrayList);

                    listView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ListActivity.this,"Error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.e("CityTask", "Error:" + e.getMessage());
                }

                dlg.cancel();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        List list=new List();
                        list = (List) listView.getAdapter().getItem(position);
                        // Create a Uri from an intent string. Use the result to create an Intent.

                        Uri gmmIntentUri = Uri.parse("geo:" + list.latitude + "," + list.longitude + "?q=" + list.name);
                        Toast.makeText(ListActivity.this, list.latitude, Toast.LENGTH_SHORT).show();

                        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        // Make the Intent explicit by setting the Google Maps package
                        mapIntent.setPackage("com.google.android.apps.maps");

                        // Attempt to start an activity that can handle the Intent
                        startActivity(mapIntent);
                    }
                });
            }
        }
    }


    //------------------- overflow Menu--------------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
           // case R.id.menu_search:
           case R.id.Search:
               Toast.makeText(getBaseContext(), "You selected Search", Toast.LENGTH_SHORT).show();
               Intent in=new Intent(ListActivity.this,Search.class);
               in.putExtra("title",title);
               in.putExtra("image",image);
            /*   in.putExtra("latitude",""+latsrc);
               in.putExtra("longitude",""+lngsrc);*/
               startActivity(in);
               break;

            case R.id.Share:
                Toast.makeText(getBaseContext(), "You selected Share", Toast.LENGTH_SHORT).show();
                List list=new List();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://maps.google.com/maps?ll=" + latsrc + "," + lngsrc);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;

            case R.id.About:
                Toast.makeText(getBaseContext(), "You selected About", Toast.LENGTH_SHORT).show();
                Intent in2=new Intent(ListActivity.this,About.class);
                startActivity(in2);
                break;

            case R.id.Rate:

                FiveStarsDialog fiveStarsDialog = new FiveStarsDialog(ListActivity.this,"l.shivkumarreddy@gmail.com");
                fiveStarsDialog.setRateText("Rate it out of 5 Stars")
                        .setTitle("Did you like Location Wiki App")
                        .setForceMode(false)
                        .setUpperBound(1) // Market opened if a rating >= 2 is selected
                       // .setNegativeReviewListener(MainActivity.this) // OVERRIDE mail intent for negative review
                        .setReviewListener(null) // Used to listen for reviews (if you want to track them )
                        .showAfter(0);
                break;

            case R.id.EXIT:
                Toast.makeText(getBaseContext(), "You selected EXIT", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;

        }

        return true;
    }

}
