package vladyslavpohrebniakov.slvnk.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils.navigateUpFromSameTask
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.activity_settings.*
import vladyslavpohrebniakov.slvnk.R
import vladyslavpohrebniakov.slvnk.about.AboutActivity

class SettingsActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		AppSettings.setTheme(this, false)
		setContentView(R.layout.activity_settings)
		setSupportActionBar(toolbar)
		supportActionBar!!.setDisplayHomeAsUpEnabled(true)
	}

	override fun onBackPressed() {
		navigateUpFromSameTask(this)
	}


	class MyPreferenceFragment : PreferenceFragmentCompat() {
		override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
			addPreferencesFromResource(R.xml.settings)

			preferenceScreen.findPreference(getString(R.string.key_about_pref))
					.onPreferenceClickListener = Preference.OnPreferenceClickListener {
				startActivity(Intent(context, AboutActivity::class.java))
				true
			}
			preferenceScreen.findPreference(getString(R.string.key_themes_pref))
					.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _: Preference, _: Any ->
				activity?.recreate()
				true
			}
		}
	}
}