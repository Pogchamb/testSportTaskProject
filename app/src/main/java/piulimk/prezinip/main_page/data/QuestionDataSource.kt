package piulimk.prezinip.main_page.data

import android.content.Context
import com.google.gson.Gson
import piulimk.prezinip.main_page.domain.model.QuizModel
import java.io.IOException
import javax.inject.Inject

class QuestionDataSource @Inject constructor() {

    fun getQuizList(context: Context): QuizModel? {
        val gson = Gson()
        val json = getJsonDataFromAsset(context, "quiz.json")
        val quiz = gson.fromJson(json, QuizModel::class.java)
        return quiz
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}