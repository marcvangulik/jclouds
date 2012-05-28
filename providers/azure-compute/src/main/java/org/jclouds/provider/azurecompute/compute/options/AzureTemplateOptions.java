package org.jclouds.provider.azurecompute.compute.options;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigInteger;
import org.jclouds.compute.options.TemplateOptions;
import java.security.SecureRandom;
import org.jclouds.provider.azurecompute.domain.AzureInstanceSize;
import org.jclouds.provider.azurecompute.domain.AzureLocation;


//location moet object Location worden en geen string
public class AzureTemplateOptions extends TemplateOptions{
    
    private String
            deploymentName, //Name of the to be created deployment
            serviceName,    //Name of the to be created service
            urlPrefix,            //URL prefix for your service. (<urlPrefix>.cloudapp.net)
            servicePackageURL,   //Download location for the Service Package
            serviceConfigURL,   //Download location for ServiceConfiguration.cscfg
            instanceSize,       //Size of the instance AzureInstanceSize {@link AzureInstanceSize}
            location;       //Location(in the world) of the deployment {@link AzureLocation}
    
    public static final AzureTemplateOptions NONE = new AzureTemplateOptions();

    public AzureTemplateOptions(){
        SecureRandom random = new SecureRandom();
        String randomStr  = new BigInteger(130, random).toString(32);
        randomStr = "JClouds-" + randomStr;
        serviceName = randomStr;
        deploymentName = "Deployment-" + randomStr;
        location = AzureLocation.ANYWHERE_EUROPE;
        urlPrefix = randomStr;
        instanceSize = AzureInstanceSize.EXTRA_SMALL;
        //serviceConfigURL = "http://joahello.blob.core.windows.net/deploymentspublic/ServiceConfiguration.cscfg";
        //servicePackageURL = "http://joahello.blob.core.windows.net/deploymentspublic/AzureTarget.cspkg";
    }
    
    public boolean isValid()
    {
        //Since we set default values for most variables 
        //  in the constructor we only need to check if 
        //  serviceConfigURL and servicePackageURL are set
        
        return !(serviceConfigURL == null) && !(servicePackageURL == null);
    }
    
    public AzureTemplateOptions deploymentName(String deploymentName)
    {
        checkArgument(checkNotNull(deploymentName).matches("[^><&;%]*"), "invalid deployment name: " + deploymentName + ". Forbidden characters are: '>', '<', '%', ';', '&'");
        checkNotNull(deploymentName);
        this.deploymentName = deploymentName;
        return this;
    }
    
    public AzureTemplateOptions serviceName(String serviceName)
    {
        checkArgument(checkNotNull(serviceName).matches("[^><&;%]*"), "invalid service name: " + serviceName + ". Forbidden characters are: '>', '<', '%', ';', '&'");
        checkNotNull(serviceName);
        this.serviceName = serviceName;
        return this;
    }
    
    public AzureTemplateOptions urlPrefix(String urlPrefix)
    {
        //Due to a bug in the WindowsAzure4J API the url-prefix is not used, it uses the ServiceName as url prefix instead, so for now:
        throw new UnsupportedOperationException("Due to a bug in the WindowsAzure4J API the url-prefix is not used, it uses the ServiceName as url prefix instead.");
        
//        checkArgument(checkNotNull(urlPrefix).matches("[a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]"), "invalid url prefix: " + urlPrefix + ". Can only contain letters, numbers and hyphens. Hyphens shouldn't be the first or last letter.");
//        this.urlPrefix = urlPrefix;
//        return this;
    }
    
    public AzureTemplateOptions servicePackageURL(String servicePackageURL)
    {
        checkArgument(checkNotNull(servicePackageURL).matches("https?://[a-zA-Z./-]*"), "invalid url: " + servicePackageURL);
        this.servicePackageURL = servicePackageURL;
        return this;
    }
    
    public AzureTemplateOptions serviceConfigURL(String serviceConfigURL)
    {
        checkArgument(checkNotNull(serviceConfigURL).matches("https?://[a-zA-Z./-]*"), "invalid url: " + serviceConfigURL);
        this.serviceConfigURL = serviceConfigURL;
        return this;
    }
    
    public AzureTemplateOptions instanceSize(String size)
    {
        checkNotNull(size);
        if(!(size.equals(AzureInstanceSize.EXTRA_SMALL) ||
                size.equals(AzureInstanceSize.SMALL) ||
                size.equals(AzureInstanceSize.MEDIUM) ||
                size.equals(AzureInstanceSize.LARGE) ||
                size.equals(AzureInstanceSize.EXTRA_LARGE))){
            throw new IllegalArgumentException("size, see org.jclouds.azure.domain.AzureInstanceSize for possible values");
        }
        
        this.instanceSize = size;
        return this;
    }
    
    public AzureTemplateOptions location(String location)
    {
        checkNotNull(location);
        if(!(location.equals(AzureLocation.EAST_ASIA) ||
                location.equals(AzureLocation.WEST_EUROPE) ||
                location.equals(AzureLocation.NORTH_CENTRAL_US) ||
                location.equals(AzureLocation.NORTH_EUROPE) ||
                location.equals(AzureLocation.SOUTH_CENTRAL_US) ||
                location.equals(AzureLocation.SOUTHEAST_ASIA) ||
                location.equals(AzureLocation.ANYWHERE_ASIA) ||
                location.equals(AzureLocation.ANYWHERE_EUROPE) ||
                location.equals(AzureLocation.ANYWHERE_US))){
            throw new IllegalArgumentException("location, see org.jclouds.azure.domain.AzureLocation for possible values");
        }
        
        this.location = location;
        return this;
    }
    
    public static class Builder
    {
        public static AzureTemplateOptions deploymentName(String deploymentName)
        {
            AzureTemplateOptions ato = new AzureTemplateOptions();
            return AzureTemplateOptions.class.cast(ato.deploymentName(deploymentName));
        }
        
        public static AzureTemplateOptions serviceName(String serviceName)
        {
            AzureTemplateOptions ato = new AzureTemplateOptions();
            return AzureTemplateOptions.class.cast(ato.serviceName(serviceName));
        }
        
        public static AzureTemplateOptions urlPrefix(String urlPrefix)
        {
            AzureTemplateOptions ato = new AzureTemplateOptions();
            return AzureTemplateOptions.class.cast(ato.urlPrefix(urlPrefix));
        }
        
        public static AzureTemplateOptions servicePackageURL(String servicePackageURL)
        {
            AzureTemplateOptions ato = new AzureTemplateOptions();
            return AzureTemplateOptions.class.cast(ato.servicePackageURL(servicePackageURL));
        }
        
        public static AzureTemplateOptions serviceConfigURL(String serviceConfigURL)
        {
            AzureTemplateOptions ato = new AzureTemplateOptions();
            return AzureTemplateOptions.class.cast(ato.serviceConfigURL(serviceConfigURL));
        }
        
        public static AzureTemplateOptions instanceSize(String size)
        {
            AzureTemplateOptions ato = new AzureTemplateOptions();
            return AzureTemplateOptions.class.cast(ato.instanceSize(size));
        }
        
        public static AzureTemplateOptions location(String location)
        {
            AzureTemplateOptions ato = new AzureTemplateOptions();
            return AzureTemplateOptions.class.cast(ato.location(location));
        }
    }
    
    /*@Override
    public String toString() {
        //Todo
    }*/

    public String getDeploymentName()
    {
        return this.deploymentName;
    }
    
    public String getServiceName()
    {
        return this.serviceName;
    }
    
    public String getUrlPrefix()
    {
        return this.urlPrefix;
    }
    
    public String getServicePackageURL()
    {
        return this.servicePackageURL;
    }
    
    public String getServiceConfigURL()
    {
        return this.serviceConfigURL;
    }
    
    public String getInstanceSize()
    {
        return this.instanceSize;
    }
    
    public String getLocation()
    {
        return this.location;
    }
}
