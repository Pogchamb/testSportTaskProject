package pa.chan.main_page.data

import pa.chan.R
import pa.chan.main_page.domain.model.TrainModel
import javax.inject.Inject

class TrainingDataSource @Inject constructor() {

    fun getWeekListOfTrain(): List<TrainModel> {
        return listOf(
            TrainModel(
                name = R.string.monday,
                warmUp = R.string.monday_warm_up,
                technic = R.string.monday_technic,
                efficiency = R.string.monday_efficiency,
                hitch = R.string.monday_hitch
            ),
            TrainModel(
                name = R.string.tuesday,
                warmUp = R.string.tuesday_warm_up,
                technic = R.string.tuesday_technic,
                efficiency = R.string.tuesday_efficiency,
                hitch = R.string.tuesday_hitch
            ),
            TrainModel(
                name = R.string.wednesday,
                warmUp = R.string.wednesday_warm_up,
                technic = R.string.wednesday_technic,
                efficiency = R.string.wednesday_efficiency,
                hitch = R.string.wednesday_hitch
            ),
            TrainModel(
                name = R.string.thursday,
                warmUp = R.string.thursday_warm_up,
                technic = R.string.thursday_technic,
                efficiency = R.string.thursday_efficiency,
                hitch = R.string.thursday_hitch
            ),
            TrainModel(
                name = R.string.friday,
                warmUp = R.string.friday_warm_up,
                technic = R.string.friday_technic,
                efficiency = R.string.friday_efficiency,
                hitch = R.string.friday_hitch
            )

        )
    }
}