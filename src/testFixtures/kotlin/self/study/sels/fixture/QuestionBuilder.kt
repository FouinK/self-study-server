package self.study.sels.fixture

import self.study.sels.model.question.Question

class QuestionBuilder(
    val memberId: Int,
    val bookId: Int,
    val question: String,
) {
    fun build() =
        Question(
            memberId = this.memberId,
            bookId = this.bookId,
            question = this.question,
        )
}
