package piulimk.prezinip.main_page.domain.model

data class QuestionModel(
    val id: Int,
    val question: String,
    val variants: List<String>,
    val correctAnswer: String,
)