/*
 * Tasks:
 * 1) Need a continuous countdown
 * --added a onTick(), but it is not working yet....needs attention
 * --onFinish() will be needed as well, can be done after the tick is finished
 * 
 */

package com.nickdown.ysera;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {

	final Handler handler = new Handler();
	public static final long MIN = 60;
	public static final long HOUR = 60 * MIN;
	public static final long DAY = 24 * HOUR;

	public static Button bt_calc;
	public static EditText et_day;
	public static EditText et_month;
	public static EditText et_year;
	public static TextView tv_result;

	public Timer timer;
	public TimerTask timerTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (bt_calc == null) {
			Log.e("ERRRROOR", "Button is null");
		}

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		startTimer();
	}

	public void startTimer() {
		timer = new Timer();

		initializeTimerTask();

		timer.schedule(timerTask, 5000, 1000);
	}

	public void stopTimerTask(View w) {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/*
	 * NEEDS WORK!!!!!!
	 */
	public void initializeTimerTask(){
		timerTask = new TimerTask(){
			public void run(){
				
				handler.post(new Runnable){
					public void run(){
						
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
						final String strDate = simpleDateFormat.format(calendar.getTime());
					
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(getApplicationContext(), strDate, duration);
						toast.show();
					}
					
				}
			}
		}
			
	}

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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			bt_calc = (Button) rootView.findViewById(R.id.bt_calc);
			et_day = (EditText) rootView.findViewById(R.id.et_day);
			et_month = (EditText) rootView.findViewById(R.id.et_month);
			et_year = (EditText) rootView.findViewById(R.id.et_year);
			tv_result = (TextView) rootView.findViewById(R.id.tv_result);

			bt_calc.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					updateCountdown();
				}

			});

			return rootView;
		}
	}

	private static void updateCountdown() {
		/*
		 * Getting inputs and parsing/setting the input to new variables
		 * variable
		 */
		String day = et_day.getText().toString();
		int d = Integer.parseInt(day);

		String month = et_month.getText().toString();
		int m = Integer.parseInt(month);

		String year = et_year.getText().toString();
		int y = Integer.parseInt(year);

		/*
		 * Setting the future date for event and setting cal to yy/mm/dd
		 */
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(y, m - 1, d);
		long eventTime = cal.getTimeInMillis();

		/*
		 * Setting the current time to right now object, then converting it to
		 * millis and setting it to todayInMillis
		 */
		Calendar rightNow = Calendar.getInstance();
		long todayInMillis = rightNow.getTimeInMillis();

		/*
		 * Calculating the difference between event/current time
		 */
		long diffInMilliSec = eventTime - todayInMillis;// rightNow.getTimeInMillis();
		long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMilliSec);

		/*
		 * Converting millis to ss/mm/hh/dd from the calculated difference
		 */
		int numDay = (int) (diffInSec / DAY);
		diffInSec %= DAY;
		int numHour = (int) (diffInSec / HOUR);
		diffInSec %= HOUR;
		int numMin = (int) (diffInSec / MIN);
		int numSec = (int) (diffInSec % MIN);

		/*
		 * Outputing the data
		 */

		tv_result.setText("Days: " + numDay + ", Hours: " + numHour
				+ ", Minutes: " + numMin + ", Seconds: " + numSec
				+ " remaining!");

	}
}
