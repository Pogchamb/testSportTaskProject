package pa.chan.main_page.data

import pa.chan.R
import pa.chan.main_page.domain.model.TrainModel

class TrainingDataSource {

    fun getWeekListOfTrain(): List<TrainModel> {
        return listOf(
            TrainModel(
                id = 1,
                warmUp = R.string.firstDay_warm_up,
                technic = R.string.firstDay_technic,
                efficiency = R.string.firstDay_efficiency,
                hitch = R.string.firstDay_hitch
            ),
            TrainModel(
                id = 2,
                warmUp = R.string.firstDay_warm_up,
                technic = R.string.firstDay_technic,
                efficiency = R.string.firstDay_efficiency,
                hitch = R.string.firstDay_hitch
            ),
            TrainModel(
                id = 3,
                warmUp = R.string.firstDay_warm_up,
                technic = R.string.firstDay_technic,
                efficiency = R.string.firstDay_efficiency,
                hitch = R.string.firstDay_hitch
            ),
            TrainModel(
                id = 4,
                warmUp = R.string.firstDay_warm_up,
                technic = R.string.firstDay_technic,
                efficiency = R.string.firstDay_efficiency,
                hitch = R.string.firstDay_hitch
            ),
            TrainModel(
                id = 5,
                warmUp = R.string.firstDay_warm_up,
                technic = R.string.firstDay_technic,
                efficiency = R.string.firstDay_efficiency,
                hitch = R.string.firstDay_hitch
            )

        )
    }
}