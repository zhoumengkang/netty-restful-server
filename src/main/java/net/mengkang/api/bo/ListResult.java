package net.mengkang.api.bo;

import net.mengkang.api.entity.BaseEntity;

import java.util.List;

/**
 * Created by zhoumengkang on 30/12/15.
 */
public class ListResult extends Result {

    private List<? extends BaseEntity> item;

    public List<? extends BaseEntity> getItem() {
        return item;
    }

    public void setItem(List<? extends BaseEntity> item) {
        this.item = item;
    }

}
