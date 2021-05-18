package com.lx.xiaolongbao.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * author  : xiaolongbao
 * time    : 5/17/21  4:27 PM$
 * desc    :
 */

public class Pcascode  implements IPickerViewData {

    private String code;
    private String name;
    private List<Pcascode> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pcascode> getChildren() {
        return children;
    }

    public void setChildren(List<Pcascode> children) {
        this.children = children;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
