package org.jclouds.provider.azurecompute.compute.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.Key;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.jclouds.provider.azurecompute.compute.reference.AzureConstants;

/**
 *
 * Singleton
 * 
 * @author Eric
 */
public class CertificateManager {
    
    private CertificateManager( )
    {
    }
    
    private static class InstanceHolder
    {
        public static final CertificateManager INSTANCE = new CertificateManager();
    }
    
    public static CertificateManager getInstance()
    {
        return InstanceHolder.INSTANCE;
    }
    
    public void GenerateCacertsFromCer(String cerFile, String cacertsTargetLoc, String cacertsPassword)
    {
        Process proc;
        //keytool -import -trustcacerts -noprompt -keystore cacertsTargetLoc -storepass cacertsPassword -alias alias -file cerFile
        //Use an array to allow spaces in the arguments.
        String[] commandExecKeytool = {"\"" + System.getProperty("java.home").replaceAll("jre", "bin") + "\\keytool.exe\"", "-import", "-trustcacerts", "-noprompt", "-keystore", cacertsTargetLoc, "-storepass", cacertsPassword, "-alias", AzureConstants.CERTIFICATE_ALIAS, "-file", cerFile};
        
        try
        {
            //run keytool
            proc = Runtime.getRuntime().exec(commandExecKeytool);            
            proc.waitFor();
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch(InterruptedException ie)
        {
            ie.printStackTrace();
        }
    }
    
    /*
     * @Author http://www.windowsazure4j.org/learn/labs/Management/index.html
     */
    public void GenerateKeystoreFromPFX(String pfxFile, String keystoreTargetLocation, String pfxPassword, String keystorePassword) throws Exception 
    {
        File fileIn = new File(pfxFile);
        File fileOut = new File(keystoreTargetLocation);
        
        if (!fileIn.canRead()) {
            System.out.println("Unable to access input keystore: "
                    + fileIn.getPath());
            throw new Exception("Unable to access input keystore: "
                    + fileIn.getPath());
        }
        if (fileOut.exists() && !fileOut.canWrite()) {
            System.out.println("Output file is not writable: "
                    + fileOut.getPath());
            throw new Exception("Output file is not writable: "
                    + fileOut.getPath());
        }
        KeyStore kspkcs12 = KeyStore.getInstance("pkcs12");
        KeyStore ksjks = KeyStore.getInstance("jks");
        char[] inphrase = pfxPassword.toCharArray();
        char[] outphrase = keystorePassword.toCharArray();
        kspkcs12.load(new FileInputStream(fileIn), inphrase);
        ksjks.load((fileOut.exists()) ? new FileInputStream(fileOut) : null, outphrase);
        Enumeration eAliases = kspkcs12.aliases();
        int n = 0;
        List <String> list = new ArrayList<String>();
        if (!eAliases.hasMoreElements()) {
            throw new Exception(
                    "Certificate is not valid. It does not contain any alias.");
        }
        while (eAliases.hasMoreElements()) {
            String strAlias = (String) eAliases.nextElement();
            if (kspkcs12.isKeyEntry(strAlias)) {
                Key key = kspkcs12.getKey(strAlias, inphrase);
                Certificate[] chain = kspkcs12.getCertificateChain(strAlias);
 
                if (AzureConstants.CERTIFICATE_ALIAS != null)
                    strAlias = AzureConstants.CERTIFICATE_ALIAS;
 
                ksjks.setKeyEntry(strAlias, key, outphrase, chain);
                list.add(strAlias);
            }
        }
        OutputStream out = new FileOutputStream(fileOut);
        ksjks.store(out, outphrase);
        out.close();
    }
}
