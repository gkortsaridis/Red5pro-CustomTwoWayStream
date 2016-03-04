package gr.gkortsaridis.mytwowaystream;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5Stream;
import com.red5pro.streaming.R5StreamProtocol;
import com.red5pro.streaming.config.R5Configuration;
import com.red5pro.streaming.source.R5AdaptiveBitrateController;
import com.red5pro.streaming.source.R5Camera;
import com.red5pro.streaming.source.R5Microphone;

public class PublishFragment extends BaseExample {

    String publishStreamName;

    public PublishFragment() {
        // Required empty public constructor
    }

    public PublishFragment(String publish_stream){
        publishStreamName = publish_stream;
        Log.i("Gonna publish at", publishStreamName);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish, container, false);


        Resources res = getResources();

        //Create the configuration from the values.xml
        R5Configuration config = new R5Configuration(
                R5StreamProtocol.RTSP,
                res.getString(R.string.domain),
                res.getInteger(R.integer.port),
                res.getString(R.string.context), 0.5f);

        R5Connection connection = new R5Connection(config);

        //setup a new stream using the connection
        publish = new R5Stream(connection);

        //show all logging
        publish.setLogLevel(R5Stream.LOG_LEVEL_DEBUG);

        //attach a camera video source
        cam = openFrontFacingCameraGingerbread();
        //cam.setDisplayOrientation(90);

        R5Camera camera  = new R5Camera(cam, 320, 240);
        camera.setBitrate(res.getInteger(R.integer.bitrate));
        //camera.setOrientation(-90);

        R5AdaptiveBitrateController adaptor = new R5AdaptiveBitrateController();
        adaptor.AttachStream(publish);

        //attach a microphone
        R5Microphone mic = new R5Microphone();
        mic.setBitRate(64000);

        publish.attachMic(mic);

        SurfaceView r5VideoView =(SurfaceView) view.findViewById(R.id.video2);

        publish.setView(r5VideoView);

        publish.attachCamera(camera);

        publish.publish(publishStreamName, R5Stream.RecordType.Live);

        cam.startPreview();

        return view;
    }


}
