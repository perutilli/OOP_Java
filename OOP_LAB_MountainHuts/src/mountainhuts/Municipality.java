package mountainhuts;

/**
 * Represents a municipality
 *
 */
public class Municipality {
	
	private String name;
	private String province;
	private int altitude;
	private long nMountHuts = -1;
	
	public Municipality(String name, String province, int altitude) {
		this.name = name;
		this.province = province;
		this.altitude = altitude;
	}

	/**
	 * Name of the municipality.
	 * 
	 * Within a region the name of a municipality is unique
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Province of the municipality
	 * 
	 * @return province
	 */
	public String getProvince() {
		return this.province;
	}

	/**
	 * Altitude of the municipality
	 * 
	 * @return altitude
	 */
	public Integer getAltitude() {
		return this.altitude;
	}

	@Override
	public boolean equals(Object o) {
		Municipality other = (Municipality) o;
		return this.name.equals(other.name);
	}
	
	public long getNMountHuts() {
		return this.nMountHuts;
	}
	
	public void setNMountHuts(long n) {
		this.nMountHuts = n;
	}

}
