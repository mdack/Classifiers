package business.classifiers.matrizsimilitud;

import java.util.ArrayList;
import java.util.List;

import business.classifiers.cluster.Cluster;
import business.classifiers.cluster.ClusterImg;
import business.classifiers.cluster.ClusterSig;
import business.elements.Image;
import business.elements.Signal;
import business.factory.FactoryAS;
import business.files.Data;
import business.transfers.TMatrizSim;
import business.transfers.TResult;

public class MatrizSimImpl implements MatrizSim{
	private List<Signal> signals;
	private int total_files;
	private List<Image> imgs;
	private boolean areSignals;
	private List<Cluster> clusters = new ArrayList<>();
	private double[][] matrix;
	private int A = 0;
	private int teta = 0;
	
	@Override
	public TResult executeAlgorithm(TMatrizSim transfer) {
		Data data = FactoryAS.getInstance().readData(transfer.gettZip().getFiles(),transfer.gettZip().getNames());
		
		if(transfer.gettZip().isAreSignals()) {
			this.signals = data.readSignals();
			this.total_files = signals.size();
		}else {
			this.imgs = data.readImages();
			this.total_files = imgs.size();
			this.areSignals = false;
		}
		teta = transfer.getO();
		matrix = new double[total_files][total_files];
		
		
		//Calcular las distancias entre todas las parejas de patrones
		if(this.areSignals)
			calculateDistancesSig();
		else
			calculateDistancesImgs();

		while(!isFullMatrix()) {
			//Seleccionan el agrupamiento más numeroso entre todos los candidatos, 
			//siendo Xi el patrón ”central” del agrupamiento seleccionado.
			int row = 0;
			double[] r = this.getRowNumerosa(row);
			
			if(this.areSignals)
				loopSig(row, r);
			else
				loopImgs(row, r);
			
			boolean found = false;
			int pos = 0;
			while(pos < r.length && !found) {
				if(r[pos] != 1){
					found = true;
					row = pos;
				}
				pos++;
			}
		}
		
		TResult result = new TResult();
		result.setList(clusters);
		result.setN(A);
		
		return result;
	}

	private boolean isFullMatrix() {
		boolean found  = true;
		for(int i = 0; i < this.total_files && found; i++) {
			for(int j = 0; j < this.total_files && found; j++) {
				if(matrix[i][j] != 1) {
					found = false;
				}
			}
		}
		return found;
	}

	private void loopSig(int row, double[] r) {
		Cluster cl = new ClusterSig(A, signals.get(row));
		cl.setCentroid(signals.get(row));
		
		for(int i = 0; i < r.length; i++) {
			if(r[i] == 1) {
				cl.addItem(signals.get(i));
				for(int k = 0; k < r.length; k++) {
					if(matrix[i][k] == 1 && r[k] != 1) {
						cl.addItem(signals.get(k));
					}else if(signals.get(i).calculateDistanceTo(signals.get(k)) <= teta) {
						cl.addItem(signals.get(k));
						r[k] = 1;
					}
				}
			}
		}
		matrix[row] = r;
		clusters.add(cl);
		A++;
		
	}

	private void calculateDistancesSig() {
		for(int i = 0; i < signals.size(); i++) {
			for(int j = 0; j < signals.size(); j++) {
					double dist = signals.get(i).calculateDistanceTo(signals.get(j));
					if(dist <= teta) {
						matrix[i][j] = 1;
					}
			}
		}
		
	}

	private void loopImgs(int row, double[] r) {
		Cluster cl = new ClusterImg(A, imgs.get(row));
		cl.setCentroid(imgs.get(row));
		imgs.remove(row);
		
		for(int i = 0; i < r.length; i++) {
			if(r[i] == 1) {
				cl.addItem(imgs.get(i));
				for(int k = 0; k < r.length; k++) {
					if(matrix[i][k] == 1 && r[k] != 1) {
						cl.addItem(imgs.get(k));
					}else if(imgs.get(i).calculateDistanceTo(imgs.get(k)) <= teta) {
						cl.addItem(imgs.get(k));
						r[k] = 1;
					}
				}
			}
		}
		clusters.add(cl);
		A++;
		
	}

	private void calculateDistancesImgs() {
		for(int i = 0; i < imgs.size(); i++) {
			for(int j = 0; j < imgs.size(); j++) {
					double dist = imgs.get(i).calculateDistanceTo(imgs.get(j));
					
					if(dist <= teta) {
						matrix[i][j] = 1;
					}
			}
		}		
	}
	
	private double[] getRowNumerosa(int r_numerosa) {
		int r_max = 0;
		for(int r = 0; r < total_files; r++) {
			int cont = 0;
			for(int c = 0; c < total_files; c++) {
				if(matrix[r][c] == 1)
					cont++;
			}
			if(cont > r_max) {
				r_max = cont;
				r_numerosa = r;
			}
		}
		return matrix[r_numerosa];
	}

}
