package self.study.sels.model.member

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Int> {
    fun findByAuthToken(authToken: String): Member?
}
