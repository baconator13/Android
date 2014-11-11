package edu.bc.baconju.bacon6;
/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.SystemClock;
import android.view.MotionEvent;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Doghouse doghouse;
    public volatile float xAngle;
    public volatile float yAngle;
    
    @Override
    // Initialization goes here:
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.9f, 0.9f, 0.9f, 1.0f);  // background color, R G B Alpha
        gl.glEnable(GL10.GL_DEPTH_TEST); // necessary for hidden surface removal

        doghouse = new Doghouse();
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        // Draw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();   // reset the matrix to its default state

        // eye location, look-at location, up
        GLU.gluLookAt(gl, 0, 0, 0,   0f, 0f, 0f,   0f, 1.0f, 0.0f);      

	    gl.glPushMatrix();
	    	gl.glTranslatef(-0.1f,  -0.6f,  0.25f);
	    	gl.glRotatef(yAngle, 0, 1, 0);		
	    	gl.glRotatef(xAngle, 1, 0, 0);		
            doghouse.draw(gl);
        gl.glPopMatrix();  
    }
    
    
    @Override
    // Adjust the viewport based on geometry changes
    // such as screen rotations
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // gl.glViewport(0, 0, width, height); // if you want to draw into  only a portion of the GLSurfaceView

        // make adjustments for screen ratio
        float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        GLU.gluPerspective(gl, 70, ratio, 0.5f, 10);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    public float getAngleX() {
        return xAngle;
    }
    
    public float getAngleY() {
        return yAngle;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float angleX, float angleY) {
        xAngle = angleX;
        yAngle = angleY;     
    }

}