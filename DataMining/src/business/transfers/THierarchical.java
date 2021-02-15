package business.transfers;

public class THierarchical {
	private int tipo_link;
	private TZip tZip;
	
	public THierarchical(int tLink, TZip tZip2) {
		tipo_link = tLink;
		tZip = tZip2;
	}
	public int getTipo_link() {
		return tipo_link;
	}
	public void setTipo_link(int tipo_link) {
		this.tipo_link = tipo_link;
	}
	public TZip gettZip() {
		return tZip;
	}
	public void settZip(TZip tZip) {
		this.tZip = tZip;
	}
	
	
}
