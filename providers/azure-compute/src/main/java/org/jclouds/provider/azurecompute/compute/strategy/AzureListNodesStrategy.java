package org.jclouds.provider.azurecompute.compute.strategy;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.strategy.ListNodesStrategy;

@Singleton
public class AzureListNodesStrategy implements ListNodesStrategy{
    
    @Inject
    protected AzureListNodesStrategy()
    {
    }
    
    @Override
    public Iterable<? extends ComputeMetadata> listNodes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<? extends NodeMetadata> listDetailsOnNodesMatching(Predicate<ComputeMetadata> filter) {
        throw new UnsupportedOperationException();
    }
}
