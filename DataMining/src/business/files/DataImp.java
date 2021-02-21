package business.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import business.classifiers.cluster.Cluster;
import business.elements.FileData;
import business.elements.Image;
import business.elements.Signal;
import business.transfers.TResult;

public class DataImp implements Data{

	private List<FileData> list_files;
	
	public DataImp(List<FileData> list) {
		this.list_files = list;
	}
	
	public DataImp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Image> readImages() {
		
		List<Image> list = new ArrayList<>();
		
		for(FileData fd : list_files) {
			double row = 0;
			List<List<Double>> list_rows = new ArrayList<List<Double>>();
				System.out.println(fd.getName() + "\n");
				
	            String text = null;
	            try (Scanner scanner = new Scanner(fd.getData(), StandardCharsets.UTF_8.name())) {
					List<Double> list_cols = null;
	            	while(scanner.hasNext()) {
		                text = scanner.nextLine();

		                text = text.replaceAll(" ", ",");
		                String[] cols = text.split(",");
		                double[] nums = new double[3];
		                
		                int cont = 0;
		                for(int i = 0; i < cols.length; i++) {
		                	if(!cols[i].isEmpty()) {
		                		nums[cont] = Double.valueOf(cols[i]);
		                		cont++;
		                	}
		                }

					     
					     double r = nums[0];
					     if(r != row) {
					    	 row = r;
					    	 if(r != 1) {
					    		 list_rows.add(list_cols);
					    		 list_cols  = new ArrayList<Double>();
					    	 }else {
					    		 list_cols  = new ArrayList<Double>();
					    	 }
					     }
					     
						double pixel = nums[2];
						list_cols.add(pixel);
	            	}
					Image img = new Image();
					img.setImage(list_rows);
					img.setCols(list_cols.size());
					img.setRows(list_rows.size());
					img.setName(fd.getName());
					list.add(img);
	            };
	            
		}
		return list;
	}

	@Override
	public List<Signal> readSignals() {
		List<Signal> list = new ArrayList<>();

		for(FileData fd : list_files) {
			System.out.println(fd.getName() + "\n");
			
			String text = null;
			try (Scanner scanner = new Scanner(fd.getData(), StandardCharsets.UTF_8.name())) {
				TreeMap<Double,Double> list_s = new TreeMap<>();
	            	while(scanner.hasNext()) {
		                text = scanner.nextLine();

		                text = text.replaceAll(" ", ",");
		                String[] cols = text.split(",");
		                double[] nums = new double[2];
		                
		                int cont = 0;
		                for(int i = 0; i < cols.length; i++) {
		                	if(!cols[i].isEmpty()) {
		                		nums[cont] = Double.valueOf(cols[i]);
		                		cont++;
		                	}
		                }
						list_s.put(nums[0], nums[1]);
	            	}
	            	
					Signal signal = new Signal();
					signal.setSignal(list_s);
					signal.setName(fd.getName());
					list.add(signal);
					
				}
		}
		return list;
	}

	@Override
	public void writeCluster(TResult transfer) {
        File directorio = new File("C:/Resultados-Clasificadores");
        
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
            	writeResults(transfer.getList());         	
            	
                System.out.println("Resultados copiados en la ruta C:/Resultados-Clasificadores/");
            } else {
                System.out.println("Error al crear directorio");
            }
        }else {
        	directorio.delete();
        	
        	if (directorio.mkdirs()) {
            	writeResults(transfer.getList());
            	System.out.println("Resultados copiados en la ruta C:/Resultados-Clasificadores/");
        	}
        	else {
                System.out.println("Error al escribir resultados");
            }
        }
		
	}

	private void writeResults(List<Cluster> list) {
		for(int i = 0; i < list.size(); i++) {
    		File directorio2 = new File("C:/Resultados-Clasificadores/Cluster-" + i);
            	if (directorio2.mkdirs()) {
            		List<Image> list_img = list.get(i).getImages();
            		
            		if(list_img != null) {
            			createFileImg(list_img,i, (Image) list.get(i).getCentroid());
            		}
            		else {
            			List<Signal> list_sig = list.get(i).getSignals();
            			createFileSig(list_sig,i, (Signal) list.get(i).getCentroid());
            		}
            		
            	}
    	}
		
	}

	private void createFileSig(List<Signal> list_sig, int id, Signal centroide) {
		
		if(!list_sig.isEmpty()) {
			for(Signal sig: list_sig) {
				FileWriter fichero = null;
		        PrintWriter pw = null;
		        try
		        {
		            fichero = new FileWriter("C:/Resultados-Clasificadores/Cluster" + id + "/" + sig.getName());
		            pw = new PrintWriter(fichero);
		
		            for (Map.Entry<Double, Double> entry: sig.getSignal().entrySet()) {
		            		pw.println("\t" + entry.getKey() + "\t" + entry.getValue());
		            }   
		               
		
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		           try {
		           if (null != fichero)
		              fichero.close();
		           } catch (Exception e2) {
		              e2.printStackTrace();
		           }
		        }	
			}
				PrintWriter pw_c = null;
				FileWriter fichero_c = null;
		        try {
					fichero_c = new FileWriter("C:/Resultados-Clasificadores/Cluster" + id + "/centroide.txt");
					pw_c = new PrintWriter(fichero_c);
			        for (Map.Entry<Double, Double> entry: centroide.getSignal().entrySet()) {
			    		pw_c.println("\t" + entry.getKey() + "\t" + entry.getValue());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
			           try {
				           if (null != fichero_c)
				              fichero_c.close();
				           } catch (Exception e2) {
				              e2.printStackTrace();
				           }
				        }
		}
	}
        

	private void createFileImg(List<Image> list_img, int id, Image centroide) {
		
		if(!list_img.isEmpty()) {
			for(Image img: list_img) {
				FileWriter fichero = null;
		        PrintWriter pw = null;
		        try
		        {
		            fichero = new FileWriter("C:/Resultados-Clasificadores/Cluster" + id + "/" + img.getName() + ".dat");
		            pw = new PrintWriter(fichero);
		
		            for (int i = 0; i < img.getRows(); i++) {
		            	for(int j = 0; j < img.getCols(); j++) {
		            		int r = i+1;
		            		int c = j+1;
		            		pw.println("\t" + r + "\t" + c + "\t" + img.getPixel(i, j));
		            	}
		            }    
		
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		           try {
		           if (null != fichero)
		              fichero.close();
		           } catch (Exception e2) {
		              e2.printStackTrace();
		           }
		        }
			}
			PrintWriter pw_c = null;
			FileWriter fichero_c = null;
	        try {
				fichero_c = new FileWriter("C:/Resultados-Clasificadores/Cluster" + id + "/centroide.txt");
				pw_c = new PrintWriter(fichero_c);
				for (int i = 0; i < centroide.getRows(); i++) {
	            	for(int j = 0; j < centroide.getCols(); j++) {
	            		int r = i+1;
	            		int c = j+1;
	            		pw_c.println("\t" + r + "\t" + c + "\t" + centroide.getPixel(i, j));
	            	}
	            }    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
		           try {
		           if (null != fichero_c)
		              fichero_c.close();
		           } catch (Exception e2) {
		              e2.printStackTrace();
		           }
		        }
		}
		
	}
	
	

}
