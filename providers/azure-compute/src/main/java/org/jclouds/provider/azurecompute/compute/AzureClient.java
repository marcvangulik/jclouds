package org.jclouds.provider.azurecompute.compute;

import java.util.concurrent.TimeUnit;
import org.jclouds.concurrent.Timeout;

@Timeout(duration = 180, timeUnit = TimeUnit.SECONDS)
public interface AzureClient {
    public final static String VERSION = "1.4";
}
