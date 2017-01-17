package www.wipro.com.locationwiki;

import android.graphics.Bitmap;

import java.util.BitSet;

//---------- class to store json data-------------------//
public class List {

    String icon;
    String name;
    String vicinity;
    String latitude,longitude;
    double latsrc,lngsrc;
    String distance;

    @Override
    public String toString() {
        return name+"\n"+vicinity+"\nDistance: "+distance+" kms";
    }
}
