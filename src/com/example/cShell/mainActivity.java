package com.example.cShell;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class mainActivity extends Activity {
	private Button  testShell;
	private TextView tv;
	private EditText cmd;
	String testGit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		testShell=(Button)findViewById(R.id.testShell);
		testShell.setOnClickListener(new testBtOnClickListener());
		
		tv=(TextView)findViewById(R.id.show);
		cmd=(EditText)findViewById(R.id.cmd);
	}
	/************************************************************************/
	 public void execCommand(String command) throws IOException {  
		     // start the ls command running  
		     //String[] args =  new String[]{"sh", "-c", command};
 
		     Runtime runtime = Runtime.getRuntime();    
		     Process proc = runtime.exec(command);        //这句话就是shell与高级语言间的调用  
		         //如果有参数的话可以用另外一个被重载的exec方法  
		         //实际上这样执行时启动了一个子进程,它没有父进程的控制台  
		         //也就看不到输出,所以我们需要用输出流来得到shell执行后的输出  
		        InputStream inputstream = proc.getInputStream();  
		        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);  
		         BufferedReader bufferedreader = new BufferedReader(inputstreamreader);  
		         // read the ls output  
		         String line = "";  
		         StringBuilder sb = new StringBuilder(line);  
		         while ((line = bufferedreader.readLine()) != null) {  
		             //System.out.println(line);  
		                 sb.append(line);  
		                 sb.append('\n');  
		         }  
		         tv.setText(sb.toString());  
		         //使用exec执行不会等执行成功以后才返回, 它会立即返回  
		         //所以在某些情况下是很要命的(比如复制文件的时候)  
		         //使用wairFor()可以等待命令执行完成以后才返回  
		         try {  
		             if (proc.waitFor() != 0) {  
		                 System.err.println("exit value = " + proc.exitValue());  
		             }  
		         }  
		         catch (InterruptedException e) {    
		             System.err.println(e);  
		         }  
		     }  
		 
	/*************************************************************************/
	     /**
	      * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
	      * @param command 命令：String apkRoot="chmod 777 "+getPackageCodePath(); RootCommand(apkRoot);
	      * @return 应用程序是/否获取Root权限
	      */
	     public  boolean RootCommand(String command)throws IOException
	     {
	         Process process = null;
	         DataOutputStream os = null;
	         try
	         {
	             process = Runtime.getRuntime().exec("su");
	             os = new DataOutputStream(process.getOutputStream());
	             os.writeBytes(command + "\n");
	             os.writeBytes("exit\n");
	             os.flush();
	             process.waitFor();
	             
	             	InputStream inputstream = process.getInputStream();  
			        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);  
			         BufferedReader bufferedreader = new BufferedReader(inputstreamreader);  
			         // read the ls output  
			         String line = "";  
			         StringBuilder sb = new StringBuilder(line);  
			         while ((line = bufferedreader.readLine()) != null) {  
			             //System.out.println(line);  
			                 sb.append(line);  
			                 sb.append('\n');  
			         }  
			         tv.setText(sb.toString()); 
	         } catch (Exception e)
	         {
	             Log.d("*** DEBUG ***", "ROOT REE" + e.getMessage());
	             return false;
	         } finally
	         {
	             try
	             {
	                 if (os != null)
	                 {
	                     os.close();
	                 }
	                 process.destroy();
	             } catch (Exception e)
	             {
	             }
	         }
	         Log.d("*** DEBUG ***", "Root SUC ");
	         return true;
	     }
 /*************************************************************************/ 
	private class testBtOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
			try {
				String apkRoot="chmod 777 "+getPackageCodePath();
				//execCommand("./data/data/com.example.cShell/test.sh\n");
				//execCommand("toolbox kill 819\n");
				
				execCommand("/system/bin/su");
				//execCommand(apkRoot);
				execCommand(cmd.getText().toString());
				
		        //RootCommand(apkRoot);//android 4.4 can run even can run "reboot",but can't in 2.3
		        //RootCommand(cmd.getText().toString());
		        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
}
