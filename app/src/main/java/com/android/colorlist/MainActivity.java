package com.android.colorlist;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar topProgressBar;
    RecyclerView recyclerView;
    ColorRecyclerAdapter adapter;
    ArrayList<Color> colorList = new ArrayList<>();
    int currentPage = 1;
    private boolean isLoading = false, isLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
    }

    public void initializeView(){

        topProgressBar = findViewById(R.id.topProgressBar);
        recyclerView = findViewById(R.id.recyclerView);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new ColorRecyclerAdapter(this, colorList);
        recyclerView.setAdapter(adapter);

        loadData();

        RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition + 1) >= totalItemCount) {
                        loadData();
                    }
                }
            }
        };

        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);

    }

    private void loadData() {

            @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, String> myTask = new AsyncTask<Void, Void, String>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    topProgressBar.setVisibility(View.VISIBLE);
                    isLoading = true;

                }

                @Override
                protected String doInBackground(Void... params) {

                    String response;

                    try {

                        String url = InternetOperations.SERVER_URL+"unknown?page="+currentPage;

                        response = InternetOperations.get(url);

                        return response;

                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    return null;
                }

                protected void onPostExecute(String s) {

                    topProgressBar.setVisibility(View.GONE);
                    isLoading = false;

                    if (s != null) {


                        try {


                            JSONObject object = new JSONObject(s);

                            JSONArray jsonArray = object.optJSONArray("data");

                            if(currentPage==1)
                                colorList.clear();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.optJSONObject(i);
                                colorList.add(new Color(jsonObject.optString("color"),jsonObject.optString("name")));
                            }

                            adapter.updateList(colorList);

                            currentPage++;

                            if(jsonArray.length()==0)
                                isLastPage = true;


                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }


                }
            };
            myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


}
