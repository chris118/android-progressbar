package com.example.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("HandlerLeak") public class MainActivity extends Activity {

	ProgressBar mProgressbar = null;
	Button mStartButon = null;
	TextView mText = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mProgressbar = (ProgressBar)this.findViewById(R.id.progressBar1);
		mStartButon = (Button)this.findViewById(R.id.startBtn);
		mText  = (TextView)this.findViewById(R.id.percent);
		
		mStartButon.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				
				mProgressbar.setProgress(0);
				
				Thread thead = new Thread(new Runnable(){

					@Override
					public void run() {
						 
						while(mProgressbar.getProgress() < 100){
							
							mProgressbar.setProgress(mProgressbar.getProgress() + 10);
							//mText.setText(String.valueOf(mProgressbar.getProgress()));
							
							Message msg = mHandler.obtainMessage();
							Bundle data = new Bundle();
							data.putString("percent", String.valueOf(mProgressbar.getProgress()));
							msg.setData(data);
							mHandler.sendMessage(msg);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					
					
				});
				
				thead.start();
				
			}
			
		});
	}

	Handler mHandler = new Handler(){
		
	    public void handleMessage(Message msg) {
	    	super.handleMessage(msg);
	    	
	    	Bundle data = msg.getData();
	    	String percent = data.getString("percent");
	    	mText.setText(percent);
	    }
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
