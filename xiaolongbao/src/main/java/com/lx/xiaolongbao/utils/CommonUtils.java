package com.lx.xiaolongbao.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lx.xiaolongbao.bean.CitiesData;
import com.lx.xiaolongbao.bean.CityBean;
import com.lx.xiaolongbao.bean.CityDataBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @ClassName: CommonUtils
 * @Author: 冻品
 * @CreateDate: 2020-05-21 16:55
 * @UpdateUser: 小笼包
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class CommonUtils {
    @SuppressLint("SimpleDateFormat")
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public static CityBean getCityData(Context context, String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        Gson gson = gsonBuilder.create();
        CitiesData cityData = gson.fromJson(stringBuilder.toString(), CitiesData.class);
        List<CityDataBean> data = cityData.getCities();

        CityBean city = new CityBean();

        List<String> options1Items = new ArrayList<>();//第一级数据源
        List<List<String>> options2Items = new ArrayList<>();//第二级数据源
        List<List<List<String>>> options3Items = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {//遍历省份
            options1Items.add(data.get(i).getCityName());
            List<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            //该省的城市列表（第二级）
            List<List<String>> areaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < data.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = data.get(i).getCityList().get(c).getCityName();
                cityList.add(cityName);//添加城市
                ArrayList<String> cityAreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (data.get(i).getCityList().get(c) == null
                        || data.get(i).getCityList().get(c).getCityList().size() == 0) {
                    cityAreaList.add("");
                } else {
                    for (int j = 0; j < data.get(i).getCityList().get(c).getCityList().size(); j++) {
                        cityAreaList.add(data.get(i).getCityList().get(c).getCityList().get(j).getCityName());
                    }
                }
                areaList.add(cityAreaList);//添加该省所有地区数据
            }
            options2Items.add(cityList);
            /**
             * 添加地区数据
             */
            options3Items.add(areaList);
        }
        city.setOptions1Items(options1Items);
        city.setOptions2Items(options2Items);
        city.setOptions3Items(options3Items);
        return city;
    }

    public static void getCityData2(Context context, String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        Gson gson = gsonBuilder.create();
        CitiesData cityData = gson.fromJson(stringBuilder.toString(), CitiesData.class);
        List<CityDataBean> cities = cityData.getCities();


        List<CityDataBean> options1Items = new ArrayList<>();//第一级数据源
        List<List<CityDataBean>> options2Items = new ArrayList<>();//第二级数据源
        List<List<List<CityDataBean>>> options3Items = new ArrayList<>();

        for (int a = 0; a < cities.size(); a++) {//遍历省份
            CityDataBean cityDataBean = cities.get(a);
            options1Items.add(cityDataBean);
            List<CityDataBean> cityList = new ArrayList<>();//该省的城市列表（第二级）
            //该省的城市列表（第二级）
            List<List<CityDataBean>> areaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int b = 0; b < cityDataBean.getCityList().size(); b++) {//遍历该省份的所有城市
                cityList.add(cityDataBean.getCityList().get(b));//添加城市
                List<CityDataBean> cityAreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (cityDataBean.getCityList().get(b) == null
                        || cityDataBean.getCityList().get(b).getCityList().size() == 0) {
                    CityDataBean cityBean = new CityDataBean();
                    cityBean.setCityId("");
                    cityBean.setCityName("");
                    cityAreaList.add(cityBean);
                } else {
                    for (int c = 0; c < cityDataBean.getCityList().get(b).getCityList().size(); c++) {
                        cityAreaList.add(cityDataBean.getCityList().get(b).getCityList().get(c));
                    }
                }
                areaList.add(cityAreaList);//添加该省所有地区数据
            }
            options2Items.add(cityList);
            /**
             * 添加地区数据
             */
            options3Items.add(areaList);
        }

    }


    public static String getAssets(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void callPhone(String phone, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        activity.startActivity(intent);
    }

    private static final String REGEX_MOBILE = "^[1][345789][0-9]{8}$";
    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    /**
     * 匹配手机号的规则：[3578]是手机号第二位可能出现的数字
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    public static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 只允许数字和汉字
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) {
        String strs = "";
        try {
            String regEx = "[^0-9\u4E00-\u9FA5]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            strs = m.replaceAll("").trim();
        } catch (PatternSyntaxException e) {
            ToastUtils.w(e);
            return "";
        }
        return strs;
    }
}
