package pa.chan.main_page.domain

import pa.chan.main_page.domain.model.TrainModel

interface MainRepository {
    fun getWeekTrain(): List<TrainModel>
}