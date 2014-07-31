package com.example.waldo;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


  /////////////////////////////////////////////////////////////////////
 //WARNING: This may not work indoors if you have a weak GPS device.//
/////////////////////////////////////////////////////////////////////

public class MainActivity extends Activity
{
	private static double myLat = 0; //variable to hold latitude
	private static double myLong = 0; //variable to hold longitude
	private static Button b1=null; //button to launch GPS request
	private static Button b2=null; //button to launch maps app
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				getGpsPos(); //set button 1 to find location
			}
		});
		b2=(Button)findViewById(R.id.button2);
		b2.setEnabled(false); //disable until a location has been found. 
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				launchMaps(myLat, myLong); //launch the maps intent with the found coordinates
			}
		});
	}
	private void launchMaps(double lat, double lon)
	{
		String locationUri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lon); //create a URI with embedded location data
		Intent mapsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationUri)); //launch an intent from that.
		startActivity(mapsIntent); //start the maps app
	}
	private void getGpsPos()
	{
		LocationManager locationManager=(LocationManager) getSystemService( //initialize location manager
				Context.LOCATION_SERVICE);
		LocationListener locationListener = new MyLocationListener(); //create a listener to handle GPS updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener); //tell it when to run the listener
	}
	private class MyLocationListener implements LocationListener //handles GPS information
	{
		@Override
		public void onLocationChanged(Location loc) //maintain current position
		{
			myLat = loc.getLatitude();
			myLong = loc.getLongitude();
			
			Toast.makeText(getBaseContext(), 
					"Lat: " + myLat +", Long: "+myLong,
					Toast.LENGTH_SHORT).show(); //display location
			b2.setEnabled(true); //enable "Open in map" button
		}
		
		//the rest of these methods are required by LocationListener and are empty.
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) 
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider)
		{
			// TODO Auto-generated method stub
			
		}
	}
}
