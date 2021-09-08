package pageObjects.ui;

import org.openqa.selenium.By;

public class OnlineShopPageObjects {
    public static final By ADD_TO_CART_BUTTON = By.cssSelector("a[data-id-product='1']");
    public static final By PRODUCT_ADDED_CONFIRMATION = By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2");
    public static final By ONLINE_SHOP_LOGO = By.id("header_logo");
    public static final By CLOSE_PRODUCT_ADDED_POPUP_BUTTON = By.cssSelector("#layer_cart > div.clearfix > div.layer_cart_product.col-xs-12.col-md-6 > span");
    public static final By REMOVE_FIRST_ITEM_FROM_CART_BUTTON = By.cssSelector("#header > div:nth-child(3) > div > div > div:nth-child(3) > div > div > div > div > dl > dt > span > a");
    public static final By EMPTY_CART_LABEL = By.cssSelector("#header > div:nth-child(3) > div > div > div:nth-child(3) > div > a > span.ajax_cart_no_product");
    public static final By SEARCH_INPUT_FIELD = By.id("search_query_top");
    public static final By SEARCH_BUTTON = By.className("button-search");
    public static final By SEARCH_COUNTER = By.className("heading-counter");
    public static final By STORE_INFORMATION_DIV = By.cssSelector("#block_contact_infos > div > h4");
    public static final By STORE_INFORMATION = By.xpath("//*[@id=\"block_contact_infos\"]/div/ul/li[1]");
}
