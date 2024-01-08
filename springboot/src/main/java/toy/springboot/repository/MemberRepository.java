package toy.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.springboot.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
