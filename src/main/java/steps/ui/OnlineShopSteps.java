package steps.ui;

import controllers.ui.UIActions;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static support.Variables.*;
import static pageObjects.ui.OnlineShopPageObjects.*;

public class OnlineShopSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineShopSteps.class);

    public static void visitOnlineShop(UIActions uiActions) {
        uiActions.visitSite(UI_BASE_URL);
        uiActions.shouldSee(ONLINE_SHOP_LOGO);
        LOGGER.info("Online shop visited");
    }

    public static void addItemToShoppingCart(UIActions uiActions) {
        uiActions.clickDOMElement(ADD_TO_CART_BUTTON);
        uiActions.shouldSee(PRODUCT_ADDED_CONFIRMATION);
        LOGGER.info("Product added to cart successfully");
    }

    public static void removeItemFromShoppingCart(UIActions uiActions) {
        uiActions.click(CLOSE_PRODUCT_ADDED_POPUP_BUTTON);
        uiActions.clickDOMElement(REMOVE_FIRST_ITEM_FROM_CART_BUTTON);
        uiActions.shouldSee(EMPTY_CART_LABEL);
        String actualEmptyCartText = uiActions.getElementText(EMPTY_CART_LABEL);
        Assertions.assertEquals(EXPECTED_EMPTY_CART_TEXT, actualEmptyCartText);
    }

    public static void searchForExistentItems(UIActions uiActions) {
        uiActions.sendKeys(SEARCH_INPUT_FIELD, EXISTENT_ITEMS_SEARCH_TEXT);
        uiActions.click(SEARCH_BUTTON);
        uiActions.shouldSee(SEARCH_COUNTER);
        uiActions.textShouldBeEqualsTo(SEARCH_COUNTER, EXPECTED_POSITIVE_SEARCH_RESULTS_TEXT);
    }

    public static void searchForNonExistentItems(UIActions uiActions) {
        uiActions.sendKeys(SEARCH_INPUT_FIELD, NON_EXISTENT_ITEMS_SEARCH_TEXT);
        uiActions.click(SEARCH_BUTTON);
        uiActions.shouldSee(SEARCH_COUNTER);
        uiActions.textShouldBeEqualsTo(SEARCH_COUNTER, EXPECTED_NEGATIVE_SEARCH_RESULTS_TEXT);
    }

    public static void validateStoreInformation(UIActions uiActions) {
        uiActions.scrollDownToElement(STORE_INFORMATION_DIV);
        uiActions.textShouldBeEqualsTo(STORE_INFORMATION, EXPECTED_STORE_INFORMATION);
    }
}
