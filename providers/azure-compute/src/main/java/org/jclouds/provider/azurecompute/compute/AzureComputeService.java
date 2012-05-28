package org.jclouds.provider.azurecompute.compute;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newLinkedHashMap;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static com.google.common.collect.Iterables.concat;

import org.jclouds.compute.*;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.Location;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.jclouds.Constants;
import org.jclouds.provider.azurecompute.compute.options.AzureTemplateOptions;
import org.jclouds.provider.azurecompute.compute.AzureContextBuilder;
import org.jclouds.collect.Memoized;
import org.jclouds.compute.config.CustomizationResponse;
import org.jclouds.compute.domain.NodeState;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.internal.NodeMetadataImpl;
import org.jclouds.compute.internal.BaseComputeService;
import org.jclouds.compute.reference.ComputeServiceConstants.Timeouts;
import org.jclouds.compute.strategy.CreateNodesInGroupThenAddToSet;
import org.jclouds.compute.strategy.DestroyNodeStrategy;
import org.jclouds.compute.strategy.GetNodeMetadataStrategy;
import org.jclouds.compute.strategy.InitializeRunScriptOnNodeOrPlaceInBadMap;
import org.jclouds.compute.strategy.ListNodesStrategy;
import org.jclouds.compute.strategy.RebootNodeStrategy;
import org.jclouds.compute.strategy.ResumeNodeStrategy;
import org.jclouds.compute.strategy.SuspendNodeStrategy;
import org.jclouds.domain.Credentials;

/**
 * 
 * @author Eric Liefaart
 */
@Singleton
public class AzureComputeService extends BaseComputeService {

    private CreateNodesInGroupThenAddToSet runNodesAndAddToSetStrategy;
    private Set<NodeMetadata> allRunningNodes;
    
    @Inject
    protected AzureComputeService(ComputeServiceContext context, Map<String, Credentials> credentialStore,
            @Memoized Supplier<Set<? extends Image>> images,
            @Memoized Supplier<Set<? extends Hardware>> hardwareProfiles,
            @Memoized Supplier<Set<? extends Location>> locations, ListNodesStrategy listNodesStrategy,
            GetNodeMetadataStrategy getNodeMetadataStrategy, CreateNodesInGroupThenAddToSet runNodesAndAddToSetStrategy,
            RebootNodeStrategy rebootNodeStrategy, DestroyNodeStrategy destroyNodeStrategy,
            ResumeNodeStrategy resumeNodeStrategy, SuspendNodeStrategy suspendNodeStrategy,
            Provider<TemplateBuilder> templateBuilderProvider, Provider<TemplateOptions> templateOptionsProvider,
            @Named("NODE_RUNNING") Predicate<NodeMetadata> nodeRunning,
            @Named("NODE_TERMINATED") Predicate<NodeMetadata> nodeTerminated,
            @Named("NODE_SUSPENDED") Predicate<NodeMetadata> nodeSuspended,
            InitializeRunScriptOnNodeOrPlaceInBadMap.Factory initScriptRunnerFactory, Timeouts timeouts,
            @Named(Constants.PROPERTY_USER_THREADS) ExecutorService executor,
            AzureContextBuilder builder,
            Properties props,
            @Named(Constants.PROPERTY_IDENTITY) String subscriptionId,
            @Named(Constants.PROPERTY_CREDENTIAL) String credential) {
    
        super(context, credentialStore, images, hardwareProfiles, locations, listNodesStrategy, getNodeMetadataStrategy,
                runNodesAndAddToSetStrategy, rebootNodeStrategy, destroyNodeStrategy, resumeNodeStrategy,
                suspendNodeStrategy, templateBuilderProvider, templateOptionsProvider, nodeRunning, nodeTerminated,
                nodeSuspended, initScriptRunnerFactory, timeouts, executor);
        
        this.runNodesAndAddToSetStrategy = runNodesAndAddToSetStrategy;
        allRunningNodes = newLinkedHashSet();
    }
    
    @Override
    public Set<? extends NodeMetadata> createNodesInGroup(String group, int count, Template template)
              throws RunNodesException {
        logger.debug(">> running %d node%s group(%s) location(%s) image(%s) hardwareProfile(%s) options(%s)", count,
               count > 1 ? "s" : "", group, template.getLocation().getId(), template.getImage().getId(), template
                          .getHardware().getId(), template.getOptions());
        
        NodeMetadata nodeMetadata;
        Set<NodeMetadata> newNodes = newLinkedHashSet();
        Map<NodeMetadata, Exception> badNodes = newLinkedHashMap();
        Multimap<NodeMetadata, CustomizationResponse> customizationResponses = LinkedHashMultimap.create();

        Map<?, Future<Void>> responses = runNodesAndAddToSetStrategy.execute(group, count, template, newNodes, badNodes, customizationResponses);
        
        nodeMetadata = new NodeMetadataImpl("azurecompute", 
                template.getOptions().as(AzureTemplateOptions.class).getServiceName(), 
                template.getOptions().as(AzureTemplateOptions.class).getServiceName(), null, 
                URI.create("http://" + template.getOptions().as(AzureTemplateOptions.class).getServiceName() + ".cloudapp.net"), 
                new HashMap<String, String>(), group, template.getHardware(), null, null, NodeState.UNRECOGNIZED, -1, 
                new ArrayList<String>(), new ArrayList<String>(), null, null);
        newNodes.add(nodeMetadata);
        allRunningNodes.addAll(newNodes);
        
        for(NodeMetadata m : allRunningNodes)
        {
            System.out.println(m.getId());
        }
        
        return newNodes;
    }
    
    @Override
    public Set<? extends NodeMetadata> createNodesInGroup(String group, int count) throws RunNodesException {
        throw new UnsupportedOperationException("Not supported: use createNodesInGroup(string, int, Template(Options)) instead");
    }

    @Override
    public AzureTemplateOptions templateOptions() {
        return AzureTemplateOptions.class.cast(super.templateOptions());
    }
}
