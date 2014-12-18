package com.github.mtzw.fixedlength.format;

import java.io.Serializable;
import java.util.List;

import com.github.mtzw.fixedlength.FixedLengthString;

public class ZenginFormat implements Serializable {

	private static final long serialVersionUID = -6941308477907630081L;

	private FixedLengthString header;
	private List<FixedLengthString> data;
	private FixedLengthString trailer;
	private FixedLengthString end;

	public FixedLengthString getHeader() {
		return header;
	}

	public void setHeader(FixedLengthString header) {
		this.header = header;
	}

	public List<FixedLengthString> getData() {
		return data;
	}

	public void setData(List<FixedLengthString> data) {
		this.data = data;
	}

	public FixedLengthString getTrailer() {
		return trailer;
	}

	public void setTrailer(FixedLengthString trailer) {
		this.trailer = trailer;
	}

	public FixedLengthString getEnd() {
		return end;
	}

	public void setEnd(FixedLengthString end) {
		this.end = end;
	}

}
