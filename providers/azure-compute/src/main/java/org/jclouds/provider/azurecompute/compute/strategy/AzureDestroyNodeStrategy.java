package org.jclouds.provider.azurecompute.compute.strategy;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.strategy.DestroyNodeStrategy;
import org.soyatec.windowsazure.error.StorageServerException;
import org.soyatec.windowsazure.management.AsyncResultCallback;
import org.soyatec.windowsazure.management.DeploymentSlotType;
import org.soyatec.windowsazure.management.DeploymentStatus;
import org.soyatec.windowsazure.management.ServiceManagementRest;
import org.soyatec.windowsazure.management.UpdateStatus;

@Singleton
public class AzureDestroyNodeStrategy implements DestroyNodeStrategy{

    private ServiceManagementRest sm;
    
    @Inject
    public AzureDestroyNodeStrategy(ServiceManagementRest sm)
    {
        this.sm = sm;
    }
    
    @Override
    public NodeMetadata destroyNode(String serviceName) {
        
        if(sm.getDeployment(serviceName, DeploymentSlotType.Production) == null){
            try
            {
                sm.deleteHostedService(serviceName);
            }catch(StorageServerException e)
            {
                //Service most likely doesn't exist
                e.printStackTrace();
            }
        }//Nodes can only be destroyed if the node is in de suspended state
        else if(sm.getDeployment(serviceName, DeploymentSlotType.Production).getStatus() == DeploymentStatus.Suspended)
        {
            sm.deleteDeployment(serviceName, DeploymentSlotType.Production, new DestroyNodeCallback(serviceName));
        }
        else{
            //Suspend the node, and tell the callback class to destroy the node after the node has been suspended.
            sm.updateDeplymentStatus(serviceName, DeploymentSlotType.Production, UpdateStatus.Suspended, new SuspendNodeCallback(serviceName, true));
        }
        
        //TODO: fix return
        return null;
    }
    
    private class DestroyNodeCallback implements AsyncResultCallback
    {
        private String serviceName;
        
        private DestroyNodeCallback(String serviceName)
        {
            this.serviceName = serviceName;
        }
        
        public void onSuccess(Object arg)
        {
            destroyNode(serviceName);
        }
        
        public void onError(Object arg)
        {
            System.err.println("Error deleting node.");
        }
    }
    
    private class SuspendNodeCallback implements AsyncResultCallback
    {
        private String serviceName;
        private boolean destroyAfter;
        
        private SuspendNodeCallback(String serviceName)
        {
            this.serviceName = serviceName;
            this.destroyAfter = false;
        }
        
        private SuspendNodeCallback(String serviceName, boolean destroyAfter)
        {
            this.serviceName = serviceName;
            this.destroyAfter = destroyAfter;
        }
        
        public void onSuccess(Object arg)
        {
            sm.getDeployment(serviceName, DeploymentSlotType.Production).setStatus(DeploymentStatus.Suspended);
            
            if(destroyAfter)
            {
                destroyNode(serviceName);
            }
        }
        
        public void onError(Object arg)
        {
            
        }
    }
}
