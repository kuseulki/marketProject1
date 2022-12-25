package com.market2.controller.api;

import com.market2.config.auth.PrincipalDetails;
import com.market2.dto.CommentDto;
import com.market2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class  CommentApiController {

    private final CommentService commentService;

    /** 댓글 작성 **/
    @PostMapping("/board/{board_id}/comment")
    public ResponseEntity save(@PathVariable Long board_id, @RequestBody CommentDto.RequestDto requestDto,
                               @AuthenticationPrincipal PrincipalDetails user) {
        Long member_id = user.getMember().getId();
        commentService.save(board_id, member_id, requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    /** 댓글 조회 **/
    @GetMapping("/board/{board_id}/comment")
    public List<CommentDto.ResponseDto> read(@PathVariable Long board_id) {
        return commentService.findAllByBoard(board_id);
    }


    /** 댓글 삭제 **/
    @DeleteMapping("/board/{board_id}/comment/{comment_id}")
    public ResponseEntity delete(@PathVariable("comment_id") Long comment_id){
        commentService.delete(comment_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}