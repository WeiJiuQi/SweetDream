package com.example.sweetdream;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.github.mikephil.charting.charts.BarChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    protected boolean isCreate = false;
    private  BarChart barChart;
    private BarChartManager barChartManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DataFragment newInstance(String param1, String param2) {
        DataFragment fragment = new DataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreate = true;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        barChart = (BarChart) view.findViewById(R.id.bar_chart);
        barChartManager = new BarChartManager(barChart);
        //设置x轴的数据
        ArrayList<String> xValues = new ArrayList<>();
//        Calendar cal=Calendar.getInstance();
//        String times[] = new String[7];
//        for(int i=1;i<=7;i++)
//        {
//            cal.add(Calendar.DATE, -1);
//            Date time=cal.getTime();
//            SimpleDateFormat format = new SimpleDateFormat("MM-dd");
//            times[7-i]=format.format(time);
//        }
        for(int i=7;i>=1;i--)
        {
//            xValues.add(times[i]);
            xValues.add("前"+i+"天");
        }
        //设置y轴的数据()
        ArrayList<Float> yValues = new ArrayList<>();
//        for (int j = 1; j <= 7; j++) {
//            yValues.add((float) (Math.random() * 10));
//        }
        yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum1());
        yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum2());
        yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum3());
        yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum4());
        yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum5());
        yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum6());
        yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum7());
        //颜色集合
        List<Integer> colours = new ArrayList<>();
        colours.add(Color.rgb(174, 221, 129));
        colours.add(Color.rgb(248, 232, 137));
        colours.add(Color.rgb(229, 187, 129));
        colours.add(Color.rgb(147, 224, 255));
        colours.add(Color.rgb(254, 67, 101));
        //条的名字集合
        List<String> names = new ArrayList<>();
        names.add("睡眠时长");

        //创建柱状图表
        barChartManager.showBarChart(xValues, yValues, names.get(0), colours);
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

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        if (enter) {
            ArrayList<String> xValues = new ArrayList<>();
//        Calendar cal=Calendar.getInstance();
//        String times[] = new String[7];
//        for(int i=1;i<=7;i++)
//        {
//            cal.add(Calendar.DATE, -1);
//            Date time=cal.getTime();
//            SimpleDateFormat format = new SimpleDateFormat("MM-dd");
//            times[7-i]=format.format(time);
//        }
            for(int i=7;i>=1;i--)
            {
//            xValues.add(times[i]);
                xValues.add("前"+i+"天");
            }
            //设置y轴的数据()
            ArrayList<Float> yValues = new ArrayList<>();
//            for (int j = 1; j <= 7; j++) {
//                yValues.add((float) (Math.random() * 10));
//            }
            yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum1());
            yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum2());
            yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum3());
            yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum4());
            yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum5());
            yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum6());
            yValues.add((float)UserManage.getInstance().getUserInfo(getActivity()).getNum7());
            //颜色集合
            List<Integer> colours = new ArrayList<>();
            colours.add(Color.rgb(174, 221, 129));
            colours.add(Color.rgb(248, 232, 137));
            colours.add(Color.rgb(229, 187, 129));
            colours.add(Color.rgb(147, 224, 255));
            colours.add(Color.rgb(254, 67, 101));
            //条的名字集合
            List<String> names = new ArrayList<>();
            names.add("睡眠时长");

            //创建柱状图表
            barChartManager.showBarChart(xValues, yValues, names.get(0), colours);
        } else {
        }
        return super.onCreateAnimator(transit, enter, nextAnim);
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
}
