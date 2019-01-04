package com.zookeeper.monitor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.zookeeper.server.DataTreeMXBean;
import org.apache.zookeeper.server.ZooKeeperServerMXBean;

public class ZKMonitor {
	static JMXConnector connector;

	/**
	 * 获取zk的当前运行状态数据，如果不使用name和pwd登录，这两个参数传入 null
	 * 
	 * 
	 * @param ip
	 *            zk ip
	 * @param port
	 *            zk jmx port
	 * @param name
	 * @param pwd
	 * @return  Map<String, String>
	 * 如果 连接zk jmx 失败，返回 null
	 * @throws MalformedObjectNameException
	 * @throws IOException
	 */
	public Map<String, String> Monitor(String ip, String port, String name,
			String pwd) throws MalformedObjectNameException, IOException {
		MBeanServerConnection mbsc;
		// TODO 加上ip和port的不为空 并且服务响应格式的 校验
		if (name == null && pwd == null) {
			mbsc = createMBeanServer("192.168.153.130", "2179");
			if (mbsc == null) {
				return null;
			}
		} else {

			mbsc = createMBeanServer("192.168.153.130", "2179", "controlRole",
					"zookeeper");
			if (mbsc == null) {
				return null;
			}
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		ObjectName replica = new ObjectName(
				"org.apache.ZooKeeperService:name0=ReplicatedServer_id2,name1=replica.2");
		System.out.println("replica.1运行状态:"
				+ getAttribute(mbsc, replica, "State"));// 运行状态

		ObjectName dataTreePattern = new ObjectName(
				"org.apache.ZooKeeperService:name0=ReplicatedServer_id?,name1=replica.?,name2=*,name3=InMemoryDataTree");
		Set<ObjectName> dataTreeSets = mbsc.queryNames(dataTreePattern, null);
		Iterator<ObjectName> dataTreeIterator = dataTreeSets.iterator();
		while (dataTreeIterator.hasNext()) {

			ObjectName dataTreeObjectName = dataTreeIterator.next();
			DataTreeMXBean dataTree = JMX.newMBeanProxy(mbsc,
					dataTreeObjectName, DataTreeMXBean.class);
			resultMap.put("NodeCount", dataTree.getNodeCount() + "");
			System.out.println("节点总数:" + dataTree.getNodeCount());// 节点总数
			resultMap.put("WatchCount", dataTree.getWatchCount() + "");
			System.out.println("Watch总数:" + dataTree.getWatchCount());// Watch总数
			System.out.println("临时节点总数:" + dataTree.countEphemerals());// 瞬时节点数
			System.out.println("节点名及字符总数:" + dataTree.approximateDataSize());// 节点全路径和值的总字符数

			Map<String, String> dataTreeMap = dataTreeObjectName
					.getKeyPropertyList();
			String replicaId = dataTreeMap.get("name1").replace("replica.", "");
			String role = dataTreeMap.get("name2");// Follower,Leader,Observer,Standalone
			resultMap.put("role", role);
			String canonicalName = dataTreeObjectName.getCanonicalName();
			int roleEndIndex = canonicalName.indexOf(",name3");

			ObjectName roleObjectName = new ObjectName(canonicalName.substring(
					0, roleEndIndex));
			System.out.println("==============zk服务状态===========");
			ZooKeeperServerMXBean ZooKeeperServer = JMX.newMBeanProxy(mbsc,
					roleObjectName, ZooKeeperServerMXBean.class);
			resultMap.put("IpPort", ZooKeeperServer.getClientPort());
			System.out.println(role + " 的IP和端口:"
					+ ZooKeeperServer.getClientPort());// IP和端口
			resultMap.put("AliveConnections",
					ZooKeeperServer.getNumAliveConnections() + "");
			System.out.println(role + " 活着的连接数:"
					+ ZooKeeperServer.getNumAliveConnections());// 连接数
			System.out.println(role + " 未完成请求数:"
					+ ZooKeeperServer.getOutstandingRequests());// 未完成的请求数
			System.out.println(role + " 接收的包:"
					+ ZooKeeperServer.getPacketsReceived());// 收到的包
			System.out.println(role + " 发送的包:"
					+ ZooKeeperServer.getPacketsSent());// 发送的包
			System.out.println(role + " 平均延迟（毫秒）:"
					+ ZooKeeperServer.getAvgRequestLatency());
			System.out.println(role + " 最大延迟（毫秒）:"
					+ ZooKeeperServer.getMaxRequestLatency());
			resultMap.put("MaxClientCn",
					ZooKeeperServer.getMaxClientCnxnsPerHost() + "");
			System.out.println(role + " 每个客户端IP允许的最大连接数:"
					+ ZooKeeperServer.getMaxClientCnxnsPerHost());
			System.out.println(role + " 最大Session超时（毫秒）:"
					+ ZooKeeperServer.getMaxSessionTimeout());
			System.out.println(role + " 心跳时间（毫秒）:"
					+ ZooKeeperServer.getTickTime());
			resultMap.put("version", ZooKeeperServer.getVersion());
			System.out.println(role + " 版本:" + ZooKeeperServer.getVersion());// 版本
			resultMap.put("StartTime", ZooKeeperServer.getStartTime());
			System.out
					.println(role + " 开机时间:" + ZooKeeperServer.getStartTime());// 版本

		}
		// close connection
		if (connector != null) {
			connector.close();
		}
		return resultMap;
	}

	/**
	 * 建立连接
	 *
	 * @param ip
	 * @param jmxport
	 * @return
	 */
	public static MBeanServerConnection createMBeanServer(String ip,
			String jmxport, String userName, String password) {
		try {
			String jmxURL = "service:jmx:rmi:///jndi/rmi://" + ip + ":"
					+ jmxport + "/jmxrmi";
			// jmxurl
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);

			Map<String, String[]> map = new HashMap<String, String[]>();
			String[] credentials = new String[] { userName, password };
			map.put("jmx.remote.credentials", credentials);
			connector = JMXConnectorFactory.connect(serviceURL, map);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			return mbsc;

		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.err.println(ip + ":" + jmxport + " 连接建立失败");
		}
		return null;
	}

	public static MBeanServerConnection createMBeanServer(String ip,
			String jmxport) {
		String jmxURL = "service:jmx:rmi:///jndi/rmi://" + ip + ":" + jmxport
				+ "/jmxrmi";
		// jmxurl
		try {
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
			connector = JMXConnectorFactory.connect(serviceURL);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			return mbsc;
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(ip + ":" + jmxport + " 连接建立失败");
			e.printStackTrace();
		}

		return null;
	}

	private static String getAttribute(MBeanServerConnection mbeanServer,
			ObjectName objName, String objAttr) {
		if (mbeanServer == null || objName == null || objAttr == null)
			throw new IllegalArgumentException();
		try {
			return String.valueOf(mbeanServer.getAttribute(objName, objAttr));
		} catch (Exception e) {
			return null;
		}
	}
}
