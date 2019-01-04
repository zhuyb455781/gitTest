package com.zookeeper.socketZK;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZKStatusThread {

	public Map<String, ZKStatus> getAllZKStatus(Map<String, String> ipMap) {

		ExecutorService executorService = Executors.newFixedThreadPool(20);
		final CountDownLatch latch = new CountDownLatch(ipMap.size());
		final ConcurrentHashMap<String, ZKStatus> map = new ConcurrentHashMap<String, ZKStatus>();
		Set<Entry<String, String>> entrySet = ipMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			final String ipport = entry.getKey() + ":" + entry.getValue();
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					ZKStatusUtils.getzkStatus(map, ipport);
					latch.countDown();
				}
			});
		}
		executorService.shutdown();
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;

	}

	public static void main(String[] args) {
		ZKStatusThread thread = new ZKStatusThread();
		Map<String, String> ipMap = new HashMap<String, String>();
		ipMap.put("172.21.11.64", "2180");
		ipMap.put("172.21.11.63", "2180");
		
		Map<String, ZKStatus> allZKStatus = thread.getAllZKStatus(ipMap);
		Set<Entry<String, ZKStatus>> entrySet = allZKStatus.entrySet();
		for (Entry<String, ZKStatus> entry : entrySet) {
			System.out.println(entry.getKey()+":"+entry.getValue().toString());
			
		}
		
		
		
	}
	
	
}
