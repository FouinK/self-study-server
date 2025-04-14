package self.study.sels.service

import org.springframework.stereotype.Service

data class BookcasePOJO(
    val name: String = "한국사"
)

data class BookPOJO(
    val name: String = "조선시대 (연습문제)"
)

data class AnswerPOJO(
    val answer: String,
    val correctYn: Boolean = false,
)

data class QuestionPOJO(
    val question: String,
    val answerPOJOs: List<AnswerPOJO>
)

@Service
class NewbieJoinService {
    fun createNewbieQuestionPOJOs(): List<QuestionPOJO> {
        return listOf(
            QuestionPOJO(
                question = "조선을 건국한 인물은 누구인가요?",
                answerPOJOs = answersWithCorrect(
                    correct = "이성계",
                    wrongs = listOf("정도전", "세종대왕", "이순신", "연산군"),
                ),
            ),
            QuestionPOJO(
                question = "훈민정음을 창제한 왕은?",
                answerPOJOs = answersWithCorrect(
                    correct = "세종대왕",
                    wrongs = listOf("세조", "성종", "광해군", "태종"),
                ),
            ),
            QuestionPOJO(
                question = "거북선과 함께 임진왜란에서 승리한 장군은?",
                answersWithCorrect(
                    correct = "이순신",
                    wrongs = listOf("권율", "원균", "김시민", "정도전"),
                ),
            ),
        )
    }

    private fun answersWithCorrect(correct: String, wrongs: List<String>): List<AnswerPOJO> {
        return listOf(AnswerPOJO(correct, correctYn = true)) + wrongs.map { AnswerPOJO(it) }
    }
}
