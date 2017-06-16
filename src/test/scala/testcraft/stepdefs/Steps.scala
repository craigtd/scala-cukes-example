package testcraft.stepdefs

import java.util.concurrent.TimeUnit

import cats.syntax.either._
import cucumber.api.scala.{EN, ScalaDsl}
import testcraft.utils.Driver
import org.openqa.selenium.WebDriver
import org.scalatest.Matchers

trait Steps extends ScalaDsl with EN with Matchers {

  import Steps._

  /** Tries to get the value of [[_driver]] - will throw an exception if it doesn't exist */
  implicit def getDriverUnsafe: WebDriver = _driver.getOrElse(sys.error("Driver does not exist"))

  // create a new driver for each scenario
  Before { _ ⇒
    if(_driver.isEmpty) {
      val d = Driver.newWebDriver()
        // map the left to Nothing
        .leftMap(e ⇒ sys.error(s"Could not find driver: $e"))
        // merge will merge Nothing and WebDriver to WebDriver since Nothing is a subtype of everything
        .merge
      d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
      _driver = Some(d)
    }
  }

  After { _ ⇒
    _driver.foreach(_.quit())
    _driver = None
  }


}

object Steps {

  /**
    * Each step definition file extends the `Steps` trait , but they will all reference this single driver
    * in the companion object. Having this variable in the trait would cause multiple drivers to be
    * created
    */
  private var _driver: Option[WebDriver] = None

}
