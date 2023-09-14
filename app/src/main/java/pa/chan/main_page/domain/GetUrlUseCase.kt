package pa.chan.main_page.domain

import javax.inject.Inject

class GetUrlUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(): String? {
        return mainRepository.getLink()
    }
}