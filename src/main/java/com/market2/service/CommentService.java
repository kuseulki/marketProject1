package com.market2.service;

import com.market2.domain.Board;
import com.market2.domain.Comment;
import com.market2.domain.Member;
import com.market2.dto.CommentDto;
import com.market2.repository.BoardRepository;
import com.market2.repository.CommentRepository;
import com.market2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    /** 댓글 목록 조회 **/
    public List<CommentDto.ResponseDto> findAllByBoard(Long board_id) {
        Board board = boardRepository.findById(board_id).orElseThrow(() ->
                new IllegalArgumentException("댓글 조회 실패 : 해당 게시물이 존재하지 않습니다."));

        List<Comment> comment = board.getComment();
        return comment.stream().map(CommentDto.ResponseDto::new).collect(Collectors.toList());
    }

    /** 댓글 작성 **/
    public Long save(Long board_id, Long member_id, CommentDto.RequestDto requestDto) {

        Member member = memberRepository.findById(member_id).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        Board board = boardRepository.findById(board_id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        Comment comment = requestDto.toEntity(member, board);
        comment.count();
        commentRepository.save(comment);

        log.info("댓글 저장 완료");
        return comment.getId();
    }

    /** 댓글 삭제 **/
    public void delete(Long comment_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        commentRepository.delete(comment);
        log.info("댓글 삭제 완료");
    }
}