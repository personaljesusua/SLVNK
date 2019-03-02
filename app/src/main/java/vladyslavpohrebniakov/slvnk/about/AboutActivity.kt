package vladyslavpohrebniakov.slvnk.about

import android.os.Bundle
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.aboutlibraries.ui.LibsActivity
import vladyslavpohrebniakov.slvnk.R
import vladyslavpohrebniakov.slvnk.settings.AppSettings

class AboutActivity : LibsActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		intent = LibsBuilder()
				.withLicenseShown(true)
				.withLicenseDialog(true)
				.withActivityTitle(getString(R.string.pref_title_about_the_app))
				.withActivityTheme(AppSettings.getThemeAbout(this, false))
				.withAboutIconShown(true)
				.withAboutVersionShown(true)
				.withAboutAppName(getString(R.string.app_name))
				.withAboutDescription(getString(R.string.about_descr, getString(R.string.app_name), getString(R.string.app_name)))
				.intent(this)
		super.onCreate(savedInstanceState)
	}

}