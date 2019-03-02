package vladyslavpohrebniakov.slvnk

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import vladyslavpohrebniakov.slvnk.search.SearchPresenter
import vladyslavpohrebniakov.slvnk.search.SearchView

class SearchPresenterTest {
    @Test
    fun `should go to interpretation activity when search clicked word is valid`() {
        //given
        val correctInput = "слово"
        val mockView : SearchView = mock()
        val objectUnderTest = SearchPresenter(mockView)
        //when
        objectUnderTest.searchButtonClicked(correctInput)
        //then
        verify(mockView).navigateToInterpretationActivity(any())
    }

    @Test
    fun `should show error message when search button clicked word is invalid`() {
        //given
        val correctInput = " "
        val mockView : SearchView = mock()
        val objectUnderTest = SearchPresenter(mockView)
        //when
        objectUnderTest.searchButtonClicked(correctInput)
        //then
        verify(mockView).showErrorMessage()
    }

    @Test
    fun `should go to settings when settings button clicked`() {
        //given
        val mockView : SearchView = mock()
        val objectUnderTest = SearchPresenter(mockView)
        //when
        objectUnderTest.settingButtonClicked()
        //then
        verify(mockView).navigateToSettigsActivity()
    }

    @Test
    fun `should show dialog when help button clicked`() {
        //given
        val mockView : SearchView = mock()
        val objectUnderTest = SearchPresenter(mockView)
        //when
        objectUnderTest.helpButtonClicked()
        //then
        verify(mockView).showDesriptionDialog()
    }

    @Test
    fun `should show new background when load new background`() {
        //given
        val mockView : SearchView = mock()
        val objectUnderTest = SearchPresenter(mockView)
        //when
        objectUnderTest.loadNewBackground()
        //then
        verify(mockView).showNewBackground()
    }

    @Test
    fun `should show dialog when first launch`() {
        //given
        val isFirstLaunch = true
        val mockView : SearchView = mock()
        val objectUnderTest = SearchPresenter(mockView)
        //when
        objectUnderTest.appLaunched(isFirstLaunch)
        //then
        verify(mockView).showDesriptionDialog()
    }

    @Test
    fun `should clear search field when clear input called`() {
        //given
        val mockView : SearchView = mock()
        val objectUnderTest = SearchPresenter(mockView)
        //when
        objectUnderTest.clearInputField()
        //then
        verify(mockView).clearInputField()
    }
}