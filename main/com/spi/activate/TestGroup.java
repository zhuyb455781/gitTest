package com.spi.activate;

import java.util.List;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class TestGroup {
	/*
	 * @Activate 有三个配置 group：筛选条件 value：筛选条件 order：排序
	 * 首先按group筛选，如果配置了value，再按value筛选。 筛选结束后，筛选的结果按配置的order排序，小的排前面
	 */

	public static void main(String[] args) {

		TestGroup test = new TestGroup();

		test.defaultGroup();
		// @Activate(group = "consumer")
		test.group("consumer");
		// @Activate(group = "consumer2", value = "value_xx")
		test.groupValue("consumer2", "value_xx");

		// @Activate(group = "provider", order = 1)
		// @Activate(group = "provider", order = 3)
		test.groupOrder("provider");
	}

	/*
	 * 当group为default_grop时
	 */
	public void defaultGroup() {
		ExtensionLoader<ActivateInterface> loader = ExtensionLoader
				.getExtensionLoader(ActivateInterface.class);
		URL url = URL.valueOf("test://localhost/test");
		List<ActivateInterface> list = loader.getActivateExtension(url,
				new String[] {}, "default_group");

		printActivateExtensionInfo(list);

	}

	/*
	 * 通过group来定位
	 * 
	 * @Activate(group = "consumer")
	 */
	public void group(String group) {
		ExtensionLoader<ActivateInterface> loader = ExtensionLoader
				.getExtensionLoader(ActivateInterface.class);
		URL url = URL.valueOf("test://localhost/test");
		List<ActivateInterface> list = loader.getActivateExtension(url,
				new String[] {}, group);

		printActivateExtensionInfo(list);

	}

	public void groupValue(String group, String value) {
		ExtensionLoader<ActivateInterface> loader = ExtensionLoader
				.getExtensionLoader(ActivateInterface.class);
		URL url = URL.valueOf("test://localhost/test");
		url = url.addParameter(value, group);
		List<ActivateInterface> list = loader.getActivateExtension(url,
				new String[] {}, group);

		printActivateExtensionInfo(list);

	}

	public void groupOrder(String group) {
		ExtensionLoader<ActivateInterface> loader = ExtensionLoader
				.getExtensionLoader(ActivateInterface.class);
		URL url = URL.valueOf("test://localhost/test");
		// url = url.addParameter(value, group);
		List<ActivateInterface> list = loader.getActivateExtension(url,
				new String[] {}, group);

		printActivateExtensionInfo(list);

	}

	public void printActivateExtensionInfo(List<ActivateInterface> list) {

		if (list != null) {
			int num = list.size();
			System.out.println("获取实例个数为：" + num);
			for (int i = 0; i < num; i++) {
				ActivateInterface activateInterface = list.get(i);
				System.out.println(activateInterface.getResult("test"));
			}

		}
	}
}
