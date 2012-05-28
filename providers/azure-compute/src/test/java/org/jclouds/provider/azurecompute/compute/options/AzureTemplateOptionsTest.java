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
package org.jclouds.provider.azurecompute.compute.options;

//import static org.jclouds.azure.compute.options.AzureTemplateOptions.Builder.urlPrefix;
import org.jclouds.provider.azurecompute.compute.options.AzureTemplateOptions;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import org.jclouds.provider.azurecompute.domain.AzureInstanceSize;
import org.jclouds.provider.azurecompute.domain.AzureLocation;

import org.jclouds.compute.options.TemplateOptions;
import org.testng.annotations.Test;

/**
 * Tests possible uses of AzureTemplateOptions and AzureTemplateOptions.Builder.*
 * 
 * @author Adrian Cole
 */
public class AzureTemplateOptionsTest {

   public void testAs() {
      TemplateOptions options = new AzureTemplateOptions();
      assertEquals(options.as(AzureTemplateOptions.class), options);
   }

   // superclass tests
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testinstallPrivateKeyBadFormat() {
      AzureTemplateOptions options = new AzureTemplateOptions();
      options.installPrivateKey("whompy");
   }

   @Test
   public void testinstallPrivateKey() throws IOException {
      AzureTemplateOptions options = new AzureTemplateOptions();
      options.installPrivateKey("-----BEGIN RSA PRIVATE KEY-----");
      assertEquals(options.getPrivateKey(), "-----BEGIN RSA PRIVATE KEY-----");
   }

   @Test
   public void testNullinstallPrivateKey() {
      AzureTemplateOptions options = new AzureTemplateOptions();
      assertEquals(options.getPrivateKey(), null);
   }

   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testauthorizePublicKeyBadFormat() {
      AzureTemplateOptions options = new AzureTemplateOptions();
      options.authorizePublicKey("whompy");
   }

   @Test
   public void testauthorizePublicKey() throws IOException {
      AzureTemplateOptions options = new AzureTemplateOptions();
      options.authorizePublicKey("ssh-rsa");
      assertEquals(options.getPublicKey(), "ssh-rsa");
   }

   @Test
   public void testNullauthorizePublicKey() {
      AzureTemplateOptions options = new AzureTemplateOptions();
      assertEquals(options.getPublicKey(), null);
   }

   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testblockOnPortBadFormat() {
      AzureTemplateOptions options = new AzureTemplateOptions();
      options.blockOnPort(-1, -1);
   }

   @Test
   public void testblockOnPort() {
      AzureTemplateOptions options = new AzureTemplateOptions();
      options.blockOnPort(22, 30);
      assertEquals(options.getPort(), 22);
      assertEquals(options.getSeconds(), 30);

   }

   @Test
   public void testNullblockOnPort() {
      AzureTemplateOptions options = new AzureTemplateOptions();
      assertEquals(options.getPort(), -1);
      assertEquals(options.getSeconds(), -1);
   }

   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testinboundPortsBadFormat() {
      AzureTemplateOptions options = new AzureTemplateOptions();
      options.inboundPorts(-1, -1);
   }

   @Test
   public void testinboundPorts() {
      AzureTemplateOptions options = new AzureTemplateOptions();
      options.inboundPorts(22, 30);
      assertEquals(options.getInboundPorts()[0], 22);
      assertEquals(options.getInboundPorts()[1], 30);

   }

   @Test
   public void testDefaultOpen22() {
      AzureTemplateOptions options = new AzureTemplateOptions();
      assertEquals(options.getInboundPorts()[0], 22);
   }
   
   
   
   //Azure tests
   
   @Test
   public void testDeploymentName()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.deploymentName("whompy");
       assertEquals(options.getDeploymentName(), "whompy");
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testDeploymentNameBadName()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.deploymentName("whompy;&");
   }
   
   @Test 
   public void testServiceName()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.serviceName("whompy");
       assertEquals(options.getServiceName(), "whompy");
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testServiceNameBadName()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.serviceName("whompy<&");
   }
   
//   @Test
//   public void testUrlPrefix()
//   {
//       AzureTemplateOptions options = new AzureTemplateOptions();
//       options.urlPrefix("whompy");
//       assertEquals(options.getUrlPrefix(), "whompy");
//   }
//   
//   @Test(expectedExceptions = IllegalArgumentException.class)
//   public void testUrlPrefixBadUrl()
//   {
//       AzureTemplateOptions options = new AzureTemplateOptions();
//       options.urlPrefix("-whompy");
//   }
   
   @Test
   public void testServicePackageUrl()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.servicePackageURL("http://storagename.blob.core.windows.net/container/blobname");
       assertEquals(options.getServicePackageURL(), "http://storagename.blob.core.windows.net/container/blobname");
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testServicePackageUrlBadUrl1()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.servicePackageURL("httpx://someurl.com/whompy");
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testServicePackageUrlBadUrl2()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.servicePackageURL("http.someurl.com/whompy");
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testServicePackageUrlBadUrl3()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.servicePackageURL("http://^^^^^^");
   }
   
   @Test
   public void testServiceConfigUrl()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.serviceConfigURL("http://storagename.blob.core.windows.net/container/blobname");
       assertEquals(options.getServiceConfigURL(), "http://storagename.blob.core.windows.net/container/blobname");
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testServiceConfigUrlBadUrl1()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.serviceConfigURL("httpx://someurl.com/whompy");
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testServiceConfigUrlBadUrl2()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.serviceConfigURL("http.someurl.com/whompy");
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testServiceConfigUrlBadUrl3()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.serviceConfigURL("http://^^^^^^");
   }
   
   @Test
   public void testInstanceSize()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.instanceSize(AzureInstanceSize.EXTRA_LARGE);
       assertEquals(options.getInstanceSize(), AzureInstanceSize.EXTRA_LARGE);
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testInstanceSizeBadSize()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.instanceSize("heel groot");
   }
   
   @Test
   public void testLocation()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.location(AzureLocation.EAST_ASIA);
       assertEquals(options.getLocation(), AzureLocation.EAST_ASIA);
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testLocationBadLocation()
   {
       AzureTemplateOptions options = new AzureTemplateOptions();
       options.location("the moon");
   }
}
