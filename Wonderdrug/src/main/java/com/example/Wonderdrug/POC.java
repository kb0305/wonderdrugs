package com.example.Wonderdrug;

public class POC {
	

	public static void main(String[] args) {
		
		try { 
			
			String str1="Completed/";
			String str2="Completed/Wonderdrug.war";
			
			if(str1.equals("Completed/")) {
				System.out.println(str1.contains("Completed"));
			}
			
			if(str2.equals("Completed/")) {
				System.out.println(str2.equals("Completed"));
			}
			
			
//			System.out.println(split[1]);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
