package httpapi;

import java.io.IOException;
import android.os.AsyncTask;
import android.widget.TextView;

public class HttpTask extends AsyncTask<String, String, String> {

	HttpUtils util;
	TextView tv;
	String tab;
	public HttpTask(TextView v,String table,double tagId)
	{
		tv=v;tab=table;
		util=new HttpUtils(table,tagId);
		
	}
	@Override
	protected String doInBackground(String... params) {
		
		String a="";
		try {
			a = util.getinputResult();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String b=util.getResult(a);
		System.out.println(b);
		
		publishProgress(b);//更新ui线程，将查询的内容提交给ui线程
		String s="";
		if(b.equals("查无此结果"))
		{
			return "没有结果";
		}
		else{
		if(tab.equals("tag"))
		{
			s=b.split("\n")[1];
			s=s.replaceAll("medicineName:","" );
			return s; //返还的是这个信息的最重要的信息，通过百度可以查询
		
		}
		if(tab.equals("factory"))
		{
			s=b.split("\n")[1];
			s=s.replaceAll("工厂名字:", "");
			return s; //返还的是这个信息的最重要的信息，通过百度可以查询
		}
		if(tab.equals("store"))
		{
			s=b.split("\n")[1];
			s=s.replaceAll("药店名:","" );
			return s; //返还的是这个信息的最重要的信息，通过百度可以查询
		
		}
		if(tab.equals("tag"))
		{
			s=b.split("\n")[1];
			s=s.replaceAll("运输商:","" );
			return s;           //返还的是这个信息的最重要的信息，通过百度可以查询
		
		}
		}
		return b;
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
		String a=values[0];		//这是ui线程跟异步线程的接口，ui将在这里产生用户直观可见的效果：本程序是将结果更新显示在屏幕上

		tv.setText(a);
		super.onProgressUpdate(values);
	}
	
}
