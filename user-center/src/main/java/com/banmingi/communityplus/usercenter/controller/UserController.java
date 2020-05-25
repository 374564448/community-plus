package com.banmingi.communityplus.usercenter.controller;

import com.banmingi.communityplus.commons.utils.JwtOperator;
import com.banmingi.communityplus.usercenter.dto.user.GeneralLoginDTO;
import com.banmingi.communityplus.usercenter.dto.user.GitHubLoginDTO;
import com.banmingi.communityplus.usercenter.dto.user.JwtTokenRespDTO;
import com.banmingi.communityplus.usercenter.dto.user.LoginRespDTO;
import com.banmingi.communityplus.usercenter.dto.user.RegisterDTO;
import com.banmingi.communityplus.usercenter.dto.user.UserDTO;
import com.banmingi.communityplus.usercenter.entity.User;
import com.banmingi.communityplus.usercenter.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class UserController {

    private final UserService userService;
    private final JwtOperator jwtOperator;

    /**
     * 发送验证码
     */
    @GetMapping("/sendCheckCode")
    public ResponseEntity<Void> sendCheckCode(@RequestParam("accountId") String accountId) {
        this.userService.sendCheckCode(accountId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 注册用户
     * @return
     */
   @PostMapping("/register")
   public ResponseEntity<Integer> register(@RequestBody RegisterDTO registerDTO) {
       Integer status =  this.userService.register(registerDTO);
        if (status==0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(status);
        }
       return ResponseEntity.badRequest().body(status);
   }


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
        UserDTO userRespDTO = new UserDTO();
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
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);

        LoginRespDTO loginRespDTO = LoginRespDTO.builder()
                .user(userDTO)
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
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        User user = this.userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return ResponseEntity.ok(userDTO);
    }


    /**
     * 根据token获取用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public ResponseEntity<UserDTO> getUserInfo(HttpServletRequest request) {
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
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return ResponseEntity.ok(userDTO);
    }

}
