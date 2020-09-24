package com.chad.library.adapter.base.entity;

import java.io.Serializable;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class SectionMultiEntity<T> implements Serializable, MultiItemEntity {

    public boolean isHeader;
    public T t;
    public List<T> list;
    public String header;

    public SectionMultiEntity(boolean isHeader, String header) {
        this.isHeader = isHeader;
        this.header = header;
        this.t = null;
    }

    public SectionMultiEntity(T t) {
        this.isHeader = false;
        this.header = null;
        this.t = t;
    }


    public SectionMultiEntity(List<T> list) {
        this.isHeader = false;
        this.header = null;
        this.t = null;
        this.list = list;
    }
}
