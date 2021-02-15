package business.transfers;

public class Pixel {
	private double value;
    private int id_cluster;

    public Pixel(double v) {
        this.value = v;
        this.id_cluster = -1;
    }


    public double getColorValue(int index) {
        int v = (int) value;
        switch(index)
        {
            case 0:
                return v>>16&0x000000FF;
            case 1:
                return v>> 8&0x000000FF;
            case 2:
                return v>> 0&0x000000FF;
            default:
                return 0;
        }
    }

    public double getValue() {
        return value;
    }

    public int getId_cluster() {
        return id_cluster;
    }

    public void setId_cluster(int id_cluster) {
        this.id_cluster = id_cluster;
    }
}
