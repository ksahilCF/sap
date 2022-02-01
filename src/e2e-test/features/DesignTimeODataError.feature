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
Feature: Design Time OData Negative Scenario

  @OData @Design-Time @Negative-Test1
  Scenario Outline: User is able to view error messages for invalid "<parameter>" parameters
    Given Open CDF application
    When Source is SAP OData
    When Configure Connection "baseURL" "serviceName" "entityName"
    When SAP Username and Password is provided
    Then "<parameter>" as "<value>" and getting inline error message "<errorMessage>"
    Examples:
      | parameter     | value | errorMessage       |
      |baseURL        |       |nobaseURL           |
      |serviceName    |       |noserviceName       |
      |entityName     |       |noentityName        |
      |baseURL        |invalid|invalidbaseURL      |
      |serviceName    |invalid|invalidserviceName  |
      |username       |       |nousername          |
      |username       |invalid|invalidusername     |
      |password       |       |nopassword          |
      |password       |invalid|invalidpassword     |
      |certGcsPath    |wrong  |missingParaPhrase   |
      |skipRowCount   |-1     |invalidskipRowCount |
      |numRowsToFetch |-1     |invalidnumRowsToFetch|
      |splitCount     |-1     |invalidsplitCount   |
      |batchSize      |-1     |invalidbatchSize    |


  @OData @Design-Time @Negative-Test
  Scenario Outline: User is able to view error messages for invalid "<parameter>" parameters
    Given Open CDF application
    When Source is SAP OData
    When Configure Connection "baseURL" "serviceName" "entityName"
    When SAP Username and Password is provided
    Then "<parameter>" as "<value>" and getting error message "<errorMessage>"
    Examples:
      | parameter     | value | errorMessage       |
      |entityName     |invalid|invalidentityName   |
      |expandOption   |invalid|invalidexpandOption |

  @OData @Design-Time @Negative-Test
  Scenario: User is able to view error messages for invalid Filter Options
    Given Open CDF application
    When Source is SAP OData
    When Configure Connection "baseURL" "serviceName" "entityName"
    When SAP Username and Password is provided
    Then Filter Options as "invalid" and getting error message "invalidFilter"

  @OData @Design-Time @Negative-Test
  Scenario: User is able to view error messages for invalid Select Fields
    Given Open CDF application
    When Source is SAP OData
    When Configure Connection "baseURL" "serviceName" "entityName"
    When SAP Username and Password is provided
    Then Select Fields as "invalid" and getting error message "invalidSelect"


