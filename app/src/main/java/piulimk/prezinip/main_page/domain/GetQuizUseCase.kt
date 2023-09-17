package piulimk.prezinip.main_page.domain

import android.content.Context
import piulimk.prezinip.main_page.domain.model.QuestionModel
import javax.inject.Inject

class GetQuizUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    operator fun invoke(context: Context): List<QuestionModel>? {
        return mainRepository.getQuiz(context)
    }
}