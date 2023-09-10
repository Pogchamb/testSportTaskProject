package pa.chan.main_page.domain

import pa.chan.main_page.domain.model.TrainModel

class GetTrainingProgramUseCase(
    private val mainRepository: MainRepository
) {
    operator fun invoke(): List<TrainModel> {
        return mainRepository.getWeekTrain()
    }
}