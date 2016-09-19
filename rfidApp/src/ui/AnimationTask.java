package ui;

import ui.MyProcess;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

public class AnimationTask extends AsyncTask<String, String, String> {

	MyProcess process;

	TranslateAnimation t1,t2;
	public AnimationTask(MyProcess p)
	{
		process=p;
		
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		t1=new TranslateAnimation(-100,300,500,300);
		t1.setDuration(1250);
		publishProgress("start");
				float r=0;
		for(int i=0;i<108;i++)
		{
			for(int p=0;p<20000;p++)
        	{
        		for(int w=0;w<700;w++)
        			{
        				 ;
        			}
        		
        	}
			r-=10;
			publishProgress(r+"");
		}
		publishProgress("done");
		t2=new TranslateAnimation(500, 300,-100, 300 );
		t2.setDuration(1000);
		
		return "done";
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(String... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	
		String i=values[0];
		
		if(i.equals("start"))
		{
			process.setVisibility(View.VISIBLE);
		}
		else if(i.equals("done"))
		{
			process.setVisibility(View.GONE);
		}else{
		process.rotate=Float.valueOf(i);
		process.invalidate();
		}
	}

}
