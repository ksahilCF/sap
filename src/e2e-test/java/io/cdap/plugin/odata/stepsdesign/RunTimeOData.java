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

import io.cdap.e2e.pages.actions.CdfLogActions;
import io.cdap.e2e.pages.actions.CdfPipelineRunAction;
import io.cdap.e2e.pages.actions.CdfStudioActions;
import io.cdap.e2e.utils.CdfHelper;
import io.cdap.e2e.utils.GcpClient;
import io.cdap.e2e.utils.SeleniumDriver;
import io.cdap.e2e.utils.SeleniumHelper;
import io.cdap.plugin.odata.actions.ODataActions;
import io.cdap.plugin.odata.utils.CDAPUtils;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepsdesign.BeforeActions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * DesignTimeOData.
 */
public class RunTimeOData implements CdfHelper {
    GcpClient gcpClient = new GcpClient();
    static int countRecords;
    static String rawLog;
    static PrintWriter out;

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

    @Then("^Wait till preview pipeline is in running state$")
    public void wait_till_preview_pipeline_is_in_running_state() {
        WebDriverWait wait = new WebDriverWait(SeleniumDriver.getDriver(), 1000000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='fa fa-play text-success']")));
    }

    @Then("^Verify preview data is present$")
    public void verify_preview_data_is_present() {
        ODataActions.previewData();
        List<WebElement> previewRowcount= SeleniumDriver.getDriver().findElements(By.xpath("//*[@data-cy=\"preview-data-row\"]"));
        Assert.assertTrue(previewRowcount.size()>1);
    }

    @Then("^Open Logs of OData Pipeline$")
    public void open_logs_of_odata_pipeline() throws Throwable {
        CdfPipelineRunAction.logsClick();
        Thread.sleep(5000); //TODO
        rawLog = CdfPipelineRunAction.captureRawLogs();
//        BeforeActions.scenario.write(rawLog);
//        out.println(rawLog);
//        out.close();
    }

    @Then("^Verify the OData pipeline status is \"([^\"]*)\"$")
    public void verify_the_odata_pipeline_status_is_something(String status) {
        boolean bool = SeleniumHelper.verifyElementPresent(
                "//*[@data-cy='" + status + "']");
        Assert.assertTrue(bool);
    }

    @Then("^validate successMessage is displayed for the OData pipeline$")
    public void validate_successmessage_is_displayed_for_the_odata_pipeline() {
        CdfLogActions.validateSucceeded();
    }

    @Then("^Get Count of no of records transferred from OData to BigQuery in \"([^\"]*)\"$")
    public void get_count_of_no_of_records_transferred_from_odata_to_bigquery_in_something(String table) throws
            IOException, InterruptedException {
        countRecords = gcpClient.countBqQuery(io.cdap.plugin.odp.utils.CDAPUtils.getPluginProp(table));
    }

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

    @Then("^Verify the \"([^\"]*)\" is equal to \"([^\"]*)\"$")
    public void verify_the_something_is_equal_to_1000(String parameter, String  val) throws IOException {
        Assert.assertEquals(CDAPUtils.getParsedLogs(rawLog,
                CDAPUtils.getPluginProp(parameter)), val);

    }

    @Then("^Verify the \"([^\"]*)\" is equal to or less than \"([^\"]*)\"$")
    public void verify_the_is_equal_to_or_less(String parameter, String  val)
            throws Throwable {
        Assert.assertTrue(Integer.valueOf(CDAPUtils.getParsedLogs(rawLog,
                CDAPUtils.getPluginProp(parameter))) <= Integer.valueOf(val));
    }

    @Then("^Verify the \"([^\"]*)\" is more than \"([^\"]*)\"$")
    public void verify_the_is_more(String parameter, String  val)
            throws Throwable {
        Assert.assertTrue(Integer.valueOf(CDAPUtils.getParsedLogs(rawLog,
                CDAPUtils.getPluginProp(parameter))) > Integer.valueOf(val));
    }
}
