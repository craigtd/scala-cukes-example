package testcraft.stepdefs

import testcraft.pages.GoogleHomePage


class GoogleSteps extends Steps {

  When("""^I navigate to the Google homepage$""") { () =>
    GoogleHomePage.goToGoogleHome
  }

}
