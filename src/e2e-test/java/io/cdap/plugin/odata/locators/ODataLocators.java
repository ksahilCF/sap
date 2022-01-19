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
package io.cdap.plugin.odata.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

/**
 * ODataLocators.
 */
public class ODataLocators {

  @FindBy(how = How.XPATH, using = "//*[@data-cy=\"plugin-SapOData-batchsource\"]")
  public static WebElement sapODataSource;

  @FindBy(how = How.XPATH, using = "//*[@title=\"SapOData\"]/following-sibling::div")
  public static WebElement sapODataProperties;

  @FindBy(how = How.XPATH, using = "//input[@data-cy=\"referenceName\"]")
  public static WebElement referenceName;

  @FindBy(how = How.XPATH, using = "//input[@data-cy=\"baseURL\"]")
  public static WebElement baseURL;

  @FindBy(how = How.XPATH, using = "//input[@data-cy=\"serviceName\"]")
  public static WebElement serviceName;

  @FindBy(how = How.XPATH, using = "//input[@data-cy=\"entityName\"]")
  public static WebElement entityName;

  @FindBy(how = How.XPATH, using = "//input[@data-cy=\"username\"]")
  public static WebElement username;

  @FindBy(how = How.XPATH, using = "//input[@data-cy=\"password\"]")
  public static WebElement password;

  @FindBy(how = How.XPATH, using = "//*[@data-cy=\"get-schema-btn\"]")
  public static WebElement getSchema;

  @FindBy(how = How.XPATH, using = "//*[@placeholder=\"Field name\"]")
  public static List<WebElement> schemaFields;

  @FindBy(how = How.XPATH, using = "(//*[@placeholder=\"Field name\"])[2]")
  public static WebElement schemaFieldSecondRow;

  @FindBy(how = How.XPATH, using = "//*[@data-testid=\"close-config-popover\"]")
  public static WebElement closeProperty;

  @FindBy(how = How.XPATH, using = "//*[@data-cy='plugin-endpoint-SAP OData-batchsource-right']")
  public static WebElement fromOData;

  @FindBy(how = How.XPATH, using = "//*[@data-cy='Succeeded']")
  public static List<WebElement> succeeded;

  @FindBy(how = How.XPATH, using = "//*[@data-cy='Failed']")
  public static WebElement failed;

  @FindBy(how = How.XPATH, using = "//input[@data-cy='skipRowCount']")
  public static WebElement skipRowCount;

  @FindBy(how = How.XPATH, using = "//input[@data-cy='expandOption']")
  public static WebElement expandOption;

  @FindBy(how = How.XPATH, using = "//input[@data-cy='numRowsToFetch']")
  public static WebElement numRowsToFetch;

  @FindBy(how = How.XPATH, using = "//input[@data-cy='splitCount']")
  public static WebElement splitCount;

  @FindBy(how = How.XPATH, using = "//input[@data-cy='batchSize']")
  public static WebElement batchSize;

  @FindBy(how = How.XPATH, using = "//textarea[@data-cy='filterOption']")
  public static WebElement filterOption;

  @FindBy(how = How.XPATH, using = "//textarea[@data-cy='selectOption']")
  public static WebElement selectOption;
}
