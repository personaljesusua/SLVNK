package vladyslavpohrebniakov.slvnk

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object AppSharedPrefs {

	private const val PREF_FIRST_RUN_KEY = "${BuildConfig.APPLICATION_ID} pref_first_run_key"
	private const val PREF_FIRST_RUN = "${BuildConfig.APPLICATION_ID} pref_first_run"
	private var sharedPrefs: SharedPreferences? = null

	fun loadFirstRunSharedPrefs(context: Context): Boolean =
			sharedPreferences(context).getBoolean(PREF_FIRST_RUN_KEY, true)

	fun editFirstLaunchSharedPrefs(context: Context, value: Boolean) =
			sharedPreferences(context).edit().putBoolean(PREF_FIRST_RUN_KEY, value).apply()

	private fun sharedPreferences(context: Context): SharedPreferences {
		if (sharedPrefs == null)
			sharedPrefs = context.applicationContext.getSharedPreferences(PREF_FIRST_RUN, MODE_PRIVATE)
		return sharedPrefs!!
	}
}