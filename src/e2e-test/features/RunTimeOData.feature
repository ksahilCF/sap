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

  @OData @RunTime-TC-OData-DSGN-02(Direct)
  Scenario:User configured direct connection parameters and Security parameters
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Direct Connection "baseURL" "serviceName" "entityName"
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

  @OData @RunTime-TC-OData-DSGN-03(Direct)
  Scenario:User configured direct connection parameters and Security parameters Count mismatch
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Direct Connection "baseURL" "serviceName" "entityName"
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
#    Then Get Count of no of records transferred from OData to BigQuery in "tableDemo"
    Then Verify the Data load transfer is successful
    Then Clear the parameters

  @ODataRun @RunTime-TC-OData-DSGN-03(Direct)
  Scenario:User configured direct connection parameters and advanced parameters Count mismatch
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Direct Connection "baseURL" "serviceName" "entityName"
    When SAP Username and Password is provided
    Then Fill Filter Options as "filetr"
    Then Fill Select Fields as "select"
    Then Fill Expand Fields as "expand"
    Then Fill Number of Rows to Skip as "10"
    Then Fill Number of Rows to Fetch as "100"
    Then Fill Number of Splits to Generate as "8"
    Then Fill Batch Size as "5000"
    Then Validate the Schema is created

  @ODataRun @RunTime-TC-OData-DSGN-04(Direct)
  Scenario:User configured Hierarchical OData
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Direct Connection "baseURL" "hierarchyServiceName" "hierarchyEntityName"
    When SAP Username and Password is provided
#    Then Fill Filter Options as "filetr"
#    Then Fill Select Fields as "select"
#    Then Fill Expand Fields as "to_Text"
#    Then Fill Number of Rows to Skip as "10"
#    Then Fill Number of Rows to Fetch as "100"
#    Then Fill Number of Splits to Generate as "8"
#    Then Fill Batch Size as "5000"
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




  @ODataRun @RunTime-TC-OData-DSGN-04(Direct)
  Scenario:User configured Hana Cloud OData service
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Direct Connection "baseURLCloud" "serviceNameCloud" "entityNameCloud"
    When SAP Username and Password is provided cloud
#    Then Fill Filter Options as "filetr"
#    Then Fill Select Fields as "select"
#    Then Fill Expand Fields as "to_Text"
#    Then Fill Number of Rows to Skip as "10"
#    Then Fill Number of Rows to Fetch as "100"
#    Then Fill Number of Splits to Generate as "8"
#    Then Fill Batch Size as "5000"
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




