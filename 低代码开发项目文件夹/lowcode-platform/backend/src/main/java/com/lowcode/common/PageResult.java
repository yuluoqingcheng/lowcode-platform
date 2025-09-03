package com.lowcode.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * 分页结果类
 *
 * @author 低代码平台
 * @since 2024-01-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    
    /**
     * 数据列表
     */
    private List<T> records;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 当前页码
     */
    private Long current;
    
    /**
     * 每页大小
     */
    private Long size;
    
    /**
     * 总页数
     */
    private Long pages;
    
    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;
    
    /**
     * 是否有下一页
     */
    private Boolean hasNext;
    
    /**
     * 静态工厂方法
     */
    public static <T> PageResult<T> of(List<T> records, Long total, Long current, Long size) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setRecords(records);
        pageResult.setTotal(total);
        pageResult.setCurrent(current);
        pageResult.setSize(size);
        
        // 计算总页数
        Long pages = (total + size - 1) / size;
        pageResult.setPages(pages);
        
        // 计算是否有上一页和下一页
        pageResult.setHasPrevious(current > 1);
        pageResult.setHasNext(current < pages);
        
        return pageResult;
    }
    
    /**
     * 空结果
     */
    public static <T> PageResult<T> empty(Long current, Long size) {
        return of(List.of(), 0L, current, size);
    }
}