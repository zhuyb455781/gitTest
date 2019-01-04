package com.hbase.page;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ReadCnfFile {
	public static Map<String, String> hostmap = new HashMap<String, String>();

	public static Map<String, String> read(String path) {
		String filePath = path;

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));

			int line = 0;
			// 一次读入一行，直到读入null为文件结束
			String tempString = "";
			while ((tempString = reader.readLine()) != null) {
				// 显示行号

				if (!tempString.isEmpty() && !tempString.startsWith("#")) {
					String[] split = tempString.split("( |\t)+");
					hostmap.put(split[0], split[1]);

					line++;
				}

			}
			System.out.println("总共行数为：" + line);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hostmap;
	}

	public static void test(String[] args) {
		Map<String, String> read = read("D:\\home\\hosts");

		String string = read.get("cc");
		System.out.println(string);

		Set<Entry<String, String>> entrySet = read.entrySet();
		for (Entry<String, String> entry : entrySet) {
			System.out.println(entry.getKey() + "----" + entry.getValue());

		}
	}

	public static void main(String[] args) {
		String url = "http://e3base2:14004/rs-status";
		boolean a = ReadCnfFile.iscontain(url, "rs-satus");
		System.out.println(a);

	}

	public static boolean iscontain(String url, String contains) {
		String[] split = url.split("/");
		int length = split.length;

		return contains.equals(split[length - 1]);

	}
}
