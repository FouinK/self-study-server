package self.study.sels.model.book

import com.querydsl.core.Tuple
import com.querydsl.jpa.impl.JPAQueryFactory
import self.study.sels.controller.dto.GetBookResponseDto
import self.study.sels.controller.dto.GetBookResponseDto.QuestionItem
import self.study.sels.model.book.QBook.book
import self.study.sels.model.question.QQuestion.question1

class BookRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : BookRepositoryCustom {
    override fun findByBookIdAndMemberId(
        bookId: Int,
        memberId: Int
    ): GetBookResponseDto? {
        val results = queryFactory
            .select(book.id, book.name, question1.id, question1.question)
            .from(book)
            .leftJoin(question1).on(question1.bookId.eq(book.id))
            .where(book.id.eq(bookId).and(book.memberId.eq(memberId)))
            .fetch()

        return if (results.isNotEmpty()) {
            GetBookResponseDto(
                bookId = results[0].get(book.id)!!,
                bookName = results[0].get(book.name)!!,
                questionList = results.mapNotNull { tuple: Tuple ->
                    val questionId = tuple.get(question1.id)!!
                    val questionTitle = tuple.get(question1.question)!!
                    QuestionItem(
                        questionId = questionId,
                        question = questionTitle,
                    )
                },
            )
        } else {
            return null
        }
    }
}
