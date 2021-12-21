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

  @OData @DesignTime-TC-OData-DSGN-01(Direct)
  Scenario:User configured direct connection parameters and Security parameters by providing values on SAP UI(ENV)
    Given Open CDF application
    When Source is SAP OData
    When Configure Direct Connection "baseURL" "serviceName" "entityName"
    When SAP Username and Password is provided
    Then Validate the Schema is created

  @OData1 @DesignTime-TC-OData-DSGN-01(Direct)
  Scenario:User configured direct connection parameters and Security parameters
    Given Open CDF application
    When Source is SAP OData
    When Configure Direct Connection "baseURL" "serviceName" "entityName"
    When SAP Username and Password is provided
    Then Validate the Schema is created
