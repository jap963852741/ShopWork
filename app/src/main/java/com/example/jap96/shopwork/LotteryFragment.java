package com.example.jap96.shopwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Thread.sleep;


/**
 * A simple {@link Fragment} subclass.
 */
public class LotteryFragment extends Fragment {
    private ImageView mImgRollingDice;
    private TextView mCountdown;
    private Button mBtnRollDice,mBtnChangeLottery;
    int count;
    Bitmap ImageResource ;

    public LotteryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_lottery, container, false);
            mImgRollingDice = (ImageView) view.findViewById(R.id.imgRollingDice);

            mCountdown = (TextView) view.findViewById(R.id.countdown);
            mBtnRollDice = (Button) view.findViewById(R.id.btnRollDice);
            mBtnChangeLottery = (Button) view.findViewById(R.id.btnChangeLottery);

            mBtnRollDice.setOnClickListener(btnRollDiceOnClick);
            mBtnChangeLottery.setOnClickListener(btnChangeLotteryOnClick);
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Intent it = new Intent();
        ImageResource = mImgRollingDice.getDrawingCache();
        outState.putParcelable("ImageResource",ImageResource);
        it.putExtras(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getpic();
        playbutton();
    }
    private View.OnClickListener btnRollDiceOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //取得目前時間
            // 從程式資源中取得動畫檔，設定給ImageView物件，然後開始播放。
            Resources res = getResources();
            final AnimationDrawable animDraw = (AnimationDrawable) res.getDrawable(R.drawable.anim_roll_dice);
            mImgRollingDice.setImageDrawable(animDraw);
            animDraw.start();
            final Handler mHandler = new StaticHandler((MainActivity) getActivity());
            // 啟動background thread進行計時。
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    animDraw.stop();
                    mHandler.sendMessage(mHandler.obtainMessage());
                }
            }).start();

            mBtnRollDice.setVisibility(View.GONE);
            SharedPreferences SP = getActivity().getSharedPreferences("playE", Context.MODE_PRIVATE);
            SP.edit().putInt("playE", 0).commit();
        }
    };


    private View.OnClickListener btnChangeLotteryOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onesec();// 從程式資源中取得動畫檔，設定給ImageView物件，然後開始播放。
        }
    };
    public void playbutton() {
        SharedPreferences SP = getActivity().getSharedPreferences("playE",Context.MODE_PRIVATE);
        int PlayButton = SP.getInt("playE",2);

        if(PlayButton == 0){
            mBtnRollDice.setVisibility(View.GONE);
            Log.d("PlayButton","1231323132123231231");

        }else if(PlayButton == 1){
            mBtnRollDice.setVisibility(View.VISIBLE);
        }
    }

    public void onesec() {  // for the change prise
        new Thread(new Runnable(){
            @Override
            public void run() {
                count = 5;
                try {
                    while(count > 0){            //判斷是否使用Thread顯示字串
                        mCountdown.getHandler().post(new Runnable() {
                            public void run() {
                                try {
                                    mCountdown.setText(Integer.toString(count));    //倒數
                                    count--;
                                    if(count == 0){
                                        mCountdown.setText("");
                                        mImgRollingDice.setImageResource(R.drawable.changed);

                                        SharedPreferences SP = getActivity().getSharedPreferences("picture",Context.MODE_PRIVATE);
                                        SP.edit().putString("Text", mCountdown.getText().toString())
                                                 .putString("PrisePic", "8")
                                                 .apply();
                                    }
                                }catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Thread.sleep(1000);                //每隔1秒顯示一次
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }



    private void getpic(){
        SharedPreferences dd = getActivity().getSharedPreferences("picture",Context.MODE_PRIVATE);
        String Pic = dd.getString("PrisePic","");
        String Text = dd.getString("Text","");
        if(Text != "") {
            mCountdown.setText("明日再抽一次吧 ");
        }
        if(Pic != "") {
            int pic = Integer.parseInt(Pic);
            switch (pic) {
                case 1:
                    mImgRollingDice.setImageResource(R.drawable.dice01);
                    break;
                case 2:
                    mImgRollingDice.setImageResource(R.drawable.dice02);
                    break;
                case 3:
                    mImgRollingDice.setImageResource(R.drawable.dice03);
                    break;
                case 4:
                    mImgRollingDice.setImageResource(R.drawable.dice04);
                    break;
                case 5:
                    mImgRollingDice.setImageResource(R.drawable.dice05);
                    break;
                case 6:
                    mImgRollingDice.setImageResource(R.drawable.dice06);
                    break;
                case 8:
                    mImgRollingDice.setImageResource(R.drawable.changed);
                    break;
            }
        }
    }

    public static class StaticHandler extends Handler { //the animation

        private final WeakReference<MainActivity> mActivity;

        public StaticHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            ImageView mImgRollingDice = (ImageView) activity.findViewById(R.id.imgRollingDice);
            if (activity == null) return;
            int iRand = (int) (Math.random() * 6 + 1);

            switch (iRand) {
                case 1:
                    mImgRollingDice.setImageResource(R.drawable.dice01);
                    break;
                case 2:
                    mImgRollingDice.setImageResource(R.drawable.dice02);
                    break;
                case 3:
                    mImgRollingDice.setImageResource(R.drawable.dice03);
                    break;
                case 4:
                    mImgRollingDice.setImageResource(R.drawable.dice04);
                    break;
                case 5:
                    mImgRollingDice.setImageResource(R.drawable.dice05);
                    break;
                case 6:
                    mImgRollingDice.setImageResource(R.drawable.dice06);
                    break;
            }
            String pic = String.valueOf(iRand);
            SharedPreferences SP = activity.getSharedPreferences("picture",Context.MODE_PRIVATE);
            SP.edit().putString("PrisePic", pic).commit();
        }
    }

}
