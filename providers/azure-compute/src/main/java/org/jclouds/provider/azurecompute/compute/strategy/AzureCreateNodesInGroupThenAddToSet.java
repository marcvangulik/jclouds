package org.jclouds.provider.azurecompute.compute.strategy;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jclouds.provider.azurecompute.compute.options.AzureTemplateOptions;
import org.jclouds.compute.config.CustomizationResponse;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.strategy.CreateNodesInGroupThenAddToSet;
import org.jclouds.compute.util.ComputeUtils;
import org.soyatec.windowsazure.management.DeploymentConfiguration;
import org.soyatec.windowsazure.management.DeploymentSlotType;
import org.soyatec.windowsazure.management.HostedServiceProperties;
import org.soyatec.windowsazure.management.ServiceManagementRest;

public class AzureCreateNodesInGroupThenAddToSet implements CreateNodesInGroupThenAddToSet {

    private final ServiceManagementRest sm;
    final ComputeUtils utils;

    @Inject
    private AzureCreateNodesInGroupThenAddToSet(ServiceManagementRest sm, ComputeUtils utils) {
        this.sm = sm;
        this.utils = utils;
    }

    @Override
    public Map<?, Future<Void>> execute(String group, int count, Template template, Set<NodeMetadata> goodNodes, Map<NodeMetadata, Exception> badNodes, Multimap<NodeMetadata, CustomizationResponse> customizationResponses) {

        CreateHostedServiceAndDeploymentAndLaunchNodes(template.getOptions().as(AzureTemplateOptions.class), count);
//        OperatingSystem os = new OperatingSystem(OsFamily.WINDOWS, "Windows Azure,", null, null, "Standard Windows Azure operating System", true);
//        ArrayList<String> publicAddresses = new  ArrayList<String>();
//        publicAddresses.add(ato.getServiceName()+".cloudapp.net");
//        ArrayList<String> privateAddresses = new  ArrayList<String>();
//        privateAddresses.add(ato.getServiceName()+".cloudapp.net");
//        Credentials credentials = null;
//        Map<String, String> userMetadata = new HashMap<String,String>();
//        userMetadata.put("User", "MetaData");
//        NodeMetadataImpl nodes = new NodeMetadataImpl("providerId", ato.getServiceName(), "id", null, null, userMetadata, group, null, null, os, NodeState.UNRECOGNIZED, 1, publicAddresses, privateAddresses, null, credentials);
//        Set dataSet = new HashSet();
//        dataSet.add(nodes);

        Iterable<NodeMetadata> runningNodes = new ArrayList<NodeMetadata>();
        
        return utils.customizeNodesAndAddToGoodMapOrPutExceptionIntoBadMap(template.getOptions().as(AzureTemplateOptions.class), runningNodes, goodNodes, badNodes, customizationResponses);
    }
    
    //Todo: Change return type, should return Set<? extends NodeMetadata>
    private void CreateHostedServiceAndDeploymentAndLaunchNodes(AzureTemplateOptions templateOptions, int count) {

        if(!templateOptions.isValid()){
            throw new IllegalArgumentException("AzureTemplateOptions not valid.");
        }
        
        downloadServiceConfigurationFromBlobStore(templateOptions.getServiceConfigURL());
        updateInstanceCountInServiceConfiguration(getCfgFileNameFromUrl(templateOptions.getServiceConfigURL()), count);

        HostedServiceProperties hsp = new HostedServiceProperties();
        hsp.setName(templateOptions.getServiceName());
        hsp.setLocation(templateOptions.getLocation());
        hsp.setUrl(templateOptions.getUrlPrefix());
        hsp.setLabel(templateOptions.getServiceName());

        sm.createHostedService(hsp.getName(), hsp.getLabel(),hsp.getDescription(),hsp.getLocation(), hsp.getAffinityGroup());
        
        DeploymentConfiguration dc = new DeploymentConfiguration();
        dc.setName(templateOptions.getDeploymentName());
        dc.setLabel(templateOptions.getDeploymentName());
        dc.setConfigurationFileUrl(getCfgFileNameFromUrl(templateOptions.getServiceConfigURL()));
        dc.setPackageBlobUrl(templateOptions.getServicePackageURL());
        dc.setStartDeployment(true);

        sm.createDeployment(templateOptions.getServiceName(), DeploymentSlotType.Production, dc, null);
    }

    /**
     * @return Location of downloaded ServiceConfiguration.cscfg
     */
    private void downloadServiceConfigurationFromBlobStore(String servConfigUrl) {
        InputStream reader;

        String config;
        BufferedWriter bw;
        File file = new File(getCfgFileNameFromUrl(servConfigUrl));
        try {
            URL url = new URL(servConfigUrl);
            url.openConnection();
            reader = url.openStream();

            config = convertStreamToString(reader);
            if (!config.startsWith("<")) {
                config = config.substring(1);
            }
            bw = new BufferedWriter(new FileWriter(getCfgFileNameFromUrl(servConfigUrl)));
            bw.write(config);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @VisibleForTesting
    public String getCfgFileNameFromUrl(String url) {
        if(!url.endsWith(".cscfg")){
            throw new IllegalArgumentException("url, ..");
        }
        
        Pattern p = Pattern.compile("[A-Za-z0-9]+.cscfg");
        Matcher m = p.matcher(url);
        String lastValidMatch = "";

        while (m.find()) {
            lastValidMatch = m.group();
        }
        return lastValidMatch;
    }

    public String convertStreamToString(InputStream is)
            throws IOException {
        /*
         * To convert the InputStream to String we use the
         * Reader.read(char[] buffer) method. We iterate until the
         * Reader return -1 which means there's no more data to
         * read. We use the StringWriter class to produce the string.
         */
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    @VisibleForTesting
    public void updateInstanceCountInServiceConfiguration(String serviceConfigLocation, int newInstanceCount) {
        File f = new File(serviceConfigLocation);
        BufferedReader br;
        BufferedWriter bw;
        String line, s = "";

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

            while ((line = br.readLine()) != null) {
                s += line + "\r\n";
            }

            br.close();

            s = s.replaceAll("count=(\"[0-9]*\")", "count=\"" + newInstanceCount + "\"");

            bw = new BufferedWriter(new FileWriter(serviceConfigLocation));

            bw.write(s);

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
