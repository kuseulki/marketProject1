package com.market2.service;

import com.market2.domain.Member;
import com.market2.dto.MemberDto;
import com.market2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    /**     회원가입    */
    public void saveMember(MemberDto.RequestDto memberDto) {

        memberDto.encryptPassword(encoder.encode(memberDto.getPassword()));
        Member member = memberDto.toEntity();
        memberRepository.save(member);
    }


    @Transactional(readOnly = true)
    public List<Member> allMemberView() {
        return memberRepository.findAll();
    }


    /** member_id로 memberDto 반환 **/
    public MemberDto.ResponseDto getById(Long member_id) {
        Member member = memberRepository.findById(member_id).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        return new MemberDto.ResponseDto(member);
    }
}
