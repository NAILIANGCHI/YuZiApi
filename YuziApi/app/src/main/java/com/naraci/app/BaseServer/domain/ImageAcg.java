package com.naraci.app.BaseServer.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.naraci.core.util.IdUtil;
import lombok.Data;

/**
 *
 * @TableName image_acg
 */
@TableName(value ="image_acg")
@Data
public class ImageAcg implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id = IdUtil.getSnowflakeId();

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 文件名
     */
    private String name;

    /**
     * url
     */
    private String url;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
