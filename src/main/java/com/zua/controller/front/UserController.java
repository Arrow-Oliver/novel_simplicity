package com.zua.controller.front;

import com.zua.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arrow
 * @date 2022/6/15 17:14
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


}
