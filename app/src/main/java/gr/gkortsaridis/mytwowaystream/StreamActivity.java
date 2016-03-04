package gr.gkortsaridis.mytwowaystream;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StreamActivity extends AppCompatActivity {

    boolean isArtist;
    String room,publish_stream,subscribe_stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        isArtist = getIntent().getBooleanExtra("isArtist",false);
        room = getIntent().getStringExtra("room");
        if(isArtist){
            publish_stream = room + "-Artist";
            subscribe_stream = room + "-Venue";
        }else{
            publish_stream = room + "-Venue";
            subscribe_stream = room + "-Artist";
        }

        inflateMenus();
    }

    public void inflateMenus(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.upstreamContainer, new PublishFragment(publish_stream));
        ft.replace(R.id.downstreamContainer, new SubscribeFragment(subscribe_stream));
        ft.commit();
    }
}
