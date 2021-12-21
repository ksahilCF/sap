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

import io.cdap.e2e.utils.CdfHelper;
import io.cdap.e2e.utils.SeleniumDriver;
import io.cdap.plugin.odata.actions.ODataActions;
import io.cdap.plugin.odata.utils.CDAPUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static io.cdap.plugin.odata.actions.ODataActions.odataLocators;

/**
 * DesignTimeOData.
 */
public class DesignTimeOData implements CdfHelper {

    @Given("^Open CDF application$")
    public void open_cdf_application() throws IOException, InterruptedException {
        openCdf();
    }

    @When("^Source is SAP OData$")
    public void source_is_sap_odata() {
        ODataActions.selectODataSource();
    }

    @When("^Configure Direct Connection \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void configure_direct_connection_something_something_something
            (String baseURL, String serviceName, String entityName) throws Throwable {
        ODataActions.openODataProperties();
        ODataActions.enterConnectionProperties(baseURL, serviceName, entityName);
    }

    @When("^SAP Username and Password is provided$")
    public void sap_username_and_password_is_provided() throws IOException {
        ODataActions.enterUserNamePassword(CDAPUtils.getPluginProp("Odata_User"),
                CDAPUtils.getPluginProp("Odata_Pass"));
    }

    @Then("^Validate the Schema is created$")
    public void validate_the_schema_is_created() throws InterruptedException {
        ODataActions.getSchema();
        WebDriverWait wait = new WebDriverWait(SeleniumDriver.getDriver(), 10000);
        wait.until(ExpectedConditions.visibilityOf(odataLocators.schemaFieldSecondRow));
        Assert.assertEquals(true, CDAPUtils.schemaValidation());
    }


}
