package com.yep.yepai;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yep.yepai.tencentChat.CodeActivity;
import com.yep.yepai.tencentChat.JokeActivity;
import com.yep.yepai.tencentChat.StoryActivity;
import com.yep.yepai.tencentChat.TencentChatActivity;
import com.yep.yepai.tencentChat.WeatherActivity;
import com.yep.yepai.tuling.TuLingActivity;
import com.yep.yepai.tuling.TuLingChengyu;
import com.yep.yepai.tuling.TuLingCount;
import com.yep.yepai.tuling.TuLingJoke;
import com.yep.yepai.tuling.TuLingStory;

public class Frag4Activity extends Fragment {
    private TextView textView;
    private Button tencentchat;
    private Button joke;
    private Button story;
    private Button weather;
    private Button code;
    private Button tuling;
    private Button tulingjoke;
    private Button tulingstory;
    private Button tulingchengyu;
    private Button tulingcount;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //不同的Activity对应不同的布局
        View view = inflater.inflate(R.layout.activity_frag4, container, false);
        tencentchat = view.findViewById(R.id.tencent_chat);
        tencentchat.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TencentChatActivity.class);
                startActivity(intent);
                //onActivityResult(0,0,intent);
            }
        });
        joke = view.findViewById(R.id.tencent_joke);
        joke.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), JokeActivity.class);
                startActivity(intent);
                //onActivityResult(0,0,intent);
            }
        });
        story = view.findViewById(R.id.tencent_story);
        story.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StoryActivity.class);
                startActivity(intent);
                //onActivityResult(0,0,intent);
            }
        });
        code = view.findViewById(R.id.tencent_code);
        code.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CodeActivity.class);
                startActivity(intent);
                //onActivityResult(0,0,intent);
            }
        });
        weather = view.findViewById(R.id.tencent_weather);
        weather.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                startActivity(intent);
                //onActivityResult(0,0,intent);
            }
        });
        tuling = view.findViewById(R.id.tuling);
        tuling.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TuLingActivity.class);
                startActivity(intent);
                //onActivityResult(0,0,intent);
            }
        });
        tulingjoke = view.findViewById(R.id.tuling_joke);
        tulingjoke.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TuLingJoke.class);
                startActivity(intent);
                //onActivityResult(0,0,intent);
            }
        });
        tulingstory = view.findViewById(R.id.tuling_story);
        tulingstory.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TuLingStory.class);
                startActivity(intent);
                //onActivityResult(0,0,intent);
            }
        });
        tulingchengyu = view.findViewById(R.id.tuling_chengyu);
        tulingchengyu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TuLingChengyu.class);
                startActivity(intent);
                //onActivityResult(0,0,intent);
            }
        });
        tulingcount = view.findViewById(R.id.tuling_count);
        tulingcount.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TuLingCount.class);
                startActivity(intent);
                //onActivityResult(0,0,intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
