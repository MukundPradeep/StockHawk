package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.view.LineChartView;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.rest.GraphData;

public class StockDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);
        GraphData obj = (GraphData) getIntent().getSerializableExtra("dataClass");

//        LineSet dataset = new LineSet(String[] labels, Float[] values);
//
//        dataset.addPoint(new Point(string, float));
//
//        dataset.addPoint(string, float);

        Context varContext = this.getApplicationContext();
        LineChartView chartView= (LineChartView) findViewById(R.id.linechart);
//        String[] dates = {"March 10", "March 20", "March 30"};
//        double[] prices = {102.5, 103.8, 92.7};
//        LineSet lineSet = new LineSet();
//        lineSet.addPoint(new Point("March 10", (float) 102.5));
//        lineSet.addPoint(new Point("March 20", (float) 106.7));
//        lineSet.addPoint(new Point("March 30", (float) 97.5));

        String[] dates = obj.getDates();
        String[] prices = obj.getPrices();

        LineSet lineSet = new LineSet();

        for(int i=0;i<prices.length;i++){
            Float price = Float.parseFloat(prices[i]);
            String date= String.valueOf(i+1);
            lineSet.addPoint(new Point(date ,price));
        }

        chartView.addData(lineSet);

        chartView.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stock_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);

    }
}
