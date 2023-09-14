package pa.chan.main_page.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pa.chan.main_page.domain.*
import pa.chan.main_page.domain.model.QuestionModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getQuizUseCase: GetQuizUseCase,
    private val saveLinkUseCase: SaveLinkUseCase,
    private val checkLInkUseCase: CheckLInkUseCase,
    private val getUrlUseCase: GetUrlUseCase
) : ViewModel() {
    private val _quizLiveData: MutableLiveData<List<QuestionModel?>?> = MutableLiveData()
    val quizLiveData: LiveData<List<QuestionModel?>?>
        get() = _quizLiveData

    private val _hasLinkLiveData: MutableLiveData<Boolean?> = MutableLiveData()
    val hasLinkLiveData: LiveData<Boolean?>
        get() = _hasLinkLiveData

    private val _linkLiveData: MutableLiveData<String?> = MutableLiveData()
    val linkLiveData: LiveData<String?>
        get() = _linkLiveData

    private val _questionLiveData: MutableLiveData<QuestionModel?> = MutableLiveData()
    val questionLiveData: LiveData<QuestionModel?>
        get() = _questionLiveData

    fun getUrl() {
        viewModelScope.launch {
            _linkLiveData.postValue(getUrlUseCase())
        }
    }

    fun nextQuestion() {
        val randomInt = (0..48).random()
        val question = _quizLiveData.value?.get(randomInt)
        _questionLiveData.postValue(question)
    }

    fun startStub(context: Context) {
        _quizLiveData.postValue(getQuizUseCase(context))
    }

    fun hasLink() {
        viewModelScope.launch {
            _hasLinkLiveData.postValue(checkLInkUseCase())
        }
    }

    fun saveLink(url: String) {
        viewModelScope.launch {
            saveLinkUseCase(url)
        }
    }

}