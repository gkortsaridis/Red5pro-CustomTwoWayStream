package gr.gkortsaridis.mytwowaystream;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5Stream;
import com.red5pro.streaming.R5StreamProtocol;
import com.red5pro.streaming.config.R5Configuration;
import com.red5pro.streaming.view.R5VideoView;

public class SubscribeFragment extends BaseExample {

    public R5Configuration configuration;
    R5Stream stream;
    boolean isSubscribing;
    String subscrive_stream;

    public SubscribeFragment() {
        // Required empty public constructor
    }

    public SubscribeFragment(String subscribe_stream) {
        this.subscrive_stream = subscribe_stream;
        Log.i("Gonna subscribe at",this.subscrive_stream);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribe, container, false);

        if(subscribe == null) {

            Resources res = getResources();

            //Create the configuration from the values.xml
            R5Configuration config = new R5Configuration(R5StreamProtocol.RTSP, res.getString(R.string.domain), res.getInteger(R.integer.port), res.getString(R.string.context), 0.5f);
            R5Connection connection = new R5Connection(config);

            //setup a new stream using the connection
            subscribe = new R5Stream(connection);

            //show all logging
            subscribe.setLogLevel(R5Stream.LOG_LEVEL_DEBUG);

            //find the view and attach the stream
            R5VideoView r5VideoView = (R5VideoView) view.findViewById(R.id.subscribeView);
            r5VideoView.attachStream(subscribe);

            subscribe.play(subscrive_stream);

        }

        return view;
    }

    private void onSubscribeToggle() {
        Button subscribeButton = (Button) getActivity().findViewById(R.id.subscribeButton);
        if(isSubscribing) {
            stop();
        }
        else {
            start();
        }
        isSubscribing = !isSubscribing;
        subscribeButton.setText(isSubscribing ? "stop" : "start");
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isSubscribing) {
            onSubscribeToggle();
        }
    }

    public void start() {
        R5VideoView videoView = (R5VideoView) getActivity().findViewById(R.id.subscribeView);

        stream = new R5Stream(new R5Connection(configuration));
        videoView.attachStream(stream);
        stream.play(subscrive_stream);
    }

    public void stop() {
        if(stream != null) {
            stream.stop();
        }
    }

}
