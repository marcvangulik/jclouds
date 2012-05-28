package org.jclouds.provider.azurecompute.compute.internal;


//package org.jclouds.azure.compute.internal;
//
//import static org.easymock.classextension.EasyMock.*;
//
//import com.google.common.base.Function;
//import com.google.common.base.Supplier;
//import com.google.common.base.Suppliers;
//import com.google.common.collect.ImmutableSet;
//import com.google.common.collect.MapMaker;
//import com.google.inject.Provider;
//import java.util.Set;
//import java.util.concurrent.ConcurrentMap;
//import junit.framework.Assert;
//import org.jclouds.azure.compute.domain.AzureHardwareBuilder;
//import org.jclouds.azure.compute.domain.RegionAndName;
//import org.jclouds.azure.compute.options.AzureTemplateOptions;
//import org.jclouds.azure.domain.AzureInstanceSize;
//import org.jclouds.collect.Memoized;
//import org.jclouds.compute.domain.Hardware;
//import org.jclouds.compute.domain.Image;
//import org.jclouds.compute.domain.Template;
//import org.jclouds.compute.domain.TemplateBuilder;
//import org.jclouds.compute.domain.internal.TemplateBuilderImplTest;
//import org.jclouds.compute.options.TemplateOptions;
//import org.jclouds.domain.Location;
//import org.jclouds.domain.LocationBuilder;
//import org.jclouds.domain.LocationScope;
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.Test;
//
//@Test(groups = "unit", sequential = true)
//public class AzureTemplateBuilderImplTest extends TemplateBuilderImplTest{
//    
//    private AzureTemplateBuilderImpl template;
//    
//    private Image mImage;
//    private Location mLocation;
//    private Supplier<Set<? extends Location>> mLocations;
//    private Supplier<Set<? extends Image>> mImages;
//    private Supplier<Set<? extends Hardware>> mHardware;
//    private Provider<TemplateOptions> mTOptionsProv;
//    private Provider<TemplateBuilder> mTBuilderProv;
//    private TemplateOptions mTemplateOptions;
//    
//    @BeforeSuite
//    public void setup()
//    {
//        Location location = new LocationBuilder().scope(LocationScope.REGION).id("region").description("region").build();
//
//        Supplier<Set<? extends Location>> mLocations = Suppliers.<Set<? extends Location>> ofInstance(ImmutableSet
//                   .<Location> of(location));
//        Supplier<Set<? extends Image>> mImages = Suppliers.<Set<? extends Image>> ofInstance(ImmutableSet.<Image> of());
//        Supplier<Set<? extends Hardware>> mHardware = Suppliers.<Set<? extends Hardware>> ofInstance(ImmutableSet
//                   .<Hardware> of(new AzureHardwareBuilder(AzureInstanceSize.MEDIUM).build()));
//
//        Location mLocation = createMock(Location.class);
//        Provider<TemplateOptions> mTOptionsProv = createMock(Provider.class);
//        Provider<TemplateBuilder> mTBuilderProv = createMock(Provider.class);
//        TemplateOptions defaultOptions = createMock(TemplateOptions.class);
//        Image mImage = createMock(Image.class);
//
//        expect(mLocation.getId()).andReturn("region");
//        expect(mTOptionsProv.get()).andReturn(defaultOptions);
//
//        replay(mImage);
//        replay(defaultOptions);
//        replay(mLocation);
//        replay(mTOptionsProv);
//        replay(mTBuilderProv);
//
//        template = createTemplateBuilder(mImage, mLocations, mImages, mHardware, mLocation, mTOptionsProv, mTBuilderProv);
//    }
//    
//   @Override
//   protected TemplateOptions provideTemplateOptions() {
//      return new AzureTemplateOptions();
//   }
//
//   protected AzureTemplateBuilderImpl createTemplateBuilder(final Image knownImage,
//            @Memoized Supplier<Set<? extends Location>> locations, @Memoized Supplier<Set<? extends Image>> images,
//            @Memoized Supplier<Set<? extends Hardware>> sizes, Location defaultLocation,
//            Provider<TemplateOptions> optionsProvider, Provider<TemplateBuilder> templateBuilderProvider) {
//      final RegionAndName knownRegionAndName = new RegionAndName("region", "ami");
//
//      ConcurrentMap<RegionAndName, Image> imageMap = new MapMaker()
//               .makeComputingMap(new Function<RegionAndName, Image>() {
//                  @Override
//                  public Image apply(RegionAndName from) {
//                     return from.equals(knownRegionAndName) ? knownImage : null;
//                  }
//
//               });
//      
//
//      return new AzureTemplateBuilderImpl(locations, images, sizes, Suppliers.ofInstance(defaultLocation),
//               optionsProvider, templateBuilderProvider);
//   }
//   
//   @Test
//   public void testDefaultTemplate()
//   {
//       Template t = template.build();
//
//       
//       
//       
//       if(t.getHardware() == null ||
//               t.getLocation() == null ||
//               t.getImage() == null ||
//               t.getOptions() == null)
//       {
//           Assert.fail();
//       }
//       else{
//           //pass test
//           //TestNG has to Assert.pass()?
//           Assert.assertEquals(true, true);
//       }
//   }
//}
