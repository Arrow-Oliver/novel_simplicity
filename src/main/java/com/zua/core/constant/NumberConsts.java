package com.zua.core.constant;

import static com.zua.core.constant.SystemConfigConsts.CONST_INSTANCE_EXCEPTION_MSG;

/**
 * @author Arrow
 * @date 2023/4/27 16:05
 */
public class NumberConsts {

    private NumberConsts() {
        throw new IllegalStateException(CONST_INSTANCE_EXCEPTION_MSG);
    }

    /**
     * 用户状态码，0：正常，1：禁用
     */
    public static final int USER_NO_STATUS = 1;
}
