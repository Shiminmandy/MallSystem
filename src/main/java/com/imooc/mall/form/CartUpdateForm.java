package com.imooc.mall.form;

import lombok.Data;

@Data
public class CartUpdateForm {
    //非必填

    private Integer quantity;
    private Boolean selected;
}
