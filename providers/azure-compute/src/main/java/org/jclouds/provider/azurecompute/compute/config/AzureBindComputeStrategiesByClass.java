package org.jclouds.provider.azurecompute.compute.config;

import org.jclouds.compute.config.BindComputeStrategiesByClass;
import org.jclouds.compute.strategy.*;
import org.jclouds.provider.azurecompute.compute.strategy.*;

public class AzureBindComputeStrategiesByClass extends BindComputeStrategiesByClass{

    @Override
   protected Class<? extends CreateNodesInGroupThenAddToSet> defineRunNodesAndAddToSetStrategy() {
      return AzureCreateNodesInGroupThenAddToSet.class;
   }

    @Override
    protected Class<? extends CreateNodeWithGroupEncodedIntoName> defineAddNodeWithTagStrategy() {
        return AzureAddNodeWithTagStrategy.class;
    }

    @Override
    protected Class<? extends DestroyNodeStrategy> defineDestroyNodeStrategy() {
        return AzureDestroyNodeStrategy.class;
    }

    @Override
    protected Class<? extends RebootNodeStrategy> defineRebootNodeStrategy() {
        return AzureRebootNodeStrategy.class;
    }

    @Override
    protected Class<? extends ResumeNodeStrategy> defineStartNodeStrategy() {
        return AzureStartNodeStrategy.class;
    }

    @Override
    protected Class<? extends SuspendNodeStrategy> defineStopNodeStrategy() {
        return AzureStopNodeStrategy.class;
    }

    @Override
    protected Class<? extends GetNodeMetadataStrategy> defineGetNodeMetadataStrategy() {
        return AzureGetNodeMetadataStrategy.class;
    }

    @Override
    protected Class<? extends ListNodesStrategy> defineListNodesStrategy() {
        return AzureListNodesStrategy.class;
    }
}
