package eu.i07.models;

public class Module implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6261282609441290974L;
	
	private String name;
	private String filename;
	
	public Module() {
		
	}
	
	public Module(String name, String filename) {
		this.name = name;
		this.filename = filename;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}
