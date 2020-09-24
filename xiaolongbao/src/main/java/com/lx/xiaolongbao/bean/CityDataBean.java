package com.lx.xiaolongbao.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @ClassName: CityDataBean
 * @Author: 冻品
 * @CreateDate: 2020-05-26 10:23
 * @UpdateUser: 小笼包
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class CityDataBean implements IPickerViewData {


    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private String cityName;
    private String cityId;
    private List<CityDataBean> childs;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public List<CityDataBean> getCityList() {
        return childs;
    }

    public void setCityList(List<CityDataBean> city) {
        this.childs = city;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.cityName;
    }


}
