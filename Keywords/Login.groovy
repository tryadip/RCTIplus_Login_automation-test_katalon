import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.concurrent.ExecutionException

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import com.kms.katalon.core.webui.exception.WebElementNotFoundException


class Login {


	@Keyword
	def verifytext(String text) {
		try {
			WebUI.verifyTextPresent(text, false)
			KeywordUtil.markPassed("text sudah sesuai")
		} catch (Exception e) {
			KeywordUtil.markError("Text tidak ada")
			e.printStackTrace()
		}
	}

	@Keyword
	def Tocountrycode() {
		WebUI.setText(findTestObject("Loginpage/Field/F_email"), "081")
		WebUI.click(findTestObject("Loginpage/Button/Dropdown_number"))
		WebUI.click(findTestObject("Loginpage/Button/btn_search"))
	}

	@Keyword
	def inputcountry (String country, TestObject to) {
		WebDriver driver = DriverFactory.getWebDriver()
		WebUI.setText(to, country)
		List<WebElement> countryList = driver.findElements(By.xpath('//*[@id="country-list"]/*'))

		//*[@id="country-list"]/div[2]/span[1]
		try {
			int id = 1;
			for(WebElement e : countryList) {
				List<WebElement> divList = driver.findElements(By.xpath('//*[@id="country-list"]/div['+id+']/*'))

				for(WebElement e2 : divList)
				{
					String title = e2.getAttribute("innerHTML");

					if(title.equalsIgnoreCase(country)) {
						e.click()
					}

				}

				id = id + 1;
			}
		} catch (Exception e) {
			println("here6")
			println(e.getMessage())
			e.printStackTrace()
			println("here7")
		}
	}

	@Keyword
	def ss (String ts) {
		WebUI.takeFullPageScreenshot("C:/Users/user/Documents/Latihan Testing/RCTI+/Login/" + ts)
		KeywordUtil.markPassed("berhasil terscreenshoot")
	}
	@Keyword
	def toLoginpage () {
		WebUI.openBrowser("https://www.rctiplus.com/")
		WebUI.maximizeWindow()
		WebUI.click(findTestObject("Homepage/Button/btn_login"))
	}
	@Keyword
	def refreshBrowser() {
		KeywordUtil.logInfo("Refreshing")
		WebDriver webDriver = DriverFactory.getWebDriver()
		webDriver.navigate().refresh()
		KeywordUtil.markPassed("Refresh successfully")
	}

	/**
	 * Click element
	 * @param to Katalon test object
	 */
	@Keyword
	def clickElement(TestObject to) {
		try {
			WebElement element = WebUiBuiltInKeywords.findWebElement(to);
			KeywordUtil.logInfo("Clicking element")
			element.click()
			KeywordUtil.markPassed("Element has been clicked")
		} catch (WebElementNotFoundException e) {
			KeywordUtil.markFailed("Element not found")
		} catch (Exception e) {
			KeywordUtil.markFailed("Fail to click on element")
		}
	}

	/**
	 * Get all rows of HTML table
	 * @param table Katalon test object represent for HTML table
	 * @param outerTagName outer tag name of TR tag, usually is TBODY
	 * @return All rows inside HTML table
	 */
	@Keyword
	def List<WebElement> getHtmlTableRows(TestObject table, String outerTagName) {
		WebElement mailList = WebUiBuiltInKeywords.findWebElement(table)
		List<WebElement> selectedRows = mailList.findElements(By.xpath("./" + outerTagName + "/tr"))
		return selectedRows
	}
}