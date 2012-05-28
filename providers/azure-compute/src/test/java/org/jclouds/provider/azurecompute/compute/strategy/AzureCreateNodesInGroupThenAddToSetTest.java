package org.jclouds.provider.azurecompute.compute.strategy;

//package org.jclouds.azure.compute.strategy;
//
//
//import org.easymock.EasyMock;
//import org.jclouds.compute.util.ComputeUtils;
//import org.soyatec.windowsazure.management.ServiceManagementRest;
//import static org.easymock.classextension.EasyMock.createMock;
//import static org.easymock.classextension.EasyMock.replay;
//import static org.testng.Assert.assertEquals;
//
//import org.testng.annotations.Test;
//
///*
// * is dit wel te testen?
// * Constructor van AzureCreateNodesInGroupThenAddToSet is private...
// * 
// */
//
//@Test(groups = "unit")
//public class AzureCreateNodesInGroupThenAddToSetTest {
//    
//    private AzureCreateNodesInGroupThenAddToSet createNodeStrat;
//    private ServiceManagementRest mock_smr;
//    private ComputeUtils mock_compUtils;
//
//    public AzureCreateNodesInGroupThenAddToSetTest() {
//        try{
////            mock_smr = createMock(ServiceManagementRest.class);
////            mock_compUtils = createMock(ComputeUtils.class);
//            //createNodeStrat = createMock(AzureCreateNodesInGroupThenAddToSet.class);
//            //replay(createNodeStrat);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    
//    
//    @Test
//    public void TestGetCfgFileNameFromUrl()
//    {
//        String filename = createNodeStrat.getCfgFileNameFromUrl("http://name.blob.core.windows.net/container.ServiceConfiguration.cscfg");
//        
//        assertEquals(filename, "ServiceConfiguration.cscfg");
//    }
//    
//    @Test
//    public void TestGetCfgFileNameFromUrlBadUrl()
//    {
//        String filename = createNodeStrat.getCfgFileNameFromUrl("http://name.blob.core.windows.net/cscfg");
//
//    }
//    
//    @Test(expectedExceptions = { IllegalArgumentException.class })
//    public void TestGetCfgFileNameFromUrlWrongExtension()
//    {
//        String filename = createNodeStrat.getCfgFileNameFromUrl("http://name.blob.core.windows.net/container.ServiceConfiguration.xxx");
//    }
//}
