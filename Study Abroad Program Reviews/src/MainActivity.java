package com.example.baconfinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class MainActivity extends FragmentActivity implements
ActionBar.TabListener{

	private WebView webView;
	String programName;
	String searchEntry;
	String review, user, reviewStr, site;
	String id;
	InputStream is=null;
	String result=null;
	String line=null;
	int code;
	String program;
	String name;
	int inserted = 0;
	int selected = 0;
	
	private Pattern  pattern = null;
	private String	 programExp = "\\<p\\>\\w+[\\w\\s\\.\\,\\&\\;\\Ñ\\'\\Õ\\Ò\\Ó]+";
	private TextView description = null;
	TextView exploreLabel;
	ViewPager mViewPager;
	SectionsPagerAdapter mSectionsPagerAdapter;
	TextView info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
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
}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	//When a user selects a program radio button, the map
	//on the MAP tab is updated with a map of the school via a WebView
	public void radioButtonClicked(View view) {
	    
		boolean checked = ((RadioButton) view).isChecked();
	    
		// Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.morocco_radio:
	            if (checked)
	            	program = getString(R.string.moroccoUniversity).toString();
	                Log.d("Morocco", "Morocco selected");
	                showMap(program);
	            break;
	        case R.id.tokyo_radio:
	            if (checked)
	            	program = getString(R.string.japan).toString();
	                Log.d("Japan", "Japan selected");
	                showMap(program);
	            break;
	        case R.id.greece_radio:
	            if (checked)
	            	program = getString(R.string.greece).toString();
	                Log.d("Greece", "Greece selected");
	                showMap(program);
	            break;
	    }
	}
	
	//Show Map of checked program using GoogleMaps within
	//A WebView
	public void showMap(String program){
		Log.d("Program is", program);
		
		webView = (WebView)findViewById(R.id.webView1);
		webView.setWebViewClient(new WebViewClient()); // handle clicks here, don't launch browser		
		
		if(program.equals(getString(R.string.moroccoUniversity).toString())){
			Log.d("Morocco", "Show map of Morocco");
			String url = "http://maps.googleapis.com/maps/api/staticmap";
			// first param starts with ?, the rest start with &
			url += "?center=AlAkhawaynUniversity,+Ifrane,+Morocco"; // optional if markers or paths or 'visible' used. Could also use lat,lon (6 decimal places)
			url += "&zoom=16";
			url += "&size=400x500";  // width, height (in pixels)
			url += "&maptype=hybrid"; // choices are: roadmap, satellite, hybrid, terrain
			url += "&sensor=false"; // true if location came from sensor like GPS.  Required.
			
			Log.d("Maps", "Length of URL:" + url.length()); // max allowed length is approx 1,500 characters
			
			webView.loadUrl(url); // do it
		}
		else if(program.equals(getString(R.string.japan).toString())){
			Log.d("Japan", "Show map of Japan");
			//URL is http://maps.googleapis.com/maps/api/staticmap?center=SophiaUniversity,+Tokyo,+Japan&zoom=16&size=400x500&maptype=hybrid&sensor=false
			String url = "http://maps.googleapis.com/maps/api/staticmap";
			// first param starts with ?, the rest start with &
			url += "?center=SophiaUniversity,+Tokyo,+Japan"; // optional if markers or paths or 'visible' used. Could also use lat,lon (6 decimal places)
			url += "&zoom=16";
			url += "&size=400x500";  // width, height (in pixels)
			url += "&maptype=hybrid"; // choices are: roadmap, satellite, hybrid, terrain
			url += "&sensor=false"; // true if location came from sensor like GPS.  Required.
			
			Log.d("Maps", "Length of URL:" + url.length()); // max allowed length is approx 1,500 characters
			
			webView.loadUrl(url); // do it
		}
		else if(program.equals(getString(R.string.greece).toString())){
			Log.d("Greece", "Show map of greece");
			String url = "http://maps.googleapis.com/maps/api/staticmap";
			// first param starts with ?, the rest start with &
			url += "?center=AmericanCollegeOfThessaloniki,+Thessaloniki,+Greece"; // optional if markers or paths or 'visible' used. Could also use lat,lon (6 decimal places)
			url += "&zoom=16";
			url += "&size=400x500";  // width, height (in pixels)
			url += "&maptype=hybrid"; // choices are: roadmap, satellite, hybrid, terrain
			url += "&sensor=false"; // true if location came from sensor like GPS.  Required.
			
			Log.d("Maps", "Length of URL:" + url.length()); // max allowed length is approx 1,500 characters
			
			webView.loadUrl(url); // do it
		}
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
		case 2: return new SectionFragment4();
		case 3: return new SectionFragment3();
		}
		return null; // shouldn't happen
	}

	@Override
	public int getCount() {
		// Show 2 total pages.
		return 4;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return getString(R.string.selectTab).toUpperCase(l);
		case 1:
			return getString(R.string.mapTab).toUpperCase(l);
		case 2:
			return getString(R.string.addTab).toUpperCase(l);
		case 3:
			return getString(R.string.learnTab).toUpperCase(l);
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
		View rootView = inflater.inflate(R.layout.newfragment1,
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
		View rootView = inflater.inflate(R.layout.newfragment2,
				container, false);
		return rootView;
	}
}

class SectionFragment3 extends Fragment {

	public SectionFragment3() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
				View rootView = inflater.inflate(R.layout.newfragment3,
				container, false);
				return rootView;
			}
	
	@Override
	public void onResume() {
		super.onResume();
		
		final EditText e_search =(EditText)findViewById(R.id.searchProgram);
        Button select=(Button) findViewById(R.id.getReviewButton);
        
    	final EditText description = (EditText)findViewById(R.id.description);
    	description.setText(R.string.noReview);
    	
    	//Make description text view scrollable
    	description.setScroller(new Scroller(getBaseContext()));
    	description.setMaxLines(10);
    	description.setVerticalScrollBarEnabled(true);
    	description.setMovementMethod(new ScrollingMovementMethod());
        
    	//On select button click, web scrape the BC study abroad site
    	//for the program description, and select reviews from the table
    	//on phpMyAdmin
    	//Set the description text to be the program description followed by student reviews
        select.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchEntry = e_search.getText().toString();

				(new Thread(){
					public void run(){
						select();
						final String site = getUrl(searchEntry);							        
						final String temperature = getDescriptionStr(site);
						temperature.substring(4);
						description.post(new Runnable(){
							public void run(){
								if(selected==0){
									description.setText(R.string.noReview);
									e_search.setText("");									
								}
								else{
									reviewStr = ("THE PROGRAM\n"+temperature+"\n\n"+"REVIEWS\n"+user+": "+review);
									description.setText(reviewStr);
									e_search.setText("");
									user="";
									review="";
								}								
							}
						});
					}
				}).start();
			}
		});
	}
	
	//Pass over the program abbreviation and return the website URL
	//from BCs study abroad website for web scraping!
	private String getUrl(String program){
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        
        if (networkInfo != null && networkInfo.isConnected()) {
        	
        	if(searchEntry.equals(getString(R.string.AU).toString())){
        		site = "http://www.bc.edu/offices/international/programs/progsemesteryear/africamideast/ifrane.html";
        	}
        	else if(searchEntry.equals(getString(R.string.SUT).toString())){
        		site = "http://www.bc.edu/offices/international/programs/progsemesteryear/asia/tokyo.html";
        	}
        	else if(searchEntry.equals(getString(R.string.ACT).toString())){
        		site = "http://www.bc.edu/offices/international/programs/progsemesteryear/europerussia/thessaloniki.html";
        	}
        }
        
        else {
        	site = getString(R.string.noNetworkError);
        }
        return site;
	}
	
	//Pass over the website url, web scrape the source for the specified pattern
	//And return a match if found
	private String getDescriptionStr(String site){
		BufferedReader in = null;
		if (pattern == null)
			pattern = Pattern.compile(programExp); // slow, only do once, and not on UI thread
		try {
			URL url = new URL(site);
			in = new BufferedReader(
			            new InputStreamReader(
			            url.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				Matcher m = pattern.matcher(inputLine);
				if (m.find()) {
					String s = m.group(0);
					return s;
				}
			}
			return getString(R.string.unknown);  // never found the pattern
		} catch (IOException e) {
			return getString(R.string.unknown);
		} catch (Exception e) {
			return getString(R.string.unknown);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// ignore, we tried and failed to close, limp along anyway
				}
		}
	}
	
	//Using name value pairs in an array list, send over program to the
	//select.php file in phpMyAdmin
	//this file will query the table and return a jsonencode() of the
	//returned items (username and review)
    public void select()
    {
    	if(searchEntry.equals("")){
    		selected = 0;
    	}
    	else{
    		selected = 1;
    	
    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 
    	nameValuePairs.add(new BasicNameValuePair("program",searchEntry));
    	
    	try
    	{
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://cscilab.bc.edu/~baconju/select.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost); 
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
	        Log.e("pass 1", "connection success ");
	}
        catch(Exception e)
	{
        	Log.e("Fail 1", e.toString());
	    	Toast.makeText(getApplicationContext(), "Invalid IP Address",
			Toast.LENGTH_LONG).show();
	}     
        
        try
        {
         	BufferedReader reader = new BufferedReader
				(new InputStreamReader(is,"iso-8859-1"),8);
            	StringBuilder sb = new StringBuilder();
            	while ((line = reader.readLine()) != null)
		{
       		    sb.append(line + "\n");
           	}
            	is.close();
            	result = sb.toString();
	        Log.e("pass 2", "connection success ");
	}
        catch(Exception e)
    	{
		//Log.e("Fail 2", e.toString());
	}     
       
   	try
    	{
        	JSONObject json_data = new JSONObject(result);
        	review=(json_data.getString("review"));
        	user=(json_data.getString("username"));
    	}
        catch(Exception e)
    	{
        	//Log.e("Fail 3", e.toString());
    	}
    }
    }
}



class SectionFragment4 extends Fragment {


	public SectionFragment4() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
				
			View rootView = inflater.inflate(R.layout.newfragment4,
				container, false);
		        
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		final EditText e_program=(EditText)findViewById(R.id.editText0);
		final EditText e_id=(EditText)findViewById(R.id.editText1);
        final EditText e_review=(EditText)findViewById(R.id.editText2);
        Button insert=(Button) findViewById(R.id.button1);
        
        //Make review edit text scrollable
        e_review.setScroller(new Scroller(getBaseContext()));
        e_review.setMaxLines(2);
        e_review.setVerticalScrollBarEnabled(true);
        e_review.setMovementMethod(new ScrollingMovementMethod());
          
        //Upon clicking the insert button, get values of program, username, review
        //Then calls insert() which will use insert.php to insert values into table
        //Finally, clear these input fields, and use toast alert if success/fail
        insert.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				programName = e_program.getText().toString();
				id = e_id.getText().toString();
				review = e_review.getText().toString();
				
				(new Thread(){
					public void run(){
						insert();
						e_program.post(new Runnable(){
							public void run(){
								e_program.setText("");
								e_id.setText("");
								e_review.setText("");
								if(inserted==1)
								Toast.makeText(getBaseContext(), "Review Added!",
										Toast.LENGTH_SHORT).show();
								else if(inserted==0)
									Toast.makeText(getBaseContext(), "Review Failed: Please Check All Fields",
											Toast.LENGTH_SHORT).show();								
								}
							});
						}
					}).start();
				}
			});
		}

	
	public void insert(){
		//If trying to send over blank values, do not perform insertion
		if(programName.equals("") || id.equals("") || review.equals("")){
			inserted=0;
		}
		//Otherwise, using name value pairs in an array list, send over the program,
		//username, and review. insert.php file in phpMyAdmin will insert these
		//values into the table
		else{
			inserted = 1;
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("program",programName));
				nameValuePairs.add(new BasicNameValuePair("id",id));
				nameValuePairs.add(new BasicNameValuePair("review",review));
			try
			{
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://cscilab.bc.edu/~baconju/insert.php");
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost); 
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
	        
				BufferedReader in = new BufferedReader(new InputStreamReader(is));
				String data = ""; // A comment to show in the output box
				String nextLine;
				
				while ((nextLine = in.readLine()) != null) // show everything that comes to us from the socket
					data += nextLine + "\n";
					Log.d("Data", data);
	        
	         
					//make buffered reader, read in from string
					//Log.d("IS", is);
					Log.e("pass 1", "connection success ");
			}
			catch(Exception e)
			{
				Log.e("Fail 1", e.toString());
				Toast.makeText(getApplicationContext(), e.toString(),
						Toast.LENGTH_LONG).show();
			}         
			try{
				BufferedReader reader = new BufferedReader
						(new InputStreamReader(is,"iso-8859-1"),8);
				StringBuilder sb = new StringBuilder();
				while ((line = reader.readLine()) != null){
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();
				Log.e("pass 2", "connection success ");
			}
			catch(Exception e){
				//Log.e("Fail 2", e.toString());
			}     		
			try{
				JSONObject json_data = new JSONObject(result);
				code=(json_data.getInt("code"));
			
				if(code==1){
					Toast.makeText(getBaseContext(), "Inserted Successfully",
					Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getBaseContext(), "Sorry, Try Again",
					Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception e)
			{
				//Log.e("Fail 3", e.toString());
			}
		}
	}
}
}
