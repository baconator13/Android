package edu.bc.baconju.getlost;

import java.text.DecimalFormat;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class GetLostActivity extends FragmentActivity implements
		ActionBar.TabListener {
	private double bearing;
	private TextView currLatField, currLongField, markLatField, markLongField, findLatField, findLongField, distanceField, bearingField;
	private LocationListener listener;
	private LocationManager locManager;
	SectionsPagerAdapter mSectionsPagerAdapter;
	private Compass compass;
	private float azimuth;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_lost);
	
		currLatField  = (TextView) findViewById(R.id.currLat);
		currLongField = (TextView) findViewById(R.id.currLong);
		markLatField  = (TextView) findViewById(R.id.markLat);
		markLongField = (TextView) findViewById(R.id.markLong);
		findLatField = (TextView) findViewById(R.id.findLat);
		findLongField = (TextView) findViewById(R.id.sparkles);
		
		locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
        compass = new Compass(this);
        azimuth = compass.getAzimuth();
		
		setupGPSWatcher();
	}
	
	private void setupGPSWatcher() {
		listener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					double latitude  = location.getLatitude();
					double longitude = location.getLongitude();
					
					//For Rounding!
					latitude = Math.round(latitude*100)/100.00;
					longitude = Math.round(longitude*100)/100.00;
					
					Log.d("FindMyLocation", ""+latitude);
					Log.d("FindMyLocation", ""+longitude);
					
					currLatField  = (TextView) findViewById(R.id.currLat);
					currLongField = (TextView) findViewById(R.id.currLong);
					
					if(currLatField != null && currLongField != null){
						currLatField.setText(" "+latitude);  // notice that we're on the UI thread, "post" not needed
						currLongField.setText(" "+longitude);
					}
					
					findLatField = (TextView) findViewById(R.id.findLat);
					findLongField = (TextView) findViewById(R.id.sparkles);
					
					if(findLatField != null && findLongField != null){
						findLatField.setText(" "+latitude);  // notice that we're on the UI thread, "post" not needed
						findLongField.setText(" "+longitude);
					}
					
					if(currLatField != null && currLongField != null && markLatField != null && markLongField != null ){
						Log.d("GoingtoCalc", "Going to Calculate");	
						calc();
					}
				}
				
				
					else {
						Log.d("FindMyLocation", "Location changed to null!!");
					}
			}

			@Override
			public void onProviderDisabled(String provider) {
				Log.d("FindMyLocation", provider + " disabled");
			}

			@Override
			public void onProviderEnabled(String provider) {
				Log.d("FindMyLocation", provider + " enabled");

			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				Log.d("FindMyLocation", provider + " changed, see status/extras");
				switch (status) {
					case LocationProvider.OUT_OF_SERVICE:          currLatField.setText(R.string.unknown); break;
					case LocationProvider.AVAILABLE:               currLatField.setText(R.string.unknown);    break;
					case LocationProvider.TEMPORARILY_UNAVAILABLE: currLatField.setText(R.string.unknown);  break;
				}
			}
		};
	}
	
	private void logAllFields() {
		TextView section1Field = (TextView)findViewById(R.id.markLat);
		TextView section2Field = (TextView)findViewById(R.id.findLat);

		if (section1Field != null) Log.i("Swipe", "section1: " + section1Field.getText());
		if (section2Field != null) Log.i("Swipe", "section2: " + section2Field.getText());
		Log.i("Swipe", "-----");
	}
	
	public void markLoc(View view){
		Log.d("MarkLoc", "In Mark Location");
		String lat1 = currLatField.getText().toString();
		String long1 = currLongField.getText().toString();
		
		markLatField  = (TextView) findViewById(R.id.markLat);
		markLongField = (TextView) findViewById(R.id.markLong);
		
		markLatField.setText(lat1);
		markLongField.setText(long1);
	}
	
	public void calc(){
		currLatField  = (TextView) findViewById(R.id.currLat);
		currLongField = (TextView) findViewById(R.id.currLong);
		markLatField  = (TextView) findViewById(R.id.markLat);
		markLongField = (TextView) findViewById(R.id.markLong);
		findLatField = (TextView) findViewById(R.id.findLat);
		findLongField = (TextView) findViewById(R.id.sparkles);
	        
		double latitude1 = Double.parseDouble(currLatField.getText().toString());
		double longitude1 = Double.parseDouble(currLongField.getText().toString());
		double latitude2 = Double.parseDouble(markLatField.getText().toString());
		double longitude2 = Double.parseDouble(markLongField.getText().toString());
		
		findDistance(latitude1, longitude1, latitude2, longitude2);
		
		pointArrow();
	}

	public void pointArrow(){    
            bearing = Double.parseDouble(bearingField.getText().toString());
            
            ImageView arrow = (ImageView)findViewById(R.id.arrow); // initially points up
            if (arrow != null) {
            	Matrix matrix=new Matrix();
            	arrow.setScaleType(ScaleType.MATRIX); //required
            	int cx = arrow.getWidth()/2;
            	int cy = arrow.getHeight()/2;
            	azimuth = compass.getAzimuth();
            	matrix.postRotate(-azimuth, cx, cy); // toward north
            	matrix.postRotate((float)bearing, cx, cy); // toward mark, should be no minus on this line
            	arrow.setImageMatrix(matrix);
        }
        else{
        	Log.d("NotChecked", "Checkbox is not checked");
        }
	}
	
	public void findDistance(double lat1, double lon1, double lat2, double lon2){
		// formulas from http://www.ig.utexas.edu/outreach/googleearth/latlong.html 
		double earthRadius = 3963; // miles 
		double dLat = lat2 - lat1; 
		double dLon = lon2 - lon1; 
		double sdLat = sinD(dLat/2); 
		double sdLon = sinD(dLon/2); 
		double a = sdLat*sdLat + cosD(lat1)*cosD(lat2)*sdLon*sdLon; 
		double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double distance = earthRadius * c; // miles 
		bearing = Math.toDegrees(Math.atan2(sinD(dLon)*cosD(lat2), 
				 cosD(lat1)*sinD(lat2) - 
				 sinD(lat1)*cosD(lat2)*cosD(dLon)) 
				 );
		
		//Rounding distance and bearing
		distance = Math.round(distance*100)/100.00;
		bearing = Math.round(bearing*100)/100.00;
		
		distanceField = (TextView) findViewById(R.id.distance);
		distanceField.setText(" " + Double.toString(distance) + " miles");
	
		bearingField = (TextView) findViewById(R.id.bearing);
		bearingField.setText(" " + Double.toString(bearing));
		
	}
	
	public double sinD(double d){
		return Math.sin(Math.toRadians(d));
	}
	
	public double cosD(double d){
		return Math.cos(Math.toRadians(d));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_lost, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
		logAllFields();
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	@Override
	public void onResume() {
		super.onResume();
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 /* milliseconds */, 1 /* meters */, listener);
		Log.i("FindMyLocation", "Location listening on");
		compass.startListening();
	}
	
	@Override
	public void onPause() { // Don't use CPU time or battery when paused!
		super.onPause();
		locManager.removeUpdates(listener);
		Log.i("FindMyLocation", "Location listening off");
		compass.stopListening();
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		@Override
		public Fragment getItem(int position) {
			Log.i("Swipe", "*****getItem " + position);
			switch(position) {
			case 0: return new SectionFragment1();
			case 1: return new SectionFragment2();
			}
			return null; // shouldn't happen
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.mark).toUpperCase(l);
			case 1:
				return getString(R.string.find).toUpperCase(l);
			}
			return null;
		}
	}

	class SectionFragment1 extends Fragment {

		public SectionFragment1() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment1,
					container, false);
			return rootView;
		}
}
	
	class SectionFragment2 extends Fragment {

		public SectionFragment2() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment2,
					container, false);
			return rootView;
		}
	}

}
