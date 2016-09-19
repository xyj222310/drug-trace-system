package myclass;

public class TransPort {
	public int transId;
	public String tagId;
	public String startTime;
	public String endTime;
	public String transportStatus;	
	public String transportName;
	
	
	public int getTransId() {
		return transId;
	}
	public void setTransId(int transId) {
		this.transId = transId;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTransportName() {
		return transportName;
	}
	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}
	
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getTransportStatus() {
		return transportStatus;
	}
	public void setTransportStatus(String transportStatus) {
		this.transportStatus = transportStatus;
	}
	public String getTransportResult()
	{
		String result="TagID:"+this.tagId+"\n"+"运输商:"+this.transportName+"\n"
				+"运输开始:"+this.startTime+"\n"+"运输结束:"+this.endTime+"\n"+
				"运输状态:"+this.transportStatus+"\n";
		return result;
	}
	
}
