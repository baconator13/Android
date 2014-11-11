//Julie Bacon
//Professor Ames CS344
//HW1

package edu.bc.baconju.bacon1;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalcActivity extends Activity {

	private String operand1 = "";
	private String operand2 ="";
	private String currOp = "";
	private String answerString = "";
	private double answer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calc);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calc, menu);
		return true;
	}
	
	public void digitPressed(View view){
		//If user hits equal button and then a digit, the calculator will clear
		if(currOp.equals("eq")){
			operand1 = "";
			operand2 = "";
			answer = 0;
			currOp = "";
		}
		
		Log.d("CalcActivity", "currOp is " + currOp);
		
		Button button = (Button)view;
		Log.d("Digit", "Digit was pressed");
		TextView result = (TextView) findViewById(R.id.result);

		
		//	Something reasonable happens when you click the decimal button twice!
		if(button.getText().equals(".")){ //if you hit the decimal button				
			if(operand1.contains(".")){ //If the operand already contains a decimal,
			operand1 = operand1.replace(".", ""); //remove the old one first
			}		
		}		
		
		operand1 += button.getText();
		result.setText(operand1);
	}
	
	public void clear(View view){
		//Clears the operands and calculator answer
		Log.d("Clear", "Clear was pressed");
		TextView result = (TextView) findViewById(R.id.result);
		operand1 = "";
		operand2 = "";
		answer = 0;
		result.setText(operand1);
	}
	
	public void currOp(View view){
		doMath(operand1, operand2, currOp); //Do the math with the most recent operands/operator
		
		Button button = (Button)view;
		currOp = "" + button.getText();	 //Then update the operator
		
		operand2 = Double.toString(answer); //Set operand to the answer, and clear the other operand
		operand1 = "";
	}
	
	public double doMath(String op1, String op2, String op){
		//If no operator is set, answer is just the first number entered
		if(currOp.equals("")){
			if(operand1 != ""){
				answer=(Double.parseDouble(op1));
			}
		}
		
		//If operator is equals
		if(currOp.equals("eq")){
			if(operand1==""){
				answer = 0;
			}
			else if(operand2==""){
				answer = Double.parseDouble(op1);
			}
			else{
			answer = 0;
			}
		}
		
		//Addition
		if(currOp.equals("+")){
			if(op1 ==""){
				answer = 0;
			}
			else if(op2 ==""){
				answer = Double.parseDouble(op1);
			}
			else{
				double num1 = Double.parseDouble(operand2);
				double num2 = Double.parseDouble(operand1);	
				answer = num1 + num2;
			}
		}
		
		//Subtraction
		if(currOp.equals("-")){
			if(op1 ==""){
				answer = 0;
			}
			else if(op2 ==""){
				answer = Double.parseDouble(op1);
			}
			else{
				double num1 = Double.parseDouble(operand2);
				double num2 = Double.parseDouble(operand1);	
				answer = num1 - num2;
			}
		}
		
		//Multiplication
		if(currOp.equals("X")){
			if(op1 ==""){
				answer = 0;
			}
			else if(op2 ==""){
				answer = Double.parseDouble(op1);
			}
			else{
				double num1 = Double.parseDouble(operand2);
				double num2 = Double.parseDouble(operand1);	
				answer = num1 * num2;
			}
		}
			
		//Division
			if(currOp.equals("Ö")){
				if(op1 ==""){
					answer = 0;
				}
				else if(op2 ==""){
					answer = Double.parseDouble(op1);
				}
				else{
					double num1 = Double.parseDouble(operand2);
					double num2 = Double.parseDouble(operand1);	
					answer = num1 / num2;
				}
			}
		
		return answer;
	}
	
	//Find the result 
	public void findResult(View view){
		Button button = (Button)view;
		TextView result = (TextView) findViewById(R.id.result);		
		
		doMath(operand1, operand2, currOp);
		
		answerString = Double.toString(answer);
		result.setText(answerString);
		
		operand1 = answerString; //Do the math, then set operand1 to the answer, clear second operand
		operand2 = "";
		currOp = "eq";
	}
	
	public void changeSign(View view){
		//If operand one has a negative sign in the zero-th index, shift over one index to remove
		//Otherwise concatenate a negative sign infront of the positive number string
		Button button = (Button)view;
		TextView result = (TextView) findViewById(R.id.result);
		if(operand1.indexOf("-") == 0)
			operand1 = operand1.substring(1);
		else
			operand1 = "-"+operand1;
		result.setText(operand1);
	}
}
