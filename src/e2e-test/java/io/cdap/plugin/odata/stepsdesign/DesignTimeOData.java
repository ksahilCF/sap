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
import io.cdap.e2e.pages.actions.CdfGcsActions;
import io.cdap.e2e.pages.actions.CdfPipelineRunAction;
import io.cdap.e2e.pages.actions.CdfStudioActions;
import io.cdap.e2e.pages.actions.CdfBigQueryPropertiesActions;
import io.cdap.e2e.pages.actions.CdfLogActions;
import io.cdap.e2e.pages.locators.CdfBigQueryPropertiesLocators;
import io.cdap.e2e.pages.locators.CdfStudioLocators;
import io.cdap.e2e.utils.CdfHelper;
import io.cdap.e2e.utils.GcpClient;
import io.cdap.e2e.utils.SeleniumDriver;
import io.cdap.e2e.utils.SeleniumHelper;
import io.cdap.plugin.odata.actions.ODataActions;
import io.cdap.plugin.odata.locators.ODataLocators;
import io.cdap.plugin.odata.utils.CDAPUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepsdesign.BeforeActions;

import java.io.IOException;
import java.io.PrintWriter;

import static io.cdap.e2e.pages.actions.CdfStudioActions.runAndPreviewData;
import static io.cdap.plugin.odata.actions.ODataActions.odataLocators;

/**
 * DesignTimeOData.
 */
public class DesignTimeOData implements CdfHelper {
    GcpClient gcpClient = new GcpClient();
    static int countRecords;
    static String rawLog;
    static PrintWriter out;

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
    @When("^SAP Username and Password is provided cloud$")
    public void sap_username_and_password_is_provided_cloud() throws IOException {
        ODataActions.enterUserNamePassword(CDAPUtils.getPluginProp("Odata_UserHANACloud"),
                CDAPUtils.getPluginProp("Odata_PassHANACloud"));
    }

    @Then("^Validate the Schema is created$")
    public void validate_the_schema_is_created() {
        ODataActions.getSchema();
        WebDriverWait wait = new WebDriverWait(SeleniumDriver.getDriver(), 10000);
        wait.until(ExpectedConditions.visibilityOf(odataLocators.schemaFieldSecondRow));
        Assert.assertEquals(true, CDAPUtils.schemaValidation());
    }

    @Then("^Close the OData Properties$")
    public void close_the_odata_properties() {
        ODataActions.closeProperty();
    }

    @When("Target is BigQuery for OData data transfer")
    public void targetIsBigQueryForODataTransfer() {
        CdfStudioActions.sinkBigQuery();
    }

    @Then("^Enter the BigQuery Properties for OData datasource \"([^\"]*)\"$")
    public void enter_the_bigquery_properties_for_odata_datasource_something(String tableName) throws Throwable {
        CdfStudioLocators.bigQueryProperties.click();
        CdfBigQueryPropertiesActions cdfBigQueryPropertiesActions = new CdfBigQueryPropertiesActions();
        SeleniumHelper.replaceElementValue(CdfBigQueryPropertiesLocators.projectID,
                io.cdap.plugin.odp.utils.CDAPUtils.getPluginProp("odataProjectId"));
        CdfBigQueryPropertiesLocators.bigQueryReferenceName.sendKeys("automation_test");
        CdfBigQueryPropertiesLocators.datasetProjectID.sendKeys(io.cdap.plugin.odp.utils.CDAPUtils.
                getPluginProp("odataProjectId"));
        CdfBigQueryPropertiesLocators.dataset.sendKeys(io.cdap.plugin.odp.utils.CDAPUtils.
                getPluginProp("dataSetOdp"));
        CdfBigQueryPropertiesLocators.bigQueryTable.sendKeys(io.cdap.plugin.odp.utils.CDAPUtils.
                getPluginProp(tableName));
        CdfBigQueryPropertiesLocators.truncatableSwitch.click();
        CdfBigQueryPropertiesLocators.updateTable.click();
        CdfBigQueryPropertiesLocators.validateBttn.click();
        SeleniumHelper.waitElementIsVisible(CdfBigQueryPropertiesLocators.textSuccess, 1L);
    }

    @Then("^Close the BigQuery Properties$")
    public void close_the_bigquery_properties() {
        CdfGcsActions.closeButton();
    }

    @Then("^Link source and target$")
    public void link_source_and_target() {
        SeleniumHelper.dragAndDrop(ODataLocators.fromODP, CdfStudioLocators.toBigQiery);
    }

    @Then("^Click on Preview and run$")
    public void click_on_preview_and_run()  {
        runAndPreviewData();
    }

    @Then("^Validate Preview is generated$")
    public void validate_preview_is_generated()  {
    }
    @Then("^Save and Deploy OData Pipeline$")
    public void save_and_deploy_odata_pipeline() throws Throwable {
        saveAndDeployPipeline();
    }

    @Then("^Run the OData Pipeline in Runtime$")
    public void run_the_odata_pipeline_in_runtime() throws Throwable {
        CdfPipelineRunAction.runClick();
    }

    @Then("^Wait till OData pipeline is in running state$")
    public void wait_till_odata_pipeline_is_in_running_state() {
        WebDriverWait wait = new WebDriverWait(SeleniumDriver.getDriver(), 1000000);
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-cy='Succeeded']")),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-cy='Failed']"))));
    }

    @Then("^Open Logs of OData Pipeline$")
    public void open_logs_of_odata_pipeline() throws Throwable {
        CdfPipelineRunAction.logsClick();
        Thread.sleep(5000); //TODO
        rawLog = CdfPipelineRunAction.captureRawLogs();
        BeforeActions.scenario.write(rawLog);
//        out.println(rawLog);
//        out.close();
    }

    @Then("^Verify the OData pipeline status is \"([^\"]*)\"$")
    public void verify_the_odata_pipeline_status_is_something(String status) {
        boolean bool = SeleniumHelper.verifyElementPresent("//*[@data-cy='" + status + "']");
        Assert.assertTrue(bool);
    }

    @Then("^validate successMessage is displayed for the OData pipeline$")
    public void validate_successmessage_is_displayed_for_the_odata_pipeline() {
        CdfLogActions.validateSucceeded();
    }

    @Then("^Get Count of no of records transferred from OData to BigQuery in \"([^\"]*)\"$")
    public void get_count_of_no_of_records_transferred_from_odata_to_bigquery_in_something(String table) throws
        IOException, InterruptedException {
            countRecords = gcpClient.countBqQuery(io.cdap.plugin.odp.utils.CDAPUtils.getPluginProp(table));    }

    @Then("^Verify the Data load transfer is successful$")
    public void verify_the_full_load_transfer_is_successful() {
        String rec = String.valueOf(countRecords);
        int resultRec = Integer.valueOf(rec.replaceAll(",", "").toString());
        Assert.assertTrue(resultRec == recordOut());    }

    @Then("^Clear the parameters$")
    public void reset_the_parameters() throws Throwable {
        countRecords = 0;
        BeforeActions.scenario.write("countRecords=0;\n" +
                "    presentRecords=0;\n" +
                "    dsRecordsCount=0;");
    }

    @Then("^Verify the Data load transfer is successfuls$")
    public void verify_the_full_load_transfer_is_successfuls() {
        String rec = String.valueOf(countRecords);
        int resultRec = Integer.valueOf(rec.replaceAll(",", "").toString());
        resultRec++;
        Assert.assertTrue(resultRec == recordOut());
    }

    @Then("^Fill Filter Options as \"([^\"]*)\"$")
    public void fill_filter_options_as_something(String filter) {
        ODataActions.filterOption(filter);
    }

    @Then("^Fill Select Fields as \"([^\"]*)\"$")
    public void fill_select_fields_as_something(String selectFields) {
        ODataActions.selectOption(selectFields);
    }

    @Then("^Fill Expand Fields as \"([^\"]*)\"$")
    public void fill_expand_fields_as_something(String expand) {
        ODataActions.expandOption(expand);
    }

    @Then("^Fill Number of Rows to Skip as \"([^\"]*)\"$")
    public void fill_number_of_rows_to_skip_as_1(String  numOfRows) {
        ODataActions.skipRowCount(numOfRows);
    }


    @Then("^Fill Number of Rows to Fetch as \"([^\"]*)\"$")
    public void fill_number_of_rows_to_fetch_as_100(String rowCount) {
        ODataActions.numRowsToFetch(rowCount);
    }

    @Then("^Fill Number of Splits to Generate as \"([^\"]*)\"$")
    public void fill_number_of_splits_to_generate_as_8(String split) {
        ODataActions.splitCount(split);
    }


    @Then("^Fill Batch Size as \"([^\"]*)\"$")
    public void fill_batch_size_as_5000(String batch) {
        ODataActions.batchSize(batch);
    }



}
