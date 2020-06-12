package com.banmingi.communityplus.contentcenter.controller;

import com.banmingi.communityplus.commons.enums.CheckLogin;
import com.banmingi.communityplus.contentcenter.dto.comment.CommentCreateDTO;
import com.banmingi.communityplus.contentcenter.dto.comment.CommentDTO;
import com.banmingi.communityplus.contentcenter.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 半命i 2020/6/11
 * @description
 */
@RestController
@RequestMapping("comment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentController {

    private final CommentService commentService;

    /**
     * 评论.
     * @param commentCreateDTO 实体
     * @return 成功返回201
     */
    @PostMapping("/")
    @CheckLogin
    public ResponseEntity<Void> comment(@RequestBody CommentCreateDTO commentCreateDTO) {
        this.commentService.comment(commentCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 获取评论列表.
     * @param parentId 列表parentId
     * @param type 类型
     * @return 评论列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<CommentDTO>> getCommentList(@RequestParam("parentId") Integer parentId,
                                                           @RequestParam("type") Integer type) {
        List<CommentDTO> commentDTOList = this.commentService.getCommentList(parentId,type);
        return ResponseEntity.ok(commentDTOList);
    }

}
