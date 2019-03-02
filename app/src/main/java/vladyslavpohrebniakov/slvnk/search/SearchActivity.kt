package vladyslavpohrebniakov.slvnk.search

import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity
import vladyslavpohrebniakov.slvnk.AppConstants
import vladyslavpohrebniakov.slvnk.AppSharedPrefs
import vladyslavpohrebniakov.slvnk.GlideApp
import vladyslavpohrebniakov.slvnk.R
import vladyslavpohrebniakov.slvnk.about.AboutActivity
import vladyslavpohrebniakov.slvnk.interpretation.InterpretationActivity
import vladyslavpohrebniakov.slvnk.settings.AppSettings
import vladyslavpohrebniakov.slvnk.settings.SettingsActivity
import java.util.*


class SearchActivity : AppCompatActivity(), View.OnClickListener, SearchView {

	private lateinit var random: Random
	private lateinit var presenter: SearchPresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		AppSettings.setTheme(this, true)
		setContentView(R.layout.activity_search)

		random = Random()
		presenter = SearchPresenter(this)

		searchEditText.setOnEditorActionListener { _, actionId, _ ->
			var handled = false
			if (actionId == EditorInfo.IME_ACTION_GO) {
				presenter.searchButtonClicked(searchEditText.text.toString())
				handled = true
			}
			handled
		}

		searchBtn.setOnClickListener(this)
		helpBtn.setOnClickListener(this)
		settingsBtn.setOnClickListener(this)

		presenter.loadNewBackground()
	}

	public override fun onResume() {
		super.onResume()
		presenter.appLaunched(AppSharedPrefs.loadFirstRunSharedPrefs(this))
	}

	public override fun onStart() {
		super.onStart()
		presenter.clearInputField()
	}

	override fun onClick(v: View) {
		when (v.id) {
			R.id.searchBtn -> presenter.searchButtonClicked(searchEditText.text.toString())
			R.id.helpBtn -> presenter.helpButtonClicked()
			R.id.settingsBtn -> presenter.settingButtonClicked()
		}
	}

	override fun showErrorMessage() {
		searchEditText.error = getString(R.string.no_word_to_search)
	}

	override fun showNewBackground() {
		val size = Point()
		windowManager.defaultDisplay.getRealSize(size)
		val source = "https://source.unsplash.com/weekly/?nature/${size.x}x${size.y}"

		GlideApp.with(this)
				.load(source)
				.centerCrop()
				.encodeFormat(Bitmap.CompressFormat.WEBP)
				.transition(withCrossFade())
				.into(backgroundImageView)
	}

	override fun showDesriptionDialog() {
		AlertDialog.Builder(this)
				.setTitle(R.string.what_is_it_title)
				.setMessage(getString(R.string.what_is_it, getString(R.string.app_name)))
				.setPositiveButton(android.R.string.ok, null)
				.setNeutralButton(R.string.learn_more) { _, _ -> startActivity<AboutActivity>() }
				.show()
	}

	override fun clearInputField() {
		searchEditText.text?.clear()
		searchEditText.error = null
	}

	override fun navigateToInterpretationActivity(text: String) {
		startActivity<InterpretationActivity>(
				AppConstants.WORD_EXTRA_KEY to text.trim(),
				AppConstants.CALLED_SAME_ACTIVITY_EXTRA_KEY to false)
	}

	override fun navigateToSettigsActivity() {
		startActivity<SettingsActivity>()
	}

	override fun editFirtsLaunchSharedPref(value: Boolean) {
		AppSharedPrefs.editFirstLaunchSharedPrefs(this, value)
	}
}