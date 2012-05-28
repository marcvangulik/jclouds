package org.jclouds.provider.azurecompute.compute.strategy;

import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.strategy.SuspendNodeStrategy;

public class AzureStopNodeStrategy implements SuspendNodeStrategy{

    @Override
    public NodeMetadata suspendNode(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
