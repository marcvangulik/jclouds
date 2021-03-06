/**
 *
 * Copyright (C) 2011 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */
package org.jclouds.terremark.ecloud.functions;

import java.net.URI;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.terremark.ecloud.domain.TerremarkECloudOrg;
import org.jclouds.vcloud.domain.Org;
import org.jclouds.vcloud.terremark.domain.TerremarkOrg;
import org.jclouds.vcloud.terremark.functions.OrgURIToEndpoint;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

/**
 * 
 * @author Adrian Cole
 */
@Singleton
public class OrgURIToTagsListEndpoint extends OrgURIToEndpoint implements Function<Object, URI> {
   @Inject
   public OrgURIToTagsListEndpoint(Supplier<Map<String, ? extends Org>> orgMap,
         @org.jclouds.vcloud.endpoints.Org URI defaultUri) {
      super(orgMap, defaultUri);
   }

   public URI getUriFromOrg(TerremarkOrg org) {
      return TerremarkECloudOrg.class.cast(org).getTags().getHref();
   }

}