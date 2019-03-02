package vladyslavpohrebniakov.slvnk.settings

import android.content.Context
import android.preference.PreferenceManager.getDefaultSharedPreferences
import vladyslavpohrebniakov.slvnk.R

object AppSettings {

    fun setTheme(context: Context, isNavigationBarTranslucent: Boolean) {
        val darkTheme = getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.key_themes_pref), false)
        if (darkTheme) {
            context.setTheme(
                    if (isNavigationBarTranslucent) R.style.DarkTheme_Search_NoActionBar
                    else R.style.DarkTheme_NoActionBar
            )
        } else {
            context.setTheme(
                    if (isNavigationBarTranslucent) R.style.LightTheme_Search_NoActionBar
                    else R.style.LightTheme_NoActionBar
            )
        }
    }

    fun getThemeAbout(context: Context, actionBar: Boolean): Int {
        val darkTheme = getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.key_themes_pref), false)
        return if (darkTheme) {
            if (actionBar) R.style.DarkAboutTheme else R.style.DarkAboutTheme_NoActionBar
        } else {
            if (actionBar) R.style.LightAboutTheme else R.style.LightAboutTheme_NoActionBar
        }
    }

    fun textSize(context: Context): Float {
        return getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.pref_key_textsize), "16")
                ?.toFloat() ?: 16F
    }

    fun isLinksViaAppItself(context: Context): Boolean {
        return getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.key_links_pref), true)
    }
}