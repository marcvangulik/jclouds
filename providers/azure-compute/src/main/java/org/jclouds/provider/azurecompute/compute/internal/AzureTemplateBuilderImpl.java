package org.jclouds.provider.azurecompute.compute.internal;

import com.google.common.annotations.VisibleForTesting;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;


import org.jclouds.collect.Memoized;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.compute.domain.internal.TemplateBuilderImpl;
import org.jclouds.domain.Location;

import com.google.common.base.Supplier;
import org.jclouds.provider.azurecompute.compute.options.AzureTemplateOptions;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.internal.TemplateImpl;
import org.jclouds.compute.options.TemplateOptions;

public class AzureTemplateBuilderImpl extends TemplateBuilderImpl {

    protected AzureTemplateOptions options;

    @Inject
    protected AzureTemplateBuilderImpl(@Memoized Supplier<Set<? extends Location>> locations,
            @Memoized Supplier<Set<? extends Image>> images, @Memoized Supplier<Set<? extends Hardware>> sizes,
            Supplier<Location> defaultLocation, Provider<TemplateOptions> optionsProvider,
            @Named("WINDOWS") Provider<TemplateBuilder> defaultTemplateProvider) {
        
        super(locations, images, sizes, defaultLocation, optionsProvider, defaultTemplateProvider);
        
        this.options = AzureTemplateOptions.NONE;
    }
    @Override
   
    public Template build() {
      if (nothingChangedExceptOptions()) {
         TemplateBuilder defaultTemplate = defaultTemplateProvider.get();
         if (options != null)
            defaultTemplate.options(options);
         return defaultTemplate.build();
      }
      if (location == null)
         location = defaultLocation.get();
      if (options == null)
         options = optionsProvider.get().as(AzureTemplateOptions.class);
      logger.debug(">> searching params(%s)", this);
      Set<? extends Image> images = getImages();
      Hardware hardware = resolveSize(hardwareSorter(), images);
      Image image = resolveImage(hardware, images);
      logger.debug("<<   matched image(%s)", image);

      return new TemplateImpl(image, hardware, location, options.as(AzureTemplateOptions.class));
    }
    
    @Override
    public TemplateBuilder any() {
        return this;
    }
    
    @Override
    public TemplateBuilder options(TemplateOptions options) {
       copyTemplateOptions(options.as(AzureTemplateOptions.class), this.options);
       return this;
    }
    
    @Override
    protected void copyTemplateOptions(TemplateOptions from, TemplateOptions to) {
      super.copyTemplateOptions(from, to);
      
      AzureTemplateOptions _from = from.as(AzureTemplateOptions.class), 
              _to = to.as(AzureTemplateOptions.class);
      
      if(_from.getDeploymentName() != null){
          _to.deploymentName(_from.getDeploymentName());
      }
      if(_from.getInstanceSize() != null){
          _to.instanceSize(_from.getInstanceSize());
      }
      if(_from.getLocation() != null){
          _to.location(_from.getLocation());
      }
      if(_from.getServiceConfigURL() != null){
          _to.serviceConfigURL(_from.getServiceConfigURL());
      }
      if(_from.getServiceName() != null){
          _to.serviceName(_from.getServiceName());
      }
      if(_from.getServicePackageURL() != null){
          _to.servicePackageURL(_from.getServicePackageURL());
      }
//      if(_from.getUrlPrefix() != null){
//          _to.urlPrefix(_from.getUrlPrefix());
//      }
   }
    
   @VisibleForTesting
   boolean nothingChangedExceptOptions() {
      return osFamily == null && location == null && imageId == null && hardwareId == null && osName == null
               && osDescription == null && imageVersion == null && osVersion == null && osArch == null
               && os64Bit == null && imageName == null && imageDescription == null && minCores == 0 && minRam == 0
               && !biggest && !fastest;
   }
}
