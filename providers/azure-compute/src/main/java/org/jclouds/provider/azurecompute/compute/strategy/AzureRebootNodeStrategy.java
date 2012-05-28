package org.jclouds.provider.azurecompute.compute.strategy;

import com.google.inject.Inject;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.strategy.RebootNodeStrategy;
import org.soyatec.windowsazure.management.ServiceManagementRest;

public class AzureRebootNodeStrategy implements RebootNodeStrategy{
    
    private ServiceManagementRest smr;
    
    @Inject
    public AzureRebootNodeStrategy(ServiceManagementRest smr)
    {
        this.smr = smr;
    }
    
    @Override
    public NodeMetadata rebootNode(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
