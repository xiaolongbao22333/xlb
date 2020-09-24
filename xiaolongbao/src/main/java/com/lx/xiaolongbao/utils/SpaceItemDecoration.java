package com.lx.xiaolongbao.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int left, top, right, bottom;

    public SpaceItemDecoration() {
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = left;
        outRect.right = right;
        outRect.bottom = bottom;
        outRect.top = top;

        if (parent.getChildAdapterPosition(view) != 0) {

        }

    }

    public SpaceItemDecoration(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
}
