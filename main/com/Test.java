package com;

import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		
		System.out.println(args.length);
		int  length = args.length;
		for(int i =0 ;i<length;i++){
			System.out.println(args[i]);
			
		}
		
		try {
			int read = System.in.read();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
	}
	
	
}
