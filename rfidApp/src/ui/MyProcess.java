package ui;

import com.example.rfid.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class MyProcess extends View {
	Bitmap process;
	public float rotate=0;
	boolean isroll=false;

	public MyProcess(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		process=BitmapFactory.decodeResource(getResources(), R.drawable.ref);
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(process, 0, 0, null);
		canvas.rotate(rotate,process.getWidth()/2,process.getHeight()/2);
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int measuredHeight = measureheight(heightMeasureSpec);

		int measuredWidth = measurewidth(widthMeasureSpec);

		setMeasuredDimension(measuredHeight, measuredWidth);
	}
	
	public int measureheight(int heightMeasureSpec)
	{
		int result=0;
		int mode=MeasureSpec.getMode(heightMeasureSpec);
		int size=MeasureSpec.getSize(heightMeasureSpec);
		if(mode==MeasureSpec.AT_MOST)
		{
			result=size;
		}
		return result;
	}

	public int measurewidth(int widthMeasureSpec)
	{
		int result=0;
		int mode=MeasureSpec.getMode(widthMeasureSpec);
		int size=MeasureSpec.getSize(widthMeasureSpec);
		if(mode==MeasureSpec.AT_MOST)
		{
			result=size;
		}
		return result;
	}
}
