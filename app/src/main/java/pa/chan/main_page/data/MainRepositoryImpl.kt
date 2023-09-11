package pa.chan.main_page.data

import pa.chan.main_page.data.userException.ConnectionException
import pa.chan.main_page.domain.MainRepository
import pa.chan.main_page.domain.model.TrainModel
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val trainingDataSource: TrainingDataSource,
    private val prefDataSource: PrefDataSource,
    private val remoteConfigDataSource: RemoteConfigDataSource
) : MainRepository {
    override fun getWeekTrain(): List<TrainModel> {
        return trainingDataSource.getWeekListOfTrain()
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