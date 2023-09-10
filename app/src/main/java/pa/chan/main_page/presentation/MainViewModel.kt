package pa.chan.main_page.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pa.chan.main_page.domain.GetTrainingProgramUseCase
import pa.chan.main_page.domain.model.TrainModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTrainingProgramUseCase: GetTrainingProgramUseCase
): ViewModel() {
    private val _trainLiveData: MutableLiveData<List<TrainModel?>> = MutableLiveData()
    val trainLiveData: LiveData<List<TrainModel?>>
        get() = _trainLiveData


    fun startStub() {
        _trainLiveData.postValue(getTrainingProgramUseCase())
    }

}