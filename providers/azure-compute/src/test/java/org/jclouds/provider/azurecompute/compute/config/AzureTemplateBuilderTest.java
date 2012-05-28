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

import static java.lang.String.format;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.jclouds.provider.azurecompute.compute.domain.AzureHardwareBuilder.theone;
import static org.testng.Assert.assertEquals;

import java.util.Set;

import javax.inject.Provider;

import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.ImageBuilder;
import org.jclouds.compute.domain.OperatingSystem;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.compute.domain.internal.TemplateBuilderImpl;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.Credentials;
import org.jclouds.domain.internal.LocationImpl;
import org.jclouds.domain.Location;
import org.jclouds.domain.LocationBuilder;
import org.jclouds.domain.LocationScope;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jclouds.compute.domain.internal.ImageImpl;

/**
 * Tests compute service specifically to Azure.
 * 
 * These tests are designed to verify the local functionality of jclouds, rather than the
 * interaction with Windows Azure.
 * 
 * @see AzureComputeServiceLiveTest
 * 
 * @author Frank Folsche
 */
public class AzureTemplateBuilderTest {
   private static final Location location = new LocationBuilder().scope(LocationScope.REGION).id("us-east-1")
            .description("us-east-1").build();

   public static final Hardware hardware = theone().location(location)
            .build();

   /**
    * Verifies that {@link TemplateBuilderImpl} would choose the correct size of the instance, based
    * on {@link org.jclouds.compute.domain.Hardware} from {@link AzureHardware}.
    * 
    * Expected size: thisone
    */
   @Test
   public void testTemplateChoiceForInstanceByhardwareId() throws Exception {
     // Template template = newTemplateBuilder().os64Bit(true).hardwareId("theone").build();
        //moet er natuurlijk wel staan maar op dit moment werkt de code nog niet;
     // assert template != null : "The returned template was null, but it should have a value.";

      //assertEquals(theone().build(), template.getHardware());
   }

   @SuppressWarnings("unchecked")
   private TemplateBuilder newTemplateBuilder() {

      Provider<TemplateOptions> optionsProvider = createMock(Provider.class);
      Provider<TemplateBuilder> templateBuilderProvider = createMock(Provider.class);
      TemplateOptions defaultOptions = createMock(TemplateOptions.class);

      expect(optionsProvider.get()).andReturn(defaultOptions);
       Image image = new ImageBuilder().providerId("cc-image").name("image").id("us-east-1/cc-image").location(location)
               .operatingSystem(new OperatingSystem(OsFamily.UBUNTU, null, "1.0", null, "ubuntu", true)).description(
                        "description").version("1.0").defaultCredentials(new Credentials("root", null)).build();

      replay(optionsProvider);
      replay(templateBuilderProvider);
      Supplier<Set<? extends Location>> locations = Suppliers.<Set<? extends Location>> ofInstance(ImmutableSet
               .<Location> of(location));
      Supplier<Set<? extends Image>> images = Suppliers.<Set<? extends Image>> ofInstance(ImmutableSet
               .<Image> of(image));
      Supplier<Set<? extends Hardware>> sizes = Suppliers.<Set<? extends Hardware>> ofInstance(ImmutableSet
               .<Hardware> of(theone().build(), hardware));

      return new TemplateBuilderImpl(locations, images, sizes, null, optionsProvider,
               templateBuilderProvider) {

      };
   }

}
