package com.yep.yepai;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.yep.yepai.ui.FacePlus;
import com.yep.yepai.util.PathUtil;

import java.io.File;
import java.util.Calendar;

public class Frag5Activity extends Fragment {
    private TextView textView;
    private Button faceplus;
    //private static TextView textView;
    //private static final int CHOOSE_NAME=1;
    //public String Temp_name;
    public TextView user_sex;
    public TextView user_birth;
    public TextView user_address;//地址
    public TextView user_nation;//民族
    public TextView user_zodiac;//星座
    public TextView user_cons;//属相
    public TextView user_zipcode;//邮编
    public ImageView user_photo;
    public TextView user_get_photoifm;
    public File file = null;
    private static final int CHOOSE_PHOTO=0;
    private String[] sexArry = new String[]{"其他", "女", "男"};// 性别选择
    private String[] nationArry = new String[]{"汉族", "美利坚", "满族","蒙古族","维吾尔族","哈萨克族"};// 性别选择
    private String[] zodiacArry = new String[]{"鼠", "牛", "虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪"};// 生肖选择
    private String[] consArry = new String[]{"水瓶座","双鱼座","白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座",
            "天蝎座","射手座","魔羯座"};
    //申明对象
    CityPickerView mPicker=new CityPickerView();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //不同的Activity对应不同的布局
        View view = inflater.inflate(R.layout.activity_frag5, container, false);
        faceplus = view.findViewById(R.id.face_plus);
        faceplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getActivity(),FacePlus.class);
                startActivity(intent);
            }
        });
        //点击进入编辑框
        //性别选择
        user_sex = view.findViewById(R.id.user_sex);
        user_sex.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showSexChooseDialog();
            }
        });
        //生日选择
        user_birth = view.findViewById(R.id.user_birth);
        user_zodiac = view.findViewById(R.id.user_zodiac);
        user_cons = view.findViewById(R.id.user_cons);
        user_birth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar nowdate = Calendar.getInstance();
                final int mYear = nowdate.get(Calendar.YEAR);
                final int mMonth = nowdate.get(Calendar.MONTH);
                final int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
                //调用DatePickerDialog
                new DatePickerDialog(getActivity(),onDateSetListener, mYear, mMonth, mDay).show();
            }});
        //城市选择
        user_address = view.findViewById(R.id.user_address);
        user_zipcode = view.findViewById(R.id.user_zipcode);
        user_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheel();
            }
        });
        //民族选择
        user_nation = view.findViewById(R.id.user_nation);
        user_nation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNationChooseDialog();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void showSexChooseDialog() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this.getActivity());// 自定义对话框
        builder3.setTitle("性别");
        builder3.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                user_sex.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }

    public void showNationChooseDialog() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this.getActivity());// 自定义对话框
        builder3.setTitle("民族");
        builder3.setSingleChoiceItems(nationArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                user_nation.setText(nationArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear+1;
            int mDay = dayOfMonth;
            String days;
            days = new StringBuffer().append(mYear).append("年").append(mMonth).append("月").append(mDay).append("日").toString();
            user_birth.setText(days);//显示到界面上
            int zodia = (mYear-1996)%12;//生肖的id
            user_zodiac.setText(zodiacArry[zodia]);//设置生肖
            //设置
            int cons = 0;//星座字符串标号
            if(mMonth==1&&mDay>=20||mMonth==2&&mDay<=18){
                cons = 0;
            }
            else if(mMonth==2&&mDay>=19||mMonth==3&&mDay<=20){
                cons = 1;
            }
            else if(mMonth==3&&mDay>=21||mMonth==4&&mDay<=19){
                cons = 2;
            }
            else if(mMonth==4&&mDay>=20||mMonth==5&&mDay<=20){
                cons = 3;
            }
            else if(mMonth==5&&mDay>=21||mMonth==6&&mDay<=20){
                cons = 4;
            }
            else if(mMonth==6&&mDay>=21||mMonth==7&&mDay<=22){
                cons = 5;
            }
            else if(mMonth==7&&mDay>=23||mMonth==8&&mDay<=22){
                cons = 6;
            }
            else if(mMonth==8&&mDay>=23||mMonth==9&&mDay<=22){
                cons = 7;
            }
            else if(mMonth==9&&mDay>=23||mMonth==10&&mDay<=22){
                cons = 8;
            }
            else if(mMonth==10&&mDay>=23||mMonth==11&&mDay<=21){
                cons = 9;
            }
            else if(mMonth==11&&mDay>=22||mMonth==12&&mDay<=21){
                cons = 10;
            }
            else if(mMonth==12&&mDay>=22||mMonth==1&&mDay<=19){
                cons = 11;
            }
            user_cons.setText(consArry[cons]);
        }
    };

    //城市选择

    /**
     * 弹出选择器
     */
    private void wheel(){
        mPicker.init(getActivity());
        //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo

        //对话框属性
        CityConfig cityConfig = new CityConfig.Builder()
                .title("选择城市")//标题
                .titleTextSize(18)//标题文字大小
                .titleTextColor("#585858")//标题文字颜  色
                .titleBackgroundColor("#E9E9E9")//标题栏背景色
                .confirTextColor("#585858")//确认按钮文字颜色
                .confirmText("确认")//确认按钮文字
                .confirmTextSize(16)//确认按钮文字大小
                .cancelTextColor("#585858")//取消按钮文字颜色
                .cancelText("取消")//取消按钮文字
                .cancelTextSize(16)//取消按钮文字大小
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(3)//显示item的数量
                .province("北京市")//默认显示的省份
                .city("北京市")//默认显示省份下面的城市
                .district("东城区")//默认显示省市下面的区县数据
                .provinceCyclic(true)//省份滚轮是否可以循环滚动
                .cityCyclic(true)//城市滚轮是否可以循环滚动
                .districtCyclic(true)//区县滚轮是否循环滚动
                //.setCustomItemLayout(R.layout.item_city)//自定义item的布局
                //.setCustomItemTextViewId(R.id.item_city_name_tv)//自定义item布局里面的textViewid
                .drawShadows(false)//滚轮不显示模糊效果
                .setLineColor("#03a9f4")//中间横线的颜色
                .setLineHeigh(5)//中间横线的高度
                .setShowGAT(true)//是否显示港澳台数据，默认不显示
                .build();
        //监听选择点击事件及返回结果
        mPicker.setConfig(cityConfig);
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuilder sb = new StringBuilder();
                if (province != null) {
                    sb.append(province.getName()+"\n");
                }

                if ((city != null) && !city.getName().equals("省直辖县级行政单位")) { // 不相等的时候
                    sb.append(city.getName()+"\n");
                }

                if (district != null) {
                    sb.append(district.getName()+"\n");
                }

                user_address.setText("" + sb.toString());//转化成字符串
                user_zipcode.setText(district.getId());//得到邮编
            }
            @Override
            public void onCancel() {
                ToastUtils.showLongToast(getActivity(), "已取消");
            }
        });
        //显示
        mPicker.showCityPicker();
    }

}