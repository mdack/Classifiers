package business.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zip {
    
	private static final int N_BYTES = 1024;
	
	private String nameZip;
	private String pathZip;
	
	public Zip(String path) {
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
                	
                    nameZip = ze.getName();
                    
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
    
}
