package piulimk.prezinip.main_page.domain

import android.content.Context
import piulimk.prezinip.main_page.domain.model.QuestionModel

interface MainRepository {
    fun getQuiz(context: Context): List<QuestionModel>?
    suspend fun saveLink(url: String)
    suspend fun checkLink(): Boolean?
    suspend fun getLink(): String?
}