import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Lab1 {
	public static void main(String []args) {
		int c,arr_basic[],arr_moved[],arr_bigramm_basic[],arr_bigramm_moved[];
        char symb;
        Map<Character, Integer> symb_entry_basic = new LinkedHashMap<Character, Integer>();
        Map<Character, Integer> symb_entry_moved = new LinkedHashMap<Character, Integer>();
        Map<String, Integer> symb_entry_basic_bigramm = new LinkedHashMap<String, Integer>();
        Map<String, Integer> symb_entry_moved_bigramm = new LinkedHashMap<String, Integer>();
        List<Character> alphabet = new ArrayList<Character>();
        
        arr_basic = new int[33];
        arr_moved = new int[33];
        arr_bigramm_basic = new int[30];
        arr_bigramm_moved = new int[30];
        init_alphabet(alphabet);
        init_map(symb_entry_basic);
        init_map(symb_entry_moved);
        init_bigramm(symb_entry_basic_bigramm);
        init_bigramm(symb_entry_moved_bigramm);
		try(BufferedReader br = new BufferedReader (new FileReader("Voynaimir.txt")))
        {
           // чтение посимвольно
            while((c=br.read())!=-1) {
            	symb = Character.toLowerCase((char)c);
            	if(alphabet.contains(symb)) {
            		incFrequency(symb,symb_entry_basic);
            	}
            }
            
            System.out.println("Исходный текст: ");
            
           
            
            for(Map.Entry<Character, Integer> item : symb_entry_basic.entrySet()){
                System.out.print(item.getKey());
                System.out.print(' ');
                System.out.println(item.getValue());
            }
            
            toArray(arr_basic,symb_entry_basic);
            
            br.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
		
		try(
				BufferedReader br = new BufferedReader (new FileReader("Chapter_14.txt"));
				BufferedWriter bw = new BufferedWriter(new FileWriter("Chapter_14_encrypted.txt"));
			)
        {
            while((c=br.read())!=-1) {
            	symb = Character.toLowerCase((char)c);
            	if(alphabet.contains(symb)){
            		if(alphabet.indexOf(symb) + 4<=32) {
            			symb = alphabet.get(alphabet.indexOf(symb)+4);
            			bw.write(symb);
            		}
            		else {
            			symb = alphabet.get(alphabet.indexOf(symb)+4 -33);
            			bw.write(symb);
            		}
            	}
            	else bw.write(symb);
            }
            
            br.close();
            bw.close();
         }
            
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
		
		try(BufferedReader br = new BufferedReader (new FileReader("Chapter_14_encrypted.txt")))
        {
		 
            while((c=br.read())!=-1) {
            	symb = Character.toLowerCase((char)c);
            	if(alphabet.contains(symb)) {
            		incFrequency(symb,symb_entry_moved);
            	}
				
            }
            
            System.out.println("Сдвинутый текст: ");
            
            for(Map.Entry<Character, Integer> item : symb_entry_moved.entrySet()){
                System.out.print(item.getKey());
                System.out.print(' ');
                System.out.println(item.getValue());
            }
            
            toArray(arr_moved,symb_entry_moved);
           
            br.close();
        }
		
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
		
		try(
				BufferedReader br = new BufferedReader (new FileReader("Chapter_14_encrypted.txt"));
				BufferedWriter bw = new BufferedWriter(new FileWriter("Chapter_14_decrypted.txt"));
			)
        {
			toArray(arr_moved,symb_entry_moved);
			toArray(arr_basic,symb_entry_basic);
			
            while((c=br.read())!=-1) {
            	symb = Character.toLowerCase((char)c);
            	if(alphabet.contains(symb)){
            		int value,index;
            		value = symb_entry_moved.get(symb);
            		index = findIndex(arr_moved,value);
            		value = arr_basic[index];
            		for(Map.Entry<Character, Integer> item : symb_entry_basic.entrySet()){
                        if(item.getValue() == value) {
                        	symb = item.getKey();
                        	bw.write(symb);
                        	break;
                        }
                    }
            	}
            	else bw.write(symb);
            }
            
            br.close();
            bw.close();
         }
            
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
		
		//Подсчет биграмм для оригинального текста
		try(
				BufferedReader br = new BufferedReader (new FileReader("Voynaimir.txt"));
			)
        {
			String slog;
			char ch0 = ' ',ch1;
            while((c=br.read())!=-1) {
            	ch1 = Character.toLowerCase((char)c);
            	if(alphabet.contains(ch1)) {
            		slog = new String(new char[] {ch0, ch1});
            		if(symb_entry_basic_bigramm.containsKey(slog)) {
            			incFrequency(slog,symb_entry_basic_bigramm);
            		}
  
            	}
            	ch0 = ch1;
            }
            
         }
            
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
		
		try(
				BufferedReader br = new BufferedReader (new FileReader("Chapter_14_decrypted.txt"));
			)
		 {
			String slog;
			char ch0 = ' ',ch1;
            while((c=br.read())!=-1) {
            	ch1 = Character.toLowerCase((char)c);
            	if(alphabet.contains(ch1)) {
            		slog = new String(new char[] {ch0, ch1});
            		if(symb_entry_moved_bigramm.containsKey(slog)) {
            			symb_entry_moved_bigramm.replace(slog, symb_entry_moved_bigramm.get(slog)+1);
            		}
  
            	}
            	ch0 = ch1;
            }
            
         }
            
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
		
		try(
				BufferedReader br = new BufferedReader (new FileReader("Chapter_14_decrypted.txt"));
				BufferedWriter bw = new BufferedWriter(new FileWriter("Chapter_14_decrypted_bigramm.txt"));
			)
        {
			toArray(symb_entry_basic_bigramm,arr_bigramm_basic);
			toArray(symb_entry_moved_bigramm,arr_bigramm_moved);
			char ch0 = ' ', ch1 = ' ';
			boolean readNext = true;
            while((c=br.read())!=-1) {
        
            	String slog;
            	if (readNext) {
            		ch0 = (char)c;
            		ch1 = (char)br.read();
            	}
            	else {
            		ch1 = (char)c;
            	}
    			slog = new String(new char[] {ch0,ch1});
                if((!alphabet.contains(ch0) && alphabet.contains(ch1))||
                	(alphabet.contains(ch0) && !alphabet.contains(ch1))||
                	(!alphabet.contains(ch0) && !alphabet.contains(ch1)))
                {
                	bw.write(slog);
                	readNext = true;
                }
                else {
                	if(symb_entry_moved_bigramm.containsKey(slog)){
                		int value,index;
                		value = symb_entry_moved_bigramm.get(slog);
                		index = findIndex(arr_bigramm_moved,value);
                		if(index!=-1) {
                			boolean isFound = false;
                			value = arr_bigramm_basic[index];
                    		for(Map.Entry<String, Integer> item : symb_entry_basic_bigramm.entrySet()){
                                if(item.getValue() == value) {
                                	if(
                                			((item.getKey().charAt(0)- slog.charAt(0))>5) ||( slog.charAt(0)> item.getKey().charAt(0)) ||
                                			((item.getKey().charAt(1)- slog.charAt(1))>5) || (slog.charAt(1)> item.getKey().charAt(1)) 
                                			//(item.getKey().charAt(0) - slog.charAt(0)<2) ||
                                			//(item.getKey().charAt(1) - slog.charAt(1)<2) 
                                	)
                                	{
                                		continue;
                                	}
                                	else {
                                		slog = item.getKey();
                                    	bw.write(slog);
                                    	isFound = true;
                                    	readNext = true;
                                	}
                                }
                            }
                    		if (!isFound){
                    			readNext = true;
                    			bw.write(slog);
                    		}
                		}
                		else {
                    		bw.write(ch0);
                    		ch0 = ch1;
                    		readNext = false;
                    	}
                	}
                }
            }
          
            br.close();
            bw.close();
            }
           
            
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
		
		
	}
	
	static void init_map(Map<Character, Integer> map) {
		for(char ch = 'а'; ch <= 'е'; ch++){
			map.put(ch, 0);
		}
		map.put('ё', 0);
		for(char ch = 'ж'; ch <= 'я'; ch++){
			map.put(ch, 0);
		}
	}
	
	static void init_bigramm(Map<String, Integer> map) {
		for(char ch0 = 'а'; ch0 <= 'я'; ch0++){
			for(char ch1 = 'а';ch1<='я';ch1++) {
				String slog = new String(new char[] {ch0, ch1});
				map.put(slog, 0);
			}
		}
		
		for(char ch0 = 'а';ch0<='я';ch0++) {
			String slog = new String(new char[] {'ё', ch0});
			map.put(slog, 0);
			slog = new String(new char[] {ch0, 'ё'});
			map.put(slog, 0);
		}
		
		
		String useless_slog = new String(new char[] {'ё', 'ё'});
		map.put(useless_slog , 0);
		
	}
	
	static void init_alphabet(List<Character> list) {
		for(char ch = 'а'; ch <= 'е'; ch++){
			list.add(ch);
		}
		list.add('ё');
		for(char ch = 'ж'; ch <= 'я'; ch++){
			list.add(ch);
		}
	}
	
	//конвертация значений LinkedHashMap в Int массив
	static <K> void toArray(int arr[], Map<Character, Integer> map) {
		int index = 0;
        for(Entry<Character, Integer> item : map.entrySet()){
            arr[index] = item.getValue();
            index++;
        }
        Arrays.sort(arr);
	}
	
	static void toArray( Map<String, Integer> map,int arr[]) {
		int index = 0,new_arr[],k = 1088;
		new_arr = new int[1089];
        for(Entry<String, Integer> item : map.entrySet()){
            new_arr[index] = item.getValue();
            index++;
        }
        Arrays.sort(new_arr);
        for(int i = 0;i<30;i++) {
        	arr[i] = new_arr[k];
        	k--;
        }
	}
	
	static int findIndex(int arr[], int t) 
    { 
        if (arr == null) { 
            return -1; 
        } 
  
        int len = arr.length; 
        int i = 0; 
  
        while (i < len) { 
            if (arr[i] == t) { 
                return i; 
            } 
            else { 
                i = i + 1; 
            } 
        } 
        return -1; 
   } 
	 
	static <K> void incFrequency(K c, Map<K,Integer> map) {
		 map.replace(c,map.get(c)+1);
	}
	
	
}

