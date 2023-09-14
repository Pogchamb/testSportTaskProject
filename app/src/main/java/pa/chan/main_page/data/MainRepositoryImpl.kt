package pa.chan.main_page.data

import android.content.Context
import pa.chan.main_page.data.userException.ConnectionException
import pa.chan.main_page.domain.MainRepository
import pa.chan.main_page.domain.model.QuestionModel
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val questionDataSource: QuestionDataSource,
    private val prefDataSource: PrefDataSource,
    private val remoteConfigDataSource: RemoteConfigDataSource
) : MainRepository {
    override fun getQuiz(context: Context): List<QuestionModel>? {
        val quiz = questionDataSource.getQuizList(context)?.questions
        return quiz
    }

    override suspend fun saveLink(url: String) {
        prefDataSource.setLink(url)
    }

    override suspend fun checkLink(): String? {
        return prefDataSource.getLink()
    }

    override suspend fun getLink(): String {
        try {
            return remoteConfigDataSource.getUrl()
        } catch (e: Exception) {
            throw ConnectionException
        }
    }

}