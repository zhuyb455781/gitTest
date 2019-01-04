package com.spi.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class AdaptiveTest {

	public static void main(String[] args) {
		AdaptiveTest test = new AdaptiveTest();
		// test.valueInSPI();
		test.valueInSPIAndAdpInImpl();
		test.testUrl();

	}

	/*
	 * 接口@SPI上有值:@SPI("impl1"),@Adaptive 无值。实现类上都没有@Adaptive注解
	 */
	public void valueInSPI() {
		ExtensionLoader<AdaptiveInterface1> loader = ExtensionLoader
				.getExtensionLoader(AdaptiveInterface1.class);

		AdaptiveInterface1 adaptiveExtension = loader.getAdaptiveExtension();
		URL url = URL.valueOf("test://localhost/test");
		adaptiveExtension.print("aa", url);
		// adaptiveExtension.getparam("bb");
		// adaptiveExtension.doSome("cc");
	}

	/*
	 * 接口@SPI上有值:@SPI("impl1"),@Adaptive 无值。实现类上都没有@Adaptive注解
	 */
	public void valueInSPIAndAdpInImpl() {
		ExtensionLoader<AdaptiveInterface1> loader = ExtensionLoader

		.getExtensionLoader(AdaptiveInterface1.class);
		//AdaptiveInterface1 extension = loader.getExtension("impl3");

		// extension.print(param, url);
		 AdaptiveInterface1 extension = loader.getAdaptiveExtension();
		URL url = URL
				.valueOf("impl2://localhost/test");
		extension.print("aa", url);

	}

	public void testUrl() {

		URL url = URL
				.valueOf("registry://192.168.113.128:2181/com.sitech.hsf.registry.RegistryService?application=consumerApp&backup=192.168.113.128:2182,192.168.113.128:2183&hsf=hsf_curator&pid=2948&refer=application%3DconsumerApp%26default.flume%3Dfalse%26flume%3Dfalse%26generic%3Dtrue%26hsf%3Dhsf_curator%26interface%3Dacom.sitech.test.hsf.provider.ServiceDemo%26pid%3D2948%26register.ip%3D192.168.113.1%26side%3Dconsumer%26timestamp%3D1534490714485&registry=zookeeper&timestamp=1534490714493");

		String protocol = url.getProtocol();
		System.out.println("protocol:" + protocol);
	}

}
