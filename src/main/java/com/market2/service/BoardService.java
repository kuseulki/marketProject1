package com.market2.service;

import com.market2.domain.Board;
import com.market2.domain.Member;
import com.market2.dto.BoardDto;
import com.market2.file.FileStore;
import com.market2.file.UploadFile;
import com.market2.repository.BoardRepository;
import com.market2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardService {

    private final FileStore fileStore;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    /** create **/
    public Long write(BoardDto.RequestDto requestDto, Long member_id) throws IOException {

        Member member = memberRepository.findById(member_id).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        MultipartFile board_file = requestDto.getFile();
        UploadFile uploadFile = fileStore.storeFile(board_file);

        if(uploadFile == null){
            requestDto.addFileName(null);
        } else {
            /* 파일명 추가 */
            requestDto.addFileName(uploadFile.getStoreFileName());
            /* RequestDto -> Entity */
        }
        Board board = requestDto.toEntity(member);
        return boardRepository.save(board).getId();

    }

    /** update **/
    public void update(BoardDto.RequestDto requestDto,Long member_id ,Long board_id) throws IOException  {

        Member member = memberRepository.findById(member_id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        Board board = boardRepository.findById(board_id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        board.update(requestDto.getTitle(), requestDto.getContent());
    }

    /**   2. 글 목록 - 리스트     - 페이징 처리 추가      */
    @Transactional(readOnly = true)
    public Page<BoardDto.ResponsePageDto> boardList(Pageable pageable){
        Page<Board> page = boardRepository.findAll(pageable);

        Page<BoardDto.ResponsePageDto> boardList = page.map(
                board -> new BoardDto.ResponsePageDto(
                        board.getId(),
                        board.getMember().getId(),
                        board.getTitle(),
                        board.getMember().getUserName(),
                        board.getViewCount(),
                        board.getCreatedDate()
                )
        );
        return boardList;
    }

    /**   3. 글 목록 - 리스트     - 검색 기능 추가      */
    public Page<BoardDto.ResponsePageDto> boardSearchList(String searchKeyword, Pageable pageable){
        Page<Board> page = boardRepository.findByTitleContaining(searchKeyword, pageable);
        Page<BoardDto.ResponsePageDto> boardList = page.map(
                board -> new BoardDto.ResponsePageDto(
                        board.getId(),
                        board.getMember().getId(),
                        board.getTitle(),
                        board.getMember().getUserName(),
                        board.getViewCount(),
                        board.getCreatedDate()
                )
        );
        return boardList;
    }


    @Transactional(readOnly = true)
    public Page<BoardDto.ResponsePageDto> myBoardAll(Member member, Pageable pageable) {

        Page<Board> list = boardRepository.findAllByMember(member, pageable);

        Page<BoardDto.ResponsePageDto> myBoardList = list.map(

                board -> new BoardDto.ResponsePageDto(
                        board.getId(),
                        board.getMember().getId(),
                        board.getTitle(),
                        board.getMember().getUserName(),
                        board.getViewCount(),
                        board.getCreatedDate()
                )
        );
        return myBoardList;
    }


    /** board_id 에 해당하는 게시물 반환 (조회) **/
    public BoardDto.ResponseDto getById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        board.count();
        return new BoardDto.ResponseDto(board);
    }

    /** delete **/
    public void boardDelete(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        boardRepository.delete(board);
    }
}
