package piulimk.prezinip.main_page.domain

import javax.inject.Inject

class CheckLInkUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(): Boolean? {
        return mainRepository.checkLink()
    }
}