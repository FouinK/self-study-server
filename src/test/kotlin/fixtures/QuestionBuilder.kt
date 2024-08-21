package fixtures

import self.study.sels.model.book.Book
import self.study.sels.model.question.Question

class QuestionBuilder(
    val memberId: Int,
    val book: Book,
    val question: String,
) {
    fun build() =
        Question(
            memberId = this.memberId,
            book = this.book,
            question = this.question,
        )
}
