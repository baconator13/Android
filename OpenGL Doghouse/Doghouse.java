package edu.bc.baconju.bacon6;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

/**
 * A three-dimensional solid pyramid for use as a drawn object in OpenGL ES 1.0/1.1.
 */
public class Doghouse {

    private final FloatBuffer vertexBuffer, colorBuffer;
    
    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float doghouseCoords[] = {
    	
    //LEFT OF DOORWAY
   	 -0.5f,  -0.5f,  0.0f,   // bottom left triangle
   	 -0.5f,  1.0f,   0.0f,    
   	 -0.1f,  -0.5f,  0.0f,   
   	 
   	 -0.1f,  -0.5f,  0.0f,   // top right triangle
   	 -0.1f,  1.0f,  0.0f,    
   	 -0.5f,  1.0f,   0.0f, 
   	 
     //RIGHT OF DOORWAY
   	  0.3f,  -0.5f,  0.0f,   // bottom left triangle
   	  0.3f,  1.0f,   0.0f,    
   	  0.7f,  -0.5f,  0.0f, 
   	  
   	  0.3f,  1.0f,  0.0f,   // top right triangle
   	  0.7f,  1.0f,   0.0f,    
   	  0.7f,  -0.5f,  0.0f, 
   	  
      //FRONT ROOF TRIANGLE
   	  -0.5f,  1.0f,  0.0f,   // bottom left triangle
   	  0.1f,   1.7f,   0.0f,    
   	  0.7f,   1.0f,  0.0f, 
   	  
      //BACK WALL
  	  -0.5f,  -0.5f,  -2.0f,   // bottom left triangle
   	  -0.5f,   1.0f,  -2.0f,    
   	  0.7f,   -0.5f,  -2.0f,

  	  -0.5f,   1.0f,  -2.0f,   // top right triangle
   	   0.7f,   1.0f,   -2.0f,    
   	   0.7f,   -0.5f,  -2.0f,
   	   
       //BACK WALL TRIANGLE
   	  -0.5f,   1.0f,   -2.0f,   
       0.1f,   1.7f,   -2.0f,    
       0.7f,   1.0f,   -2.0f,
       
       
       //LEFT WALL
   	  -0.5f,  -0.5f,   0.0f,   // bottom left triangle
      -0.5f,   1.0f,   0.0f,    
      -0.5f,   -0.5f,  -2.0f,

   	  -0.5f,   -0.5f,  -2.0f,   // top right triangle
      -0.5f,   1.0f,   -2.0f,    
      -0.5f,   1.0f,   0.0f,
      
      //LEFT ROOF PANE
  	  -0.5f,   1.0f,   -2.0f,   // top left triangle
       0.1f,   1.7f,   -2.0f,    
       0.1f,   1.7f,    0.0f,

      -0.5f,   1.0f,   -2.0f,   // bottom right triangle
      -0.5f,   1.0f,    0.0f,    
       0.1f,   1.7f,   0.0f,
       
       //RIGHT WALL
   	    0.7f,  -0.5f,    0.0f,   // top left triangle
        0.7f,   1.0f,    0.0f,    
        0.7f,  -0.5f,   -2.0f,

        0.7f,  -0.5f,   -2.0f,   // bottom right triangle
        0.7f,   1.0f,   -2.0f,    
        0.7f,   1.0f,   0.0f,
        
        //RIGHT ROOF PANE
    	 0.7f,   1.0f,    0.0f,   // bottom left triangle
         0.1f,   1.7f,    0.0f,    
         0.7f,   1.0f,   -2.0f,

         0.7f,   1.0f,   -2.0f,   // bottom right triangle
         0.1f,   1.7f,   -2.0f,    
         0.1f,   1.7f,   0.0f,
   	  
    };
    
    static final int COLORS_PER_VERTEX = 4;
    static float colors[] = {
    	1, 0, 0, 1, // front, note blended colors on this triangle
    	0, 1, 0, 1, // front
    	0, 0, 1, 1, // front
    	
    	1, 1, 0, 1, // left
    	0, 1, 0, 1, // left
    	0, 0, 1, 1, // left
    	
    	1, 0, 0, 1, // right
    	0, 1, 0, 1, // right
    	0, 0, 1, 1, // right
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    	
    	1, 0, 0, 1, // bottom
    	0, 1, 0, 1, // bottom
    	0, 0, 1, 1, // bottom
    };
    

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Doghouse() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                doghouseCoords.length * 4); // (# of coordinate values * 4 bytes per float)
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(doghouseCoords);
        vertexBuffer.position(0);
        
        ByteBuffer b2 = ByteBuffer.allocateDirect(colors.length*4);
        b2.order(ByteOrder.nativeOrder());
        colorBuffer = b2.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

    public void draw(GL10 gl) {
        // Since this shape uses vertex arrays, enable them
        gl.glVertexPointer(COORDS_PER_VERTEX, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        gl.glColorPointer(COLORS_PER_VERTEX, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

	        // draw the shape
            // with glDrawArrays (as opposed to glDrawElements) there is no separate "order" list: uses vertices in sequence
	        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertexBuffer.remaining()/COORDS_PER_VERTEX); // count is # of vertices, not drawn objects or coords

        // Disable vertex array drawing to avoid
        // conflicts with shapes that don't use it
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}