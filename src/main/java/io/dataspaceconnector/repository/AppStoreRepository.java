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
package io.dataspaceconnector.repository;

import io.dataspaceconnector.model.appstore.AppStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The repository containing all objects of
 * type {@link io.dataspaceconnector.model.appstore.AppStore}.
 */
@Repository
public interface AppStoreRepository extends BaseEntityRepository<AppStore> {

    /**
     * Get all related appstores for an appid.
     *
     * @param id app id for which relative appstores should be found.
     * @param pageable pageable for portioning response.
     * @return pageable of related appstores.
     */
    Page<AppStore> findAppStoresWithPaginationByApps(UUID id, Pageable pageable);

}