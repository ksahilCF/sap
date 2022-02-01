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
package io.cdap.plugin.odata.actions;

import io.cdap.e2e.utils.SeleniumDriver;
import io.cdap.e2e.utils.SeleniumHelper;
import io.cdap.plugin.odata.locators.ODataLocators;
import io.cdap.plugin.odata.utils.CDAPUtils;
import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;
import java.util.UUID;

/**
 * ODataActions.
 */
public class ODataActions {
  public static ODataLocators odataLocators;
  private static JavascriptExecutor js;

  static {
    js = (JavascriptExecutor) SeleniumDriver.getDriver();
    odataLocators = SeleniumHelper.getPropertiesLocators(ODataLocators.class);
  }

  public static void selectODataSource() {
    odataLocators.sapODataSource.click();
  }

  public static void openODataProperties() {

    odataLocators.sapODataProperties.click();
  }

  public static void enterConnectionProperties(
    String baseURL, String serviceName, String entityName
  ) throws IOException {
    odataLocators.referenceName.sendKeys(UUID.randomUUID().toString());
    odataLocators.baseURL.sendKeys(CDAPUtils.getPluginProp(baseURL));
    odataLocators.serviceName.sendKeys(CDAPUtils.getPluginProp(serviceName));
    odataLocators.entityName.sendKeys(CDAPUtils.getPluginProp(entityName));

//    odataLocators.dataSourceName.sendKeys(CDAPUtils.getPluginProp(dsName));
//    js.executeScript("window.scrollBy(0,350)", StringUtils.EMPTY);
//    odataLocators.gcsPath.sendKeys(CDAPUtils.getPluginProp(gcsPath));
//    odataLocators.splitRow.sendKeys(CDAPUtils.getPluginProp(splitRow));
//    odataLocators.packageSize.sendKeys(null != CDAPUtils.getPluginProp(packSize) ?
//                                       CDAPUtils.getPluginProp(packSize) : StringUtils.EMPTY);
  }

  public static void enterUserNamePassword(String user, String pass) throws IOException {
    odataLocators.username.sendKeys(user);
    odataLocators.password.sendKeys(pass);
  }

  public static void getSchema() {
    odataLocators.getSchema.click();
  }

  public static void validateConnection(){
    odataLocators.validate.click();
  }
  public static void closeProperty() {
    odataLocators.closeProperty.click();
  }

  public static void skipRowCount(String skipRowCount) {
    odataLocators.skipRowCount.sendKeys(skipRowCount);
  }

  public static void expandOption(String expandOption) {
    odataLocators.expandOption.sendKeys(expandOption);
  }

  public static void numRowsToFetch(String numRowsToFetch) {
    odataLocators.numRowsToFetch.sendKeys(numRowsToFetch);
  }

  public static void splitCount(String splitCount) {

    odataLocators.splitCount.sendKeys(splitCount);
  }

  public static void filterOption(String filterOption) {
    odataLocators.filterOption.sendKeys(filterOption);
  }

  public static void batchSize(String batchSize)  {
    odataLocators.batchSize.sendKeys(batchSize);
  }

  public static void selectOption(String selectOption) {
    odataLocators.selectOption.sendKeys(selectOption);
  }

  public static String rowErrorColor() {
    return odataLocators.rowError.getCssValue("border-color");
  }

  public static String rowErrorMessage() {
    return odataLocators.rowError.getText();
  }

  public static String errorMessage() {
    return ODataLocators.errorMessage.getText();
  }

  public static void previewData() {
    odataLocators.previewData.click();
  }

  public static void certGcsPath(String certGcsPath) {
    odataLocators.certGcsPath.sendKeys(certGcsPath);
  }

  public static void certPassphrase(String certPassphrase) {
    odataLocators.certPassphrase.sendKeys(certPassphrase);
  }
}
