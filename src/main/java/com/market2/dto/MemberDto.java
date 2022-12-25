package com.market2.dto;

import com.market2.domain.Member;
import com.market2.enums.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RequestDto {

        private Long id;

        @NotBlank(message = "아이디는 필수 입력값입니다.")
        @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~10자리여야 합니다.")
        private String userId;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
        private String password;

        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "이름은 특수문자를 포함하지 않은 2~10자리여야 합니다.")
        private String userName;

        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        private Integer phone;

        private String address;
        private Role role;

        // 암호화된 password
        public void encryptPassword(String BCryptpassword) {
            this.password = BCryptpassword;
        }


        // Dto -> Entity
        public Member toEntity() {
            Member member = Member.builder()
                    .id(id)
                    .userId(userId)
                    .password(password)
                    .userName(userName)
                    .email(email)
                    .phone(phone)
                    .address(address)
                    .role(Role.ADMIN)
                    .build();
            return member;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDto {
        private Long id;
        private String userId;
        private String userName;
        private String email;
        private Integer phone;
        private String address;
        private Role role;
        private String updatedDate;

        /* Entity -> DTO */
        public ResponseDto(Member member) {
            this.id = member.getId();
            this.userId = member.getUserId();
            this.userName = member.getUserName();
            this.email = member.getEmail();
            this.phone = member.getPhone();
            this.address = member.getAddress();
            this.role = member.getRole();
            this.updatedDate = member.getUpdatedDate();
        }
    }
}