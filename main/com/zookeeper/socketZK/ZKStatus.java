package com.zookeeper.socketZK;

public class ZKStatus {


//	zk_version	3.4.6-1569965, built on 02/20/2014 09:09 GMT
//	zk_avg_latency	65  //延迟时间
//	zk_max_latency	10737
//	zk_min_latency	0
//	zk_packets_received	12725
//	zk_packets_sent	12725
//	zk_num_alive_connections	3
//	zk_outstanding_requests	0
//	zk_server_state	leader
//	zk_znode_count	2074
//	zk_watch_count	250
//	zk_ephemerals_count	187
//	zk_approximate_data_size	242024
//	zk_open_file_descriptor_count	36
//	zk_max_file_descriptor_count	65535
//	zk_followers	1
//	zk_synced_followers	1
//	zk_pending_syncs	0
	
	private String  zkVersion	;
	private String  zkAvgLatency;	  //延迟时间
	private String  zkMaxLatency;	
	private String  zkMinLatency;	
	private String  zkNumAliveConnections;	//存活连接数
	private String zkOutstandingRequests	;//请求队列数
	private String zkServerState;	//leader 、follower
	private String zkNodeCount;//节点数
	private String zkWatchCount;	//watch 数
	private String zkEphemeralsCount;//瞬时节点数
	private String zkFollowers; //leader 才有，
	private String 	zkSyncedFollowers	;//leader 才有:已同步的 Follower 数
	public String getZkVersion() {
		return zkVersion;
	}
	public void setZkVersion(String zkVersion) {
		this.zkVersion = zkVersion;
	}
	public String getZkAvgLatency() {
		return zkAvgLatency;
	}
	public void setZkAvgLatency(String zkAvgLatency) {
		this.zkAvgLatency = zkAvgLatency;
	}
	public String getZkMaxLatency() {
		return zkMaxLatency;
	}
	public void setZkMaxLatency(String zkMaxLatency) {
		this.zkMaxLatency = zkMaxLatency;
	}
	public String getZkMinLatency() {
		return zkMinLatency;
	}
	public void setZkMinLatency(String zkMinLatency) {
		this.zkMinLatency = zkMinLatency;
	}
	public String getZkNumAliveConnections() {
		return zkNumAliveConnections;
	}
	public void setZkNumAliveConnections(String zkNumAliveConnections) {
		this.zkNumAliveConnections = zkNumAliveConnections;
	}
	public String getZkOutstandingRequests() {
		return zkOutstandingRequests;
	}
	public void setZkOutstandingRequests(String zkOutstandingRequests) {
		this.zkOutstandingRequests = zkOutstandingRequests;
	}
	public String getZkServerState() {
		return zkServerState;
	}
	public void setZkServerState(String zkServerState) {
		this.zkServerState = zkServerState;
	}
	public String getZkNodeCount() {
		return zkNodeCount;
	}
	public void setZkNodeCount(String zkNodeCount) {
		this.zkNodeCount = zkNodeCount;
	}
	public String getZkWatchCount() {
		return zkWatchCount;
	}
	public void setZkWatchCount(String zkWatchCount) {
		this.zkWatchCount = zkWatchCount;
	}
	public String getZkEphemeralsCount() {
		return zkEphemeralsCount;
	}
	public void setZkEphemeralsCount(String zkEphemeralsCount) {
		this.zkEphemeralsCount = zkEphemeralsCount;
	}
	public String getZkFollowers() {
		return zkFollowers;
	}
	public void setZkFollowers(String zkFollowers) {
		this.zkFollowers = zkFollowers;
	}
	public String getZkSyncedFollowers() {
		return zkSyncedFollowers;
	}
	public void setZkSyncedFollowers(String zkSyncedFollowers) {
		this.zkSyncedFollowers = zkSyncedFollowers;
	}
	@Override
	public String toString() {
		return "ZKStatus [zkVersion=" + zkVersion + ", zkAvgLatency="
				+ zkAvgLatency + ", zkMaxLatency=" + zkMaxLatency
				+ ", zkMinLatency=" + zkMinLatency + ", zkNumAliveConnections="
				+ zkNumAliveConnections + ", zkOutstandingRequests="
				+ zkOutstandingRequests + ", zkServerState=" + zkServerState
				+ ", zkNodeCount=" + zkNodeCount + ", zkWatchCount="
				+ zkWatchCount + ", zkEphemeralsCount=" + zkEphemeralsCount
				+ ", zkFollowers=" + zkFollowers + ", zkSyncedFollowers="
				+ zkSyncedFollowers + "]";
	}

	
}
