package com.github.mtzw.fixedlength;

/*internal*/ class FixedLengthStringParser {

	private final String source;
	private int position = 0;

	public FixedLengthStringParser(String source) {
		this.source = source;
	}

	public String next(int length) {
		if (source == null) {
			return null;
		}
		if (length < 1) {
			return null;
		}
		if (position >= source.length()) {
			return null;
		}
		String ret = null;
		if (position + length > source.length()) {
			ret = source.substring(position);
			position += length;
			return ret;
		}
		ret = source.substring(position, position + length);
		position += length;
		return ret;
	}

	public FixedLengthStringParser forward(int length) {
		position += length;
		return this;
	}

}