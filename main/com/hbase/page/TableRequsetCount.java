package com.hbase.page;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Selectable;

public class TableRequsetCount {
	// private static Logger logger =
	// LoggerFactory.getLogger(TableRequsetCount.class);

	private static HttpClientDownloader httpClientDownloader = new HttpClientDownloader();

	public static void main(String[] args) throws InterruptedException {

		// String master = "http://172.21.11.73:14002";

		//String conf = "D:\\home\\conf.txt";
		//String hosts = "D:\\home\\host.txt";

		 String conf = args[0];
		 String hosts = args[1];

		Map<String, String> params = ReadCnfFile.read(conf);

		String file = params.get("result");
		String master = params.get("url");

		final String name = params.get("name");
		final String pass = params.get("password");
		// logger.info("输入参数为： file--" + file + ",name--"+ name + ",passwd--" +
		// pass + ", url--" + master);
		System.out.println("输入参数为： file--" + file + ",name--" + name
				+ ",passwd--" + pass + ", url--" + master);
		MonitorUtil.init(hosts);
		master = MonitorUtil.formatURL(master);
		// 获取所有的region
		List<String> regionServerList = getRegoinServerList(master
				+ "/master-status", name, pass);

		// logger.info("regionServerList"+regionServerList.toString());
		ExecutorService executorService = Executors.newFixedThreadPool(20);

		final CountDownLatch latch = new CountDownLatch(regionServerList.size());
		final Map<String, TableAndRW> map = new ConcurrentHashMap<>();
		// 循环所有的server
		for (String url : regionServerList) {

			// http://e3base2:14004/rs-status
			final String new_url;
			if (ReadCnfFile.iscontain(url, "rs-status")) {

				new_url = transformURL(url) + "#regionRequestStats";
			} else {
				new_url = transformURL(url) + "rs-status#regionRequestStats";
			}

			executorService.execute(new Runnable() {
				@Override
				public void run() {
					getTableRequest(new_url, map, name, pass);
					latch.countDown();
				}
			});
		}

		executorService.shutdown();
		latch.await();

		Set<Entry<String, TableAndRW>> set = map.entrySet();
		ExcelUtils.WriteWb2File(set, file);
		for (Entry<String, TableAndRW> entry : set) {
			System.out.println(entry.getValue());
		}

		try {
			System.in.read();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// CY_DATA_003,684,1542223785691.30567c56c18422250adcdcef45d8100d.
	// 852741 495097
	public static void getTableRequest(String url, Map<String, TableAndRW> map,
			String name, String pass) {
		long write = 0L;
		long read = 0L;

		System.out.println("url------------>" + url);

		Page RegionServerPage = null;
		if (name == null) {
			RegionServerPage = httpClientDownloader.download(new Request(url),
					Site.me().setCharset(null).toTask());
		} else {

			RegionServerPage = httpClientDownloader.download(new Request(url),
					Site.me().setUsernamePassword(name, pass).setCharset(null)
							.toTask());
		}

		List<String> all = RegionServerPage.getHtml()
				.$("#tab_regionRequestStats").xpath("//tr").all();
		System.out.println("数据条数：" + all.size());
		for (int i = 1; i < all.size(); i++) {
			String cleanHtml = MonitorUtil.cleanHtml(all.get(i));
			String[] new_line = cleanHtml.split(" +");
			String tableName = new_line[0].split(",")[0];
			read = Long.parseLong(new_line[1]);
			write = Long.parseLong(new_line[2]);
			if (map.containsKey(tableName)) {
				TableAndRW b = map.get(tableName);
				b.readcount += read;
				b.writecount += write;
			} else {
				TableAndRW b = new TableAndRW(tableName, read, write);
				map.put(tableName, b);
			}

		}

	}

	/*
	 * 从tab_baseStats中获取所有 的server
	 */

	private static List<String> getRegoinServerList(String hm, String name,
			String pass) {
		Page HbasePage = null;
		if (name == null) {
			HbasePage = httpClientDownloader.download(new Request(hm), Site
					.me().setCharset(null).toTask());
		} else {

			HbasePage = httpClientDownloader.download(new Request(hm), Site
					.me().setUsernamePassword(name, pass).setCharset(null)
					.toTask());
		}

		Selectable links = HbasePage.getHtml().$("#tab_baseStats").links();
		List<String> list = links.all();
		for (String s : list) {
			System.out.println(s);
		}
		return list;
	}

	public static String transformURL(String url) {
		System.out.println("url is : " + url);
		if (url.split("\\.").length == 4) {
			return url;
		}
		int start = url.indexOf("//") + 2;
		int end = url.indexOf(":", start);
		System.out.println(start + " ------------ " + end);
		String key = url.substring(start, end);
		String ip = MonitorUtil.getIP(key);
		System.out.println(key + "------> " + ip);
		String ip_url = url.replace(key, ip);
		System.out.println("ip_url:" + ip_url);
		return ip_url;

	}
}

class TableAndRW {
	String name;
	long readcount;
	long writecount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getReadcount() {
		return readcount;
	}

	public void setReadcount(long readcount) {
		this.readcount = readcount;
	}

	public long getWritecount() {
		return writecount;
	}

	public void setWritecount(long writecount) {
		this.writecount = writecount;
	}

	public TableAndRW(String name, long readcount, long writecount) {
		super();
		this.name = name;
		this.readcount = readcount;
		this.writecount = writecount;
	}

	@Override
	public String toString() {

		return name + "\t" + readcount + "\t" + writecount;
	}

}
