package pa.chan.main_page.data

import android.content.SharedPreferences
import javax.inject.Inject

class PrefDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun setLink(url: String) {
        sharedPreferences.edit()
            .putString("url", url)
            .apply()
    }

    fun getLink(): String? {
        return sharedPreferences.getString("url", "")
    }


}