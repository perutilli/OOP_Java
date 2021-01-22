package mountainhuts;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	
	private class Range {
		
		private int bottom;
		private int top;
		
		public Range(int bottom, int top) {
			this.bottom = bottom;
			this.top = top;
		}
		
		public boolean inRange(int alt) {
			return (alt >= this.bottom && alt <= this.top);
		}
		
		@Override
		public String toString() {
			return this.bottom + "-" + this.top;
		}
	}
	
	private List<Municipality> municipalities = new ArrayList<>();
	private List<MountainHut> mountainHuts = new ArrayList<>();
	private List<Range> ranges = new ArrayList<>();
	
	
	private String name;
	
	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name = name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for (String range: ranges) {
			String[] r = range.split("-");
			this.ranges.add(new Range(Integer.parseInt(r[0]), Integer.parseInt(r[1])));
		}
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		for (Range r: this.ranges) {
			if (r.inRange(altitude)) {
				return r.toString();
			}
		}
		return "0-INF";
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		Municipality newM = new Municipality(name, province, altitude);
		for (Municipality m: this.municipalities) {
			if(m.equals(newM)) {
				return m;
			}
		}
		this.municipalities.add(newM);
		return newM;
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return this.municipalities;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		MountainHut newM = new MountainHut(name, category, bedsNumber, municipality);
		for (MountainHut m: this.mountainHuts) {
			if(m.equals(newM)) {
				return m;
			}
		}
		this.mountainHuts.add(newM);
		return newM;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		MountainHut newM = new MountainHut(name, category, bedsNumber, municipality, altitude);
		for (MountainHut m: this.mountainHuts) {
			if(m.equals(newM)) {
				return m;
			}
		}
		this.mountainHuts.add(newM);
		return newM;
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return this.mountainHuts;
	}

	/**
	 * Factory methods that creates a new region by loadomg its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		String[] tok;
		List<String> lines = readData(file);
		
		if (lines == null) return null;
		
		Region reg = new Region(name);
		for (String line: lines.subList(1, lines.size())) {
			tok = line.split(";");
			Municipality m = reg.createOrGetMunicipality(tok[1], tok[0], 
					Integer.parseInt(tok[2]));
			try {
				reg.createOrGetMountainHut(tok[3], Integer.parseInt(tok[4]), tok[5], 
						Integer.parseInt(tok[6]), m);
			} catch(NumberFormatException n) {
				reg.createOrGetMountainHut(tok[3], tok[5], Integer.parseInt(tok[6]), m);
			}
		}
		
		return reg;
	}

	/**
	 * Internal class that can be used to read the lines of
	 * a text file into a list of strings.
	 * 
	 * When reading a CSV file remember that the first line
	 * contains the headers, while the real data is contained
	 * in the following lines.
	 * 
	 * @param file the file name
	 * @return a list containing the lines of the file
	 */
	private static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		Map<String, Long> m = this.municipalities.stream().collect(
				Collectors.groupingBy(Municipality::getProvince, Collectors.counting()));
		return m;
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		Map<String, Map<String, Long>> map =
			this.mountainHuts.stream().collect(
					Collectors.groupingBy((MountainHut m) -> m.getMunicipality().getProvince(), 
							Collectors.groupingBy(a -> a.getMunicipality().getName(),
									Collectors.counting())));
		return map;
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		Map<String, Long> map = new HashMap<>();
		for(Range r: this.ranges) {
			long value = this.mountainHuts.stream().filter(m -> {
				Optional<Integer> alt = m.getAltitude();
				if (alt.isPresent()) {
					return r.inRange(alt.get());
				}
				return r.inRange(m.getMunicipality().getAltitude());
			}).collect(Collectors.counting());
			map.put(r.toString(), value);
		}
		return map;
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		Map<String, Integer> map = 
			this.mountainHuts.stream().collect(Collectors.groupingBy(
					m -> m.getMunicipality().getProvince(),
					Collectors.summingInt(MountainHut::getBedsNumber)));
		return map;
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		Map<String, Optional<Integer>> map = new HashMap<>();
		for(Range r: this.ranges) {
			Optional<Integer> b = Optional.empty();
			OptionalInt beds =
				this.mountainHuts.stream().filter(m -> {
					Optional<Integer> alt = m.getAltitude();
					if (alt.isPresent()) {
						return r.inRange(alt.get());
					}
					return r.inRange(m.getMunicipality().getAltitude());
				}).mapToInt(MountainHut::getBedsNumber).max();
			if (beds.isPresent()) {
				b = Optional.of(beds.getAsInt());
			}
			map.put(r.toString(), b);
		}
		return map;
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		for (Municipality m: this.municipalities) {
			if (m.getNMountHuts() < 0) { 
				long n = this.mountainHuts.stream().filter(a -> a.getMunicipality().equals(m)).count();
				m.setNMountHuts(n);
			}
		}
		
		Map<Long, List<String>> map =
			this.municipalities.stream().collect(
					Collectors.groupingBy(Municipality::getNMountHuts,
							Collectors.mapping(Municipality::getName, Collectors.toList())));
				
		return map;
	}

}
