package edu.bc.baconju.bacon4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChatActivity extends Activity {
	private TextView chatTextField = null;
	private TextView groupField = null;
	private TextView passwordField = null;
	private TextView messageField = null;
	private String connectedGroup = "";
	private BufferedReader in  = null;
	private PrintWriter out = null;
	
	private static final String ENCRYPTION_ALGORITHM = "AES"; // or DES
	private static final int    KEY_LENGTH = 128; // 128 bit for AES, 56 bit for DES


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		chatTextField = (TextView)findViewById(R.id.chatTextField);
		groupField = (TextView)findViewById(R.id.groupField);
		passwordField = (TextView)findViewById(R.id.passwordField);
		messageField = (TextView)findViewById(R.id.messageField);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}
	
	public void connectToServer(View view){
		Log.d("ConnectToServer", "In connectToServer");
		
		final String groupName = groupField.getText().toString();
		Log.d("group", "group is " + groupName);
		
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) { // this line and the two above are to find out if we're connected to the net
	        new Thread(new Runnable() { // network activity MUST occur on a separate thread
		        public void run() {
		            connect(groupName); //send the group name
		        }
		    }).start();
        } else {
        	chatTextField.setText(getString(R.string.notConnected));
        }
        
        final String message = messageField.getText().toString();
		final String encryptedMessage = encrypt(message);
		new Thread(new Runnable() { // network activity MUST occur on a separate thread
	        public void run() {
	            out.println(encryptedMessage);
	        }
		}).start();
	}
	
	private void connect(String groupName) {
		
		if(connectedGroup.equals(groupName)){ //check if already in group
			return;
		}
		
		else {
		Socket socket = null;
		String source = "cslab.bc.edu";
		in  = null;
		out = null;
		try {
			socket = new Socket(source, 10000); //website, port #
			in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			// Step one of protocol (client sends first)
            out.println(groupName); // send group name
            connectedGroup = groupName;
            
            //Next, Reading in messages
            while(true){
            	String nextLine = in.readLine();
            	if(nextLine != null){
            		Log.d("NextLine", "Next line is " + nextLine);
            		final String msg = decrypt(nextLine); //decrypt message coming in
            		 chatTextField.post(new Runnable() { 
 		                public void run() { //append messages to chat field, scroll automatically
 		                	chatTextField.append(msg + "\n");
 		                	ScrollView scrollView = (ScrollView) chatTextField.getParent();
 		                	scrollView.fullScroll(View.FOCUS_DOWN);
 		                }
            		 });
            	}
            }
            
		} catch (IOException e) {
			chatTextField.setText(getString(R.string.unknown));
		} catch (Exception e) {
			chatTextField.setText(getString(R.string.unknown));
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					// we tried
				}
			}
		}
	}

	public String encrypt(String text) {
		byte[] result = encryptDecryptHelper(text.getBytes(), Cipher.ENCRYPT_MODE);
		String finalStr = Base64.encodeToString(result, Base64.DEFAULT);
		finalStr = finalStr.replace("\n", "");
		return finalStr;
	}
	
	public String decrypt(String text) {
		String cipherTextString =  text;
		byte[] cipherText = Base64.decode(cipherTextString, Base64.DEFAULT);
		byte[] result = encryptDecryptHelper(cipherText, Cipher.DECRYPT_MODE);
		String decryptedMessage = new String(result);
		Log.d("Decrypted", "Decrypted message is " + new String(result));
		return decryptedMessage;
	}

	// Can be slow, should be in non-UI thread
	public byte[] encryptDecryptHelper(byte[] oldBytes, int mode) {
		//errorField.setText(""); // might get changed below
		final String groupName = groupField.getText().toString();
		String key = passwordField.getText().toString();
		key = fixKey(key);
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ENCRYPTION_ALGORITHM);
			cipher.init(mode, keySpec);
			byte[] newBytes = cipher.doFinal(oldBytes);
			return newBytes; // normal case, no problems
		} catch (InvalidKeyException e) {
			//errorField.setText(R.string.invalidKey);
			return new byte[] {0};
		} catch (NoSuchAlgorithmException e) {
			// Handled below
		} catch (NoSuchPaddingException e) {
			// Handled below
		} catch (IllegalBlockSizeException e) {
			// Handled below
		} catch (BadPaddingException e) {
			// Handled below
		}

		return new byte[] {0};
	}
	
	// make key be KEY_LENGTH bits
	private String fixKey(String key) {
		if (key.length() > KEY_LENGTH/8)
			key = key.substring(0, KEY_LENGTH/8);
			while (key.length() < KEY_LENGTH/8)
				key += " ";
			return key;
	}
}
