package com.market2.domain;

import com.market2.enums.Role;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity @Builder
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;      // 아이디

    @Column // (nullable = false, length = 100)
    private String password;    // 비밀번호

    @Column(nullable = false)
    private String userName;      // 이름

    @Column(nullable = false)
    private String email;           // 이메일

    @Column(length = 11)
    private Integer phone;        //휴대폰

    @Enumerated(EnumType.STRING)
    private Role role;          // USER, ADMIN

    private String address;      // 주소

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id asc")
    private List<Board> board;

//    public void update(String address){
////        this.phone = phone;
//        this.address = address;
//    }
}
