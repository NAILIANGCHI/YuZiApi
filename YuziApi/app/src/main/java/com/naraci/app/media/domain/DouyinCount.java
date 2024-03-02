package com.naraci.app.media.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName douyin_count
 */
@TableName(value ="douyin_count")
@Data
public class DouyinCount implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 次数
     */
    private Integer count;

    /**
     * 角色id
     */
    private String userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime = new Date();

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
