package com.spi.adaptive.impl;

import com.alibaba.dubbo.common.URL;
import com.spi.adaptive.AdaptiveInterface1;

public class AdapImpl1 implements AdaptiveInterface1 {

	@Override
	public void print(String param,URL url) {
		System.out.println("AdapImpl1-----print---" + param);

	}

	@Override
	public void getparam(String param) {
		System.out.println("AdapImpl1-----getparam" + param);

	}

	@Override
	public void doSome(String param) {
		System.out.println("AdapImpl1-----doSome" + param);

	}

}
