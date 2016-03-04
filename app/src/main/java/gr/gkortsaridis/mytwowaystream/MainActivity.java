package gr.gkortsaridis.mytwowaystream;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.dubu.runtimepermissionshelper.rxver.RxPermissions;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    EditText room;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        room = (EditText) findViewById(R.id.roomNameET);
        rg = (RadioGroup) findViewById(R.id.radioGroup);

        // Must be done during an initialization phase like onCreate
        RxPermissions.getInstance(this)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.i("On Completed","");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("On ERROR","");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(aBoolean) {
                            Toast.makeText(MainActivity.this,
                                    "RX Permissions OK",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this,
                                    "Permission denied, can't enable the camera ",
                                    Toast.LENGTH_SHORT).show();
                        }

                        Log.i("On Next",aBoolean+"");
                    }
                });

    }

    public void startChat(View view){
        int id = rg.getCheckedRadioButtonId();
        boolean isAsrtist = false;
        if(id == R.id.radioButton){
            //Asrtist
            isAsrtist = true;
        }else if(id == R.id.radioButton2){
            //Venue
            isAsrtist = false;
        }


        Intent intent = new Intent(MainActivity.this, StreamActivity.class);
        intent.putExtra("room",room.getText().toString());
        intent.putExtra("isArtist",isAsrtist);
        startActivity(intent);

    }

}
