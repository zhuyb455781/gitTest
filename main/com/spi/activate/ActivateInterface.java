package com.spi.activate;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface ActivateInterface {
	// com.spi.activate.ActivateInterface
	public String getResult(String param);
}
