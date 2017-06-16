package testcraft.pages

import org.scalatest.Assertions
import org.scalatest.concurrent.{Eventually, PatienceConfiguration}
import org.scalatest.selenium.WebBrowser
import org.scalatest.time.{Millis, Seconds, Span}


trait WebPage extends org.scalatest.selenium.Page with PatienceConfiguration with WebBrowser with Eventually with Assertions {

  override val url = ""

  override implicit val patienceConfig: PatienceConfig = PatienceConfig(timeout = scaled(Span(10, Seconds)), interval = scaled(Span(500, Millis)))

}
