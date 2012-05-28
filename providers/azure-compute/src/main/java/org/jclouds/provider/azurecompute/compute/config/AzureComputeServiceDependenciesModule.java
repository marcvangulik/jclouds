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
package org.jclouds.provider.azurecompute.compute.config;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import org.jclouds.provider.azurecompute.compute.AzureAsyncClient;
import org.jclouds.provider.azurecompute.compute.AzureClient;
import org.jclouds.provider.azurecompute.compute.AzureComputeService;
import org.jclouds.provider.azurecompute.compute.internal.AzureTemplateBuilderImpl;
import org.jclouds.provider.azurecompute.compute.options.AzureTemplateOptions;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.compute.internal.ComputeServiceContextImpl;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.rest.RestContext;
import org.jclouds.rest.internal.RestContextImpl;

/**
 * 
 * @author Adrian Cole
 */
public class AzureComputeServiceDependenciesModule extends AbstractModule {

   @Override
   protected void configure() {
      bind(TemplateBuilder.class).to(AzureTemplateBuilderImpl.class);
      bind(TemplateOptions.class).to(AzureTemplateOptions.class);
      bind(ComputeService.class).to(AzureComputeService.class).asEagerSingleton();
      //bind(new TypeLiteral<Function<RunningInstance, NodeMetadata>>() {
      //}).to(RunningInstanceToNodeMetadata.class);
//      bind(new TypeLiteral<Function<RunningInstance, Credentials>>() {
//      }).to(CredentialsForInstance.class);
      bind(new TypeLiteral<ComputeServiceContext>() {
      }).to(new TypeLiteral<ComputeServiceContextImpl<AzureClient, AzureAsyncClient>>() {
      }).in(Scopes.SINGLETON);
      bind(new TypeLiteral<RestContext<AzureClient, AzureAsyncClient>>() {
      }).to(new TypeLiteral<RestContextImpl<AzureClient, AzureAsyncClient>>() {
      }).in(Scopes.SINGLETON);
      
      //bind(Properties.class).annotatedWith(Names.named("AzureProperties"));
   }
//   @Provides
//   @Singleton
//   protected Map<RegionAndName, Image> provideImageMap(RegionAndIdToImage regionAndIdToImage) {
//      return new MapMaker().makeComputingMap(regionAndIdToImage);
//   }
}
