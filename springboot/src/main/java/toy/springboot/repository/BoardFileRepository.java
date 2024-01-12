package toy.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.springboot.entity.BoardFileEntity;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {
}
