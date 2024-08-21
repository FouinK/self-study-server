package self.study.sels.model.question_answer

import org.springframework.data.jpa.repository.JpaRepository

interface QuestionAnswerRepository : JpaRepository<QuestionAnswer, Int>
