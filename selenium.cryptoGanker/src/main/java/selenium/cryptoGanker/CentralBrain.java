package selenium.cryptoGanker;

import org.openqa.selenium.chrome.ChromeDriver;

import selenium.cryptoGanker.baseNodes.basePage;

public class CentralBrain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\davgo\\repos\\selenium.cryptoGanker\\extras\\Chromedriver.exe");
		basePage controller = new basePage();//builds everything, sets up driver, navigates to page
		controller.waitForLogin();
		controller.loadCrypto();
		controller.cryptoLoop();
	}
}
