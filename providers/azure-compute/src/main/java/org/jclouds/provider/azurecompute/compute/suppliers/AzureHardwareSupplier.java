package org.jclouds.provider.azurecompute.compute.suppliers;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import org.jclouds.compute.domain.Hardware;
import static org.jclouds.provider.azurecompute.compute.domain.AzureHardwareBuilder.theone;

public class AzureHardwareSupplier implements Supplier<Set<? extends Hardware>>{

    @Override
    public Set<? extends Hardware> get() {
        return ImmutableSet.<Hardware> of(theone().build());
    }
    
}
