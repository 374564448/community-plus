package com.banmingi.communityplus.contentcenter.feignclient;

import com.banmingi.communityplus.contentcenter.dto.usercenter.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther 半命i 2020/5/19
 * @description
 */

@FeignClient(name = "user-center",path = "user")
public interface UserFeignClient {
    @GetMapping("/{id}")
    UserDTO findById(@PathVariable Integer id);
}
