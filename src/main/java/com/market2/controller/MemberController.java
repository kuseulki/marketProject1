package com.market2.controller;

import com.market2.config.auth.PrincipalDetails;
import com.market2.dto.MemberDto;
import com.market2.service.BoardService;
import com.market2.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final AuthenticationManager authenticationManager;

    // 로그인
    @GetMapping("/login")
    public String loginForm() {
        return "members/login";
    }

    // 로그인 실패 시
    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 학인해 주세요");
        return "members/login";
    }

    // 회원가입
    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("memberDto", new MemberDto.RequestDto());
        return "members/join";
    }

    @PostMapping("/join")
    public String login(@Validated @ModelAttribute("memberDto") MemberDto.RequestDto memberDto,
                        BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.info("회원 가입 오류 발생");
            return "members/join";
        }
        memberService.saveMember(memberDto);
        return "redirect:/login";
    }

    // 작성글 보기
    @GetMapping("/mypage/myBoard")
    public String index(Model model, @AuthenticationPrincipal PrincipalDetails principalDetail,
                        @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable) {

        model.addAttribute("user", principalDetail.getMember());
        model.addAttribute("boards", boardService.myBoardAll(principalDetail.getMember(), pageable));
        return "members/myBoard";
    }
}
