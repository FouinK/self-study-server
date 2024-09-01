package self.study.sels.model.answer

import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository : JpaRepository<Answer, Int> {
    fun findAllByIdIn(idList: List<Int>): List<Answer>
}
