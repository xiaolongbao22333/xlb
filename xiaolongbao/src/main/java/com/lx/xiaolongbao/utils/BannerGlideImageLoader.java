package com.lx.xiaolongbao.utils;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.BaseBannerInfo;

/**
 * @ClassName: BannerGlideImageLoader
 * @Author: 冻品
 * @CreateDate: 2020/6/3 09:29
 * @UpdateUser: 小笼包
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class BannerGlideImageLoader implements XBanner.XBannerAdapter {
    @Override
    public void loadBanner(XBanner banner, Object model, View view, int position) {
        ImageView imageView = (ImageView) view;
        //圆角
//        RoundedCorners roundedCorners = new RoundedCorners(10);
//        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);

        Glide.with(imageView.getContext()).load(((BaseBannerInfo) model).getXBannerUrl())
//                .apply(options)
                .into(imageView);
    }
}
