package org.jclouds.provider.azurecompute.compute.config;

//package org.jclouds.azure.compute;
//
//import java.io.FileNotFoundException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import junit.framework.Assert;
//import junit.framework.TestCase;
//import org.jclouds.azure.compute.config.AzureComputeServiceContextModule;
//import org.jclouds.azure.compute.reference.AzureConstants;
//import org.soyatec.windowsazure.management.ServiceManagementRest;
//import org.testng.annotations.*;
//
//public class AzureComputeServiceContextModuleTest extends TestCase{
//    
//    private AzureComputeServiceContextModule cModule;
//    
//    @BeforeSuite
//    public void setup()
//    {
//        cModule = new AzureComputeServiceContextModule();
//    }
//    
//    @AfterSuite
//    public void tearDown()
//    {
//        
//    }
//    
//    @Test
//    public void testProvideServiceManagementRest() throws InterruptedException
//    {
//        final String subscriptionID = "6cd9743d-33cd-4f3f-8f3c-6859af4c086a";
//        final String certPassword = "avaj123!";
//        ServiceManagementRest actualResult = null, expectedResult = null;
//        String actual = "", expected = "";
//        //
//        //Build expected result
//        try {
//            expectedResult =    new ServiceManagementRest(subscriptionID, AzureConstants.CERTIFICATE_FOLDER + "/" + AzureConstants.PREFERED_CERTIFICATE_NAME + ".keystore", certPassword, AzureConstants.CERTIFICATE_FOLDER + "/" + AzureConstants.PREFERED_CERTIFICATE_NAME + ".cacerts", certPassword, AzureConstants.CERTIFICATE_ALIAS);
//        } catch (Exception ex) {
//            Assert.fail("Failed to create expected result\n" + ex.getStackTrace());
//        }
//        
//        actualResult = cModule.provideServiceManagementRest(subscriptionID, certPassword);
//        
//        actual = actualResult.getSubscriptionId();
//        expected = expectedResult.getSubscriptionId();
//        
//        Assert.assertEquals(expected, actual);
//    }
//}
