package com.csiw.fbadd;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.csiw.fbadd.adapter.ImageAdapter;
import com.csiw.fbadd.model.ImageResponse;
import com.csiw.fbadd.network.ApiInterFace;
import com.csiw.fbadd.network.RetrofitApi;
import com.facebook.ads.AudienceNetworkAds;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    //    AdView mAdView;
    RecyclerView mRecyclerView;

    ImageAdapter mImageAdapter;
    List<ImageResponse> mImageResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AudienceNetworkAds.initialize(this);
        getData();
        mRecyclerView = findViewById(R.id.rectangle_recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
    }

    public void getData() {
        ApiInterFace apiInterFace = RetrofitApi.getApi();
        Call<List<ImageResponse>> listCall = apiInterFace.getImageResponse();
        listCall.enqueue(new Callback<List<ImageResponse>>() {
            @Override
            public void onResponse(Call<List<ImageResponse>> call, Response<List<ImageResponse>> response) {
                mImageResponseList = response.body();

                mImageAdapter = new ImageAdapter(MainActivity.this, mImageResponseList);
                mRecyclerView.setAdapter(mImageAdapter);
            }

            @Override
            public void onFailure(Call<List<ImageResponse>> call, Throwable t) {

            }
        });
    }
}
