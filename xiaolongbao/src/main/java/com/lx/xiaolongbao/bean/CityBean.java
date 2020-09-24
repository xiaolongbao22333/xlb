package com.lx.xiaolongbao.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CityBean
 * @Author: 冻品
 * @CreateDate: 2020-05-26 10:37
 * @UpdateUser: 小笼包
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class CityBean {
    private List<String> options1Items ;//第一级数据源
    private List<List<String>> options2Items;//第二级数据源
    private List<List<List<String>>> options3Items;

    public List<String> getOptions1Items() {
        return options1Items;
    }

    public void setOptions1Items(List<String> options1Items) {
        this.options1Items = options1Items;
    }

    public List<List<String>> getOptions2Items() {
        return options2Items;
    }

    public void setOptions2Items(List<List<String>> options2Items) {
        this.options2Items = options2Items;
    }

    public List<List<List<String>>> getOptions3Items() {
        return options3Items;
    }

    public void setOptions3Items(List<List<List<String>>> options3Items) {
        this.options3Items = options3Items;
    }
}
