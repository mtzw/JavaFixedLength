package com.github.mtzw.fixedlength;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.github.mtzw.fixedlength.FixedLengthString.FixedLengthStringPart;

public class FixedLengthStringTest {

	@Test
	public void testFixedLengthString() {
		String source = "19109999999999ﾃｽﾄ                                     04010001ﾐｽﾞﾎ           353ｼﾝｼﾞｭｸﾆｼｸﾞﾁ    11234567                 ";
		Integer[] partLengths = new Integer[] { 1, 2, 1, 10, 40, 4, 4, 15, 3, 15, 1, 7, 17 };
		FixedLengthString actual = new FixedLengthString(source, partLengths);

		int matcherSeq = 1;
		FixedLengthStringParser matcherParser = new FixedLengthStringParser(source);
		for (FixedLengthStringPart part : actual.parts.values()) {
			Integer matcherTextLength = partLengths[matcherSeq - 1];
			String matcherText = matcherParser.next(matcherTextLength);

			assertThat(part.getSeq(), is(matcherSeq++));
			assertThat(part.getText(), is(matcherText));
			assertThat(part.getTextLength(), is(matcherTextLength));
		}
	}

	@Test(expected = NullPointerException.class)
	public void testFixedLengthString_SourceTextIsNull() {
		new FixedLengthString(null, 1, 2, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFixedLengthString_SourceTextIsEmpty() {
		new FixedLengthString("", 1, 2, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFixedLengthString_UnMatchSourceTextLengthAndSumOfPartLengths() {
		new FixedLengthString("12345", 1, 2, 3);
	}

	@Test
	public void testGetTextPartOf() {
		String source = "19109999999999ﾃｽﾄ                                     04010001ﾐｽﾞﾎ           353ｼﾝｼﾞｭｸﾆｼｸﾞﾁ    11234567                 ";
		Integer[] partLengths = new Integer[] { 1, 2, 1, 10, 40, 4, 4, 15, 3, 15, 1, 7, 17 };
		FixedLengthString actual = new FixedLengthString(source, partLengths);

		assertThat(actual.getTextPartOf(1), is("1"));
		assertThat(actual.getTextPartOf(2), is("91"));
		assertThat(actual.getTextPartOf(3), is("0"));
		assertThat(actual.getTextPartOf(4), is("9999999999"));
		assertThat(actual.getTextPartOf(5), is("ﾃｽﾄ                                     "));
		assertThat(actual.getTextPartOf(6), is("0401"));
		assertThat(actual.getTextPartOf(7), is("0001"));
		assertThat(actual.getTextPartOf(8), is("ﾐｽﾞﾎ           "));
		assertThat(actual.getTextPartOf(9), is("353"));
		assertThat(actual.getTextPartOf(10), is("ｼﾝｼﾞｭｸﾆｼｸﾞﾁ    "));
		assertThat(actual.getTextPartOf(11), is("1"));
		assertThat(actual.getTextPartOf(12), is("1234567"));
		assertThat(actual.getTextPartOf(13), is("                 "));
	}

	@Test
	public void testGetTextsAsList() {
		String source = "AAAAABBCCCCDDDDD  DDDDD";
		Integer[] partLengths = new Integer[] { 5, 2, 4, 12 };
		FixedLengthString actual = new FixedLengthString(source, partLengths);

		List<String> texts = actual.getTextsAsList();
		for (int i = 0; i < texts.size(); i++) {
			String text = texts.get(i);
			switch (i) {
			case 0:
				assertThat(text, is("AAAAA"));
				break;
			case 1:
				assertThat(text, is("BB"));
				break;
			case 2:
				assertThat(text, is("CCCC"));
				break;
			case 3:
				assertThat(text, is("DDDDD  DDDDD"));
				break;
			default:
				break;
			}
		}
	}

	@Test
	public void testGetTextsAsString() {
		String source = "あめんぼあかいなあいうえお";
		Integer[] partLengths = new Integer[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1 };
		FixedLengthString actual = new FixedLengthString(source, partLengths);

		assertThat(actual.getTextPartOf(5) + actual.getTextPartOf(6) + actual.getTextPartOf(7), is("あかい"));
		assertThat(actual.getTextsAsList().size(), is(13));
		assertThat(actual.getTextsAsString(), is("あめんぼあかいなあいうえお"));
	}

}
