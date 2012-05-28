package org.jclouds.provider.azurecompute.compute.strategy;

import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.strategy.GetNodeMetadataStrategy;

public class AzureGetNodeMetadataStrategy implements GetNodeMetadataStrategy{

    @Override
    public NodeMetadata getNode(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
