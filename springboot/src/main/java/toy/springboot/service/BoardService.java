package toy.springboot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import toy.springboot.dto.BoardDTO;
import toy.springboot.entity.BoardEntity;
import toy.springboot.entity.BoardFileEntity;
import toy.springboot.repository.BoardFileRepository;
import toy.springboot.repository.BoardRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직을 분리
        if (boardDTO.getBoardFile().isEmpty()) {
            // 첨부 파일이 없는 경우
            BoardEntity boardSaveEntity = BoardEntity.toBoardSaveEntity(boardDTO);
            boardRepository.save(boardSaveEntity);
        } else {
            // 첨부 파일이 있는 경우

            // 1. DB(board_table)에 데이터 save 처리
            BoardEntity boardSaveFileEntity = BoardEntity.toBoardSaveFileEntity(boardDTO);
            Long saveId = boardRepository.save(boardSaveFileEntity).getId();
            BoardEntity boardEntity = boardRepository.findById(saveId).get();

            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                // 2. 파일의 이름 가져오기
                String originalFilename = boardFile.getOriginalFilename();
                // 3. 서버 저장용 이름 생성
                String storeFileName = UUID.randomUUID() + "_" + originalFilename;
                // 4. 저장 경로 설정
                String savePath = "C:\\Users\\Seungwon\\Desktop\\springboot_img\\" + storeFileName;
                // 5. 해당 경로에 파일 저장
                boardFile.transferTo(new File(savePath));
                // 7. board_file_table에 해당 데이터 save 처리
                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(boardEntity, originalFilename, storeFileName);
                boardFileRepository.save(boardFileEntity);
            }
        }
    }

    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            return BoardDTO.toBoardDTO(optionalBoardEntity.get());
        } else {
            return null;
        }
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toBoardUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //
        int pageLimit = 3;  // 한 페이지에 보여줄 글 갯수
        // 한 페이지당 3개씩 글을 보여주고 정렬 기준은 id(entity) 기준으로 내림차순 정렬
        Page<BoardEntity> boardEntities
                = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<BoardDTO> boardDTOS = boardEntities
                .map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDTOS;
    }
}
