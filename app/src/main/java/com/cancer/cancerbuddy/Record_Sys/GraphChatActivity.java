package com.cancer.cancerbuddy.Record_Sys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cancer.cancerbuddy.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

@RequiresApi(api = Build.VERSION_CODES.N)
public class GraphChatActivity extends AppCompatActivity {

    GraphView graphView,graph1,graph2,graph3,graph4,graph5,graph6;
  // private LineGraphSeries series;

    LineGraphSeries<DataPoint> series;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M");

    ImageView back_to_mainN;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_chat);

        back_to_mainN = findViewById(R.id.back_to_mainN);

        back_to_mainN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GraphChatActivity.this, RecordActivity2.class);
                startActivity(intent);
                finish();


            }
        });




        graphView = findViewById(R.id.graph);
        graph1 = findViewById(R.id.graph1);
        graph2 = findViewById(R.id.graph2);
        graph3 = findViewById(R.id.graph3);
        graph4 = findViewById(R.id.graph4);
        graph5 = findViewById(R.id.graph5);
        graph6 = findViewById(R.id.graph6);

        series = new LineGraphSeries();
        graphView.addSeries(series);

       // LoadData();


      //  graphView.setHorizontalLabels(new String[] {"2 days ago", "yesterday", "today", "tomorrow"});
        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()

                                                           {
                                                               @Override
                                                               public String formatLabel(double value, boolean isValueX) {
                                                                   if (isValueX)
                                                                   {
                                                                       return super.formatLabel(value, isValueX);
                                                                     //  return simpleDateFormat.format(new Date((long) value));
                                                                   }
                                                                   else
                                                                   {
                                                                     //  return "Moderate";
                                                                       return super.formatLabel(value, isValueX);
                                                                   }
                                                               }
                                                           }
        );





        // on below line we are adding data to our graph view.
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                // on below line we are adding
                // each point on our x and y axis.
                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });

        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.
       /// graphView.setTitle("My Graph View");

        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(R.color.purple_200);

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(18);

        // on below line we are adding
        // data series to our graph view.
        graphView.addSeries(series);
        graph1.addSeries(series);
        graph2.addSeries(series);
        graph3.addSeries(series);
        graph4.addSeries(series);
        graph5.addSeries(series);
        graph6.addSeries(series);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GraphChatActivity.this, RecordActivity2.class);
        startActivity(intent);
        finish();

    }
}

