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
Feature: Design Time OData Scenario

  @OData @Design-Time @Sanity
  Scenario:User configured connection parameters and Security parameters
    Given Open CDF application
    When Source is SAP OData
    When Configure Connection "baseURL" "serviceName" "entityName"
    When SAP Username and Password is provided
    Then Validate the Schema is created

  @OData @Design-Time @Sanity
  Scenario:User configured advanced parameters and Security parameters
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "hierarchyServiceName" "hierarchyEntityName"
    When SAP Username and Password is provided
    Then Fill Filter Options as "HierarchyNode eq '10000022000'"
    Then Fill Select Fields as "GLAccountHierarchy,HierarchyNode,ParentNode,to_Text/GLAccountHierarchy"
    Then Fill Expand Fields as "to_Text"
    Then Fill Number of Rows to Skip as "10"
    Then Fill Number of Rows to Fetch as "100"
    Then Fill Number of Splits to Generate as "8"
    Then Fill Batch Size as "5000"
    Then Validate the Schema is created

  @OData @Design-Time @On-prem @Sanity
  Scenario:User configured and Preview Custom OData
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "allDataTypeServiceName" "allDataTypeEntityName"
    When SAP Username and Password is provided
    Then Fill Number of Rows to Skip as "10"
    Then Fill Number of Rows to Fetch as "100"
    Then Fill Number of Splits to Generate as "8"
    Then Fill Batch Size as "5000"
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Click on Preview and run
    Then Validate Preview is generated
    Then Wait till preview pipeline is in running state
    Then Verify preview data is present

  @OData @Design-Time @On-prem @Sanity
  Scenario:User configured and Preview Standard OData
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "serviceName3" "entityName3"
    When SAP Username and Password is provided
    Then Fill Number of Rows to Skip as "10"
    Then Fill Number of Rows to Fetch as "100"
    Then Fill Number of Splits to Generate as "8"
    Then Fill Batch Size as "5000"
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Click on Preview and run
    Then Validate Preview is generated
    Then Wait till preview pipeline is in running state
    Then Verify preview data is present


  @OData @Design-Time @On-prem @Sanity
  Scenario:User configured and Preview Hierarchical OData
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "baseURL" "hierarchyServiceName" "hierarchyEntityName"
    When SAP Username and Password is provided
    Then Fill Expand Fields as "to_Text"
    Then Fill Number of Rows to Skip as "10"
    Then Fill Number of Rows to Fetch as "100"
    Then Fill Number of Splits to Generate as "8"
    Then Fill Batch Size as "5000"
    Then Validate the Schema is created
    Then Close the OData Properties
    Then Enter the BigQuery Properties for OData datasource "tableDemo"
    Then Close the BigQuery Properties
    Then Link source and target
    Then Click on Preview and run
    Then Validate Preview is generated
    Then Wait till preview pipeline is in running state
    Then Verify preview data is present

  @OData @Design-Time @On-prem @Sanity
  Scenario:User is able to configure SAP X.509 Client Certificate
    Given Open CDF application
    When Source is SAP OData
    When Target is BigQuery for OData data transfer
    When Configure Connection "httpsbaseURL" "hierarchyServiceName" "hierarchyEntityName"
    When Fill certificate Path "certPath" and paraphrase "paraphrase"
    When SAP Username and Password is provided
    Then Validate the Schema is created

