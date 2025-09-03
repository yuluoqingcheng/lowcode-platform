package com.lowcode.mapper;

/**
 * UserMapper 临时禁用接口
 * 此文件已被临时简化以避免 MyBatis Plus 兼容性问题
 * 等兼容性问题解决后将恢复此 Mapper 的完整功能
 *
 * @author 低代码平台
 * @since 2024-01-01
 */
public interface UserMapper {
    // 临时空接口，避免编译错误
    // MyBatis Plus 相关功能已临时禁用
}

/*
原始完整接口内容（临时注释）：

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lowcode.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectByUsername(@Param("username") String username);
    User selectByEmail(@Param("email") String email);
    User selectByPhone(@Param("phone") String phone);
    void updateLastLogin(@Param("userId") Long userId, @Param("loginIp") String loginIp);
}
*/