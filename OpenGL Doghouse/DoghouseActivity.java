package edu.bc.baconju.bacon6;

import edu.bc.baconju.bacon6.MyGLRenderer;
//import edu.bc.cs.ameswi.cs344s14.opengl.R;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MotionEvent;

public class DoghouseActivity extends Activity {
    private MyGLSurfaceView mGLView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doghouse);
		
		mGLView = (MyGLSurfaceView) findViewById(R.id.glView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doghouse, menu);
		return true;
	}
	
	@Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause(); // important
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

}
