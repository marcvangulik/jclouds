package org.jclouds.provider.azurecompute.compute.domain;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.jclouds.compute.domain.HardwareBuilder;
import org.jclouds.compute.domain.Processor;
import org.jclouds.compute.domain.Volume;
import org.jclouds.compute.domain.internal.VolumeImpl;

/**
 *
 * @author Frank
 */
public class AzureHardwareBuilder extends HardwareBuilder{


    public AzureHardwareBuilder(String instanceType) {
      super();
      ids(instanceType);
   }

    public AzureHardwareBuilder ids(String id) {
      return AzureHardwareBuilder.class.cast(super.ids(id));
   }

    public AzureHardwareBuilder ram(int ram) {
      return AzureHardwareBuilder.class.cast(super.ram(ram));
   }

   public AzureHardwareBuilder processors(List<Processor> processors) {
      return AzureHardwareBuilder.class.cast(super.processors(processors));
   }

   public AzureHardwareBuilder volumes(List<Volume> volumes) {
      return AzureHardwareBuilder.class.cast(super.volumes(volumes));
   }
   
   public static AzureHardwareBuilder theone() {
      return  new AzureHardwareBuilder("theone");
//            .ram(1740)
//            .processors(ImmutableList.of(new Processor(1.0, 1.0)))
//            .volumes(
//                  ImmutableList.<Volume> of(new VolumeImpl(10.0f, "/dev/sda1", true, false)));
   }
}
