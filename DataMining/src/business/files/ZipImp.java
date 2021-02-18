package business.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import business.transfers.TZip;

public class ZipImp implements Zip {
    
	private static final int N_BYTES = 1024;
	
	private String pathZip;
	
	public ZipImp(String path) {
		pathZip = path;
	}
	
    public void unZip(String pathUnZip) {
    	
        byte[] buffer = new byte[N_BYTES];
        
        try {
        	// If file exists create a directory
            File folder = new File(pathUnZip);
            
            if (!folder.exists()) {
            	
                folder.mkdir();
                
                ZipInputStream zis = new ZipInputStream(new FileInputStream(pathZip));
                ZipEntry ze = zis.getNextEntry();
                
                while (ze != null) {
                	
                    String nameZip = ze.getName();
                    
                    File newFile = new File(pathUnZip + File.separator + nameZip);
                    
                    System.out.println("Unzip file:  " + newFile.getAbsoluteFile());
                    
                    new File(newFile.getParent()).mkdirs();
                    
                    // Write in new file
                    FileOutputStream fos = new FileOutputStream(newFile);
                    
                    int len = 0;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                                        
                    fos.close();
                                        
                    ze = zis.getNextEntry();
                }
                
                zis.closeEntry();
                zis.close();
                
                System.out.println("Unzip ok!");
                
            }else {
            	System.out.println("Zip doesn't exist!");
            }
            
        } catch (IOException ex) {
        	
            ex.printStackTrace();
        }
    }
    
    public TZip readZip(String pathTarget){
    	
    	TZip transfer = new TZip();
    	
    	List<InputStream> list = new ArrayList<InputStream>();
    	List<String> list_names = new ArrayList<String>();
        ZipFile zipFile;
        
		try {
			zipFile = new ZipFile(pathZip);
	        Enumeration<? extends ZipEntry> entries = zipFile.entries();

	        while(entries.hasMoreElements()){               
	            ZipEntry entry = entries.nextElement();
	            InputStream stream = zipFile.getInputStream(entry);

	            byte[] buffer = new byte[stream.available()];
	            stream.read(buffer);
	            
	            list.add(stream);
	            list_names.add(entry.getName());

	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}
		
		transfer.setFiles(list);
		transfer.setPath(pathZip);
		transfer.setNames(list_names);

		return transfer;
    }    
}
