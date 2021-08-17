/*
 * Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.dataspaceconnector.service.configuration;

import io.dataspaceconnector.model.appstore.AppStore;
import io.dataspaceconnector.model.appstore.AppStoreDesc;
import io.dataspaceconnector.repository.AppStoreRepository;
import io.dataspaceconnector.service.resource.BaseEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for app stores.
 */
@Service
public class AppStoreService extends BaseEntityService<AppStore, AppStoreDesc> {

    /**
     * Get AppStores which are offering the given App.
     *
     * @param appId id of the app to find related appstore for.
     * @param pageable pageable for response as view.
     * @return Page containing AppStores which are offering an app with AppID.
     */
    public Page<AppStore> getStoresByContainsApp(final UUID appId, final Pageable pageable) {
        return ((AppStoreRepository) getRepository())
         .findAppStoresWithPaginationByApps(appId, pageable);
    }
}