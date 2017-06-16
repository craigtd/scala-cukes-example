package testcraft.pages

import org.openqa.selenium.WebDriver

object GoogleHomePage extends WebPage {

  override val url: String = "https://www.google.co.uk"

  def goToGoogleHome(implicit driver: WebDriver): Unit = go to url

}
