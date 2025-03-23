package self.study.sels.model.questionresulthistory

import org.springframework.data.jpa.repository.JpaRepository

interface QuestionResultHistoryRepository : JpaRepository<QuestionResultHistory, Int> {
    fun findByQuestionId(questionId: Int): QuestionResultHistory?
}
