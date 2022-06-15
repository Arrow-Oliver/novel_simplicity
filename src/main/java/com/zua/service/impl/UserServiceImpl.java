package com.zua.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.dao.entity.UserInfo;
import com.zua.dao.mapper.UserInfoMapper;
import com.zua.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Arrow
 * @date 2022/6/15 17:11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserService{

}
