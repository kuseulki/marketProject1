package com.market2.dto;

import com.market2.domain.Board;
import com.market2.domain.Comment;
import com.market2.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RequestDto{

        private Long id;
        private String content;
        private String createdDate, updatedDate;
        private int count;  // 댓글 갯수

        private Board board;
        private Member member;

        public void setBoard(Board board){
            this.board = board;
        }

        public void setMember(Member member){
            this.member = member;
        }

        /* Dto -> Entity */
        public Comment toEntity(Member member, Board board) {
            Comment comment = Comment.builder()
                    .id(id)
                    .content(content)
                    .count(count)
                    .member(member)
                    .board(board)
                    .build();

            return comment;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDto{
        private Long id;
        private String content;
        private String userId;
        private String createdDate, updatedDate;
        private String writer;
        private int count;

        private Long memberId;
        private Long boardId;

        /* Entity -> Dto */
        public ResponseDto(Comment comment){
            this.id = comment.getId();
            this.content = comment.getContent();
            this.count = comment.getCount();

            this.userId = comment.getMember().getUserId();
            this.writer = comment.getMember().getUserName();

            this.createdDate = comment.getCreatedDate();
            this.updatedDate = comment.getUpdatedDate();

            this.boardId = comment.getBoard().getId();
            this.memberId = comment.getMember().getId();
        }

    }
}
