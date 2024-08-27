package self.study.sels.model.question

import com.querydsl.jpa.impl.JPAQueryFactory

class QuestionRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : QuestionRepositoryCustom
