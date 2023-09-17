package piulimk.prezinip.main_page.presentation

import android.content.Context
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import piulimk.prezinip.R
import piulimk.prezinip.databinding.ActivityMainBinding

fun ActivityMainBinding.refreshGame(score: Int, context: Context) {
    this.progressBar.visibility = View.GONE
    this.startLayout.visibility = View.VISIBLE
    this.ErrorField.visibility = View.GONE
    this.webView.visibility = View.GONE
    this.score.text = score.toString()
    this.textRules.text = context.getString(R.string.rules)
    this.nextAnswer.text = context.getString(R.string.start)
    this.quizLayout.visibility = View.GONE
}

fun ActivityMainBinding.startGame(score: Int) {
    this.progressBar.visibility = View.GONE
    this.startLayout.visibility = View.GONE
    this.ErrorField.visibility = View.GONE
    this.webView.visibility = View.GONE
    this.score.text = score.toString()
    this.quizLayout.visibility = View.VISIBLE
}

fun ActivityMainBinding.nextQuestion(
    rightAnswer: String?,
    score: Int,
    answer: String,
    context: Context
) {
    this.progressBar.visibility = View.GONE
    this.startLayout.visibility = View.VISIBLE
    this.ErrorField.visibility = View.GONE
    this.webView.visibility = View.GONE
    this.quizLayout.visibility = View.GONE
    if (rightAnswer == answer) {
        this.textRules.text =
            String.format(context.getString(R.string.rightAnswerStr), rightAnswer, answer)
    } else {
        this.textRules.text =
            String.format(context.getString(R.string.rightAnswerStr), rightAnswer, answer)
    }

    this.score.text = score.toString()
    this.nextAnswer.text = "Next"
}

fun ActivityMainBinding.showWebView() {
    this.progressBar.visibility = View.GONE
    this.ErrorField.visibility = View.GONE
    this.webView.visibility = View.VISIBLE
    this.quizLayout.visibility = View.GONE
    this.startLayout.visibility = View.GONE
}

fun ActivityMainBinding.showError(context: Context) {
    this.progressBar.visibility = View.GONE
    this.ErrorField.visibility = View.VISIBLE
    this.mainLayout.background = context.getDrawable(R.color.white)
    this.webView.visibility = View.GONE
    this.quizLayout.visibility = View.GONE
    this.startLayout.visibility = View.GONE
}

fun ActivityMainBinding.goneAll() {
    this.progressBar.visibility = View.GONE
    this.ErrorField.visibility = View.GONE
    this.webView.visibility = View.GONE
    this.quizLayout.visibility = View.GONE
}






