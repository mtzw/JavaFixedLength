package com.github.mtzw.fixedlength;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public class FixedLengthString implements Serializable {
	private static final long serialVersionUID = 5777134278442089041L;

	ImmutableMap<Integer, FixedLengthStringPart> parts;

	public FixedLengthString(String source, Integer... partLengths) {
		validate(source, partLengths);
		splitParts(source, partLengths);
	}

	void validate(String source, Integer... partLengths) {
		if (source == null) {
			throw new NullPointerException("source");
		} else if (Strings.isNullOrEmpty(source)) {
			throw new IllegalArgumentException("source");
		}
		if (source.length() != Arrays.asList(partLengths).stream()
				.collect(Collectors.summingInt(i -> i)).intValue()) {
			throw new IllegalArgumentException("UnMatch. (source and sum of partLengths)");
		}
	}

	void splitParts(String source, Integer... partLengths) {
		FixedLengthStringParser parser = new FixedLengthStringParser(source);
		Builder<Integer, FixedLengthStringPart> builder = ImmutableMap
				.<Integer, FixedLengthStringPart> builder();
		Integer seq = 1;
		for (Integer partLength : partLengths) {
			FixedLengthStringPart part = new FixedLengthStringPart(seq++,
					parser.next(partLength));
			builder.put(part.getSeq(), part);
		}
		this.parts = builder.build();
	}

	public String getTextPartOf(int numOfPart) {
		return this.parts.get(numOfPart).getText();
	}

	/*internal*/ static class FixedLengthStringPart implements Serializable,
			Comparable<FixedLengthStringPart> {
		private static final long serialVersionUID = -1679511988027799766L;

		private Integer seq;
		private String text;

		public FixedLengthStringPart(Integer seq, String text) {
			this.seq = seq;
			this.text = text;
		}

		public Integer getTextLength() {
			return this.text.length();
		}

		public Integer getSeq() {
			return seq;
		}

		public void setSeq(Integer seq) {
			this.seq = seq;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		@Override
		public int compareTo(FixedLengthStringPart o) {
			return this.seq.compareTo(o.seq);
		}

	}
}
