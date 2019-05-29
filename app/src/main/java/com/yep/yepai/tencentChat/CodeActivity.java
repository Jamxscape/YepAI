package com.yep.yepai.tencentChat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.yep.yepai.R;

public class CodeActivity extends AppCompatActivity {
    CityPickerView mPicker=new CityPickerView();
    String cityStr = new String();//接收转换成为的城市字符串
    TextView code;
    Button tell_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        code = findViewById(R.id.code);
        tell_code = findViewById(R.id.tell_code);
        tell_code.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                wheel();
            }
        });
    }
    private void wheel(){
        mPicker.init(this);
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
                    sb.append(province.getName());
                }

                if ((city != null) && !city.getName().equals("省直辖县级行政单位")) { // 不相等的时候
                    sb.append(city.getName());
                }

                if (district != null) {
                    sb.append(district.getName());
                }
                cityStr = sb.toString();
                code.setText(cityStr+":"+ district.getId()+"\n");
            }
            @Override
            public void onCancel() {
                ToastUtils.showLongToast(CodeActivity.this, "已取消");
            }
        });
        //显示
        mPicker.showCityPicker();
    }
}
