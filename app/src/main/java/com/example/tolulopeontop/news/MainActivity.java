package com.example.tolulopeontop.news;


        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Handler;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.DefaultRetryPolicy;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import com.squareup.picasso.Picasso;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<newsitem> newsFeed = new ArrayList<newsitem>();
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        addClickListener();
        volleyRequest();
        refreshPage();



    }
    private void volleyRequest(){
        final ListView newsItems = (ListView)(findViewById(R.id.newsItems));
        RequestQueue queue= Volley.newRequestQueue(this);

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                "https://newsapi.org/v2/top-headlines?sources=fox-news&apiKey=ccc0bc962d6e4de78614effb7dee77ed",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayAdapter<newsitem> adapter = new customAdapter();

                        newsItems.setAdapter(adapter);
                        hidePDialog();

                        try {
                            JSONArray newsItem = response.getJSONArray("articles");


                            for (int i = 0; i < newsItem.length(); i++) {

                                JSONObject temp = newsItem.getJSONObject(i);

                                String author = temp.getString("author");
                                String title = temp.getString("title");
                                String description = temp.getString("description");
                                String url = temp.getString("url");
                                String image = temp.getString("urlToImage");
                                String date = temp.getString("publishedAt");

                                Log.i("myTag", title+" "+ url);

                                newsFeed.add(new newsitem(title, description, description, date, date, url, image));

                            }


                        } catch (JSONException e) {
                            Log.i("myTag", e.toString());
                        }

                    }

                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i("myTag", error.toString());
                        hidePDialog();
                    }
                });

        myReq.setRetryPolicy(new DefaultRetryPolicy(
                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        queue.add(myReq);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void refreshPage(){
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        volleyRequest();
                    }
                },3000);
            }
        });

    }

    private void addClickListener(){
        ListView newsItems = (ListView)(findViewById(R.id.newsItems));
        newsItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "DO JHOOR", Toast.LENGTH_SHORT).show();
                newsitem currentItem = newsFeed.get(position);
                String headingText = currentItem.getNewsHeading();
                String authorText  = "Ayeni Tolu";
                String dateText = currentItem.getDate();
                String imageUrl = currentItem.getimageId();
                String contentText = "It was like I received an OTA respect update for him, because I had just 10,000 naira in my account, but I looked like I could buy him.</p>\n<p>**back to the story*</p>\n<p>I attended RCCF and NCCF and these fellowship put my music life on steroids. Just like myself, I met music thespians in both fellowships and this exposed me to a whole new level of cool, musically.</p>\n<p>I soon made a Hausa friend who had the passion to learn the English Language, and since I&rsquo;d love to learn the Hausa language too, we brought the barter system back to life. This brought me more fun, because to some extent, I could understand it when Hausas communicated.</p>\n<p>Things are so cheap that at times, I spent only hundred naira to prepare stew. Oh! About stew, I was taught to cook by some other female Corp members who didn&rsquo;t want me to end my life as a junkie. I was posted along side 9 ladies to my lodge, and they were wonderful to me. They thought me so many kitchen-related stuff.</p>\n<p>Kebbi State was more than I had imagined; aside from the really hot weather that made me and every other corp member sleep outside at times (which we loved because we got to share stories), Kebbi was altogether awesome.</p>\n<p>I never regretted serving in the North because it brought me many blessings. I had out-of-the-world experiences.</p>\n<p>If you got posted to the North, don&rsquo;t feel bad. Seize every opportunity when you get there. Also, relate with the people. Some look like they are poor, but they are connected to the people in power. You never know what form that help you need may take.</p>\n<p>&copy; <strong>Aruleba Olamide Damilare</strong></p>\n\n<!";
                Intent i = new Intent(getApplicationContext(), ContentActivity.class);
                i.putExtra("keyHead", headingText);
                i.putExtra("keyAuthor", authorText);
                i.putExtra("keyDate", dateText);
                i.putExtra("keyContent", contentText);
                i.putExtra("keyImage", imageUrl);
                startActivity(i);


            }
        });
    }

    private class customAdapter extends ArrayAdapter<newsitem> {
        public customAdapter() {

            super(MainActivity.this, R.layout.my_layout, newsFeed);
        }




        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.my_layout,parent,false);
            }

            newsitem currentItem = newsFeed.get(position);
            ImageView myImage = (ImageView)convertView.findViewById(R.id.imageView);
            TextView myHeading = (TextView)convertView.findViewById(R.id.textView);
            TextView myDesc = (TextView)convertView.findViewById(R.id.editText);


            myHeading.setText(currentItem.getNewsHeading());
            myDesc.setText(currentItem.getNewsDescSmall());
            Picasso.with(MainActivity.this).load(currentItem.getimageId()).into(myImage);

            return convertView;
        }
    }

}
