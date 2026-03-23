package pages;

import io.appium.java_client.AppiumDriver;
import appium_flutter_driver.finder.FlutterElement;
import java.util.Map;

public class CatalogPage extends BasePage {

    private final FlutterElement catalogTitle;
    private final FlutterElement productList;

    public CatalogPage(AppiumDriver driver) {
        super(driver);
        this.catalogTitle = finder.byText("Catalog");
        this.productList = finder.byValueKey("product_list");
    }

    public String getCatalogTitle() {
        return getText(catalogTitle);
    }

    public CatalogPage scrollDown() {
        FlutterElement listView = finder.byType("ListView");
        driver.executeScript("flutter:scroll", listView,
                Map.of("dx", 0, "dy", -300, "durationMilliseconds", 500));
        return this;
    }

    public CatalogPage clickAddButton(String itemName) {
        // Find the ADD button that is inside the row containing itemName
        FlutterElement itemText = finder.byText(itemName);
        FlutterElement addButton = finder.byText("ADD");
        FlutterElement specificAddButton = finder.byAncestor(addButton, itemText);
        click(specificAddButton);
        return this;
    }

    public void clickCart() {
        FlutterElement cartButton = finder.byTooltip("Cart");
        click(cartButton);
    }

}
