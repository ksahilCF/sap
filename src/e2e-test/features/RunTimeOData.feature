# Copyright © 2021 Cask Data, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may not
# use this file except in compliance with the License. You may obtain a copy of
# the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations under
# the License.
Feature: Run Time OData Scenario

  @OData @Run-Time @On-prem  @Sanity
  Scenario:Test All Data Type is supported and Plugin is able to fetch records from Custom OData services
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "allDataTypeServiceName" "allDataTypeEntityName"
    When SAP Username and Password is provided
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Save and Deploy OData Pipeline
    Then Run the OData Pipeline in Runtime
    Then Wait till OData pipeline is in running state
    Then Open Logs of OData Pipeline
    Then Verify the OData pipeline status is "Succeeded"
    Then validate successMessage is displayed for the OData pipeline
    Then Get Count of no of records transferred from OData to BigQuery in "tableDemo"
    Then Verify the Data load transfer is successful
    Then Clear the parameters

  @OData @Run-Time @On-prem
  Scenario:User is able to fetch records from custom SAP OData services
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "serviceName" "entityName"
    When SAP Username and Password is provided
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Save and Deploy OData Pipeline
    Then Run the OData Pipeline in Runtime
    Then Wait till OData pipeline is in running state
    Then Open Logs of OData Pipeline
    Then Verify the OData pipeline status is "Succeeded"
    Then validate successMessage is displayed for the OData pipeline
    Then Get Count of no of records transferred from OData to BigQuery in "tableDemo"
    Then Verify the Data load transfer is successful
    Then Clear the parameters

  @OData @Run-Time @On-prem
  Scenario:User is able to fetch records from standard SAP OData services
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "serviceName3" "entityName3"
    When SAP Username and Password is provided
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Save and Deploy OData Pipeline
    Then Run the OData Pipeline in Runtime
    Then Wait till OData pipeline is in running state
    Then Open Logs of OData Pipeline
    Then Verify the OData pipeline status is "Succeeded"
    Then validate successMessage is displayed for the OData pipeline
    Then Get Count of no of records transferred from OData to BigQuery in "tableDemo"
    Then Verify the Data load transfer is successful
    Then Clear the parameters

  @OData @Run-Time @On-prem
  Scenario:User configured connection parameters and Security parameters and match BQ record Count
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "serviceName2" "entityName2"
    When SAP Username and Password is provided
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Save and Deploy OData Pipeline
    Then Run the OData Pipeline in Runtime
    Then Wait till OData pipeline is in running state
    Then Open Logs of OData Pipeline
    Then Verify the OData pipeline status is "Succeeded"
    Then validate successMessage is displayed for the OData pipeline
    Then Get Count of no of records transferred from OData to BigQuery in "tableDemo"
    Then Verify the Data load transfer is successful
    Then Clear the parameters

  @OData @Run-Time @On-prem @Sanity
  Scenario:User is able able to extract filtered data
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "serviceNameFilter" "entityNameFilter"
    When SAP Username and Password is provided
    Then Fill Filter Options as "PurchaseOrder eq '4500000010'"
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Save and Deploy OData Pipeline
    Then Run the OData Pipeline in Runtime
    Then Wait till OData pipeline is in running state
    Then Open Logs of OData Pipeline
    Then Verify the "logsRecExtract" is equal to "1"
    Then Verify the OData pipeline status is "Succeeded"
    Then validate successMessage is displayed for the OData pipeline
    Then Get Count of no of records transferred from OData to BigQuery in "tableDemo"
    Then Verify the Data load transfer is successful
    Then Clear the parameters

  @OData @Run-Time @On-prem
  Scenario:User configured connection parameters and advanced parameters Select Field
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "serviceName" "entityName"
    When SAP Username and Password is provided
    Then Fill Select Fields as "PurchaseOrder,PurchaseOrderItem,Supplier"
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Save and Deploy OData Pipeline
    Then Run the OData Pipeline in Runtime
    Then Wait till OData pipeline is in running state
    Then Open Logs of OData Pipeline
    Then Verify the OData pipeline status is "Succeeded"
    Then validate successMessage is displayed for the OData pipeline
    Then Get Count of no of records transferred from OData to BigQuery in "tableDemo"
    Then Verify the Data load transfer is successful
    Then Clear the parameters

    #TODO Advance Prop
  @OData @Run-Time @On-prem
  Scenario:User is able to configure advanced parameters like Number of Rows to Skip, Number of Rows to Fetch
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "serviceName" "entityName"
    When SAP Username and Password is provided
    Then Fill Number of Rows to Skip as "10"
    Then Fill Number of Rows to Fetch as "200"
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Save and Deploy OData Pipeline
    Then Run the OData Pipeline in Runtime
    Then Wait till OData pipeline is in running state
    Then Open Logs of OData Pipeline
    Then Verify the "totalWP" is more than "0"
    Then Verify the "logsSkip" is equal to "10"
    Then Verify the "logsRecExtract" is equal to "200"
    Then Verify the OData pipeline status is "Succeeded"
    Then validate successMessage is displayed for the OData pipeline
    Then Get Count of no of records transferred from OData to BigQuery in "tableDemo"
    Then Verify the Data load transfer is successful
    Then Clear the parameters

  @OData @Run-Time @On-prem @Sanity
  Scenario:User configured performance parameters like Number of Splits to Generate & Batch Size
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "serviceName" "entityName"
    When SAP Username and Password is provided
    Then Fill Number of Rows to Skip as "10"
    Then Fill Number of Rows to Fetch as "200"
    Then Fill Number of Splits to Generate as "10"
    Then Fill Batch Size as "10"
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Save and Deploy OData Pipeline
    Then Run the OData Pipeline in Runtime
    Then Wait till OData pipeline is in running state
    Then Open Logs of OData Pipeline
    Then Verify the "totalWP" is more than "0"
    Then Verify the "logsSkip" is equal to "10"
    Then Verify the "logsRecExtract" is equal to "200"
    Then Verify the "logsRecPerSplit" is equal to "10"
#    Then Verify the "calculatedWP" is equal to "10"
    Then Verify the OData pipeline status is "Succeeded"
    Then validate successMessage is displayed for the OData pipeline
    Then Get Count of no of records transferred from OData to BigQuery in "tableDemo"
    Then Verify the Data load transfer is successful
    Then Clear the parameters

  @OData @Run-Time @On-prem
  Scenario:User configured Hierarchical OData
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "hierarchyServiceName" "hierarchyEntityName"
    When SAP Username and Password is provided
    Then Fill Expand Fields as "to_Text"
    Then Fill Number of Rows to Fetch as "100"
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Save and Deploy OData Pipeline
    Then Run the OData Pipeline in Runtime
    Then Wait till OData pipeline is in running state
    Then Open Logs of OData Pipeline
    Then Verify the "logsRecExtract" is equal to "100"
    Then Verify the OData pipeline status is "Succeeded"
    Then validate successMessage is displayed for the OData pipeline
    Then Get Count of no of records transferred from OData to BigQuery in "tableDemo"
    Then Verify the Data load transfer is successful
    Then Clear the parameters

  @OData @Run-Time @On-prem @Sanity
  Scenario:User configured Hierarchical OData advanced parameters Select and expand
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "hierarchyServiceName" "hierarchyEntityName"
    When SAP Username and Password is provided
    Then Fill Select Fields as "GLAccountHierarchy,HierarchyNode,ParentNode,to_Text/GLAccountHierarchy"
    Then Fill Expand Fields as "to_Text"
    Then Fill Number of Rows to Fetch as "100"
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Save and Deploy OData Pipeline
    Then Run the OData Pipeline in Runtime
    Then Wait till OData pipeline is in running state
    Then Open Logs of OData Pipeline
    Then Verify the "logsRecExtract" is equal to "100"
    Then Verify the OData pipeline status is "Succeeded"
    Then validate successMessage is displayed for the OData pipeline
    Then Get Count of no of records transferred from OData to BigQuery in "tableDemo"
    Then Verify the Data load transfer is successful
    Then Clear the parameters

  @OData @Run-Time @On-prem @Sanity
  Scenario:User is able to configure run pipeline using SAP X.509 Client Certificate
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "httpsbaseURL" "hierarchyServiceName" "hierarchyEntityName"
    When SAP Username and Password is provided
    When Fill certificate Path "certPath" and paraphrase "paraphrase"
    Then Fill Number of Rows to Fetch as "100"
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Save and Deploy OData Pipeline
    Then Run the OData Pipeline in Runtime
    Then Wait till OData pipeline is in running state
    Then Open Logs of OData Pipeline
    Then Verify the "logsRecExtract" is equal to "100"
    Then Verify the OData pipeline status is "Succeeded"
    Then validate successMessage is displayed for the OData pipeline
    Then Get Count of no of records transferred from OData to BigQuery in "tableDemo"
    Then Verify the Data load transfer is successful
    Then Clear the parameters

