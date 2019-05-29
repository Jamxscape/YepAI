package com.yep.yepai.tencentChat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.Gson;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yep.yepai.MapKeyComparator;
import com.yep.yepai.R;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class WeatherActivity extends AppCompatActivity {
    CityPickerView mPicker=new CityPickerView();
    String randomStr = getRandomString(10);
    String randomSession = getRandomString(10);
    String cityStr = new String();//接收转换成为的城市字符串
    Button tell_weather;
    TextView mTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        tell_weather = findViewById(R.id.tell_weather);
        mTv = findViewById(R.id.weather);
        tell_weather.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                wheel();
                sendWeather();
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
            }
            @Override
            public void onCancel() {
                ToastUtils.showLongToast(WeatherActivity.this, "已取消");
            }
        });
        //显示
        mPicker.showCityPicker();
    }
    /**
     * 使用 Map按key进行排序
     *
     * @param map x
     * @return x
     */
    public Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Object> sortMap = new TreeMap<>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    /**
     * 随机字符串
     *
     * @param length x
     * @return x
     */
    public String getRandomString(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public void sendWeather() {
        final String ques = cityStr +"的天气";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long currTime = (TimeUtils.getNowMills() / 1000);

                    //  参数组合
                    Map<String, Object> params = new HashMap<>();
                    int APP_ID = 2111114017;
                    params.put("app_id", APP_ID);
                    params.put("time_stamp", currTime);
                    params.put("nonce_str", randomStr);
                    params.put("session", randomSession);
                    params.put("question", ques);
                    //  鉴权开始
                    //  1. 将<key, value>请求参数对按key进行字典升序排序，得到有序的参数对列表N
                    Map<String, Object> resultMap = sortMapByKey(params);
                    //  2.列表N中的参数对按URL键值对的格式拼接成字符串，得到字符串T（如：key1=value1&key2=value2），
                    //  URL键值拼接过程value部分需要URL编码，URL编码算法用大写字母，例如%E8，而不是小写%e8
                    //  3. 将应用密钥以app_key为键名，组成URL键值拼接到字符串T末尾，得到字符串S（如：key1=value1&key2=value2&app_key=密钥)
                    Set<String> keySet = resultMap.keySet();
                    StringBuilder sb = new StringBuilder();
                    for (String key : keySet) {
                        Object value = resultMap.get(key);
                        sb.append("&").append(key).append("=").append(URLEncoder.encode(value + "", "UTF-8"));
                    }
                    sb.deleteCharAt(0);
                    String APP_KEY = "A2m9UfqFv30JnM2W";
                    sb.append("&app_key=").append(APP_KEY);
                    LogUtils.iTag("RedWolf md5运算之前", sb);
                    //  4. MD5运算
                    String md5Sign = EncryptUtils.encryptMD5ToString(sb.toString());
                    LogUtils.iTag("RedWolf md5Sign", md5Sign);
                    //  利用Http发送对话请求
                    String URL = "https://api.ai.qq.com/fcgi-bin/nlp/nlp_textchat";
                    //  拼接参数之前
                    LogUtils.iTag("RedWolf", APP_ID, randomStr, ques, randomSession, currTime, APP_KEY, md5Sign);
                    OkGo.<String>get(URL)
                            .tag(this)
                            .params("app_id", APP_ID)
                            .params("nonce_str", randomStr)
                            .params("question", ques)
                            .params("session", randomSession)
                            .params("time_stamp", currTime)
                            .params("sign", md5Sign)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(final Response<String> response) {
                                    LogUtils.iTag("RedWolfR", response.body());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            GetTencent fuck = new Gson().fromJson(response.body(), GetTencent.class);
                                            mTv.append("" + fuck.getData().getAnswer()+"\n");
                                            //mTv.append("session : " + fuck.getData().getSession() + "\n");
                                        }
                                    });

                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    LogUtils.iTag("RedWolfR", response.body());
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();

    }

    public void clearAns(View view) {
        mTv.setText("");
    }
}
