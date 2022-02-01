/*
 * Copyright © 2021 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.cdap.plugin.odata.stepsdesign;

import io.cdap.e2e.utils.SeleniumDriver;
import io.cdap.plugin.odata.actions.ODataActions;
import io.cdap.plugin.odata.locators.ODataLocators;
import io.cdap.plugin.odp.utils.CDAPUtils;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DesignTimeError{
    static String  errorMessage = null;
    static String color = null;

    @Then("^\"([^\"]*)\" as \"([^\"]*)\" and getting inline error message \"([^\"]*)\"$")
    public void something_as_something_and_getting_inline_error_message_something
            (String parameter, String value, String error) {
        WebElement elementIn = SeleniumDriver.getDriver().findElement(
                By.xpath("//input[@data-cy='" + parameter + "']"));
        CDAPUtils.clearField(elementIn);
        elementIn.sendKeys(value);
        ODataActions.validateConnection();
        color = ODataActions.rowErrorColor();
        errorMessage = ODataActions.rowErrorMessage();
        Assert.assertEquals(CDAPUtils.getErrorProp(error).toLowerCase(),errorMessage.toLowerCase());
        errorMessage = null;
        color = null;
    }

    @Then("^\"([^\"]*)\" as \"([^\"]*)\" and getting error message \"([^\"]*)\"$")
    public void something_as_something_and_getting_error_message_something
            (String parameter, String value, String error) {
        WebElement elementIn = SeleniumDriver.getDriver().findElement(
                By.xpath("//input[@data-cy='" + parameter + "']"));
        CDAPUtils.clearField(elementIn);
        elementIn.sendKeys(value);
        ODataActions.validateConnection();
        errorMessage = ODataActions.errorMessage();
        Assert.assertEquals(CDAPUtils.getErrorProp(error).toLowerCase(),errorMessage.toLowerCase());
        errorMessage = null;
        color = null;
    }

    @Then("Filter Options as \"([^\"]*)\" and getting error message \"([^\"]*)\"$")
    public void FilterOptions_as_something_and_getting_error_message_something
            (String value, String error) throws InterruptedException {
        WebElement elementIn = ODataLocators.filterOption;
        elementIn.sendKeys(value);
        Thread.sleep(500);
        ODataActions.validateConnection();
        errorMessage = ODataActions.errorMessage();
        Assert.assertEquals(CDAPUtils.getErrorProp(error).toLowerCase(),errorMessage.toLowerCase());
        errorMessage = null;
    }

    @Then("Select Fields as \"([^\"]*)\" and getting error message \"([^\"]*)\"$")
    public void SelectOptions_as_something_and_getting_error_message_something
            (String value, String error) throws InterruptedException {
        WebElement elementIn = ODataLocators.selectOption;
        elementIn.sendKeys(value);
        Thread.sleep(500);
        ODataActions.validateConnection();
        errorMessage = ODataActions.errorMessage();
        Assert.assertEquals(CDAPUtils.getErrorProp(error).toLowerCase(),errorMessage.toLowerCase());
        errorMessage = null;
    }
}
