package com.spi.activate.impl;

import com.alibaba.dubbo.common.extension.Activate;
import com.spi.activate.ActivateInterface;

@Activate(group = "consumer")
public class ActivateImpl1 implements ActivateInterface {

	@Override
	public String getResult(String param) {

		return param + "Impl1----group = consumer";
	}

}
