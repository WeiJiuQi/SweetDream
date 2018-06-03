package com.example.sweetdream;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public TextView signal;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Button sleep;
    public MyCountDownTimer mc;
    public TextView sleepTime;
    public TextView wakeupTime;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        signal = (TextView) view.findViewById(R.id.signal);
        sleepTime = (TextView) view.findViewById(R.id.sleepTime);
        wakeupTime = (TextView) view.findViewById(R.id.wakeupTime);
        sleep = (Button)view.findViewById(R.id.sleep);
        int sleepHour = UserManage.getInstance().getUserInfo(getActivity()).getSleepHour();
        int sleepMinute = UserManage.getInstance().getUserInfo(getActivity()).getSleepMinute();
        int time = dTime(sleepHour, sleepMinute);
        mc = new MyCountDownTimer(time, 1000);
        mc.start();
        String sleepHourS = UserManage.getInstance().getUserInfo(getActivity()).getSleepHourS();
        String sleepMinuteS = UserManage.getInstance().getUserInfo(getActivity()).getSleepMinuteS();
        sleepTime.setText(sleepHourS+":"+sleepMinuteS);
        String wakeupHourS = UserManage.getInstance().getUserInfo(getActivity()).getWakeUpHourS();
        String wakeupMinuteS = UserManage.getInstance().getUserInfo(getActivity()).getWakeUpMinuteS();
        wakeupTime.setText(wakeupHourS+":"+wakeupMinuteS);
        sleep.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar ca = Calendar.getInstance();
                UserManage.getInstance().saveUserSleepTimeToday(getActivity(), ca.get(Calendar.HOUR_OF_DAY), ca.get(Calendar.MINUTE));
                Log.v("aaa",UserManage.getInstance().getUserInfo(getActivity()).getUserName());
                Log.v("nowHour", String.valueOf(ca.get(Calendar.HOUR_OF_DAY)));
                Log.v("nowMinute", String.valueOf(ca.get(Calendar.MINUTE)));
                Log.v("sleepHour",String.valueOf(UserManage.getInstance().getUserInfo(getActivity()).getSleepHourToday()));
                Log.v("sleepMinute",String.valueOf(UserManage.getInstance().getUserInfo(getActivity()).getSleepMinuteToday()));
                sleep.setVisibility(View.GONE);
                final Intent intent=new Intent(getActivity(),BuildingActivity.class);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**只响一次的 时间间隔
     * @param givenHour  自定义的小时
     * @param givenMinute  自定义的分钟
     * @return 返回"当前时间"距"给定时间"的时间差（单位为毫秒）      如果"给定时间"<"当前时间"，则返回的是下一天的"给定时间"
     */
    public int dTime(int givenHour, int givenMinute){

        long currentTimeInMillis = System.currentTimeMillis();
        Calendar givenCalendar = Calendar.getInstance();
//        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
//        givenCalendar.setTimeZone(timeZone);
        givenCalendar.set(Calendar.HOUR_OF_DAY, givenHour);
        givenCalendar.set(Calendar.MINUTE, givenMinute);
        givenCalendar.set(Calendar.SECOND, 0);
        givenCalendar.set(Calendar.MILLISECOND, 0);
        long givenTimeInMillis = givenCalendar.getTimeInMillis();
        //currentTimeInMillis += 28800000;
        int result;
        if(currentTimeInMillis <= givenTimeInMillis){
            result = (int) (givenTimeInMillis - currentTimeInMillis);
        }else{
            result = (int) (24*60*60*1000 - (currentTimeInMillis - givenTimeInMillis));
        }
        return result;
    }

    public class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *      表示以毫秒为单位 倒计时的总数
         *
         *      例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mc = new MyCountDownTimer(24*60*60*1000, 1000);
            mc.start();
            sleep.setVisibility(View.GONE);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            SimpleDateFormat formatter = new SimpleDateFormat("HH小时mm分ss秒");//初始化Formatter的转换格式。
            String hms = formatter.format((millisUntilFinished+57600000)%86400000);
            signal.setText("距离开始建造房屋还有"+hms);
            if(millisUntilFinished < 2*60*60*1000)
            {
                //Toast.makeText(getActivity(),String.valueOf(millisUntilFinished)+"aa",Toast.LENGTH_SHORT).show();
                sleep.setVisibility(View.VISIBLE);
            }
            else
            {
                //Toast.makeText(getActivity(),String.valueOf(millisUntilFinished)+"bb",Toast.LENGTH_SHORT).show();
                sleep.setVisibility(View.GONE);
            }
        }
    }
}
