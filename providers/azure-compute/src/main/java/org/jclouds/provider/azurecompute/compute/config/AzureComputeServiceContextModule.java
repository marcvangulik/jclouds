/**
 *
 * Copyright (C) 2011 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */
package org.jclouds.provider.azurecompute.compute.config;


import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;
import com.google.inject.Injector;

import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.config.BaseComputeServiceContextModule;
import org.jclouds.provider.azurecompute.compute.AzureComputeService;
import static org.jclouds.compute.domain.OsFamily.WINDOWS;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import java.io.File;
import java.io.FilenameFilter;
import org.jclouds.provider.azurecompute.compute.reference.AzureConstants;
import org.jclouds.Constants;
import com.google.inject.name.Named;
import java.io.FileNotFoundException;
import org.jclouds.provider.azurecompute.compute.internal.CertificateManager;
import org.jclouds.compute.domain.TemplateBuilder;
import org.soyatec.windowsazure.management.ServiceManagementRest;

/**
 * Configures the {@link ComputeServiceContext}; requires {@link AzureComputeService} bound.
 * 
 * @author Adrian Cole
 */
public class AzureComputeServiceContextModule extends BaseComputeServiceContextModule {
   
    @Override
   protected void configure() {
      installDependencies();
      install(new AzureBindComputeStrategiesByClass());
      install(new AzureBindComputeSuppliersByClass());
      super.configure();
   }
   
   protected void installDependencies(){
      install(new AzureComputeServiceDependenciesModule());
   }
   
   @Override
   @Provides
   @Named("WINDOWS")
   protected TemplateBuilder provideTemplate(Injector injector, TemplateBuilder template) {
      return template.osFamily(WINDOWS).os64Bit(true);
   }
     
   @VisibleForTesting
   @Inject
   @Provides
   @Singleton
   /*   TODO: refactor de try-catch in catches */
   public ServiceManagementRest provideServiceManagementRest(@Named(Constants.PROPERTY_IDENTITY) String subscriptionId, @Named(Constants.PROPERTY_CREDENTIAL) String password)
   {   
       System.out.println("------------------");
       String keystoreFile = "", cacertsFile = "";
       
       try{
           keystoreFile = findFileInFolderWithExtension(AzureConstants.CERTIFICATE_FOLDER, ".keystore").getName();
       }catch(FileNotFoundException e)
       {
            try {
                System.out.println("No .keystore found, trying to generate one from .pfx");
                keystoreFile = generateKeystoreFileFromPfxIfAvailable(password);
            } catch (Exception ex) {
                System.out.println("No .keystore or .pfx files found in folder " + AzureConstants.CERTIFICATE_FOLDER);
                ex.printStackTrace();
            }
       }
       
       try{
           cacertsFile = findFileInFolderWithExtension(AzureConstants.CERTIFICATE_FOLDER, ".cacerts").getName();
       }catch(FileNotFoundException e)
       {
           try {
               System.out.println("No .cacerts found, trying to generate one from .cer");
               cacertsFile = generateCacertsFileFromCerIfAvailable(password);
           } catch (Exception ex) {
               System.out.println("No .cacerts or .cer files found in folder " + AzureConstants.CERTIFICATE_FOLDER);
               ex.printStackTrace();
           }
       }
       
       try{
           return new ServiceManagementRest(subscriptionId, 
                   AzureConstants.CERTIFICATE_FOLDER + "/" + keystoreFile, password, 
                   AzureConstants.CERTIFICATE_FOLDER + "/" + cacertsFile, password, 
                   AzureConstants.CERTIFICATE_ALIAS);
       }catch(Exception e)
       {
           e.printStackTrace();
       }
       
       return null;
    }

    private String generateKeystoreFileFromPfxIfAvailable(String password) throws Exception, FileNotFoundException
    {
        String keystoreFile = AzureConstants.PREFERED_CERTIFICATE_NAME + ".keystore";
        String pfxFile = findFileInFolderWithExtension(AzureConstants.CERTIFICATE_FOLDER, ".pfx").getName();
        CertificateManager.getInstance().GenerateKeystoreFromPFX(AzureConstants.CERTIFICATE_FOLDER + "/" + pfxFile, 
                AzureConstants.CERTIFICATE_FOLDER + "/" + keystoreFile, 
                password, password);
        
        return keystoreFile;
    }

    private String generateCacertsFileFromCerIfAvailable(String password) throws Exception, FileNotFoundException
    {
        String cacertsFile = AzureConstants.PREFERED_CERTIFICATE_NAME + ".cacerts";
        String cerFile = findFileInFolderWithExtension(AzureConstants.CERTIFICATE_FOLDER, ".cer").getName();
        CertificateManager.getInstance().GenerateCacertsFromCer(AzureConstants.CERTIFICATE_FOLDER + "/" + cerFile, 
                AzureConstants.CERTIFICATE_FOLDER + "/" + cacertsFile, 
                password);
        
        return cacertsFile;
    }
    
    private File findFileInFolderWithExtension(String folder, final String extension) throws FileNotFoundException
    {
        File directory = new File(folder);
        File[] files;

        if(!directory.exists()){
            throw new FileNotFoundException("Directory " + AzureConstants.CERTIFICATE_FOLDER + " does not exist in " + System.getProperty("user.dir"));
        }
        
        FilenameFilter fnFilter = new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name){
                return name.endsWith(extension);
            }
        };
        
        files = directory.listFiles(fnFilter);
        
        if(files.length > 1)
        {
            System.out.println("WARNING: More than one " + extension + " file was found\n\tUsing: " + System.getProperty("user.dir") + "\\" + files[0]);
            return files[0];
        }
        else if(files.length == 0)
        {
            throw new FileNotFoundException("No " + extension + " file found.");
        }
        
        return files[0];
    }
}
