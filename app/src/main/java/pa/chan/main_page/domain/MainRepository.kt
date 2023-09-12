package pa.chan.main_page.domain

import pa.chan.main_page.domain.model.TrainModel

interface MainRepository {
    fun getWeekTrain(): List<TrainModel>
    suspend fun saveLink(url: String)
    suspend fun checkLink(): String?
    suspend fun getLink(): String
}