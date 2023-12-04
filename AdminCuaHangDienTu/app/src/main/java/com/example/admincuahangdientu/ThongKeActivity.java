package com.example.admincuahangdientu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongKeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private PieChart pieChart;
    private BarChart barChart;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    APIadminStore apIadminStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        apIadminStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);
        initView();
        ActionToolbar();
        getDataPieChart();
        settingBarChart();

    }

    private void settingBarChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setAxisMinimum(0);
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);
    }

    private void getDataPieChart() {
        barChart.setVisibility(View.GONE);
        pieChart.setVisibility(View.VISIBLE);

        List<PieEntry> listData = new ArrayList<>();
        compositeDisposable.add(apIadminStore.getThongKe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeModel -> {
                            if(thongKeModel.isSuccess())
                            {
                                for (int i =0;i<thongKeModel.getResult().size(); i++)
                                {
                                    String tensp = thongKeModel.getResult().get(i).getTensp();
                                    int tong = thongKeModel.getResult().get(i).getTong();
                                    listData.add(new PieEntry(tong, tensp));

                                }

                                PieDataSet pieDataSet = new PieDataSet(listData,"Thống Kê");
                                PieData data = new PieData();
                                data.setDataSet(pieDataSet);
                                data.setValueTextSize(12f);
                                data.setValueFormatter(new PercentFormatter(pieChart));
                                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                                pieChart.setData(data);
                                pieChart.animateXY(2000,2000);
                                pieChart.setUsePercentValues(true);
                                pieChart.getDescription().setEnabled(true);
                                pieChart.invalidate();
                            }
                        }
                        ,
                        throwable -> {
                            Log.d("error", throwable.getMessage());
                        }
                ));
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thống Kê");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_thongke, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.tkThang:
                getThongKeThang();
                return true;
            case R.id.tkBanChay:
                getDataPieChart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void getThongKeThang() {
        barChart.setVisibility(View.VISIBLE);
        pieChart.setVisibility(View.GONE);
        compositeDisposable.add(apIadminStore.getThongKeTheoThang()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeModel -> {
                            if(thongKeModel.isSuccess())
                            {
                                List<BarEntry> listData = new ArrayList<>();
                                for (int i=0 ; i<thongKeModel.getResult().size();i++)
                                {
                                    String tongtien = thongKeModel.getResult().get(i).getTongtienthang();
                                    String thang = thongKeModel.getResult().get(i).getThang();
                                    listData.add(new BarEntry(Integer.parseInt(thang),Float.parseFloat(tongtien)));
                                }

                                BarDataSet barDataSet =new BarDataSet(listData, "thống kê");
                                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                barDataSet.setValueTextSize(14f);
                                barDataSet.setValueTextColor(Color.RED);

                                BarData data = new BarData(barDataSet);
                                barChart.animateXY(2000,2000);
                                barChart.setData(data);
                                barChart.invalidate();
                            }
                            else
                            {
                                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

                            }
                        }
                        ,
                        throwable -> {
                            Log.d("error", throwable.getMessage());
                        }
                ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}