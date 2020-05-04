package com.banmingi.communityplus.usercenter.controller;

import com.banmingi.communityplus.commons.utils.JwtOperator;
import com.banmingi.communityplus.usercenter.dto.login.GeneralLoginDTO;
import com.banmingi.communityplus.usercenter.dto.login.JwtTokenRespDTO;
import com.banmingi.communityplus.usercenter.dto.login.LoginRespDTO;
import com.banmingi.communityplus.usercenter.dto.login.GitHubLoginDTO;
import com.banmingi.communityplus.usercenter.dto.login.UserRespDTO;
import com.banmingi.communityplus.usercenter.entity.User;
import com.banmingi.communityplus.usercenter.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther 半命i 2020/5/1
 * @description
 */
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final JwtOperator jwtOperator;

    /**
     * 账号密码登录
     * @param generalLoginDTO
     * @return
     */
    @PostMapping("/generalLogin")
    public ResponseEntity<LoginRespDTO> generalLogin(@RequestBody GeneralLoginDTO generalLoginDTO) {
        //1. 看数据库是否有该用户
        User user = this.userService.generalLogin(generalLoginDTO);


        if (user == null) {
            log.error("账号不存在或密码验证不正确");
            return ResponseEntity.badRequest().build();
        }
        //颁发token
        String token = getToken(user);
        //构建响应
        UserRespDTO userRespDTO = new UserRespDTO();
        BeanUtils.copyProperties(user,userRespDTO);
        LoginRespDTO loginRespDTO = LoginRespDTO.builder()
                .user(userRespDTO)
                .token(
                        JwtTokenRespDTO.builder()
                                .token(token)
                                .expirationTime(jwtOperator.getExpirationTime().getTime())
                                .build()
                ).build();

        return ResponseEntity.ok(loginRespDTO);
    }

    /**
     * GitHub账户登录
     * @param gitHubLoginDTO
     * @return
     */
    @PostMapping("/githubLogin")
    public ResponseEntity<LoginRespDTO> gitHubLogin(@RequestBody GitHubLoginDTO gitHubLoginDTO) {

        //1. 看用户是否已经注册到数据库
            //如果未注册,插入返回新user
            //如果已经注册,返回user
        User user = this.userService.gitHubLogin(gitHubLoginDTO);
        String token = getToken(user);

        //构建响应
        UserRespDTO userRespDTO = new UserRespDTO();
        BeanUtils.copyProperties(user,userRespDTO);

        LoginRespDTO loginRespDTO = LoginRespDTO.builder()
                .user(userRespDTO)
                .token(
                        JwtTokenRespDTO.builder()
                                .token(token)
                                .expirationTime(jwtOperator.getExpirationTime().getTime())
                                .build()
                ).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(loginRespDTO);
    }

    /**
     * 颁发token
     * @param user
     * @return
     */
    private String getToken(User user) {
        //2. 颁发token
        Map<String,Object> userInfo = new HashMap<>(3);
        userInfo.put("id",user.getId());
        userInfo.put("name",user.getName());
        userInfo.put("roles",user.getRoles());
        return jwtOperator.generateToken(userInfo);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        User user = this.userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


    /**
     * 根据token获取用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public ResponseEntity<UserRespDTO> getUserInfo(HttpServletRequest request) {
        //1. 获取token
        String token = request.getHeader("X-Token");

        //2. 校验token是否合法或在有效期内,如果不合法或已过期,直接抛异常;如果合法或未过期,放行
        Boolean isValid = jwtOperator.validateToken(token);
        if (!isValid) {
            log.warn("token过期");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        //从token中获取用户id
        Claims claims = jwtOperator.getClaimsFromToken(token);
        Integer id = claims.get("id", Integer.class);

        User user = this.userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserRespDTO userRespDTO = new UserRespDTO();
        BeanUtils.copyProperties(user,userRespDTO);
        return ResponseEntity.ok(userRespDTO);
    }

}
