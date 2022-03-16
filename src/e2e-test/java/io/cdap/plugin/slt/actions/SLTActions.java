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
package io.cdap.plugin.slt.actions;

import io.cdap.e2e.utils.SeleniumDriver;
import io.cdap.e2e.utils.SeleniumHelper;
import io.cdap.plugin.odata.locators.ODataLocators;
import io.cdap.plugin.slt.locators.SLTLocators;
import io.cdap.plugin.slt.utils.CDAPUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import scala.util.parsing.combinator.testing.Str;

import java.io.IOException;

/**
 * SLT Actions.
 */
public class SLTActions {
    public static SLTLocators sltLocators = new SLTLocators();
//    private static JavascriptExecutor js;
    private static WebDriver CDFDriver;

    static {
        CDFDriver = SeleniumDriver.getDriver();
        sltLocators = SeleniumHelper.getPropertiesLocators(SLTLocators.class);

    }
    public static void name_fill(String name){
        sltLocators.name.sendKeys(name);
    }
    public void required_config(String gcpProjectId,
                                String gcsDataPath,
                                String massTransferId,
                                String sapJcoLibGcsPath,
                                String jcoclienthost,
                                String jcoclient,
                                String jcoclientsysnr,
                                String jcoclientuser,
                                String jcoclientpasswd) throws IOException {
        SeleniumHelper.replaceElementValue(sltLocators.gcpProjectId,
                CDAPUtils.getPluginProp(gcpProjectId));
        sltLocators.gcsDataPath.sendKeys(CDAPUtils.getPluginProp(gcsDataPath));
        sltLocators.massTransferId.sendKeys(massTransferId);
        sltLocators.sapJcoLibGcsPath.sendKeys(CDAPUtils.getPluginProp(sapJcoLibGcsPath));
        sltLocators.jcoclienthost.sendKeys(CDAPUtils.getPluginProp(jcoclienthost));
        sltLocators.jcoclient.sendKeys(CDAPUtils.getPluginProp(jcoclient));
        sltLocators.jcoclientsysnr.sendKeys(CDAPUtils.getPluginProp(jcoclientsysnr));
        sltLocators.jcoclientuser.sendKeys(CDAPUtils.getPluginProp(jcoclientuser));
        sltLocators.jcoclientpasswd.sendKeys(CDAPUtils.getPluginProp(jcoclientpasswd));
    }
    public void sltplugin_click(){
        sltLocators.sltplugin.click();
    }
    public void next_click(){
        sltLocators.next.click();
    }

    public void select_table(String table){
        CDFDriver.findElement(By.xpath("//div[contains(text(),\""+
                table+"\")]/preceding-sibling::div//input")).
                click();
    }

    public void datasetName_fill (String stagingBucket){
        sltLocators.datasetName.sendKeys(stagingBucket);
    }

    public void DeployPipeline_click(){
        sltLocators.DeployPipeline.click();
    }

    public void start_pipeline(){
        SeleniumHelper.waitAndClick(sltLocators.start, 60L);
    }

    public void stop_pipeline(){
        sltLocators.stop.click();
    }

    public void replicateExistingData_click(){
        sltLocators.replicateExistingData.click();
    }

    public String replicateExistingData_getText(){
        return sltLocators.replicateExistingData.getText();
    }

    public void suspendSltJob_click(){
        sltLocators.suspendSltJob.click();
    }

    public String suspendSltJob_getText(){
        return sltLocators.suspendSltJob.getText();
    }
}
