package selenium.cryptoGanker.baseNodes;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class basePage {
	
	baseArm leftArm;
	baseArm rightArm;
	baseEye eye;
	ChromeDriver driver;
	
	public String url = "https://www.robinhood.com/";
	public String driverLocation = "C:\\Users\\davgo\\repos\\selenium.cryptoGanker\\extras\\Chromedriver.exe";
	public String driverType = "webdriver.chrome.driver";
	WebDriverWait wait;
	
	private String userName = "";
	private String password = "";
	
	public int owned = 0;
	public float purchasePrice;
	public float sellPrice;
	public float buyingPower;
	public float currentCost;
	public String crypto = "doge";
	//Initial load paths**********************************************************************************
	public By eleUserName = By.name("username");
	public By elePassword = By.xpath("//*[@id=\"react_root\"]/div[1]/div[2]/div/div/div/div/div/form/div/div/div[2]/label/div[2]/input");
	public By eleLoginButton = By.xpath("//*[@id=\"react_root\"]/div[1]/div[2]/div/div/div/div/div/form/footer/div/button");
	public By eleMainPage = By.xpath("//*[@id=\"downshift-0-input\"]");
	public By eleBuyingPower = By.xpath("//*[@id=\"react_root\"]/main/div[2]/div/div/div/div/main/div/div[1]/div[1]/div/button/header/div/span[2]/span");
	public By eleCurrentCost = By.xpath("//*[@id=\"react_root\"]/main/div[3]/div/div/div/div/div/div/div[2]/div/div[1]/div[2]/form/div[1]/div[1]/div[1]/div[2]/div[2]/span");
	//Buy load paths**************************************************************************************
	public By eleBuyField = By.name("amount");
	public By eleUsdCryptoToggle = By.xpath("//*[@id=\"react_root\"]/main/div[3]/div/div/div/div/div/div/div[2]/div/div[1]/div[2]/form/div[1]/div[1]/div[1]/div[1]/label/a");
	public By eleReviewOrder = By.xpath("//*[@id=\"react_root\"]/main/div[3]/div/div/div/div/div/div/div[2]/div/div[1]/div[2]/form/div[1]/div[2]/div/div[2]/div/button/span");
	public By eleConfirmOrder = By.xpath("//*[@id=\"react_root\"]/main/div[3]/div/div/div/div/div/div/div[2]/div/div[1]/div[2]/form/div[1]/div[2]/div/div[2]/div[1]/button");
	public By eleCostAmount = By.xpath("//*[@id=\"react_root\"]/main/div[3]/div/div/div/div/div/div/div[1]/section[2]/div/div[2]/header/h2/span");
	//Sell load paths**************************************************************************************	
	public By eleSellTab = By.xpath("//*[@id=\"react_root\"]/main/div[3]/div/div/div/div/div/div/div[2]/div/div[1]/div[2]/form/div[1]/header/div/div[1]/div/div[2]/div/h3/span/div/span");
	public By eleUsdCrytoToggle2 = By.xpath("//*[@id=\"react_root\"]/main/div[3]/div/div/div/div/div/div/div[2]/div/div[1]/div[2]/form/div[1]/div[1]/div[1]/div[1]/label/a");
	public By eledropDownArrow = By.xpath("//*[@id=\"react_root\"]/main/div[3]/div/div/div/div/div/div/div[2]/div/div[1]/div[2]/form/div[1]/header/div/div[3]/div/span/svg");
	public By eleLimitOrder = By.xpath("/html/body/div[4]/div/div/div/div/div/div[1]/label/div/a");
	public By elePrice = By.name("price");
	public By eleReviewSell = By.xpath("//*[@id=\"react_root\"]/main/div[3]/div/div/div/div/div/div/div[2]/div/div[1]/div[2]/form/div[1]/div[2]/div/div[2]/div/button/span");
	public By eleConfirmSell = By.xpath("//*[@id=\"react_root\"]/main/div[3]/div/div/div/div/div/div/div[2]/div/div[1]/div[2]/form/div[1]/div[2]/div/div[2]/div[1]/button");
	
	
	public basePage() {
		driver = new ChromeDriver();
		leftArm = new baseArm();
		rightArm = new baseArm();
		eye = new baseEye();
		wait = new WebDriverWait(driver, 30);
		driver.manage().window().maximize();
		driver.get(url + "login");
	}
	public void waitForLogin() {
		waitUntilClickable(eleUserName);
		sendKeys(eleUserName, userName);
		sendKeys(elePassword, password);
		click(eleLoginButton);
		waitUntilVisible(eleMainPage, 300); //wait for 2auth
	}
	public void loadCrypto() {
		buyingPower = Float.parseFloat(removeSpacesSpecial(getText(eleBuyingPower))); //gets buying power text
		driver.navigate().to(url + "crypto/" + crypto);
		waitUntilVisible(eleCurrentCost);
		currentCost = Float.parseFloat(removeSpacesSpecial(getText(eleCurrentCost))); //gets buying power text
	}
	public void cryptoLoop() {
		int loopCount = 10 * 2;//try to keep even so it always ends on a sale
		for (int i = 0;i <= loopCount;i++) {
			if (owned == 0) {
				purchaseCrypto();
			} else {
				sellCrypto();
			}
		}

	}
	public void purchaseCrypto() {
		int purchasing = (int) Math.round((buyingPower * 0.80) / currentCost);
		click(eleUsdCryptoToggle);
		sendKeys(eleBuyField, Integer.toString(purchasing));
		sleep(500);
		click(eleReviewOrder);
		sleep(500);
		click(eleConfirmOrder);
		sleep(1500);
		driver.navigate().refresh();
		purchasePrice = Float.parseFloat(removeSpacesSpecial(getText(eleCostAmount)));
		sellPrice = (float) (purchasePrice * 1.0025);
		owned = purchasing;
		System.out.println(owned);
		System.out.println(purchasePrice);
		System.out.println(sellPrice);
	}
	public void sellCrypto() {
		click(eleSellTab);
		sleep(500);
		click(eledropDownArrow);//seems to be breaking here
		sleep(500);
		sendKeys(eleBuyField, Integer.toString(owned));
		clear(elePrice);
		sendKeys(elePrice, Float.toString(sellPrice));
		sleep(500);
		click(eleReviewSell);
		sleep(500);
		click(eleConfirmSell);
		//Needs to refresh until it no longer is in bank
		sleep(20000);
	}
	
	/* Start of generic functions
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public void clear(By location) {
		waitUntilVisible(location);
		driver.findElement(location).clear();
	}
	public void sleep(int milli) {
		try { Thread.sleep(milli); } catch (InterruptedException e) { }
	}
	public String removeSpacesSpecial(String str) {
		str = str.replaceAll("[-+$]*", "");  
		return str;
	}
	public String getText(By location) {
		waitUntilVisible(location);
		return driver.findElement(location).getText();
	}
	public void click(By location) {
		waitUntilClickable(location);
		driver.findElement(location).click();
	}
	public void sendKeys(By location, String text) {
		driver.findElement(location).sendKeys(text);
	}
	public void waitUntilClickable(By location) {
		wait.until(ExpectedConditions.elementToBeClickable(location));
	}
	public void waitUntilVisible(By location, int time) {
		WebDriverWait tempWait = new WebDriverWait(driver, time);
		tempWait.until(ExpectedConditions.visibilityOfElementLocated(location));
	}
	public void waitUntilVisible(By location) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(location));
	}
}
