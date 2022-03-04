package com.gxdxy.curso.resources.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	
	public static List<Integer> decodeIntList(String s){
		/*String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		
		for(int i=0;i<vet.length;i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		*/
		
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
}
