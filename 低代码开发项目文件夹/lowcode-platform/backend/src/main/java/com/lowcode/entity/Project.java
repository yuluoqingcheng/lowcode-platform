package com.lowcode.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 项目实体
 *
 * @author 低代码平台
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("project")
public class Project {
    
    /**
     * 项目ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    
    /**
     * 项目名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 项目描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 项目缩略图
     */
    @TableField("thumbnail")
    private String thumbnail;
    
    /**
     * 项目模板类型
     */
    @TableField("template_type")
    private String templateType;
    
    /**
     * 状态：0-禁用，1-启用，2-已发布
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 创建者ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 项目版本
     */
    @TableField("version")
    private String version;
    
    /**
     * 项目配置信息
     */
    @TableField("config")
    private String config;
    
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    /**
     * 删除标记：0-未删除，1-已删除
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;
}