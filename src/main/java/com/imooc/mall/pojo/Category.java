package com.imooc.mall.pojo;


import lombok.Data;

import java.sql.Date;

/**
 *
 * po(persistent object)
 * pojo(plian ordinary java object)
 * noted by shimin
 */

@Data
public class Category {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private Date createTime;
    private Date updateTime;

    //with lombok we don't have to build getter and setter and toString
}
