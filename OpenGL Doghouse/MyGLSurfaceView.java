package edu.bc.baconju.bacon6;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {
	
	public MyGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
        // Set the Renderer for drawing on the GLSurfaceView
		setEGLConfigChooser(8, 8, 8, 8, 16, 0); // capabilities required
		mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	//  private float mPreviousX = 0;
  //  private float mPreviousY = 0;
    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);
        
        setEGLConfigChooser(8, 8, 8, 8, 16, 0); // capabilities required
        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, we are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                mRenderer.setAngle(
                        (mRenderer.getAngleX() + ((dy * TOUCH_SCALE_FACTOR))),
                        (mRenderer.getAngleY() + ((dx * TOUCH_SCALE_FACTOR))));  // = 180.0f / 320
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

}