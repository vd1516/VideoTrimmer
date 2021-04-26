package com.example.videotrimmer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;

import idv.luchafang.videotrimmer.VideoTrimmerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, VideoTrimmerView.OnSelectedRangeChangedListener {
       
    Button selectVideoBtn,b;
    VideoTrimmerView videoTrimmerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectVideoBtn = (Button)findViewById(R.id.videoBtn);
        videoTrimmerView = (VideoTrimmerView) findViewById(R.id.videoTrimmerView);

        selectVideoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.videoBtn :
                Intent intent;
                if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                {
                    intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

                }
                else
                {
                    intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI);
                }
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("return-data", true);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent,200);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200){
            File file = new File(data.getData().getPath());
            videoTrimmerView
                    .setVideo(file)
                    .setMaxDuration(30_000)                   // millis
                    .setMinDuration(3_000)                    // millis
                    .setFrameCountInWindow(8)
                    .setExtraDragSpace(10)                    // pixels
                    .setOnSelectedRangeChangedListener(this)
                    .show();
        }
    }


    @Override
    public void onSelectRange(long l, long l1) {

    }

    @Override
    public void onSelectRangeEnd(long l, long l1) {

    }

    @Override
    public void onSelectRangeStart() {

    }
}
