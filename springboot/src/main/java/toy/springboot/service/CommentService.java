package toy.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.springboot.dto.CommentDTO;
import toy.springboot.entity.BoardEntity;
import toy.springboot.entity.CommentEntity;
import toy.springboot.repository.BoardRepository;
import toy.springboot.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        BoardEntity boardEntity = boardRepository.findById(commentDTO.getBoardId()).get();
        CommentEntity saveEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
        return commentRepository.save(saveEntity).getId();
    }

    @Transactional
    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
