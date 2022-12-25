package com.market2.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;   // 제목

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 내용

    @Column
    private String fileName;    // 파일 이름

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int viewCount;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id desc")
    private List<Comment> comment;

    /** 내용 수정 업데이트 */
    public void update(String title, String content){
        this.title = title;
        this.content = content;
        this.fileName = fileName;
    }

    public void count(){
        this.viewCount++;
    }

}