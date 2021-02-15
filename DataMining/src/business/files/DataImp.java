package business.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.elements.Image;
import business.elements.Signal;

public class DataImp implements Data{

	private List<InputStream> list_data;
	
	public DataImp(List<InputStream> list) {
		list_data = list;
	}
	
	@Override
	public List<Image> readImages() {
		
		List<Image> list = new ArrayList<>();
		for(InputStream is : list_data) {
			int row = 0;
			List<List<Integer>> list_rows = new ArrayList<List<Integer>>();
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					List<Integer> list_cols = null;
					
					while(reader.ready()) {
					     String line = reader.readLine();
					     String[] cols = line.split("\t");
					     
					     int r = Integer.parseInt(cols[0]);
					     if(r != row) {
					    	 row = r;
					    	 if(r != 1) {
					    		 list_rows.add(list_cols);
					    		 list_cols  = new ArrayList<Integer>();
					    	 }else {
					    		 list_cols  = new ArrayList<Integer>();
					    	 }
					     }
						int pixel = Integer.parseInt(cols[2]);
						list_cols.add(pixel);
					}
					
					Image img = new Image();
					img.setImage(list_rows);
					img.setCols(list_cols.size());
					img.setRows(list_rows.size());
					list.add(img);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return list;
	}

	@Override
	public List<Signal> readSignals() {
		List<Signal> list = new ArrayList<>();
		for(InputStream is : list_data) {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					HashMap<Float,Float> list_s = new HashMap<>();
					
					while(reader.ready()) {
					     String line = reader.readLine();
					     String[] cols = line.split("\t");
					     
					    float t = Float.parseFloat(cols[0]);
						float s = Float.parseFloat(cols[1]);
						list_s.put(t, s);
					}
					Signal signal = new Signal();
					signal.setSignal(list_s);
					list.add(signal);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return list;
	}

}
