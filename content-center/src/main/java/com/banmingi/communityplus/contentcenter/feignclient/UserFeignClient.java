package com.banmingi.communityplus.contentcenter.feignclient;

import com.banmingi.communityplus.contentcenter.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @auther 半命i 2020/5/19
 * @description
 */

@FeignClient(name = "user-center",path = "users")
public interface UserFeignClient {
    /**
     * 根据id查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/{id}")
    UserDTO findById(@PathVariable Integer id);

    /**
     * 根据用户id集合查询用户信息集合
     * @param ids 用户id集合
     * @return 用户信息集合
     */
    @GetMapping
    List<UserDTO> findListByIds(@RequestParam("ids") List<Integer> ids);
}
