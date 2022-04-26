package it.sogei.libro_firma.data.entity;

import java.io.Serializable;

public class CountAppAggregation implements Serializable {

	private static final long serialVersionUID = 1L;

	private String _id;
	private long cnt;
	
	public CountAppAggregation() {
		super();
	}

	public long getCnt() {
		return cnt;
	}

	public void setCnt(long cnt) {
		this.cnt = cnt;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
	
}
