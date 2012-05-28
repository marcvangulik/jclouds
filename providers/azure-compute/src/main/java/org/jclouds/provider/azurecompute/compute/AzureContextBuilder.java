package org.jclouds.provider.azurecompute.compute;

import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import java.util.List;
import java.util.Properties;

import org.jclouds.provider.azurecompute.compute.config.AzureComputeServiceContextModule;

import org.jclouds.compute.ComputeServiceContextBuilder;

@Singleton
public class AzureContextBuilder extends ComputeServiceContextBuilder<AzureClient, AzureAsyncClient>{
    
    @Inject
    public AzureContextBuilder(Properties props)
    {
        super(AzureClient.class, AzureAsyncClient.class, props);
    }
    
    @Override
    public AzureContextBuilder withModules(Iterable<Module> modules) {
       return (AzureContextBuilder) super.withModules(modules);
    }

    @Override
    protected void addContextModule(List<Module> modules) {
       modules.add(new AzureComputeServiceContextModule());
    }
}
