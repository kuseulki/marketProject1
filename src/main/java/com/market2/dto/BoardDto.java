package com.market2.dto;

import com.market2.domain.Board;
import com.market2.domain.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BoardDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Setter
    public static class RequestDto {
        private Long id;
        private String title;
        private String content;
        private String fileName;
        private MultipartFile file;
        private int viewCount;
        private Member member;
        private Long member_id;

        /* Dto -> Entity */
        public Board toEntity(Member member) {
            Board board = Board.builder()
                    .id(id)
                    .title(title)
                    .content(content)
                    .fileName(fileName)
                    .viewCount(0)
                    .member(member)
                    .build();
            return board;
        }

        /* 서버가 관리하는 파일명 추가 */
        public void addFileName(String storeFileName) {
            this.fileName = storeFileName;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDto {
        private Long id;
        private Long member_id;
        private String title;
        private String content;
        private String writer;
        private String userId;
        private String fileName;
        private int viewCount;
        private int likeCount;
        private String createdDate, updatedDate;
        private List<CommentDto.ResponseDto> comment;


        /* Entity -> Dto */
        public ResponseDto(Board board) {
            this.id = board.getId();
            this.member_id = board.getMember().getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.writer = board.getMember().getUserName();
            this.userId = board.getMember().getUserId();
            this.fileName = board.getFileName();
            this.viewCount = board.getViewCount();
            this.createdDate = board.getCreatedDate();
            this.updatedDate = board.getUpdatedDate();
        }
    }

    /* 페이징 객체  리스트*/
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponsePageDto {
        private Long id;
        private Long member_id;
        private String title;
        private String writer;
        private int viewCount;
        private String createdDate;

        /* Entity -> Dto */
        public ResponsePageDto(Board board) {
            this.id = board.getId();
            this.member_id = board.getMember().getId();
            this.title = board.getTitle();
            this.writer = board.getMember().getUserName();
            this.viewCount = board.getViewCount();
            this.createdDate = board.getCreatedDate();
        }
    }
}

