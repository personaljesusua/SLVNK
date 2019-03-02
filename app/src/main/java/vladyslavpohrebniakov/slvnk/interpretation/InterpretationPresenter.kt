package vladyslavpohrebniakov.slvnk.interpretation

import android.util.Log
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class InterpretationPresenter(private var view: InterpretationView?, private val wordArticle: WordArticle) {

    val LOG_TAG = InterpretationPresenter::class.java.simpleName

    fun activityLaunched() {
        wordArticle.buildUrl(view!!.getSearchedWordExtra())
        view!!.setUpViews()
    }

    fun loadArticle() {
        view!!.showSearchedWord(wordArticle.word!!)
        doAsync {
            var article: String? = null
            var sourceLink: String? = null
            try {
                article = wordArticle.getWordArticle()
                sourceLink = wordArticle.getArticleSource()
            } catch (e: Exception) {
                Log.e(InterpretationPresenter::class.java.simpleName, e.message.toString())
            } finally {
                uiThread {
                    view?.hideProgressDialog()
                    if (article != null && article.isNotEmpty()) {
                        view?.showArticle(article)
                    } else {
                        view?.showErrorMessage(wordArticle.word!!)
                    }
                    if (sourceLink != null) view?.showSourceLink(sourceLink)
                }
            }
        }
    }

    fun homeButtonClicked(isSameActivity: Boolean) {
        if (isSameActivity) view!!.finishActivity()
        else view!!.navigateUp()
    }

    fun shareButtonClicked() {
        view!!.shareArticle()
    }

    fun attachNewView(newView: InterpretationView) {
        this.view = newView
    }

    fun detachOldView() {
        this.view = null
    }
}
