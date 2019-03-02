package vladyslavpohrebniakov.slvnk.interpretation


interface InterpretationView {
	fun setUpViews()

	fun showArticle(article: String)

	fun showSourceLink(source: String)

	fun hideProgressDialog()

	fun showErrorMessage(word: String)

	fun showSearchedWord(word: String)

	fun shareArticle()

	fun navigateUp()

	fun finishActivity()

	fun getSearchedWordExtra(): String
}