package com.hbase.page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MonitorUtil {
	public static Map<String, String> hostmap = new HashMap<String, String>();

	public static void init(String path) {
		
		hostmap = ReadCnfFile.read(path);
		
/*		try {
			BufferedReader reader = null;
			if ("test".equals(type) || "test" == type) {
				System.out.println("读取hosts_test文件。。。。。。。。。。。");
				reader = new BufferedReader(new InputStreamReader(
						MonitorUtil.class.getClassLoader().getResourceAsStream(
								"hosts_test")));
			} else {
				System.out.println("读取hosts文件。。。。。。。。。。。");
				reader = new BufferedReader(new InputStreamReader(
						MonitorUtil.class.getClassLoader().getResourceAsStream(
								"hosts")));

			}
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty() && !line.startsWith("#")) {
					String[] split = line.split("( |\t)+");
					System.out.println(split[1] + "----------->" + split[0]);
					hostmap.put(split[1], split[0]);
					
					 * if (split[0].startsWith("10")) { hostmap.put(split[1],
					 * split[0]); } else { hostmap.put(split[0], split[1]); }
					 

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}

	public static void main(String[] args) {
		MonitorUtil.init("test");
		System.out.println(hostmap);
		System.out.println(getIP("e3base3"));
	}

	public static List<String> getClusterAll() {
		List<String> lineList = new LinkedList<String>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				MonitorUtil.class.getClassLoader().getResourceAsStream(
						"clusterAll")));
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty() && !line.startsWith("#")) {
					lineList.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lineList;
	}

	public static String getFormatedDate(String date) {
		String newdate = null;
		try {
			Date dd = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
					Locale.ENGLISH).parse(date);
			newdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newdate;
	}

	public static boolean floatEqual(Float f1, Float f2) {
		return Math.abs(f1 - f2) < 1;
	}

	public static String getIP(String key) {
		return hostmap.get(key);
	}

	public static String cleanHtml(String html) {
		Document doc = Jsoup.parse(html);
		String text = doc.text();
		return text;
	}

	public static void p(List<String> l) {
		for (int i = 0; i < l.size(); i++) {
			p(i + "");
			p(l.get(i));
		}
	}

	public static void p(String s) {
		System.out.println(s);
	}

	public static String formatURL(String nn) {
		if (nn.startsWith("http://")) {
			return nn;
		} else {
			return "http://" + nn;
		}
	}

	public static void formatPrint(String key, Object value, boolean size) {
		if (size) {
			System.out.format("%s ------> %s\n", key,
					MonitorUtil.size(value.toString()));
		} else {
			System.out.format("%s ------> %s\n", key, value);
		}
	}

	public static void formatPrint(String key, Object value) {
		formatPrint(key, value, false);

	}

	public static List<String> get4words() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				MonitorUtil.class.getClassLoader().getResourceAsStream(
						"fourwords")));
		List<String> list = new ArrayList<String>();
		String line = "";
		while ((line = reader.readLine()) != null) {
			if (line.length() == 4) {
				list.add(line);
			}
			System.out.println(line);

		}
		return list;
	}

	public static String max(List<String> list) {
		String value = "";
		int max = 0;
		for (String x : list) {
			int tmp = Integer.parseInt(x.replace("m", ""));
			if (max < tmp) {
				max = tmp;
				value = x;
			}
		}
		return value;
	}

	public static String min(List<String> list) {
		String value = "";
		int min = 1000000;
		for (String x : list) {
			int tmp = Integer.parseInt(x.replace("m", ""));
			if (min > tmp) {
				min = tmp;
				value = x;
			}
		}
		return value;
	}

	public static String avg(List<String> list) {
		boolean hasSubFix = false;
		if (list.get(0).contains("m")) {
			hasSubFix = true;
		}
		int sum = 0;
		for (String x : list) {
			sum += Integer.parseInt(x.replace("m", ""));
		}
		return sum / list.size() + (hasSubFix ? "m" : "");
	}

	public static String size(Float size) {
		return size(size + "");
	}

	public static String size(String size) {
		float f = Float.parseFloat(size);
		float k = 1024;
		float m = k * 1024;
		float g = m * 1024;
		float t = g * 1024;
		float p = t * 1024;

		DecimalFormat d = new DecimalFormat("0.00");

		if (f < k) {
			return (f + "B");
		} else {
			if (f < m) {
				return (d.format(f / k) + "KB");
			} else {
				if (f < g) {
					return (d.format(f / m) + "MB");
				} else {
					if (f < t) {
						return (d.format(f / g) + "GB");
					} else {
						if (f < p) {
							return (d.format(f / t) + "TB");
						} else {
							return (d.format(f / p) + "PB");
						}
					}
				}
			}
		}

	}

}
