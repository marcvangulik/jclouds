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
package org.jclouds.aws.ec2;

import com.google.common.collect.ImmutableSet;

import java.net.URI;
import java.util.Set;

import org.jclouds.providers.BaseProviderMetadata;
import org.jclouds.providers.ProviderMetadata;

/**
 * Implementation of {@ link org.jclouds.types.ProviderMetadata} for Amazon's
 * Elastic Compute Cloud (EC2) provider.
 *
 * @author Jeremy Whitlock <jwhitlock@apache.org>
 */
public class AWSEC2ProviderMetadata extends BaseProviderMetadata {

   /**
    * {@inheritDoc}
    */
   @Override
   public String getId() {
      return "aws-ec2";
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getType() {
      return ProviderMetadata.COMPUTE_TYPE;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getName() {
      return "Amazon Elastic Compute Cloud (EC2)";
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getIdentityName() {
      return "Access Key ID";
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getCredentialName() {
      return "Secret Access Key";
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public URI getHomepage() {
      return URI.create("http://aws.amazon.com/ec2/");
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public URI getConsole() {
      return URI.create("https://console.aws.amazon.com/ec2/home");
   }
   /**
    * {@inheritDoc}
    */
   @Override
   public URI getApiDocumentation() {
      return URI.create("http://docs.amazonwebservices.com/AWSEC2/latest/APIReference");
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Set<String> getLinkedServices() {
      return ImmutableSet.of("aws-s3", "aws-ec2", "aws-elb", "aws-simpledb");
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Set<String> getIso3166Codes() {
      return ImmutableSet.of("US-VA", "US-CA", "IE", "SG", "JP-13");
   }

}