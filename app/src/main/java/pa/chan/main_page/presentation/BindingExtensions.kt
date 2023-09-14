package pa.chan.main_page.presentation

import android.content.Context
import android.view.View
import pa.chan.R
import pa.chan.databinding.ActivityMainBinding

fun ActivityMainBinding.refreshGame(score: Int, context: Context) {
    this.startLayout.visibility = View.VISIBLE
    this.ErrorField.visibility = View.GONE
    this.webView.visibility = View.GONE
    this.score.text = score.toString()
    this.textRules.text = context.getString(R.string.rules)
    this.nextAnswer.text = context.getString(R.string.start)
    this.quizLayout.visibility = View.GONE
}

fun ActivityMainBinding.startGame(score: Int) {
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
    this.ErrorField.visibility = View.GONE
    this.webView.visibility = View.VISIBLE
    this.quizLayout.visibility = View.GONE
    this.startLayout.visibility = View.GONE
}

fun ActivityMainBinding.showError() {
    this.ErrorField.visibility = View.VISIBLE
    this.webView.visibility = View.GONE
    this.quizLayout.visibility = View.GONE
    this.startLayout.visibility = View.GONE
}

fun ActivityMainBinding.goneAll() {
    this.ErrorField.visibility = View.GONE
    this.webView.visibility = View.GONE
    this.quizLayout.visibility = View.GONE
}






