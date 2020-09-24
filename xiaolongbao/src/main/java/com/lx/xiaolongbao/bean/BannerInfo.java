package com.lx.xiaolongbao.bean;


import com.stx.xhb.androidx.entity.SimpleBannerInfo;

/**
 * @ClassName: BannerInfo
 * @Author: 冻品
 * @CreateDate: 2020/6/3 09:23
 * @UpdateUser: 小笼包
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class BannerInfo extends SimpleBannerInfo {
    @Override
    public Object getXBannerUrl() {
        return info;
    }
    private String info;
    public BannerInfo(String info) {
        this.info = info;

    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
