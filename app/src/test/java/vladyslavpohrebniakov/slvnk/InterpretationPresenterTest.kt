package vladyslavpohrebniakov.slvnk

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import vladyslavpohrebniakov.slvnk.interpretation.InterpretationPresenter
import vladyslavpohrebniakov.slvnk.interpretation.InterpretationView
import vladyslavpohrebniakov.slvnk.interpretation.WordArticle

class InterpretationPresenterTest {
    @Test
    fun `should set up views at launch activity`() {
        //given
        val mockView: InterpretationView = mock {
            on { getSearchedWordExtra() } doReturn "text"
        }
        val wordArticle = WordArticle()
        val objectUnderTest = InterpretationPresenter(mockView, wordArticle)

        //when
        objectUnderTest.activityLaunched()

        //then
        verify(mockView).setUpViews()
    }

    @Test
    fun `should return to last word interpretation activity when home button clicked`() {
        //given
        val mockView: InterpretationView = mock()
        val wordArticle = WordArticle()
        val objectUnderTest = InterpretationPresenter(mockView, wordArticle)

        //when
        objectUnderTest.homeButtonClicked(false)

        //then
        verify(mockView).navigateUp()
    }

    @Test
    fun `should finish activity when home button clicked`() {
        //given
        val mockView: InterpretationView = mock()
        val wordArticle = WordArticle()
        val objectUnderTest = InterpretationPresenter(mockView, wordArticle)

        //when
        objectUnderTest.homeButtonClicked(true)

        //then
        verify(mockView).finishActivity()
    }

    @Test
    fun `should share article when share button clicked`() {
        //given
        val mockView: InterpretationView = mock()
        val wordArticle = WordArticle()
        val objectUnderTest = InterpretationPresenter(mockView, wordArticle)

        //when
        objectUnderTest.shareButtonClicked()

        //then
        verify(mockView).shareArticle()
    }

}