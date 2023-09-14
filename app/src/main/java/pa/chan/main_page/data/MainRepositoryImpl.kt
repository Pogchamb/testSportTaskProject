package pa.chan.main_page.data

import android.content.Context
import pa.chan.main_page.domain.MainRepository
import pa.chan.main_page.domain.model.QuestionModel
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val questionDataSource: QuestionDataSource,
    private val prefDataSource: PrefDataSource,
) : MainRepository {
    override fun getQuiz(context: Context): List<QuestionModel>? {
        val quiz = questionDataSource.getQuizList(context)?.questions
        return quiz
    }

    override suspend fun saveLink(url: String) {
        prefDataSource.setLink(url)
    }

    override suspend fun checkLink(): Boolean? {
        return prefDataSource.getLink()?.isNotEmpty()
    }

    override suspend fun getLink(): String? {
        return prefDataSource.getLink()
    }

}