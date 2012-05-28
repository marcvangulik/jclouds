package org.jclouds.provider.azurecompute.domain;

import org.jclouds.provider.azurecompute.compute.options.AzureTemplateOptions;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.internal.TemplateImpl;
import org.jclouds.domain.Location;

public class AzureTemplate extends TemplateImpl{
    
    public AzureTemplateOptions options;
    
    public AzureTemplate(Image image, Hardware size, Location location, AzureTemplateOptions options)
    {
        super(image, size, location, options);
        
        this.options = options;
    }
    
    @Override
    public AzureTemplateOptions getOptions(){
        return options;
    }
}
