package com.iutbm.example.iutbm.couchot.healthybody.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.library.PulseView;
import com.google.android.gms.wearable.Wearable;
import com.iutbm.example.iutbm.couchot.healthybody.R;
import com.iutbm.example.iutbm.couchot.healthybody.utils.HeartBeatView;


public class HeartRateFragment extends Fragment  implements SensorEventListener {

    private PulseView psv;
    private TextView mTextViewHeart,mTextViewOperation,mTextViewTime,mTextResult,mTextID;
    private SensorManager mSensorManager;
    private LinearLayout linearLayout;
    private Integer indice=0;
    private String[] Instructions=new String[]{"s'allonger","Flexion","s'allonger"};
    private Integer[] time=new Integer[]{60000,45000,60000};
    private Integer[] pouls=new Integer[]{0,0,0};
    private Integer value=0;
    private RelativeLayout startView,MainStart,resultView;
    private Button Go;

    public HeartRateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.heart_rate_fragment, container, false);
        psv=(PulseView) rootView.findViewById(R.id.pv);
        mTextViewHeart=(TextView)rootView.findViewById(R.id.txtheart) ;
        mTextViewOperation=(TextView)rootView.findViewById(R.id.txtInstru);
        mTextViewTime=(TextView)rootView.findViewById(R.id.timetotake);
        resultView=(RelativeLayout)rootView.findViewById(R.id.viewResult);
        startView=(RelativeLayout)rootView.findViewById(R.id.viewStart);
        MainStart=(RelativeLayout)rootView.findViewById(R.id.GetStarted);
        mTextResult=(TextView)rootView.findViewById(R.id.txtResult);
        Go=(Button)rootView.findViewById(R.id.Go);

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainStart.setVisibility(View.INVISIBLE);
                resultView.setVisibility(View.INVISIBLE);
                startView.setVisibility(View.VISIBLE);
                lanceWork();
            }
        });

















        return rootView;
    }

    public void lanceWork(){

        //Configuartion
        if (getActivity().checkSelfPermission(Manifest.permission.BODY_SENSORS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.BODY_SENSORS},
                    1);
            Message("Vous devez autoriser la l'écture des pouls");
        } else {
            mSensorManager = ((SensorManager)getActivity().getApplicationContext().getSystemService(Context.SENSOR_SERVICE));
            Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
            mSensorManager.registerListener(this, mHeartRateSensor,  SensorManager.SENSOR_DELAY_FASTEST);

            Message("Bien placez la montre SVP");

            psv.startPulse();
            mTextViewOperation.setText(Instructions[indice]);
            CountDown();
        };

    }

    public void CountDown(){
        CountDownTimer countDownTimer = new CountDownTimer(time[indice],1000) {
            @Override
            public void onTick(long l) {
                Integer result = (int) (long) l/1000;
                mTextViewTime.setText(result+"");

                if(Instructions[indice].equalsIgnoreCase("Flexion")){
                    if(result%2==0){
                        Vibrator vibrator = (Vibrator) getActivity().getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                        long[] vibrationPattern = {0, 500, 50, 300};
                        //-1 - don't repeat
                        final int indexInPatternToRepeat = -1;
                        vibrator.vibrate(vibrationPattern, indexInPatternToRepeat);
                    }
                }

            }

            @Override
            public void onFinish() {
                if(indice<2){
                    pouls[indice]=value;
                    indice++;
                    mTextViewOperation.setText(Instructions[indice]);
                    CountDown();
                }else {
                    //Afficher Resultat
                    pouls[indice]=value;
                    Message("Le Test est terminé");
                    stopListener();
                    getResult();
                }
            }
        }.start();
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            //Read
            value = (int)sensorEvent.values[0];
            mTextViewHeart.setText(""+value);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("TAG", "onAccuracyChanged - accuracy: " + i);

    }

    public void stopListener(){

        mSensorManager.unregisterListener(this);
        Log.d("TAG", "Stop");
    }

    @Override
    public void onStop() {
        super.onStop();
        stopListener();
    }

    public void Message(String s){
        Toast.makeText(getActivity().getApplicationContext(),s, Toast.LENGTH_SHORT).show();

    }

    public void getResult(){
        Integer Lr=((pouls[0]+pouls[1]+pouls[2])-200)/10;
        Integer Ld = ((pouls[0]-70) + 2*(pouls[2]-pouls[0]) )/10;

        mTextResult.setText("LR => "+Lr+" \nLD => "+Ld +" \n resultat de Test chez le medecin");

        startView.setVisibility(View.INVISIBLE);
        MainStart.setVisibility(View.INVISIBLE);
        resultView.setVisibility(View.VISIBLE);

    }
}
