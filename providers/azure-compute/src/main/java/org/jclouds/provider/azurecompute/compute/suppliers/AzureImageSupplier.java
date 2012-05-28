package org.jclouds.provider.azurecompute.compute.suppliers;

import com.google.common.base.Supplier;
import com.google.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.OperatingSystem;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.internal.ImageImpl;
import org.jclouds.domain.LocationScope;
import org.jclouds.domain.internal.LocationImpl;

//map is nog null moet later wat anders
@Singleton
public class AzureImageSupplier implements Supplier<Set<? extends Image>>{
//
//    private final Supplier<Map<RegionAndName, ? extends Image>> map;
//
//   @Inject
//   AzureImageSupplier(Supplier<Map<RegionAndName, ? extends Image>> map) {
//      this.map = map;
//   }
//
//   @Override
//   public Set<? extends Image> get() {
//      return Sets.newLinkedHashSet(map.get().values());
//   }

   //lelijke image hack
   @Override
   public Set<? extends Image> get() {

       Map<String, Object> locationMetadata = new HashMap<String, Object>();
       locationMetadata.put("locationmetadata", "dfd");
       Map<String, String> imageMetadata = new HashMap<String, String>();
       locationMetadata.put("imageMetadata", "leeg");
       Iterable<String> iso = new ArrayList();
       OperatingSystem os = new OperatingSystem(OsFamily.WINDOWS, null, null, "Windows server 2008", "description", true);
       LocationImpl location = new LocationImpl(LocationScope.PROVIDER, "azurecompute", "SS", null, iso, locationMetadata);
       ImageImpl image = null;
        try {
            image = new ImageImpl("azurecompute", "AzureImage", "azurecompute", location, new URI("URI"), imageMetadata,os, "description", null, null, null);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AzureImageSupplier.class.getName()).log(Level.SEVERE, null, ex);
        }
       Set set = new HashSet<ImageImpl>();
       set.add(image);
      return set;
   }
}
