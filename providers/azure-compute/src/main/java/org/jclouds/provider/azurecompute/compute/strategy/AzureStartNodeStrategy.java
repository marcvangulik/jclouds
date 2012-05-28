package org.jclouds.provider.azurecompute.compute.strategy;

import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.strategy.ResumeNodeStrategy;

public class AzureStartNodeStrategy implements ResumeNodeStrategy{

    @Override
    public NodeMetadata resumeNode(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
