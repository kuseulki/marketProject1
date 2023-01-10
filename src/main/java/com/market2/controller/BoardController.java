package com.market2.controller;

import com.market2.config.auth.PrincipalDetails;
import com.market2.domain.Item;
import com.market2.dto.BoardDto;
import com.market2.dto.CommentDto;
import com.market2.service.BoardService;
import com.market2.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    //글 작성
    @GetMapping("/board/write")
    public String writeForm() {
        return "board/writeform";
    }

    /** 글 저장 **/
    @PostMapping("/board/writepro")
    public String save(@Validated @ModelAttribute BoardDto.RequestDto boardDto,
                       @AuthenticationPrincipal PrincipalDetails user) throws IOException {

        Long memberId = user.getMember().getId();

        // 글 저장
        Long saveId = boardService.write(boardDto, memberId);

        return "redirect:/board/list";
    }


    /**  4. 글 목록 - 리스트 - 검색 추가  */
    @GetMapping("/board/list")
    public String boardList(@PageableDefault(size = 5,  sort = "id", direction = Sort.Direction.DESC)
                                    Pageable pageable, String searchKeyword, Model model) {

        Page<BoardDto.ResponsePageDto> list = null;

        if (searchKeyword == null) {
            list = boardService.boardList(pageable);
        } else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }
        model.addAttribute("list", list);
        return "board/list";
    }

    /**     상세페이지   -  특정 글 보기  */
    @GetMapping("/board/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {

        BoardDto.ResponseDto boardResponseDto = boardService.getById(id);
        model.addAttribute("board", boardResponseDto);

        /* 댓글 DTO 반환 */
        List<CommentDto.ResponseDto> commentListDto = commentService.findAllByBoard(id);
        model.addAttribute("commentList", commentListDto);
//        return "board/11";
        return "board/view";
    }

    /**     수정     */
    @GetMapping("/board/modify/{id}")
    public String update(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetails user, Model model){

        BoardDto.ResponseDto board = boardService.getById(id);
        String boardWriter = board.getWriter();

        if(boardWriter.equals(user.getMember().getUserName())) {
            model.addAttribute("board", board);
            return "board/modify";
        } else {
            return "redirect:/board/list";
        }
    }

    /** update **/
    @PostMapping("/board/update/{id}")
    public String updateForm(@PathVariable Long id, BoardDto.RequestDto requestDto,
                                 @AuthenticationPrincipal PrincipalDetails user) throws IOException{

        Long member_id = user.getMember().getId();
        boardService.update(requestDto, member_id, id);
        return "redirect:/board/list";
    }

    /**     글 삭제    */
    @GetMapping("/board/delete")
    public String delete(Long id) {
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }
}
