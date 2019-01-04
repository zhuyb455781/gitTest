package com.spi.adaptive.impl;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.spi.adaptive.AdaptiveInterface1;

//@Adaptive
public class AdapIml2AdaptiveInterface1 implements AdaptiveInterface1 {

	@Override
	public void print(String param, URL url) {
		System.out.println("AdapIml2-----print---" + param);

	}

	@Override
	public void getparam(String param) {
		System.out.println("AdapIml2-----getparam---" + param);

	}

	@Override
	public void doSome(String param) {
		System.out.println("AdapIml2-----doSome---" + param);

	}

}
