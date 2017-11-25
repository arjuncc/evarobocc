package com.cc.quote.common.util;

import java.util.Random;

public class RandomNumberGenerator {
	public static int getInt(int maxVal) {
		Random rand = new Random();
		return rand.nextInt(maxVal);
	}
}
