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
package io.cdap.plugin.slt.stepsdesign;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.adapter.connector.SAPAdapterImpl;
import com.google.adapter.connector.SAPProperties;
import com.google.adapter.exceptions.SystemException;
import com.google.adapter.util.ErrorCapture;
import com.google.adapter.util.ExceptionUtils;
import com.google.api.gax.paging.Page;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.sap.conn.jco.JCoException;
import io.cdap.e2e.pages.actions.CdfBigQueryPropertiesActions;
import io.cdap.e2e.pages.actions.CdfGcsActions;
import io.cdap.e2e.pages.actions.CdfPipelineRunAction;
import io.cdap.e2e.pages.actions.CdfStudioActions;
import io.cdap.e2e.pages.locators.CdfBigQueryPropertiesLocators;
import io.cdap.e2e.pages.locators.CdfStudioLocators;
import io.cdap.e2e.utils.CdfHelper;
import io.cdap.e2e.utils.GcpClient;
import io.cdap.e2e.utils.SeleniumDriver;
import io.cdap.e2e.utils.SeleniumHelper;
import io.cdap.plugin.odata.actions.ODataActions;
import io.cdap.plugin.odata.locators.ODataLocators;
import io.cdap.plugin.odp.locators.ODPLocators;
import io.cdap.plugin.odp.stepsdesign.RuntimeODP;
import io.cdap.plugin.slt.actions.SLTActions;
import io.cdap.plugin.slt.locators.SLTLocators;
import io.cdap.plugin.slt.utils.CDAPUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepsdesign.BeforeActions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static io.cdap.e2e.pages.actions.CdfStudioActions.runAndPreviewData;
import static io.cdap.e2e.utils.GcpClient.getSoleQueryResult;
import static io.cdap.plugin.odata.actions.ODataActions.odataLocators;
import static io.cdap.plugin.odp.utils.AutoConstants.*;

/**
 * DesignTimeOData.
 */
public class DesignTimeSLT implements CdfHelper {

    private static final Logger logger = Logger.getLogger(RuntimeODP.class);
    private SAPProperties sapProps;
    private ErrorCapture errorCapture;
    private SAPAdapterImpl sapAdapterImpl;
    static String action;
    private ExceptionUtils exceptionUtils;
public static SLTActions sltActions = new SLTActions();
public static SLTLocators sltLocators = new SLTLocators();
public static String rawLog =null;
private static WebDriver CDFDriver;
GcpClient gcpClient = new GcpClient();
static int sapRecordsCount;
static int bqRecordCount=0;
private static ArrayList<String> tabs=null;
private static String parent=null;
    private static Properties connection = new SAPProperties();
    private List<String> fields = new ArrayList<>();
    private static boolean isErrorPresent=false;
    private static String mt_id=null;
    private static String guid=null;
    static boolean errorExist = false;
    static String color;


    static {
    CDFDriver = SeleniumDriver.getDriver();
    sltLocators = SeleniumHelper.getPropertiesLocators(SLTLocators.class);

    Properties prop = new Properties();
    try {
        prop.load(new FileInputStream("src/e2e-test/resources/Google_SAP_Connection.properties"));
    } catch (IOException e) {
        logger.error("Failed while reading SAP properties file" + e);
    }
    for (String property : prop.stringPropertyNames()) {
        connection.put(property, prop.getProperty(property));
    }

}

    @Then("^Set Dummy MTID")
    public void setDummyMTID(){
        mt_id="032";
    }


    @Given("^Open CDF replication and initiate pipeline creation")
    public void open_cdf_replication() throws IOException, InterruptedException {
        openCdf("http://localhost:11011/cdap/ns/default/replication/create");
        sltActions.name_fill(UUID.randomUUID().toString().replaceAll("-",""));
        sltActions.next_click();
        isErrorPresent=false;
        bqRecordCount=0;
        sapRecordsCount=0;
    }

    @When("^Source is SAP SLT fill connection parameters$")
    public void source_is_sap_slt() throws IOException, InterruptedException {
        sltActions.sltplugin_click();
        sltActions.required_config(
                "gcpProjectId",
                "gcsDataPath",
                mt_id,
                "sapJcoLibGcsPath",
                "jcoclienthost",
                "jcoclient",
                "jcoclientsysnr",
                "jcoclientuser",
                "jcoclientpasswd");
    }

    @When("^Source is SAP SLT$")
    public void source_is_sapslt() throws IOException, InterruptedException {
        sltActions.sltplugin_click();
    }


    @Then("^User is able to set SLT parameter (.+) as (.+) and getting row (.+) for wrong input$")
    public void userIsAbleToSetSLTParameterAsAndGettingRowForWrongInput(
            String option, String input, String errorMessage) {
        errorExist = false;
        WebElement element = SeleniumDriver.getDriver().findElement(
                By.xpath("//*[@data-cy='" + option + "' and contains(@class,'MuiInputBase-input')]"));
        io.cdap.plugin.odp.utils.CDAPUtils.clearField(element);
        element.sendKeys(input);
        sltActions.next_click();
        errorExist = io.cdap.plugin.odp.utils.CDAPUtils.getErrorProp(errorMessage).toLowerCase().contains(SLTLocators.rowError.getText()
                .toLowerCase());
        color = SLTLocators.rowError.getCssValue("border-color");
        Assert.assertTrue(errorExist);
        BeforeActions.scenario.write("Color of the text box" + color);
        Assert.assertTrue(color.toLowerCase().contains("rgb(209, 86, 104)"));
    }

    @Then("^User is able to set SLT parameter (.+) as (.+)$")
    public void userIsAbleToSetSLTParameter(
            String option, String input) {

        WebElement element = SeleniumDriver.getDriver().findElement(
                By.xpath("//*[@data-cy='" + option + "' and contains(@class,'MuiInputBase-input')]"));
        io.cdap.plugin.odp.utils.CDAPUtils.clearField(element);
        element.sendKeys(input);
    }

    @Then("^Replicate Existing Data is set to false$")
    public void replicate_existing_data() {
        sltActions.replicateExistingData_click();
        Assert.assertEquals(
                sltActions.replicateExistingData_getText().toLowerCase(),
                "no");
    }

    @Then("^Suspend Slt Job is set to true$")
    public void suspend_existing_job() {
        sltActions.suspendSltJob_click();
        Assert.assertEquals(
                sltActions.suspendSltJob_getText().toLowerCase(),
                "yes");
    }

    @Then("^Click on next$")
    public void click_next() {
        sltActions.next_click();
    }

    @Then("^Select Table \"([^\"]*)\"$")
    public void select_table_something(String table)  {
        sltActions.select_table(table.toLowerCase());
    }

    @Then("^Enter the BigQuery Properties for slt datasource$")
    public void enter_the_bigquery_properties_for_slt_datasource_something() throws IOException {
        SeleniumHelper.replaceElementValue(sltLocators.project,
                CDAPUtils.getPluginProp("bqProjectId"));
        sltActions.datasetName_fill(CDAPUtils.getPluginProp("dataset"));
    }

    @Then("^Click on Deploy Replication Pipeline$")
    public void click_deployPipeline() {
        sltActions.DeployPipeline_click();
    }

    @Then("^Run the slt Pipeline in Runtime$")
    public void run_the_slt_pipeline_in_runtime() throws Throwable {
    Thread.sleep(3000);
        sltActions.start_pipeline();
        SeleniumHelper.waitElementIsVisible(sltLocators.Running,60);
    }

    @Then("^Open Logs of SLT Pipeline$")
    public void open_logs_of_slt_pipeline() {
    sltLocators.Logs.click();
      parent = SeleniumDriver.getDriver().getWindowHandle();
        tabs = new ArrayList(SeleniumDriver.getDriver().getWindowHandles());
        CDFDriver.switchTo().window((String)tabs.get(1));
        sltLocators.AdvancedLogs.click();
    }

    @Then("^Wait till SLT pipeline is in running state and no error occurs$")
    public void wait_till_slt_pipeline_is_in_running_state() {
        WebDriverWait wait = new WebDriverWait(CDFDriver, 1000000);
        //TODO : No latency update error3??
//        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
//                By.xpath("//*[contains(text(),'Latency')]"),2));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(text(),'ERROR')])[3]")));
//        wait.until(ExpectedConditions.or(
//                ExpectedConditions.numberOfElementsToBeMoreThan(
//                        By.xpath("//*[contains(text(),'Latency')]"),2),
//                ExpectedConditions.visibilityOf(sltLocators.error3)));

        wait.until(ExpectedConditions.or(
                ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.xpath("//*[contains(text(),'replication status: Process: ')]"),2),
                ExpectedConditions.visibilityOf(sltLocators.error3)));

    }


    @Then("^Get Count of no of records transferred from SLT to BigQuery in \"([^\"]*)\"$")
    public void get_count_of_no_of_records_transferred_from_SLT_to_bigquery_in_something(String table) throws
            IOException, InterruptedException {
        String selectQuery = "SELECT count(*)  FROM `" +
                CDAPUtils.getPluginProp("bqProjectId") + "." +
                CDAPUtils.getPluginProp("dataset") + "." +
                table.toLowerCase() + "`";

        bqRecordCount= (Integer)getSoleQueryResult(selectQuery).
                map(Integer::parseInt).orElse(0);
        System.out.println("Count : "+bqRecordCount);
    }

    @Then("^Drop target BigQuery table \"([^\"]*)\"$")
    public void drop_target_bigquery_table_something(String table) {
        String selectQuery = null;
        try {
            selectQuery = "DROP TABLE `" + CDAPUtils.getPluginProp("bqProjectId") + "." + CDAPUtils.getPluginProp("dataset") + "." + table.toLowerCase() + "`";
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bqRecordCount= (Integer)getSoleQueryResult(selectQuery).map(Integer::parseInt).orElse(0);
        } catch (InterruptedException | IOException | BigQueryException e) {
            System.out.println("BQ Table is not present, It will be created by BQ plugin");
        }
    }

    @Then("^Delete GCS folder for mass transfer id table \"([^\"]*)\"$")
    public void delete_gcs_folder_for_mass_transfer_id_something_table_something(String table) throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId(CDAPUtils.getPluginProp("gcpProjectId")).build().getService();
        Page<Blob> blobs =storage.list(
                CDAPUtils.getPluginProp("gcsDataPath").replaceAll("gs://",""),
                Storage.BlobListOption.prefix("030"+"/"+table));
        for (Blob blob : blobs.iterateAll()) {
            System.out.println(blob.getName());
            storage.delete(CDAPUtils.getPluginProp("gcsDataPath").replaceAll("gs://",""),
                    blob.getName());
        }
    }

    @Then("^Close logs and stop the pipeline$")
    public void close_logs_and_stop_the_pipeline(){
        WebDriverWait wait = new WebDriverWait(CDFDriver, 1000000);
        CDFDriver.close();
        CDFDriver.switchTo().window(parent);
        sltActions.stop_pipeline();
        wait.until(ExpectedConditions.visibilityOf(sltLocators.stopped));
//        Assert.assertFalse("Error status: ",isErrorPresent);
    }

    @Then("^User is able to validate only replication data is loaded ie \"([^\"]*)\"$")
    public void user_is_able_to_validate_record_count_in_bq_matches_with_repl_count(String count) {
        Assert.assertEquals(Integer.parseInt(count),bqRecordCount);
        Assert.assertTrue(sapRecordsCount>bqRecordCount);
    }

    @Then("^User is able to validate record count in BQ matches with count in SAP$")
    public void user_is_able_to_validate_record_count_in_bq_matches_with_count_in_sap() {
        Assert.assertEquals(sapRecordsCount,bqRecordCount);
    }




    @Then("^Update mass transfer id table: \"([^\"]*)\" job mode to \"([^\"]*)\"$")
    public void slt_job_for_mtid_something_table_something_in_something_mode
            (String table, String mode)
            throws JCoException, InterruptedException {
        if (mode.equalsIgnoreCase("initial_load")) {
            action = "IM_START_LOAD";
        } else if (mode.equalsIgnoreCase("replication")) {
            action = "IM_START_REPL";
        } else if (mode.equalsIgnoreCase("only_replication")) {
            action = "IM_START_REP_ONLY";
        }else if (mode.equalsIgnoreCase("stop_load")) {
            action = "IM_STOP_REPLICATION";
        }else if (mode.equalsIgnoreCase("start_recording")) {
            action = "IM_START_RECORD";
        }
        sapProps = SAPProperties.getDefault(connection);
        errorCapture = new ErrorCapture(exceptionUtils);
        sapAdapterImpl = new SAPAdapterImpl(errorCapture, connection);

        Map opProps = new HashMap<>();
        opProps.put("RFC", "ZRFM_LTRC_OPERATIONS");
        try {

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("IM_MTID",mt_id);
            objectNode.put("IM_TABLES",table.toUpperCase());
            objectNode.put(action,"X");

            JsonNode response = sapAdapterImpl.executeRFC(objectNode.toString(), opProps, "", "");
            System.out.println("response"+response.toString());
            //TODO
            //BeforeActions.scenario.write("No of records :-" + noOfRecords + Arrays.toString(fields.toArray()));
            Assert.assertTrue(response.toString().contains("\"MESSAGE_TYPE\":\"S\""));
        } catch (Exception e) {
            throw SystemException.throwException(e.getMessage(), e);
        }
        Thread.sleep(6000); //sleep required to wait for SAP record creation

//      Assert.assertEquals(noOfRecords, Integer.parseInt(recordcount));
//    } catch (Exception e) {
//      throw SystemException.throwException(e.getMessage(), e);
//    }
//    Thread.sleep(6000); //sleep required to wait for SAP record creation
    }

    @When("^User fetches the record count of table : \"([^\"]*)\" from SAP$")
    public void user_fetches_the_record_count_of_table_something_from_sap(String table) throws JCoException, InterruptedException {

        sapProps = SAPProperties.getDefault(connection);
        errorCapture = new ErrorCapture(exceptionUtils);
        sapAdapterImpl = new SAPAdapterImpl(errorCapture, connection);


    Map opProps = new HashMap<>();
    opProps.put("RFC", "ZFM_TABLE_COUNT");
    opProps.put("autoCommit", "true");
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode objectNode = mapper.createObjectNode();
      objectNode.put("IM_TABLE",table.toUpperCase());
      JsonNode response = sapAdapterImpl.executeRFC(objectNode.toString(), opProps, "", "");
      System.out.println("Response : "+response.toString());
      sapRecordsCount=Integer.parseInt(response.get("EX_COUNT").toString().replaceAll("\"",""));
        System.out.println("record count : "+sapRecordsCount);
        //TODO
        //BeforeActions.scenario.write("No of records :-" + noOfRecords + Arrays.toString(fields.toArray()));

    } catch (Exception e) {
      throw SystemException.throwException(e.getMessage(), e);
    }
    Thread.sleep(6000); //sleep required to wait for SAP record creation
    }

    @When("^User crates new MTID on \"([^\"]*)\" SAP$")
    public void user_creates_mtid_on_sap(String mtIdDes)
            throws JCoException, InterruptedException {

        if(mt_id==null){
            sapProps = SAPProperties.getDefault(connection);
            errorCapture = new ErrorCapture(exceptionUtils);
            sapAdapterImpl = new SAPAdapterImpl(errorCapture, connection);


            Map opProps = new HashMap<>();
            opProps.put("RFC", "ZRFM_IUUC_CREATE_CONFIG");
            opProps.put("autoCommit", "true");
            try {

                ObjectMapper mapper = new ObjectMapper();
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("IV_SCHEMA_NAME",mtIdDes);
                objectNode.put("IV_SCHEMA_DESCR",mtIdDes);
                objectNode.put("IV_APPLICATION","ZGOOGLE_CDF");
                objectNode.put("IV_REPLAY_JOBS","009");
                objectNode.put("IV_INIT_LOAD_JOBS","009");
                objectNode.put("IV_REPL_MODE","1");
                objectNode.put("IV_SOURCE_SYSTEM_TYPE","SAP");
                objectNode.put("IV_SOURCE_RFCDEST","CQ1");
                objectNode.put("IV_TARGET_RFCDEST","NONE");
                objectNode.put("IV_HANA_INSTANCE","0");
                objectNode.put("IV_HANA_PROXYPORT","000");
                objectNode.put("IV_RFC_CLIENT_ONLY","X");
                objectNode.put("IV_REUSE_SENDER","X");
                objectNode.put("IV_IL_SCENARIOIV_IL_SCENARIO","1");
                objectNode.put("IV_NUM_JOBS_ACPCALC","000");

                JsonNode response = sapAdapterImpl.executeRFC(objectNode.toString(), opProps, "", "");
                mt_id=response.get("EV_MT_ID").asText();
                guid=response.get("EV_CONFIG_GUID").asText();
                System.out.println("MT id : "+mt_id+" guid : "+guid);
                //TODO
                //BeforeActions.scenario.write("No of records :-" + noOfRecords + Arrays.toString(fields.toArray()));


            } catch (Exception e) {
                throw SystemException.throwException(e.getMessage(), e);
            }
            Thread.sleep(6000); //sleep required to wait for SAP record creation
        }
    }

    @When("^User updates mtid config in /GOOG/CDF_R_SLT_SETTINGS program$")
    public void user_updates_mtid_in_program()
            throws JCoException, InterruptedException {

        sapProps = SAPProperties.getDefault(connection);
        errorCapture = new ErrorCapture(exceptionUtils);
        sapAdapterImpl = new SAPAdapterImpl(errorCapture, connection);

        Map opProps = new HashMap<>();
        opProps.put("RFC", "ZRFM_LTRC_ADVANCE_SETTINGS_1");
        opProps.put("autoCommit", "true");
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("IM_TR_KEY",mt_id);
            objectNode.put("IM_MASS_TR_ID",mt_id);
            objectNode.put("IM_EXTRA_FLDS","X");
            objectNode.put("IM_GCP_KEY_NAME",
                    CDAPUtils.getPluginProp("IM_GCP_KEY_NAME"));
            objectNode.put("IM_GCS_BUCKET",
                    CDAPUtils.getPluginProp("gcsDataPath").
                            replaceAll("gs://",""));
            objectNode.put("IM_IS_SET_ACT","X");
//            objectNode.put("IM_CUST_NAMES","X");

            JsonNode response = sapAdapterImpl.executeRFC(objectNode.toString(), opProps, "", "");
            System.out.println("Response : "+response);
            //TODO
            //BeforeActions.scenario.write("No of records :-" + noOfRecords + Arrays.toString(fields.toArray()));


        } catch (Exception e) {
            throw SystemException.throwException(e.getMessage(), e);
        }
        Thread.sleep(10000); //sleep required to wait for SAP record creation
    }

    @When("^User deletes the mtid$")
    public void user_deletes_mtid()
            throws JCoException, InterruptedException {

        sapProps = SAPProperties.getDefault(connection);
        errorCapture = new ErrorCapture(exceptionUtils);
        sapAdapterImpl = new SAPAdapterImpl(errorCapture, connection);

        Map opProps = new HashMap<>();

        opProps.put("RFC", "ZRFM_IUUC_REPL_DELETE_CONFIG");
        opProps.put("autoCommit", "true");
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("IM_MT_ID",mt_id);
            objectNode.put("IV_CONFIG_GUID",guid);
            JsonNode response = sapAdapterImpl.executeRFC(objectNode.toString(), opProps, "", "");
            System.out.println("Response : "+response);
            //TODO
            //BeforeActions.scenario.write("No of records :-" + noOfRecords + Arrays.toString(fields.toArray()));


        } catch (Exception e) {
            throw SystemException.throwException(e.getMessage(), e);
        }
        Thread.sleep(6000); //sleep required to wait for SAP record creation
    }

    @Then("{string} the {string} records with {string} in the sap table")
    public void createTheRecordsInTheODPDatasourceFromJCO(String process, String recordcount, String rfcName)
            throws IOException, JCoException, InterruptedException {
        action = process;
        if (action.equalsIgnoreCase("create")) {
            action = ACT_CREATE;
        } else if (action.equalsIgnoreCase("delete")) {
            action = ACT_DELETE;
        } else if (action.equalsIgnoreCase("update")) {
            action = ACT_UPDATE;
        }
        int dsRecordsCount = Integer.parseInt(recordcount);
        sapProps = SAPProperties.getDefault(connection);
        errorCapture = new ErrorCapture(exceptionUtils);
        sapAdapterImpl = new SAPAdapterImpl(errorCapture, connection);

    Map opProps = new HashMap<>();
    opProps.put("RFC", CDAPUtils.getPluginProp(rfcName));
    opProps.put("autoCommit", "true");
    try {

      ObjectMapper mapper = new ObjectMapper();
      ObjectNode objectNode = mapper.createObjectNode();
      objectNode.put(action, recordcount);
      JsonNode response = sapAdapterImpl.executeRFC(objectNode.toString(), opProps, "", "");
      System.out.println(response.asText());
      int noOfRecords = Integer.parseInt(response.get("EX_COUNT").asText());
      Iterator<JsonNode> iteratedData = response.get("EX_DATA").iterator();
      while (iteratedData.hasNext()) {
        JsonNode object = iteratedData.next();
        Iterator<String> fieldName = object.fieldNames();
        if (fieldName.hasNext()) {
          fields.add(object.get(fieldName.next()).asText());
        }
        Thread.sleep(1000);
      }
      BeforeActions.scenario.write("No of records :-" + noOfRecords + Arrays.toString(fields.toArray()));
//      Assert.assertEquals(noOfRecords, Integer.parseInt(recordcount));
    } catch (Exception e) {
      throw SystemException.throwException(e.getMessage(), e);
    }
    Thread.sleep(6000); //sleep required to wait for SAP record creation
    }






//    @When("^Configure Connection \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
//    public void configure_direct_connection_something_something_something
//            (String baseURL, String serviceName, String entityName) throws Throwable {
//        ODataActions.openODataProperties();
//        ODataActions.enterConnectionProperties(baseURL, serviceName, entityName);
//    }
//
//    @When("^SAP Username and Password is provided$")
//    public void sap_username_and_password_is_provided() throws IOException {
//        ODataActions.enterUserNamePassword(CDAPUtils.getPluginProp("Odata_User"),
//                CDAPUtils.getPluginProp("Odata_Pass"));
//    }
//    @When("^SAP Username and Password is provided cloud$")
//    public void sap_username_and_password_is_provided_cloud() throws IOException {
//        ODataActions.enterUserNamePassword(CDAPUtils.getPluginProp("Odata_UserHANACloud"),
//                CDAPUtils.getPluginProp("Odata_PassHANACloud"));
//    }
//
//    @Then("^Validate the Schema is created$")
//    public void validate_the_schema_is_created() {
//        ODataActions.getSchema();
//        WebDriverWait wait = new WebDriverWait(SeleniumDriver.getDriver(), 120);
//        wait.until(ExpectedConditions.visibilityOf(odataLocators.schemaFieldSecondRow));
//        Assert.assertEquals(true, CDAPUtils.schemaValidation());
//    }
//
//    @Then("^Close the OData Properties$")
//    public void close_the_odata_properties() {
//        ODataActions.closeProperty();
//    }
//
//    @When("Target is BigQuery for OData data transfer")
//    public void targetIsBigQueryForODataTransfer() {
//        CdfStudioActions.sinkBigQuery();
//    }
//
//    @Then("^Enter the BigQuery Properties for OData datasource \"([^\"]*)\"$")
//    public void enter_the_bigquery_properties_for_odata_datasource_something(String tableName) throws Throwable {
//        CdfStudioLocators.bigQueryProperties.click();
//        CdfBigQueryPropertiesActions cdfBigQueryPropertiesActions = new CdfBigQueryPropertiesActions();
//        SeleniumHelper.replaceElementValue(CdfBigQueryPropertiesLocators.projectID,
//                io.cdap.plugin.odp.utils.CDAPUtils.getPluginProp("odataProjectId"));
//        CdfBigQueryPropertiesLocators.bigQueryReferenceName.sendKeys("automation_test");
//        CdfBigQueryPropertiesLocators.datasetProjectID.sendKeys(io.cdap.plugin.odp.utils.CDAPUtils.
//                getPluginProp("odataProjectId"));
//        CdfBigQueryPropertiesLocators.dataset.sendKeys(io.cdap.plugin.odp.utils.CDAPUtils.
//                getPluginProp("dataSetOdp"));
//        CdfBigQueryPropertiesLocators.bigQueryTable.sendKeys(io.cdap.plugin.odp.utils.CDAPUtils.
//                getPluginProp(tableName));
//        CdfBigQueryPropertiesLocators.truncatableSwitch.click();
//        CdfBigQueryPropertiesLocators.updateTable.click();
//        CdfBigQueryPropertiesLocators.validateBttn.click();
//        SeleniumHelper.waitElementIsVisible(CdfBigQueryPropertiesLocators.textSuccess, 1L);
//    }
//
//    @Then("^Close the BigQuery Properties$")
//    public void close_the_bigquery_properties() {
//        CdfGcsActions.closeButton();
//    }
//
//    @Then("^Link source and target$")
//    public void link_source_and_target() {
//        SeleniumHelper.dragAndDrop(ODataLocators.fromOData, CdfStudioLocators.toBigQiery);
//    }
//
//    @Then("^Click on Preview and run$")
//    public void click_on_preview_and_run()  {
//        runAndPreviewData();
//    }
//
//    @Then("^Validate Preview is generated$")
//    public void validate_preview_is_generated()  {
//    }
//
//    @Then("^Fill Filter Options as \"([^\"]*)\"$")
//    public void fill_filter_options_as_something(String filter) {
//        ODataActions.filterOption(filter);
//    }
//
//    @Then("^Fill Select Fields as \"([^\"]*)\"$")
//    public void fill_select_fields_as_something(String selectFields) {
//        ODataActions.selectOption(selectFields);
//    }
//
//    @Then("^Fill Expand Fields as \"([^\"]*)\"$")
//    public void fill_expand_fields_as_something(String expand) {
//        ODataActions.expandOption(expand);
//    }
//
//    @Then("^Fill Number of Rows to Skip as \"([^\"]*)\"$")
//    public void fill_number_of_rows_to_skip_as_1(String  numOfRows) {
//        ODataActions.skipRowCount(numOfRows);
//    }
//
//
//    @Then("^Fill Number of Rows to Fetch as \"([^\"]*)\"$")
//    public void fill_number_of_rows_to_fetch_as_100(String rowCount) {
//        ODataActions.numRowsToFetch(rowCount);
//    }
//
//    @Then("^Fill Number of Splits to Generate as \"([^\"]*)\"$")
//    public void fill_number_of_splits_to_generate_as_8(String split) {
//        ODataActions.splitCount(split);
//    }
//
//    @Then("^Fill Batch Size as \"([^\"]*)\"$")
//    public void fill_batch_size_as_5000(String batch) {
//        ODataActions.batchSize(batch);
//    }
//
//
//    @When("^Fill certificate Path \"([^\"]*)\" and paraphrase \"([^\"]*)\"$")
//    public void fill_certificate_path_something_and_paraphrase_something(String certPath, String paraphrase) throws IOException {
//        ODataActions.certGcsPath(CDAPUtils.getPluginProp(certPath));
//        ODataActions.certPassphrase(CDAPUtils.getPluginProp(paraphrase));
//    }
}
