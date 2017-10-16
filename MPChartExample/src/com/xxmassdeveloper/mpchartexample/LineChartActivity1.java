
package com.xxmassdeveloper.mpchartexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.xxmassdeveloper.mpchartexample.custom.MyMarker;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LineChartActivity1 extends DemoBase implements OnSeekBarChangeListener,
        OnChartGestureListener, OnChartValueSelectedListener {

    private LineChart graph;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_linechart);

        tvX = (TextView) findViewById(R.id.tvXMax);
        tvY = (TextView) findViewById(R.id.tvYMax);

        mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
        mSeekBarY = (SeekBar) findViewById(R.id.seekBar2);

        mSeekBarX.setProgress(45);
        mSeekBarY.setProgress(100);

        mSeekBarY.setOnSeekBarChangeListener(this);
        mSeekBarX.setOnSeekBarChangeListener(this);

        graph = (LineChart) findViewById(R.id.chart1);

        final List<Entry> entries = new ArrayList<>();

        entries.add(new Entry(3, 100));
        entries.add(new Entry(4, 200));
        entries.add(new Entry(5, 250));
        entries.add(new Entry(6, 150));
        entries.add(new Entry(7, 200));

        final List<Entry> entries2 = new ArrayList<>();

        final Entry entry = new Entry(4.5f, 100, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.marker_big), 50, 50, true));
        entries2.add(entry);
        final Highlight h1 = new Highlight(4.5f, 150f, 1);
        final Highlight h2 = new Highlight(5, 150f, 1);
        Timer t = new Timer();
        t.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                entry.setY(entry.getY() + 1);
                                graph.invalidate();
                            }
                        });
                    }

                }, 0, 200);
        entries2.add(new Entry(3.5f, 150, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.marker_big), 50, 50, true)));

        entries2.add(new Entry(4, 200, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.marker_big), 50, 50, true)));
        entries2.add(new Entry(5, 250, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.marker_big), 50, 50, true)));
        final Highlight h3 = new Highlight(6f, 160f, 1);
        entries2.add(new Entry(6, 150, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.marker_big), 50, 50, true)));
        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        LineDataSet dataSet2 = new LineDataSet(entries2, "Label"); // add entries to dataset
        dataSet2.setDrawValues(true);
        dataSet2.setColor(Color.TRANSPARENT);
        dataSet2.setDrawCircles(false);
        dataSet2.setDrawHorizontalHighlightIndicator(false);
        dataSet2.setDrawVerticalHighlightIndicator(false);
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(255);
        dataSet.setFillColor(ContextCompat.getColor(this, R.color.aqua_blue));
        dataSet.setDrawValues(true);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setDrawHorizontalHighlightIndicator(false);
        dataSet.setDrawVerticalHighlightIndicator(false);
        LineData data = new LineData(dataSet, dataSet2);
        graph.setData(data);
        graph.getData().setHighlightEnabled(true);
        graph.setPinchZoom(false);
        graph.setDoubleTapToZoomEnabled(false);
        graph.getAxisLeft().setDrawGridLines(false);
        graph.getAxisRight().setDrawGridLines(false);
        graph.getXAxis().setDrawGridLines(false);
        graph.getLegend().setEnabled(false);
        graph.getAxisLeft().setDrawLabels(false);
        graph.getAxisLeft().setDrawAxisLine(false);
        graph.getAxisRight().setDrawLabels(false);
        graph.getAxisRight().setDrawAxisLine(false);
        graph.getXAxis().setDrawLabels(false);
        graph.getXAxis().setDrawAxisLine(false);
        graph.setBackground(null);
        graph.setDrawGridBackground(false);
        graph.setDescription(null);
        graph.setDrawBorders(false);
        graph.setViewPortOffsets(0f, 200f, 0f, 0f);
        graph.setDrawMarkers(true);
        graph.setTouchEnabled(false);
        graph.setAlwaysShowMarkers(true);
        graph.setShowValuesWithLine(true);
        MyMarker mv = new MyMarker(this, R.layout.my_marker);
        mv.setChartView(graph);
        graph.setMarker(mv);
        for (IDataSet set : graph.getData().getDataSets()) {
            set.setDrawValues(true);
        }
        graph.post(new Runnable() {
            @Override
            public void run() {
                graph.invalidate();
                graph.highlightValues(new Highlight[]{h1, h2, h3});

            }
        });

//        // no description text
////        graph.getDescription().setEnabled(false);
//
//        // enable touch gestures
//        graph.setTouchEnabled(true);
//
//        // enable scaling and dragging
//        graph.setDragEnabled(true);
////        graph.setScaleEnabled(true);
////        // graph.setScaleXEnabled(true);
////        // graph.setScaleYEnabled(true);
////
////        // if disabled, scaling can be done on x- and y-axis separately
////        graph.setPinchZoom(true);
//
//        // set an alternative background color
//        // graph.setBackgroundColor(Color.GRAY);
//
//        // create a custom MarkerView (extend MarkerView) and specify the layout
//        // to use for it

//
//        // x-axis limit line
//        LimitLine llXAxis = new LimitLine(10f, "Index 10");
//        llXAxis.setLineWidth(4f);
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);
//
//        XAxis xAxis = graph.getXAxis();
//        xAxis.enableGridDashedLine(10f, 10f, 0f);
//        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
//        //xAxis.addLimitLine(llXAxis); // add x-axis limit line
//
//
//        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
//
//        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
//        ll1.setLineWidth(4f);
//        ll1.enableDashedLine(10f, 10f, 0f);
//        ll1.setLabelPosition(LimitLabelPosition.RIGHT_TOP);
//        ll1.setTextSize(10f);
//        ll1.setTypeface(tf);
//
//        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
//        ll2.setLineWidth(4f);
//        ll2.enableDashedLine(10f, 10f, 0f);
//        ll2.setLabelPosition(LimitLabelPosition.RIGHT_BOTTOM);
//        ll2.setTextSize(10f);
//        ll2.setTypeface(tf);
//
//        YAxis leftAxis = graph.getAxisLeft();
//        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1);
//        leftAxis.addLimitLine(ll2);
//        leftAxis.setAxisMaximum(200f);
//        leftAxis.setAxisMinimum(-50f);
//        //leftAxis.setYOffset(20f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
//        leftAxis.setDrawZeroLine(false);
//
//        // limit lines are drawn behind data (and not on top)
//        leftAxis.setDrawLimitLinesBehindData(true);
//
//        graph.getAxisRight().setEnabled(false);
//
//        //graph.getViewPortHandler().setMaximumScaleY(2f);
//        //graph.getViewPortHandler().setMaximumScaleX(2f);
//
//        // add data
////        setData(45, 100);
//
////        graph.setVisibleXRange(20);
////        graph.setVisibleYRange(20f, AxisDependency.LEFT);
////        graph.centerViewTo(20, 50, AxisDependency.LEFT);
//
//        graph.animateX(2500);
//        //graph.invalidate();
//
//        // get the legend (only possible after setting data)
//        Legend l = graph.getLegend();
//
//        // modify the legend ...
//        l.setForm(LegendForm.LINE);
//
//        // // dont forget to refresh the drawing
//        // graph.invalidate();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.line, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                List<ILineDataSet> sets = graph.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setDrawValues(!set.isDrawValuesEnabled());
                }

                graph.invalidate();
                break;
            }
            case R.id.actionToggleIcons: {
                List<ILineDataSet> sets = graph.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setDrawIcons(!set.isDrawIconsEnabled());
                }

                graph.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (graph.getData() != null) {
                    graph.getData().setHighlightEnabled(!graph.getData().isHighlightEnabled());
                    graph.invalidate();
                }
                break;
            }
            case R.id.actionToggleFilled: {

                List<ILineDataSet> sets = graph.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                graph.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                List<ILineDataSet> sets = graph.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawCirclesEnabled())
                        set.setDrawCircles(false);
                    else
                        set.setDrawCircles(true);
                }
                graph.invalidate();
                break;
            }
            case R.id.actionToggleCubic: {
                List<ILineDataSet> sets = graph.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
                            ? LineDataSet.Mode.LINEAR
                            : LineDataSet.Mode.CUBIC_BEZIER);
                }
                graph.invalidate();
                break;
            }
            case R.id.actionToggleStepped: {
                List<ILineDataSet> sets = graph.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.STEPPED
                            ? LineDataSet.Mode.LINEAR
                            : LineDataSet.Mode.STEPPED);
                }
                graph.invalidate();
                break;
            }
            case R.id.actionToggleHorizontalCubic: {
                List<ILineDataSet> sets = graph.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER
                            ? LineDataSet.Mode.LINEAR
                            : LineDataSet.Mode.HORIZONTAL_BEZIER);
                }
                graph.invalidate();
                break;
            }
            case R.id.actionTogglePinch: {
                if (graph.isPinchZoomEnabled())
                    graph.setPinchZoom(false);
                else
                    graph.setPinchZoom(true);

                graph.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                graph.setAutoScaleMinMaxEnabled(!graph.isAutoScaleMinMaxEnabled());
                graph.notifyDataSetChanged();
                break;
            }
            case R.id.animateX: {
                graph.animateX(3000);
                break;
            }
            case R.id.animateY: {
                graph.animateY(3000, Easing.EasingOption.EaseInCubic);
                break;
            }
            case R.id.animateXY: {
                graph.animateXY(3000, 3000);
                break;
            }
            case R.id.actionSave: {
                if (graph.saveToPath("title" + System.currentTimeMillis(), "")) {
                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();

                // graph.saveToGallery("title"+System.currentTimeMillis())
                break;
            }
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress() + 1));
        tvY.setText("" + (mSeekBarY.getProgress()));

//        setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());

        // redraw
        graph.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(int count, float range) {
        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) + 3;
            entries.add(new Entry(i, val, BitmapFactory.decodeResource(getResources(),
                    R.drawable.marker_big)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(255);
        dataSet.setFillColor(ContextCompat.getColor(this, R.color.aqua_blue));
        dataSet.setDrawValues(true);
        dataSet.setValueTextColor(Color.WHITE);
        LineData lineData = new LineData(dataSet);
        graph.setData(lineData);
        graph.getData().setHighlightEnabled(false);
        graph.setPinchZoom(false);
        graph.setDoubleTapToZoomEnabled(false);
        graph.getAxisLeft().setDrawGridLines(false);
        graph.getAxisRight().setDrawGridLines(false);
        graph.getXAxis().setDrawGridLines(false);
        graph.getLegend().setEnabled(false);
        graph.getAxisLeft().setDrawLabels(false);
        graph.getAxisLeft().setDrawAxisLine(false);
        graph.getAxisRight().setDrawLabels(false);
        graph.getAxisRight().setDrawAxisLine(false);
        graph.getXAxis().setDrawLabels(false);
        graph.getXAxis().setDrawAxisLine(false);
        graph.setBackground(null);
        graph.setDrawGridBackground(false);
        graph.setDescription(null);
        graph.setDrawBorders(false);
        graph.setViewPortOffsets(0f, 0f, 0f, 0f);
        graph.setClickable(false);


        LineDataSet set1;

        if (graph.getData() != null &&
                graph.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) graph.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            graph.getData().notifyDataChanged();
            graph.notifyDataSetChanged();
//        } else {
//            // create a dataset and give it a type
//            set1 = new LineDataSet(entries, "DataSet 1");
//
//            set1.setDrawIcons(false);
//
//            // set the line to be drawn like this "- - - - - -"
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
//            set1.setColor(Color.BLACK);
//            set1.setCircleColor(Color.BLACK);
//            set1.setLineWidth(1f);
//            set1.setCircleRadius(3f);
//            set1.setDrawCircleHole(false);
//            set1.setValueTextSize(9f);
//            set1.setDrawFilled(true);
//            set1.setFormLineWidth(1f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set1.setFormSize(15.f);
//
//            if (Utils.getSDKInt() >= 18) {
//                // fill drawable only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//                set1.setFillDrawable(drawable);
//            } else {
//                set1.setFillColor(Color.BLACK);
//            }
//
//            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//            dataSets.add(set1); // add the datasets
//
//            // create a data object with the datasets
//            LineData data = new LineData(dataSets);
//
//            // set data
//            graph.setData(data);
        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            graph.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + graph.getLowestVisibleX() + ", high: " + graph.getHighestVisibleX());
        Log.i("MIN MAX", "xmin: " + graph.getXChartMin() + ", xmax: " + graph.getXChartMax() + ", ymin: " + graph.getYChartMin() + ", ymax: " + graph.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}
