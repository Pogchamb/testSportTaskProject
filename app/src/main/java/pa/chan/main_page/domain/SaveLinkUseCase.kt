package pa.chan.main_page.domain

import javax.inject.Inject

class SaveLinkUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(url: String) {
        mainRepository.saveLink(url)
    }
}