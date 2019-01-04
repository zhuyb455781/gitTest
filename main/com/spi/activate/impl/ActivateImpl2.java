package com.spi.activate.impl;

import com.alibaba.dubbo.common.extension.Activate;
import com.spi.activate.ActivateInterface;
@Activate(group="provider",order = 3)
public class ActivateImpl2 implements ActivateInterface{

	@Override
	public String getResult(String param) {
		
		return param+"impl2---group=provider，value= 3";
	}

}
