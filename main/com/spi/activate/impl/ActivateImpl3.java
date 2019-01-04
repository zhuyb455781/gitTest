package com.spi.activate.impl;

import com.alibaba.dubbo.common.extension.Activate;
import com.spi.activate.ActivateInterface;

@Activate(group = "provider", order = 1)
public class ActivateImpl3 implements ActivateInterface {

	@Override
	public String getResult(String param) {
		return param + "impl3----group = provider,order = 1";
	}

}
