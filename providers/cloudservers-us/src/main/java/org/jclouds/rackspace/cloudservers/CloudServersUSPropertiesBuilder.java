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
package org.jclouds.rackspace.cloudservers;

import static org.jclouds.Constants.PROPERTY_API_VERSION;
import static org.jclouds.Constants.PROPERTY_ENDPOINT;
import static org.jclouds.Constants.PROPERTY_ISO3166_CODES;

import java.util.Properties;

import org.jclouds.PropertiesBuilder;
import org.jclouds.openstack.OpenStackAuthAsyncClient;

/**
 * 
 * @author Adrian Cole
 */
public class CloudServersUSPropertiesBuilder extends PropertiesBuilder {

   @Override
   protected Properties defaultProperties() {
      Properties properties = super.defaultProperties();
      properties.setProperty(PROPERTY_ISO3166_CODES, "US-IL,US-TX");
      properties.setProperty(PROPERTY_ENDPOINT, "https://auth.api.rackspacecloud.com");
      properties.setProperty(PROPERTY_API_VERSION, OpenStackAuthAsyncClient.VERSION);
      return properties;
   }

   public CloudServersUSPropertiesBuilder(Properties properties) {
      super(properties);
   }

}
