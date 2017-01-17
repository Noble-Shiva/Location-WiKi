package www.wipro.com.locationwiki;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import angtrim.com.fivestarslibrary.FiveStarsDialog;
import angtrim.com.fivestarslibrary.NegativeReviewListener;

public class MainActivity extends AppCompatActivity implements LocationListener, NegativeReviewListener {

    TextView tv;
    // Button b;
    ListView listView;
    CoordinatorLayout coordinatorLayout;
    Double latitude, longitude;
    private LocationManager locationManager;
    String Restaurant = "restaurant";
    String ATM = "atm";
    String BusStops = "bus_station";
    String GasStations = "gas_station";
    String Hospital = "hospital";
    String Police = "police";
    String Post_office = "post_office";
    String Banks = "bank";
    String Groceries = "grocery_or_supermarket";
    String Lodging = "lodging";
    String Pharmacy = "pharmacy";
    String Train_station = "train_station";
    private View stickyViewSpacer, stickyViewList;
    public ProgressDialog dlg;

    //   AlertDialog alertDialog;
    private ShareActionProvider mShareActionProvider;
    String[] web = {
            "Restaurant",
            "ATM",
            "Bus Station",
            "Gas Station",
            "hospital",
            "Police",
            "Post Office",
            "Bank",
            "Grocery / Super Market",
            "Lodging",
            "Pharmacy",
            "Train Station"
    };
    Integer[] imageId = {
            R.drawable.restaurant1,
            R.drawable.atm1,
            R.drawable.bus1,
            R.drawable.gas1,
            R.drawable.doctor1,
            R.drawable.police1,
            R.drawable.post_office1,
            R.drawable.bank_dollar_1,
            R.drawable.shopping1,
            R.drawable.lodging1,
            R.drawable.shopping1,
            R.drawable.train1
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        listView = (ListView) findViewById(R.id.listView);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatelayout);

        //----------Custom Adapter------------------------//
        CustomList adapter1 = new CustomList(MainActivity.this, web, imageId);
        //list=(ListView)findViewById(R.id.list);
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(MainActivity.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

            }
        });


            //----------------------handling item clicks----------------//
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    int pos = position;

                    switch (pos) {
                        case 0:
                            Intent in1 = new Intent(MainActivity.this, ListActivity.class);
                            in1.putExtra("title", Restaurant);
                            in1.putExtra("latitude", "" + latitude);
                            in1.putExtra("longitude", "" + longitude);
                            // in1.putExtra("image", R.drawable.restaurant);
                            int image1 = R.drawable.restaurant;//name of image in drawable folder dont use extensions like.jpg and all
                            in1.putExtra("ImageID", image1);
                            startActivity(in1);
                            break;


                        case 1:
                            Intent in2 = new Intent(MainActivity.this, ListActivity.class);
                            in2.putExtra("title", ATM);
                            in2.putExtra("latitude", "" + latitude);
                            in2.putExtra("longitude", "" + longitude);
                            int image2 = R.drawable.atm;//name of image in drawable folder dont use extensions like.jpg and all
                            in2.putExtra("ImageID", image2);
                            startActivity(in2);
                            break;


                        case 2:
                            Intent in3 = new Intent(MainActivity.this, ListActivity.class);
                            in3.putExtra("title", BusStops);
                            in3.putExtra("latitude", "" + latitude);
                            in3.putExtra("longitude", "" + longitude);
                            int image3 = R.drawable.busstation;
                            in3.putExtra("ImageID", image3);
                            startActivity(in3);
                            break;


                        case 3:
                            Intent in4 = new Intent(MainActivity.this, ListActivity.class);
                            in4.putExtra("title", GasStations);
                            in4.putExtra("latitude", "" + latitude);
                            in4.putExtra("longitude", "" + longitude);
                            int image4 = R.drawable.gas;
                            in4.putExtra("ImageID", image4);
                            startActivity(in4);
                            break;

                        case 4:
                            Intent in5 = new Intent(MainActivity.this, ListActivity.class);
                            in5.putExtra("title", Hospital);
                            in5.putExtra("latitude", "" + latitude);
                            in5.putExtra("longitude", "" + longitude);
                            int image5 = R.drawable.hospital;
                            in5.putExtra("ImageID", image5);
                            startActivity(in5);
                            break;

                        case 5:
                            Intent in6 = new Intent(MainActivity.this, ListActivity.class);
                            in6.putExtra("title", Police);
                            in6.putExtra("latitude", "" + latitude);
                            in6.putExtra("longitude", "" + longitude);
                            int image6 = R.drawable.police;
                            in6.putExtra("ImageID", image6);
                            startActivity(in6);
                            break;

                        case 6:
                            Intent in7 = new Intent(MainActivity.this, ListActivity.class);
                            in7.putExtra("title", Post_office);
                            in7.putExtra("latitude", "" + latitude);
                            in7.putExtra("longitude", "" + longitude);
                            int image7 = R.drawable.post;
                            in7.putExtra("ImageID", image7);
                            startActivity(in7);
                            break;

                        case 7:
                            Intent in8 = new Intent(MainActivity.this, ListActivity.class);
                            in8.putExtra("title", Banks);
                            in8.putExtra("latitude", "" + latitude);
                            in8.putExtra("longitude", "" + longitude);
                            int image8 = R.drawable.bank;
                            in8.putExtra("ImageID", image8);
                            startActivity(in8);
                            break;

                        case 8:
                            Intent in9 = new Intent(MainActivity.this, ListActivity.class);
                            in9.putExtra("title", Groceries);
                            in9.putExtra("latitude", "" + latitude);
                            in9.putExtra("longitude", "" + longitude);
                            int image9 = R.drawable.groceries;
                            in9.putExtra("ImageID", image9);
                            startActivity(in9);
                            break;

                        case 9:
                            Intent in10 = new Intent(MainActivity.this, ListActivity.class);
                            in10.putExtra("title", Lodging);
                            in10.putExtra("latitude", "" + latitude);
                            in10.putExtra("longitude", "" + longitude);
                            int image10 = R.drawable.lodge;
                            in10.putExtra("ImageID", image10);
                            startActivity(in10);
                            break;

                        case 10:
                            Intent in11 = new Intent(MainActivity.this, ListActivity.class);
                            in11.putExtra("title", Pharmacy);
                            in11.putExtra("latitude", "" + latitude);
                            in11.putExtra("longitude", "" + longitude);
                            int image11 = R.drawable.pharmacy;
                            in11.putExtra("ImageID", image11);
                            startActivity(in11);
                            break;

                        case 11:
                            Intent in12 = new Intent(MainActivity.this, ListActivity.class);
                            in12.putExtra("title", Train_station);
                            in12.putExtra("latitude", "" + latitude);
                            in12.putExtra("longitude", "" + longitude);
                            int image12 = R.drawable.train;
                            in12.putExtra("ImageID", image12);
                            startActivity(in12);
                            break;
                    }
                }
            });

        //---------------- To determine location---------------------//

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //specify the type of provider
        String provider = LocationManager.NETWORK_PROVIDER;

        //register listener
        locationManager.requestLocationUpdates(provider, 60 * 1000, 1, this);
        final Location location = locationManager.getLastKnownLocation(provider);

        // Toast.makeText(MainActivity.this, "gps started", Toast.LENGTH_SHORT).show();

        if (location != null) {
            updateLocation(location);
        } else {
            showSettingsAlert();
        }
    }

    //-------------- Alert dialog to show settings------------------//
    public void showSettingsAlert() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);


        // Setting Dialog Title
        alertDialog.setTitle("GPS Disabled");
        alertDialog.setCancelable(false);

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);


            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // dialog.cancel();
                finish();
            }
        });

        // Showing Alert Message

        alertDialog.show();


    }

    @Override
    public void onLocationChanged(Location location) {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
    }


    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context) this);
        builder.setPositiveButton((CharSequence) "YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int n) {
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton((CharSequence) "NO", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int n) {
            }
        });
        builder.setMessage((CharSequence) "Are you sure you want to EXIT???");
        builder.create().show();
    }


    //---------------- latitude and longitude-----------------//
    String streetAddr = "";

    private void updateLocation(Location location) {

        double latitude = location.getLatitude();
        this.latitude = latitude;
        double longitude = location.getLongitude();
        this.longitude = longitude;

        //convert into address
        Geocoder gc = new Geocoder(this);
        //  String streetAddr="";

        try {
            List<Address> addresses = gc.getFromLocation(latitude, longitude, 2);
            if (addresses != null && !addresses.isEmpty()) ;
            {
                Address address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    streetAddr += address.getAddressLine(i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(isNetworkAvailable()){
            tv.setText("Current Location: " + "\nLatitude: " + latitude + "\nLongitude: " + longitude);
            tv.append("\nYou are Currently at:" + streetAddr);
        }else{
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
        }
    }

    //-------------- Overflow menu----------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            case R.id.Share:
                //Toast.makeText(getBaseContext(), "You selected Share", Toast.LENGTH_SHORT).show();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://maps.google.com/maps?ll=" + latitude + "," + longitude);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;

            case R.id.About:
               // Toast.makeText(getBaseContext(), "You selected About", Toast.LENGTH_SHORT).show();
                Intent in2 = new Intent(MainActivity.this, About.class);
                startActivity(in2);
                break;

            case R.id.Rate:

                FiveStarsDialog fiveStarsDialog = new FiveStarsDialog(MainActivity.this, "l.shivkumarreddy@gmail.com");
                fiveStarsDialog.setRateText("Rate it out of 5 Stars")
                        .setTitle("Did you like Location Wiki App")
                        .setForceMode(false)
                        .setUpperBound(1) // Market opened if a rating >= 2 is selected
                        .setNegativeReviewListener(MainActivity.this) // OVERRIDE mail intent for negative review
                        .setReviewListener(null) // Used to listen for reviews (if you want to track them )
                        .showAfter(0);

                break;

            case R.id.EXIT:
               // Toast.makeText(getBaseContext(), "You selected EXIT", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;

        }

        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (latitude == null || longitude == null) {
            Intent in = getIntent();
            finish();
            startActivity(in);
            dlg = ProgressDialog.show(this, "Turning on GPS", "Please Wait");
        }
    }

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        {
            Snackbar snackbar1 = Snackbar
                    .make(coordinatorLayout, "Your Internet Is connected", Snackbar.LENGTH_LONG);
            snackbar1.show();
        }
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

    @Override
    public void onNegativeReview(int i) {

    }
}
