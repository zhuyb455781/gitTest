package com.spi.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

@SPI("impl1")
// @SPI
public interface AdaptiveInterface1 {
	@Adaptive
	public void print(String param, URL url);

	public void getparam(String param);

	public void doSome(String param);
}
