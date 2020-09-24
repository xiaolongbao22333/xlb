package com.chad.library.adapter.base.entity;

import java.io.Serializable;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class SectionEntity<T> implements Serializable {
    public boolean isHeader;
    public T t;
    public String header;
    public String id;
    public String name;
    public boolean isCheck;

    public SectionEntity(boolean isHeader, String header) {
        this.isHeader = isHeader;
        this.header = header;
        this.id = id;
        this.name = null;
        this.t = null;
        this.isCheck = false;
    }

    public SectionEntity(boolean isHeader, String header, String id) {
        this.isHeader = isHeader;
        this.header = header;
        this.t = null;
        this.id = id;
        this.name = null;
        this.isCheck = false;
    }

    public SectionEntity(boolean isHeader, String header, String id, boolean isCheck) {
        this.isHeader = isHeader;
        this.header = header;
        this.t = null;
        this.id = id;
        this.name = null;
        this.isCheck = isCheck;
    }

    public SectionEntity(boolean isHeader, String header, T t) {
        this.isHeader = isHeader;
        this.header = header;
        this.t = t;
        this.id = id;
        this.name = null;
        this.isCheck = false;
    }

    public SectionEntity(T t) {
        this.isHeader = false;
        this.header = null;
        this.t = t;
        this.id = null;
        this.name = null;
        this.isCheck = false;
    }

    public SectionEntity(T t, String id) {
        this.isHeader = false;
        this.header = null;
        this.t = t;
        this.id = id;
        this.name = null;
        this.isCheck = false;
    }
    public SectionEntity(T t, String id,String name) {
        this.isHeader = false;
        this.header = null;
        this.t = t;
        this.id = id;
        this.name = name;
        this.isCheck = false;
    }

    public SectionEntity(T t, String id, boolean isCheck) {
        this.isHeader = false;
        this.header = null;
        this.t = t;
        this.id = id;
        this.name = null;
        this.isCheck = isCheck;
    }
}
