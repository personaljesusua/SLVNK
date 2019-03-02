package vladyslavpohrebniakov.slvnk.interpretation

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.widget.NestedScrollView
import kotlinx.android.synthetic.main.activity_interpretation.*
import kotlinx.android.synthetic.main.content_interpretation.*
import org.jetbrains.anko.*
import org.sufficientlysecure.htmltextview.HtmlTextView
import vladyslavpohrebniakov.slvnk.AppConstants
import vladyslavpohrebniakov.slvnk.R
import vladyslavpohrebniakov.slvnk.settings.AppSettings


class InterpretationActivity : AppCompatActivity(), View.OnClickListener, AnkoLogger, InterpretationView {

	@Suppress("DEPRECATION")
	private lateinit var dialog: android.app.ProgressDialog

	private var presenter: InterpretationPresenter? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		attachPresenter()
		presenter!!.activityLaunched()
		presenter!!.loadArticle()
	}

	private fun attachPresenter() {
		presenter = lastCustomNonConfigurationInstance as InterpretationPresenter?
		if (presenter == null) {
			presenter = InterpretationPresenter(this, WordArticle())
		} else {
			presenter!!.attachNewView(this)
		}
	}

	override fun onRetainCustomNonConfigurationInstance(): Any? {
		return presenter
	}

	override fun onDestroy() {
		presenter!!.detachOldView()
		super.onDestroy()
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		val isSameActivity: Boolean = intent?.extras?.getBoolean(AppConstants.CALLED_SAME_ACTIVITY_EXTRA_KEY)
				?: false
		when (item.itemId) {
			android.R.id.home -> presenter!!.homeButtonClicked(isSameActivity)
		}
		return true
	}

	override fun setUpViews() {
		AppSettings.setTheme(this, false)
		setContentView(R.layout.activity_interpretation)
		setSupportActionBar(toolbar)
		supportActionBar!!.setDisplayHomeAsUpEnabled(true)

		dialog = indeterminateProgressDialog(message = getString(R.string.please_wait)) {
			setCancelable(false)
		}
		dialog.show()

		interpretationTextView.textSize = AppSettings.textSize(this)
		interpretationTextView.setTextIsSelectable(true)

		fab.setOnClickListener(this)
		scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
			if (scrollY > oldScrollY) {
				fab.hide()
			} else {
				fab.show()
			}
		})
	}

	override fun onClick(v: View) {
		when (v.id) {
			R.id.fab -> presenter!!.shareButtonClicked()
		}
	}

	override fun hideProgressDialog() {
		container.visibility = View.VISIBLE
		dialog.dismiss()
	}

	override fun showArticle(article: String) {
		if (!AppSettings.isLinksViaAppItself(this)) interpretationTextView.setHtml(article)
		else overrideLinksHtmlTextView(interpretationTextView, article)
	}

	override fun showSourceLink(source: String) {
		sourceTextView.setHtml(source)
	}

	override fun showErrorMessage(word: String) {
		interpretationCard.visibility = View.GONE
		fab.hide()
		notFoundTextView.text = getString(R.string.not_found, word)
		emptyViewCard.visibility = View.VISIBLE
	}

	override fun showSearchedWord(word: String) {
		wordTextView.text = word
		title = word
	}

	override fun shareArticle() {
		share(interpretationTextView.text.toString())
	}

	override fun navigateUp() {
		NavUtils.navigateUpFromSameTask(this)
	}

	override fun finishActivity() {
		finish()
	}

	override fun getSearchedWordExtra(): String {
		return intent?.extras?.getString(AppConstants.WORD_EXTRA_KEY) ?: ""
	}

	private fun makeLinkClickable(strBuilder: SpannableStringBuilder, span: URLSpan) {
		val start = strBuilder.getSpanStart(span)
		val end = strBuilder.getSpanEnd(span)
		val flags = strBuilder.getSpanFlags(span)

		val wordBuilder = StringBuilder()
		for (i in start..(end - 1)) {
			wordBuilder.append(strBuilder[i])
		}
		val clickedWord = wordBuilder.toString().trim()


		val clickable = object : ClickableSpan() {
			override fun onClick(view: View) {
				info("Clicked url = ${span.url}")
				if (span.url.contains(AppConstants.SUM_LINK_MUST_CONTAIN)) {

					info("Clicked word = $clickedWord")

					try {
						startActivity<InterpretationActivity>(
								AppConstants.WORD_EXTRA_KEY to clickedWord,
								AppConstants.CALLED_SAME_ACTIVITY_EXTRA_KEY to true)
					} catch (e: Exception) {
						error(e.message)
						browse(span.url)
					}
				} else {
					browse(span.url)
				}
			}
		}
		strBuilder.setSpan(clickable, start, end, flags)
		strBuilder.removeSpan(span)
	}

	private fun overrideLinksHtmlTextView(text: HtmlTextView, html: String) {
		@Suppress("DEPRECATION")
		val sequence = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
			Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
		else
			Html.fromHtml(html)


		val strBuilder = SpannableStringBuilder(sequence)
		val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
		for (span in urls) {
			makeLinkClickable(strBuilder, span)
		}
		text.text = strBuilder
		text.movementMethod = LinkMovementMethod.getInstance()
	}
}

