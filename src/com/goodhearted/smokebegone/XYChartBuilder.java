package com.goodhearted.smokebegone;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class XYChartBuilder extends Activity {

	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	private XYSeries mCurrentSeries;
	private XYSeriesRenderer mCurrentRenderer;
	private GraphicalView mChartView;

	private SmokeDataSource DAO;
	private MenuHandler handler;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// save the current data, for instance when changing screen orientation
		outState.putSerializable("dataset", mDataset);
		outState.putSerializable("renderer", mRenderer);
		outState.putSerializable("current_series", mCurrentSeries);
		outState.putSerializable("current_renderer", mCurrentRenderer);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		// restore the current data, for instance when changing the screen
		// orientation
		mDataset = (XYMultipleSeriesDataset) savedState
				.getSerializable("dataset");
		mRenderer = (XYMultipleSeriesRenderer) savedState
				.getSerializable("renderer");
		mCurrentSeries = (XYSeries) savedState
				.getSerializable("current_series");
		mCurrentRenderer = (XYSeriesRenderer) savedState
				.getSerializable("current_renderer");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xychart_builder);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		readyMenu();
		
		// set some properties on the main renderer
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
		mRenderer.setAxisTitleTextSize(16);
		mRenderer.setChartTitleTextSize(20);

		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
		mRenderer.setMargins(new int[] { 20, 30, 15, 20 });
		mRenderer.setZoomButtonsVisible(false);

		mRenderer.setPointSize(5);

		String seriesTitle = "Smokes for the past 30 days";
		// create a new series of data
		XYSeries series = new XYSeries(seriesTitle);
		mDataset.addSeries(series);
		mCurrentSeries = series;
		// create a new renderer for the new series
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		// set some renderer properties
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(true);

		renderer.setDisplayChartValuesDistance(10);
		mCurrentRenderer = renderer;

		DAO = new SmokeDataSource(this);

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
			mChartView = ChartFactory.getLineChartView(this, mDataset,
					mRenderer);
			// enable the chart click events

			mRenderer.setSelectableBuffer(10);
			layout.addView(mChartView, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		} else {
			mChartView.repaint();
		}
		mRenderer.setXAxisMax(30);
		mRenderer.setXAxisMin(-1);

		mRenderer.setYAxisMin(0);

		getData();

		mChartView.repaint();
	}

	private void getData() {
		int leapyearcompat = 0;

		List<Smoke> allsmokes = DAO.getAllSmokes();
		int[] days = new int[allsmokes.size()];
		int[] smokesperday = new int[30];

		for (int i = 0; i < 30; i++) {
			smokesperday[i] = 0;
		}

		GregorianCalendar b = (new GregorianCalendar());
		int today = b.get(Calendar.DAY_OF_YEAR);
		b.setTimeInMillis(PreferenceProvider.readLong(this,
				PreferenceProvider.keyQD, -1));
		int quitday = b.get(Calendar.DAY_OF_YEAR);

		for (int i = 0; i < allsmokes.size(); i++) {
			b.setTimeInMillis((allsmokes.get(i).getDateInt()));
			days[i] = b.get(Calendar.DAY_OF_YEAR);
			if (b.isLeapYear(b.get(Calendar.YEAR)))
				leapyearcompat = 0;
		}

		for (int i = 0; i < days.length; i++) {
			int dayback = days[i] - today;
			if (dayback < 0) {
				dayback += (365 - leapyearcompat);
			}
			smokesperday[dayback] += 1;
		}

		int oldsmokesperday = PreferenceProvider.readInteger(this,
				PreferenceProvider.keyCPD, -1);

		for (int i = 0; i < 30; i++) {
			if (i > today - quitday)
				mCurrentSeries.add(i, oldsmokesperday);
			else
				mCurrentSeries.add(i, smokesperday[i]);
		}

		int highestpoint = 1;
		for (int i = 0; i < 30; i++) {
			if (smokesperday[i] > highestpoint)
				highestpoint = smokesperday[i];
		}
		if (oldsmokesperday > highestpoint)
			highestpoint = oldsmokesperday;

		highestpoint += 2;
		mRenderer.setYAxisMax(highestpoint);

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		SlideHolder x = (SlideHolder) findViewById(R.id.bla);
		x.toggle();
		return true;

	}

	private void readyMenu() {
		handler = new MenuHandler(this);
		for (int i = 0; i < MenuHandler.allitems.length; i++) {
			findViewById(MenuHandler.allitems[i]).setOnClickListener(handler);
		}
	}

}