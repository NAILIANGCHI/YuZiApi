package com.naraci.app.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 抖音配置
 * @TableName douyin_config
 */
@TableName(value ="douyin_config")
@Data
public class DouyinConfig implements Serializable {
    /**
     * 生成时间
     */
    private Date creatTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * cookie
     */
    private String cookie;

    /**
     * 头部标识
     */
    private String userAgent;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
