package edu.bc.baconju.bacon2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PuzzleActivity extends Activity {
	private Button[][] buttons = new Button[4][4];
	private int row;
	private int col;
	private String buttonString;
	private boolean above;
	private boolean below;
	private boolean left;
	private boolean right;
	private int size = 16;
	
	ArrayList<String> numList = new ArrayList<String>(size);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_puzzle);
		createList();
		randomize(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.puzzle, menu);
		return true;
	}
	
	public void createList(){
		buttons[0][0] = (Button)findViewById(R.id.btn1);
		buttons[0][1] = (Button)findViewById(R.id.btn2);
		buttons[0][2] = (Button)findViewById(R.id.btn3);
		buttons[0][3] = (Button)findViewById(R.id.btn4);
		
		buttons[1][0] = (Button)findViewById(R.id.btn5);
		buttons[1][1] = (Button)findViewById(R.id.btn6);
		buttons[1][2] = (Button)findViewById(R.id.btn7);
		buttons[1][3] = (Button)findViewById(R.id.btn8);
		
		buttons[2][0] = (Button)findViewById(R.id.btn9);
		buttons[2][1] = (Button)findViewById(R.id.btn10);
		buttons[2][2] = (Button)findViewById(R.id.btn11);
		buttons[2][3] = (Button)findViewById(R.id.btn12);
		
		buttons[3][0] = (Button)findViewById(R.id.btn13);
		buttons[3][1] = (Button)findViewById(R.id.btn14);
		buttons[3][2] = (Button)findViewById(R.id.btn15);
		buttons[3][3] = (Button)findViewById(R.id.btn16);
	}
	
	public int getRow(Button b){
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
			if(buttons[i][j] == b){
				return i; 
			}
		}
	}
		return 0;
	}
	
	public int getCol(Button b){
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
			if(buttons[i][j] == b){
				return j; 
			}
		}
	}
		return 0;
}
	
	public void buttonClicked(View view){
		Button btnClicked = (Button)view;
		btnClicked.getText();		
		
		row = getRow(btnClicked);
		col = getCol(btnClicked);
		
		if(btnClicked == buttons[row][col]){
			Log.d("ButtonsMatch", "ButtonsMatch!");
		}
		
		//test to see if you can use .getText using array
		buttonString = (buttons[row][col]).getText().toString();
		above = checkButtonAbove(row, col);	
		below = checkButtonBelow(row, col);
		left = checkButtonLeft(row, col);
		right = checkButtonRight(row, col);
		
		buttonString = (buttons[row][col]).getText().toString();
		
		if(above){
			(buttons[(row-1)][(col)]).setText(buttonString);
			(buttons[(row)][(col)]).setText("");
		}
		if(below){
			(buttons[(row+1)][(col)]).setText(buttonString);
			(buttons[(row)][(col)]).setText("");
		}
		if(left){
			(buttons[(row)][(col-1)]).setText(buttonString);
			(buttons[(row)][(col)]).setText("");
		}
		if(right){
			(buttons[(row)][(col+1)]).setText(buttonString);
			(buttons[(row)][(col)]).setText("");
		}
		Log.d("ButtonsMatch", "ButtonsMatch!");
	}
	
	public boolean checkButtonAbove(int row, int col){
		if((row-1) >=0){ //Stay in bounds
			buttonString = (buttons[(row-1)][(col)]).getText().toString();
			return buttonString.equals("");
		}
		else{
			return false;
		}
	}
	
	public boolean checkButtonBelow(int row, int col){
		if((row+1) <= 3){
			buttonString = (buttons[(row+1)][(col)]).getText().toString();
			return buttonString.equals("");
		}
		else{
			return false;
		}
	}
	
	public boolean checkButtonLeft(int row, int col){
		if((col-1) >=0){
			buttonString = (buttons[(row)][(col-1)]).getText().toString();
			return buttonString.equals("");
		}
		else{
			return false;
		}
	}
	
	public boolean checkButtonRight(int row, int col){
		if((col+1) <= 3){
		buttonString = (buttons[(row)][(col+1)]).getText().toString();
		return buttonString.equals("");
		}
		else{
			return false;
		}
	}
	
	public void randomize(View view){
		int size = 15;
		
		
		Log.d("Randomize", "In Randomize");
		for(int i = 1; i <= size; i++){	
			numList.add(Integer.toString(i));
			Log.d("List", "list contains " + i);
		}
		numList.add("");
		
		
		Random rand = new Random();
		
		//For each button
        for(int i = 0; i < 4; i++){
        	for(int j = 0; j < 4; j++){
        	
            int index = rand.nextInt(numList.size());
            (buttons[i][j]).setText(numList.remove(index)); 		
		}
		}
	}
}
