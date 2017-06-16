package testcraft.utils

import java.util.concurrent.TimeUnit

import cats.syntax.either._
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.firefox.{FirefoxDriver, MarionetteDriver}
import org.openqa.selenium.remote.{BrowserType, DesiredCapabilities}

object Driver {

  private val systemProperties = System.getProperties

  def newWebDriver(): Either[String, WebDriver] = {
    val selectedDriver: Either[String, WebDriver] = Option(systemProperties.getProperty("browser")).map(_.toLowerCase) match {
      case Some("firefox") ⇒ Right(createFirefoxDriver())
      case Some("chrome") ⇒ Right(createChromeDriver())
      case Some("gecko") ⇒ Right(createGeckoDriver())
      case Some(other) ⇒ Left(s"Unrecognised browser: $other")
      case None ⇒ Left("No browser set")
    }

    selectedDriver.map { driver ⇒
      sys.addShutdownHook(driver.quit())
      driver.manage().window().maximize()
      driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
    }
    selectedDriver
  }

  private val os: String =
    Option(systemProperties.getProperty("os.name")).getOrElse(sys.error("Could not read OS name"))

  private val isMac: Boolean = os.startsWith("Mac")

  private val isLinux: Boolean = os.startsWith("Linux")

  private val linuxArch: String =
    Option(systemProperties.getProperty("os.arch")).getOrElse(sys.error("Could not read OS arch"))

  private val isJsEnabled: Boolean = true

  private val driverDirectory: String = "resources/drivers"

  private def createGeckoDriver(): WebDriver = {
    if (isMac) {
      systemProperties.setProperty("webdriver.gecko.driver", driverDirectory + "/geckodriver_mac")
    } else if (isLinux) {
      systemProperties.setProperty("webdriver.gecko.driver", driverDirectory + "/geckodriver_linux64")
    } else {
      systemProperties.setProperty("webdriver.gecko.driver", driverDirectory + "/geckodriver.exe")
    }

    val capabilities = DesiredCapabilities.firefox()
    capabilities.setJavascriptEnabled(isJsEnabled)

    new MarionetteDriver(capabilities)
  }

  private def createFirefoxDriver(): WebDriver = {
    val capabilities = DesiredCapabilities.firefox()
    capabilities.setJavascriptEnabled(true)
    capabilities.setBrowserName(BrowserType.FIREFOX)

    new FirefoxDriver(capabilities)
  }

  private def createChromeDriver(): WebDriver = {
    if (isMac) {
      systemProperties.setProperty("webdriver.chrome.driver", driverDirectory + "/chromedriver_mac")
    } else if (isLinux && linuxArch == "amd32") {
      systemProperties.setProperty("webdriver.chrome.driver", driverDirectory + "/chromedriver_linux32")
    } else if (isLinux) {
      systemProperties.setProperty("webdriver.chrome.driver", driverDirectory + "/chromedriver")
    } else {
      systemProperties.setProperty("webdriver.chrome.driver", driverDirectory + "/chromedriver.exe")
    }

    val capabilities = DesiredCapabilities.chrome()
    val options = new ChromeOptions()

    options.addArguments("test-type")
    options.addArguments("--disable-gpu")

    capabilities.setJavascriptEnabled(isJsEnabled)
    capabilities.setCapability(ChromeOptions.CAPABILITY, options)

    new ChromeDriver(capabilities)
  }




}
