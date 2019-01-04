package com.zookeeper.socketZK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

public class ZKStatusUtils {

	public static ConcurrentHashMap<String, ZKStatus> getzkStatus(
			ConcurrentHashMap<String, ZKStatus> map, String ipport) {

		String[] split = ipport.split(":");

		Map<String, String> result = getZKStatusByMNTR(split[0],
				Integer.valueOf(split[1]), 5000);

		ZKStatus status = new ZKStatus();
		String version = result.get("zk_version");
		String realVersion = version.split("-")[0];
		status.setZkVersion(realVersion);
		status.setZkMaxLatency(result.get("zk_max_latency"));
		status.setZkMinLatency(result.get("zk_min_latency"));
		status.setZkAvgLatency(result.get("zk_avg_latency"));
		status.setZkNodeCount(result.get("zk_znode_count"));
		String role = result.get("zk_server_state");
		status.setZkServerState(role);
		status.setZkWatchCount(result.get("zk_watch_count"));
		status.setZkNumAliveConnections(result.get("zk_num_alive_connections"));
		if ("leader".equals(role)) {

			status.setZkFollowers(result.get("zk_followers"));
		}

		map.put(ipport, status);
		return map;
	}

/**
 * 通过zookeeper四字命令mntr来获取zookeeper状态
 * @param ip	zookeeper的ip
 * @param port	zookeeper的port
 * @param conTimeOut	连接zookeeper超时时间，单位ms
 * @return
 */
	public static Map<String, String> getZKStatusByMNTR(String ip, int port,
			int conTimeOut) {
		// 因为zookeeper的原因，每次只能执行一次命令。没有办法重用socket，即使是同一个ip和端口。
		Socket socket = null;

		try {
			socket = new Socket(ip, port);
			socket.setSoTimeout(conTimeOut);
		} catch (IOException e) {
			// TODO 添加失败的处理
			e.printStackTrace();
		}

		OutputStream outputStream = null;
		PrintWriter printWriter = null;
		InputStreamReader streamReader = null;
		BufferedReader bufferedReader = null;
		Map<String, String> resulMap = null;
		try {
			// 写入四字命令

			outputStream = socket.getOutputStream();
			printWriter = new PrintWriter(outputStream);
			printWriter.write("mntr");
			printWriter.flush();
			socket.shutdownOutput();
			// 获取返回的信息
			streamReader = new InputStreamReader(socket.getInputStream());
			bufferedReader = new BufferedReader(streamReader);
			resulMap = new HashMap<String, String>();
			String info = "";
			while ((info = bufferedReader.readLine()) != null) {
				String[] split = info.split("	");
				resulMap.put(split[0], split[1]);
			}

		} catch (IOException e) {
			// TODO 获取状态异常的处理
			e.printStackTrace();
		} finally {
			printWriter.close();
			try {
				outputStream.close();
				bufferedReader.close();
				streamReader.close();
				socket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return resulMap;
	}

	public static LinkedList<String> getZKStatus(String ip, int port,
			String command) {
		LinkedList<String> answerList = new LinkedList<>();
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;
		Socket socket = null;
		try {
			socket = new Socket(ip, port);
			socket.setSoTimeout(1000 * 5);
			printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.write(command);
			printWriter.flush();
			socket.shutdownOutput();

			bufferedReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String info = null;
			while ((info = bufferedReader.readLine()) != null) {
				answerList.add(info);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return answerList;
	}

	public static void main(String[] args) {
		ZKStatusUtils zkUtils = new ZKStatusUtils();
		Map<String, String> zkStatusByMNTR = zkUtils.getZKStatusByMNTR(
				"172.21.11.64", 2180, 5000);
		Set<Entry<String, String>> entrySet = zkStatusByMNTR.entrySet();
		for (Entry<String, String> entry : entrySet) {
			System.out.println(entry.getKey() + "===" + entry.getValue());
		}
		// Socket zkSocket = zkUtils.getZKSocket("172.21.11.64", 2180, 5000);

		// String[] aa= {"mntr","wchs"};
		// String[] aa= {"wchs"};
		// String[] aa= {"cons"};
		// String[] aa= {"srvr"};
		// String[] aa = { "mntr" };
		// String[] aa= {"wchc"};
		// String[] aa = { "wchp" };

		/*
		 * Map<String, String> zkStatus = zkUtils.getZKStatus("stat", zkSocket);
		 * System.out.println("--------------------------");
		 * zkUtils.getZKStatus("cons", zkSocket);
		 * System.out.println("--------------------------");
		 * zkUtils.getZKStatus("envi", zkSocket);
		 * System.out.println("--------------------------");
		 * zkUtils.getZKStatus("wchs", zkSocket);
		 * System.out.println("--------------------------");
		 * zkUtils.getZKStatus("mntr", zkSocket);
		 */
	}
}
