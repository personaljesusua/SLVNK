package vladyslavpohrebniakov.slvnk.interpretation

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import vladyslavpohrebniakov.slvnk.AppConstants
import java.net.URLEncoder

class WordArticle {
	val LOG_TAG = WordArticle::class.java.simpleName

	private var article: String? = null
	private var source: String? = null
	private var url: String? = null

	private var doc: Document? = null
	private var articleBody: Elements? = null
	private var sourceInSUM: Element? = null

	var word: String? = null

	fun buildUrl(word: String) {
		if (this.word == null) {
			this.word = word
			url = AppConstants.SUM_WEBSITE +
					AppConstants.SUM_SEARCH_WORD +
					URLEncoder.encode(word, "utf-8")
		}
	}

	fun getWordArticle(): String? {
		return if (this.article == null) {
			Log.v(LOG_TAG, "Getting article")
			doc = getDoc()
			return if (doc != null) {
				articleBody = doc!!.select("div[itemprop]")
				val articleStrBuilder = StringBuilder()
				return if (articleBody != null) {
					for (e in articleBody!!) {
						articleStrBuilder.append(e.html())
					}
					article = articleStrBuilder.toString()
							.replace("<abbr([\\w\\W]+?)>".toRegex(), "<i>")
							.replace("</abbr>".toRegex(), "</i>")
							.replace("<span class=\"tinok\">//</span>".toRegex(), "▪")
							.replace("<span class=\"s\">".toRegex(), "<font color=\"grey\">")
							.replace("</span>".toRegex(), "</font>")
							.trim()
					article
				} else {
					null
				}
			} else {
				null
			}
		} else {
			article
		}
	}

	fun getArticleSource(): String? {
		return if (source == null) {
			Log.v(LOG_TAG, "Getting source")
			doc = getDoc()
			return if (doc != null) {
				sourceInSUM = doc!!.select("p.tom").first()
				source = sourceInSUM!!.html()
				source
			} else {
				null
			}
		} else {
			source
		}
	}

	private fun getDoc(): Document? {
		return if (url != null && doc == null) {
			Log.v(WordArticle::class.java.simpleName, "Jsoup connecting to $url")
			Jsoup.connect(url).get()
		} else {
			doc
		}
	}
}