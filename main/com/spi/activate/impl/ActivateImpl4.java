package com.spi.activate.impl;

import com.alibaba.dubbo.common.extension.Activate;
import com.spi.activate.ActivateInterface;

@Activate(group = "consumer2", value = "value_xx")
public class ActivateImpl4 implements ActivateInterface {

	@Override
	public String getResult(String param) {
		return param + "Impl4----group=consumer2 ,value = value_xx";
	}

}
