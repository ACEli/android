package com.example.cShell;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class testMsgActivity extends Activity {
	Button bt;
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg);
		bt = (Button)findViewById(R.id.button1);
		bt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new myThread().run();
			}
			
		});
		tv =(TextView)findViewById(R.id.textView1);
	}
	/****************************************************************/
	class myThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();
			myHandler.sendMessage(msg);
		}
		
	}
	/****************************************************************/
	Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			tv.setText("hello msg");
		}
		
	};
	/****************************************************************/
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
