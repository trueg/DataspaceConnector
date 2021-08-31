#!/usr/bin/env python3
#
# Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from resourceapi import ResourceApi
from idsapi import IdsApi
import requests
import pprint
import sys

providerUrl = "https://connector1.drm.10.1.101.78.nip.io"
consumerUrl = "https://connector2.drm.10.1.101.78.nip.io"
client_url = "http://web-request-logger:3030"

# Suppress ssl verification warning
requests.packages.urllib3.disable_warnings()

# Provider
provider = ResourceApi(providerUrl)

## Create resources
catalog = provider.create_catalog({"title": "Stuff"})
offers = provider.create_offered_resource()
representation = provider.create_representation()
artifact = provider.create_artifact(data={"value": "Test value"})
contract = provider.create_contract()
use_rule = provider.create_rule()

## Link resources
provider.add_resource_to_catalog(catalog, offers)
provider.add_representation_to_resource(offers, representation)
provider.add_artifact_to_representation(representation, artifact)
provider.add_contract_to_resource(offers, contract)
provider.add_rule_to_contract(contract, use_rule)

print("Created provider resources")

# Consumer
consumer = IdsApi(consumerUrl)

# IDS
# Call description
offer = consumer.descriptionRequest(providerUrl + "/api/ids/data", offers)
pprint.pprint(offer)

# Negotiate contract
obj = offer["ids:contractOffer"][0]["ids:permission"][0]
obj["ids:target"] = artifact
response = consumer.contractRequest(
    providerUrl + "/api/ids/data", offers, artifact, False, obj
)
pprint.pprint(response)

# Pull data
agreement = response["_links"]["self"]["href"]

consumerResources = ResourceApi(consumerUrl)
artifacts = consumerResources.get_artifacts_for_agreement(agreement)
pprint.pprint(artifacts)

first_artifact = artifacts["_embedded"]["artifacts"][0]["_links"]["self"]["href"]
pprint.pprint(first_artifact)

data = consumerResources.get_data(first_artifact).text
pprint.pprint(data)

# subscribe to provider
consumer.subscriptionRequest(providerUrl + "/api/ids/data", artifact)

# client subscription
ResourceApi(consumerUrl).create_subscription(first_artifact, client_url)
