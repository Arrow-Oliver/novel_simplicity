package com.zua.manager.dao;

import com.zua.dao.entity.UserInfo;
import com.zua.dao.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Arrow
 * @date 2022/6/20 14:59
 */
@Component
@RequiredArgsConstructor
public class UserDaoManager {

    private final UserInfoMapper userInfoMapper;

    public List<UserInfo> getUserInfo(List<Long> userIds) {
        return userInfoMapper.selectBatchIds(userIds);
    }
}
