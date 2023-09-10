package pa.chan.main_page.data

import pa.chan.main_page.domain.MainRepository
import pa.chan.main_page.domain.model.TrainModel
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val trainingDataSource: TrainingDataSource
) : MainRepository {


    override fun getWeekTrain(): List<TrainModel> {
        return trainingDataSource.getWeekListOfTrain()
    }
}