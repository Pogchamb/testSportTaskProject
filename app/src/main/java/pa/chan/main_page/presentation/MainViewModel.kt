package pa.chan.main_page.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pa.chan.main_page.data.userException.ConnectionException
import pa.chan.main_page.data.userException.UserException
import pa.chan.main_page.domain.CheckLInkUseCase
import pa.chan.main_page.domain.GetTrainingProgramUseCase
import pa.chan.main_page.domain.GetUrlUseCase
import pa.chan.main_page.domain.SaveLinkUseCase
import pa.chan.main_page.domain.model.TrainModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTrainingProgramUseCase: GetTrainingProgramUseCase,
    private val saveLinkUseCase: SaveLinkUseCase,
    private val checkLInkUseCase: CheckLInkUseCase,
    private val getUrlUseCase: GetUrlUseCase
) : ViewModel() {
    private val _trainLiveData: MutableLiveData<List<TrainModel?>> = MutableLiveData()
    val trainLiveData: LiveData<List<TrainModel?>>
        get() = _trainLiveData

    private val _hasLinkLiveData: MutableLiveData<String?> = MutableLiveData()
    val hasLinkLiveData: LiveData<String?>
        get() = _hasLinkLiveData

    private val _linkLiveData: MutableLiveData<String?> = MutableLiveData()
    val linkLiveData: LiveData<String?>
        get() = _linkLiveData

    private val _errorLiveData: MutableLiveData<UserException> = MutableLiveData()
    val errorLiveData: LiveData<UserException>
        get() = _errorLiveData

    fun getUrl() {
        viewModelScope.launch {
            try {
                _linkLiveData.postValue(getUrlUseCase())
            } catch (e: ConnectionException) {
                _errorLiveData.postValue(e)
            }
        }
    }

    fun startStub() {
        _trainLiveData.postValue(getTrainingProgramUseCase())
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