package vladyslavpohrebniakov.slvnk.search

class SearchPresenter(private val view: SearchView) {

    fun searchButtonClicked(word: String?) {
        if (word != null && word.isNotEmpty() && !word.trim().equals("", ignoreCase = true)) {
            view.navigateToInterpretationActivity(word)
        } else {
            view.showErrorMessage()
        }
    }

    fun settingButtonClicked() = view.navigateToSettigsActivity()

    fun helpButtonClicked() = view.showDesriptionDialog()

    fun appLaunched(isFirstLaunch: Boolean) {
        if (isFirstLaunch) {
            view.showDesriptionDialog()
            view.editFirtsLaunchSharedPref(false)
        }
    }

    fun loadNewBackground() = view.showNewBackground()

    fun clearInputField() = view.clearInputField()
}
