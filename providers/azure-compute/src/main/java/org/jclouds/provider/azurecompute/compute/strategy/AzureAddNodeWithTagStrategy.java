package org.jclouds.provider.azurecompute.compute.strategy;

import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.strategy.CreateNodeWithGroupEncodedIntoName;

public class AzureAddNodeWithTagStrategy implements CreateNodeWithGroupEncodedIntoName{

    @Override
    public NodeMetadata createNodeWithGroupEncodedIntoName(String group, String name, Template template) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
