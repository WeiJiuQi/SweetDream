package com.example.sweetdream;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import static com.example.sweetdream.WebInteraction.sha512;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    private ListView listView_setting ;
    private ListView listView_about ;
    private Calendar mCalendar;
    private TextView account;
    private TextView wakeup;
    private TextView sleep;
    private Button roundButton;
    private static final String[] commonFunList1 = new String[]{
            "就寝目标",
            "起床目标"
    };
    private static final String[] commonFunList2 = new String[]{
            "版本信息",
            "联系我们",
    };




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        account = (TextView)view.findViewById(R.id.account);
        wakeup=(TextView)view.findViewById(R.id.wake);
        sleep=(TextView)view.findViewById(R.id.sleep);
        roundButton = (Button)view.findViewById(R.id.roundButton);
        roundButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                final Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        String sleepHour = UserManage.getInstance().getUserInfo(getActivity()).getSleepHourS();
        String sleepMinute = UserManage.getInstance().getUserInfo(getActivity()).getSleepMinuteS();
        sleep.setText(sleepHour+":"+sleepMinute);
        String wakeupHour = UserManage.getInstance().getUserInfo(getActivity()).getWakeUpHourS();
        String wakeupMinute = UserManage.getInstance().getUserInfo(getActivity()).getWakeUpMinuteS();
        wakeup.setText(wakeupHour+":"+wakeupMinute);
        listView_setting = (ListView)view.findViewById(R.id.listView_setting);
        listView_setting.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, commonFunList1));
        listView_setting.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        if(position==0){
                            mCalendar = Calendar.getInstance();
                            TimePickerDialog dialog = new TimePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                    mCalendar.set(Calendar.HOUR_OF_DAY, i);
                                    mCalendar.set(Calendar.MINUTE, i1);
                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                    sleep.setText(format.format(mCalendar.getTime()));
                                    String temp = format.format(mCalendar.getTime());
                                    String[] re = temp.split(":");
                                    int sleepHour =  Integer.parseInt(re[0]);
                                    int sleepMinute = Integer.parseInt(re[1]);
                                    String phone_sha512=sha512(UserManage.getInstance().getUserInfo(getActivity()).getUserName());
                                    String stringUrl = "http://39.106.155.126:3000/updatetime?user_name="+phone_sha512+"&field_name1=sleep_hh&field_value1="+sleepHour+"&field_name2=sleep_mm&field_value2="+sleepMinute;
                                    new WebInteraction().execute(stringUrl);
                                    UserManage.getInstance().saveUserSleepTime(getActivity(), mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE));
                                    UserManage.getInstance().saveUserSleepTimeS(getActivity(), String.format("%02d", mCalendar.get(Calendar.HOUR_OF_DAY)), String.format("%02d", mCalendar.get(Calendar.MINUTE)) );
                                    int time = dTime(mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE));
                                    ((MainPageActivity)getActivity()).flush(time);
                                    //sleepBut.setText(format.format(mCalendar.getTime()));
                                    //SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
                                    //Toast.makeText(getApplicationContext(), "" + format.format(mCalendar.getTime()), Toast.LENGTH_SHORT).show();
                                }
                            }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
                            dialog.show();
                        }
                        else if(position==1){
                            mCalendar = Calendar.getInstance();
                            TimePickerDialog dialog = new TimePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                    mCalendar.set(Calendar.HOUR_OF_DAY, i);
                                    mCalendar.set(Calendar.MINUTE, i1);
                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                    String temp = format.format(mCalendar.getTime());
                                    String[] ret = temp.split(":");
                                    int wakeupHour =  Integer.parseInt(ret[0]);
                                    int wakeupMinute = Integer.parseInt(ret[1]);
                                    String phone_sha512=sha512(UserManage.getInstance().getUserInfo(getActivity()).getUserName());
                                    String stringUrl = "http://39.106.155.126:3000/updatetime?user_name="+phone_sha512+"&field_name1=wake_hh&field_value1="+wakeupHour+"&field_name2=wake_mm&field_value2="+wakeupMinute;
                                    new WebInteraction().execute(stringUrl);
                                    wakeup.setText(format.format(mCalendar.getTime()));
                                    UserManage.getInstance().saveUserWakeUpTime(getActivity(), mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE));
                                    UserManage.getInstance().saveUserWakeUpTimeS(getActivity(), String.format("%02d", mCalendar.get(Calendar.HOUR_OF_DAY)), String.format("%02d", mCalendar.get(Calendar.MINUTE)) );
                                    int time = dTime(mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE));
                                    ((MainPageActivity)getActivity()).flush(time);
                                    //SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
                                    //Toast.makeText(getApplicationContext(), "" + format.format(mCalendar.getTime()), Toast.LENGTH_SHORT).show();
                                }
                            }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
                            dialog.show();

                        }
                    }
                }
        );

        account.setText(UserManage.getInstance().getUserInfo(getActivity()).getUserName());

        listView_about = (ListView)view.findViewById(R.id.listView_about);
        listView_about.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, commonFunList2));
        listView_about.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String a=position+"";
                        mParam1=a;
                        Log.i("test",mParam1);
                    }
                }
        );

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

}
