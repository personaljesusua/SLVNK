package vladyslavpohrebniakov.slvnk

import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import vladyslavpohrebniakov.slvnk.interpretation.InterpretationActivity
import vladyslavpohrebniakov.slvnk.interpretation.InterpretationPresenter
import vladyslavpohrebniakov.slvnk.interpretation.WordArticle

class InterpretationActivityTest {
    lateinit var presenter: InterpretationPresenter
    lateinit var wordArticle: WordArticle
    val testWord = "слово"

    @get:Rule
    val interpretationActivity =
            object : ActivityTestRule<InterpretationActivity>(InterpretationActivity::class.java) {
                override fun getActivityIntent(): android.content.Intent {
                    val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                    val result = Intent(targetContext, InterpretationActivity::class.java)
                    result.putExtra(AppConstants.WORD_EXTRA_KEY, testWord)
                    return result
                }
            }

    @Before
    fun createInterpretationPresenter() {
        wordArticle = WordArticle()
        wordArticle.word = testWord
        presenter = InterpretationPresenter(interpretationActivity.activity, wordArticle)
    }

    @Test
    fun should_loadArticle_afterLaunch() {
        onView(allOf(withId(R.id.interpretationTextView), withText(wordArticle.getWordArticle())))
        onView(allOf(withId(R.id.sourceTextView), withText(wordArticle.getArticleSource())))
    }
}