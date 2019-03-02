package vladyslavpohrebniakov.slvnk.search

interface SearchView {
	fun showErrorMessage()

	fun showNewBackground()

	fun showDesriptionDialog()

	fun clearInputField()

	fun navigateToInterpretationActivity(text: String)

	fun navigateToSettigsActivity()

	fun editFirtsLaunchSharedPref(value: Boolean)
}