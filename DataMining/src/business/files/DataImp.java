package business.files;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import business.elements.FileData;
import business.elements.Image;
import business.elements.Signal;

public class DataImp implements Data{

	private List<FileData> list_files;
	
	public DataImp(List<FileData> list) {
		this.list_files = list;
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
					HashMap<Double,Double> list_s = new HashMap<>();
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

}
