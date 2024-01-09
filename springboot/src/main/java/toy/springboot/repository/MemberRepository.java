package toy.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.springboot.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // 이메일로 회원 정보 조회
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
