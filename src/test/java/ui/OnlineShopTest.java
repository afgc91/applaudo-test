package ui;

import controllers.ui.UIActions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static steps.ui.OnlineShopSteps.*;

public class OnlineShopTest {
    private WebDriver driver;
    private UIActions uiActions;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        uiActions = new UIActions(driver);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    @DisplayName("Add product to the shopping cart")
    void addProductToShoppingCart() {
        visitOnlineShop(uiActions);
        addItemToShoppingCart(uiActions);
    }

    @Test
    @DisplayName("Remove product from the shopping cart")
    void removeProductFromShoppingCart() {
        visitOnlineShop(uiActions);
        addItemToShoppingCart(uiActions);
        removeItemFromShoppingCart(uiActions);
    }

    @Test
    @DisplayName("Search for existent products")
    void searchForExistentProducts() {
        visitOnlineShop(uiActions);
        searchForExistentItems(uiActions);
    }

    @Test
    @DisplayName("Search for non-existent products")
    void searchForNonExistentProducts() {
        visitOnlineShop(uiActions);
        searchForNonExistentItems(uiActions);
    }

    @Test
    @DisplayName("Validate store information")
    void validateInformation() {
        visitOnlineShop(uiActions);
        validateStoreInformation(uiActions);
    }
}
