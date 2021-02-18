package business.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import business.elements.FileData;
import business.elements.Image;
import business.elements.Signal;

public class DataImp implements Data{

	private List<InputStream> list_data;
	private List<String> names;
	private List<FileData> list_files;
	
	public DataImp(List<InputStream> list, List<String> names2) {
		list_data = list;
		names = names2;
	}
	
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
		int i = 0;
		for(InputStream is : list_data) {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					HashMap<Double,Double> list_s = new HashMap<>();
					
					while(reader.ready()) {
					     String line = reader.readLine();
					     String[] cols = line.split("\t");
					     
					    double t = Double.valueOf(cols[0]);
						double s = Double.valueOf(cols[1]);
						list_s.put(t, s);
					}
					Signal signal = new Signal();
					signal.setSignal(list_s);
					signal.setName(names.get(i));
					list.add(signal);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
		}
		return list;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

}
