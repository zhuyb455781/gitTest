package com.spi.adaptive.impl;

import com.alibaba.dubbo.common.URL;
import com.spi.adaptive.AdaptiveInterface1;

public class AdapImpl3AdaptiveInterface1 implements AdaptiveInterface1 {

	@Override
	public void print(String param,URL url) {
		System.out.println("AdapImpl3-----print---" + param);
	}

	@Override
	public void getparam(String param) {
		System.out.println("AdapImpl3-----getparam---" + param);
	}

	@Override
	public void doSome(String param) {
		System.out.println("AdapImpl3-----doSome---" + param);

	}

}
