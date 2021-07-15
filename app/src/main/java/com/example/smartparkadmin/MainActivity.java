package com.example.smartparkadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LineChart lineChart;
    ArrayList<Entry> yData;
    DatabaseReference parkingRef;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineChart=findViewById(R.id.reportingChart);
        lineChart.setScaleEnabled(true);
        lineChart.setDragEnabled(true);

        parkingRef= FirebaseDatabase.getInstance().getReference("parking_lot");
        parkingRef.addValueEventListener(valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                yData=new ArrayList<>();
                float i=1;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    i=i+1;
                    long childCount=dataSnapshot.getChildrenCount();
//                    String sv=dataSnapshot.child("").getValue().toString();
//                    Float total=Float.parseFloat(sv);
                    yData.add(new Entry(i,childCount));

                }
                final LineDataSet lineDataSet=new LineDataSet(yData,"Parking Lots");
                LineData data=new LineData(lineDataSet);
                lineChart.setData(data);
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}