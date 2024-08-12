package self.study.sels.repository

import org.springframework.data.jpa.repository.JpaRepository
import self.study.sels.model.Member

interface MemberRepository : JpaRepository<Member, Int>
