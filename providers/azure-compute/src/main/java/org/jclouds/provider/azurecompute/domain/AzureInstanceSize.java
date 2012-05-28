package org.jclouds.provider.azurecompute.domain;

public class AzureInstanceSize{
    
    /*
     * Extra Small Instance	
     * <ul>
     * <li>1.0 GHz cpu</li>
     * <li>768 MB of memory</li>
     * <li>20 GB of instance storage</li>
     * <li>IO performance: Low</li>
     * <li>$0.05 per hour</li>
     * </ul>
     */
    public static final String EXTRA_SMALL = "Extra Small";
    
     /*
     * Small Instance	
     * <ul>
     * <li>1.6 GHz cpu</li>
     * <li>1.75 GB of memory</li>
     * <li>225 GB of instance storage</li>
     * <li>IO performance: Moderate</li>
     * <li>$0.12 per hour</li>
     * </ul>
     */
    public static final String SMALL = "Small";
    
     /*
     * Medium Instance	
     * <ul>
     * <li>2x 1.6 GHz cpu</li>
     * <li>3.5 GB of memory</li>
     * <li>490 GB of instance storage</li>
     * <li>IO performance: High</li>
     * <li>$0.24 per hour</li>
     * </ul>
     */
    public static final String MEDIUM = "Medium";
    
     /*
     * Large Instance	
     * <ul>
     * <li>4x 1.6 GHz cpu</li>
     * <li>7 GB of memory</li>
     * <li>1000 GB of instance storage</li>
     * <li>IO performance: High</li>
     * <li>$0.48 per hour</li>
     * </ul>
     */
    public static final String LARGE = "Large";
    
    /*
     * Extra Large Instance	
     * <ul>
     * <li>8x 1.6 GHz cpu</li>
     * <li>14 GB of memory</li>
     * <li>2040 GB of instance storage</li>
     * <li>IO performance: High</li>
     * <li>$0.96 per hour</li>
     * </ul>
     */
    public static final String EXTRA_LARGE = "Extra Large";
}
