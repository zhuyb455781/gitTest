package com.spi.activate.impl;

import com.alibaba.dubbo.common.extension.Activate;
import com.spi.activate.ActivateInterface;

@Activate(group = "default_group")
public class ActivateImpl5 implements ActivateInterface {

	@Override
	public String getResult(String param) {
		return param + "Impl5----group=default_group";
	}

}
