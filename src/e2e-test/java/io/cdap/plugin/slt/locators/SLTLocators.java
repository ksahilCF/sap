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

package io.cdap.plugin.slt.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
/**
 * SLT Locators.
 */
public class SLTLocators {

    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"name\"]")
    public static WebElement name;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"gcpProjectId\"]")
    public static WebElement gcpProjectId;
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'SAP Landscape Transformation (SLT)')]")
    public static WebElement sltplugin;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"gcsDataPath\"]")
    public static WebElement gcsDataPath;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"guid\"]")
    public static WebElement guid;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"massTransferId\"]")
    public static WebElement massTransferId;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"sapJcoLibGcsPath\"]")
    public static WebElement sapJcoLibGcsPath;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"jco.client.ashost\"]")
    public static WebElement jcoclienthost;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"jco.client.client\"]")
    public static WebElement jcoclient;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"jco.client.sysnr\"]")
    public static WebElement jcoclientsysnr;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"jco.client.user\"]")
    public static WebElement jcoclientuser;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"jco.client.passwd\"]")
    public static WebElement jcoclientpasswd;
    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Next')]")
    public static WebElement next;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"project\"]")
    public static WebElement project;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"datasetName\"]")
    public static WebElement datasetName;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"encryptionKeyName\"]")
    public static WebElement encryptionKeyName;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"stagingBucket\"]")
    public static WebElement stagingBucket;
    @FindBy(how = How.XPATH, using = "//input[@data-cy=\"loadInterval\"]")
    public static WebElement loadInterval;
    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Select tables')]")
    public static WebElement Select;
    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Configure target')]")
    public static WebElement Configure;
    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Configure advanced properties')]")
    public static WebElement Configureadvance;
    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Review assessment')]")
    public static WebElement Review;
    @FindBy(how = How.XPATH, using = "//span[contains(text(),'View summary')]")
    public static WebElement viewsummary;
    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Deploy Replication Job')]")
    public static WebElement DeployPipeline;
    @FindBy(how = How.XPATH, using = "//*[contains(@class, 'icon-play ')]")
    public static WebElement start;
    @FindBy(how = How.XPATH, using = "//*[contains(text(), 'Logs')]")
    public static WebElement Logs;
    @FindBy(how = How.XPATH, using = "(//*[contains(text(), 'View')])[1]")
    public static WebElement AdvancedLogs;
    @FindBy(how = How.XPATH, using = "//*[contains(text(), 'Running')]")
    public static WebElement Running;
    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Polling next set of files using prefix')]")
    public static WebElement replication;
    @FindBy(how = How.XPATH, using = "//*[contains(@class, 'icon-stop')]")
    public static WebElement stop;
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'ERROR')]")
    public static WebElement error;
    @FindBy(how = How.XPATH, using = "(//div[contains(text(),'ERROR')])[3]")
    public static WebElement error3;
    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Loading batch')")
    public static WebElement loaded;
    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Stopped')]")
    public static WebElement stopped;
    @FindBy(how = How.XPATH, using = "//*[@class=\"text-danger\"]/span")
    public static WebElement rowError;
    @FindBy(how = How.XPATH, using = "//*[@data-cy=\"switch-replicateExistingData\"]/div[2]")
    public static WebElement replicateExistingData;
    @FindBy(how = How.XPATH, using = "//*[@data-cy=\"switch-suspendSltJob\"]/div[2]")
    public static WebElement suspendSltJob;
    @FindBy(how = How.XPATH, using = "//*[contains(text(),'View mappings')]")
    public static WebElement viewmappings;




}