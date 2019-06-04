
public class BigInt {

	
	static String add(String op1, String op2){
		int i = op1.length()-1;
		int j = op2.length()-1;
		int sti= 0, stj= 0;
		for(sti = 0; sti < op1.length() && op1.charAt(sti) == '0'; sti++);
		for(stj = 0; stj < op2.length() && op2.charAt(stj) == '0'; stj++);
		
		int len = Math.max(i,j);
		int index = len;
		char[] result = new char[(len+1)];
		int onHand = 0;
		while(true){
			//Exit condition
			if(!(sti == 0 || stj == 0)){
				if(i < sti && j < stj)
					break;
			}
			else {
				if(i < 0 && j < 0)
					break;
			}
			int op1_digit = op1.charAt(i) - '0';
			int op2_digit = op2.charAt(j) - '0';
			int temp = op1_digit + op2_digit + onHand;
			if(temp == 0) {
				result[index] = '0';
			}else {
				String temp_char = String.valueOf(temp);
				result[index] = temp_char.length() > 1 ? temp_char.charAt(1) : temp_char.charAt(0);
				if(i == 1 && j == 1 && temp_char.length() > 1) {
					result[index-1] = temp_char.charAt(0);
				}
				onHand = temp_char.length() > 1 ? temp_char.charAt(0) - '0' : 0;
			}

			if(i >= 0) i--;
			if(j >= 0) j--;
			if(index > 0) index--;
		}
		for (int k = 0; k < result.length; k++) {
			if(!Character.isDigit(result[k])) result[k] = '0';
			if(k > 0 && result[k] != '0') break;
		}
		return new String(result).trim();
	}

	static String multiply(String base, String multiplier){
		boolean first = true;
		int base_len = base.length();
		int mult_len = multiplier.length();
		char[] result = new char[(base_len+mult_len+1)];
		int i = 0;
		for(i = mult_len-1; i >= 0; i--){
			int mult_index = -(i - (mult_len-1));
			int j;
			int mult_digit = multiplier.charAt(i) - '0';
			char[] line = new char[base_len+mult_len];
			int onHand = 0;
			for(j = base_len-1; j >= 0; j--){
				int base_digit = base.charAt(j) - '0';
				int pre_result = base_digit*mult_digit;
				pre_result += onHand;
				if(pre_result <= 0) {
					line[j+1] = '0';
					continue;
				}
				String pre_res_char = String.valueOf(pre_result);
				line[j+1] = pre_res_char.length() > 1 ? pre_res_char.charAt(1) : pre_res_char.charAt(0);
				if(j == 0 && pre_res_char.length() > 1){
					line[j] = pre_res_char.charAt(0);
				}
				onHand = pre_res_char.length() > 1 ? pre_res_char.charAt(0) - '0' : 0;
			}
			for(j = 0; j < base_len+mult_len; j++){
				if(!Character.isDigit(line[j])) line[j] = '0';
			}
			//Shift
			int shift_count = base_len + mult_index;
			int dist = (base_len+mult_len)-shift_count-1;
			int c = base_len;
			j = base_len;
			if(dist > 0){
				while(c-- >= 0){
					line[j+dist] = line[j];
					line[j] = '0';
					j--;
				}
			}
			//Add shifted values
			if(!first){
				String temp = add(new String(line), new String(result));
				result = temp.toCharArray();
			}
			else{
				result = line;
				first = false;
			}
		}
		//Clear all zeros at the beginning
		for (int j = 0; j < result.length; j++) {
			if(result[j] == '0')
				result[j] = '\0';
			else
				break;
		}
		return new String(result).trim();
	}

	public static void main(String args[]){
		
		int goal = 100;
		String result = null;
		int int_result = 1;
		boolean first = true;
		for(int i = 1; i <= goal; i++){
			//Calculate by integer
			if(i <= 12){
				int_result *= i;
			}
			//Calculate by strings
			else{
				if(first){
					first = false;
					result = String.valueOf(int_result);
				}
				String cur = String.valueOf(i);
				result = multiply(result, cur);
			}
			//System.out.print("Index: " + i + " > ");
			//System.out.println(result == null ? int_result : result);
		}
		if(first){
			result = String.valueOf(int_result);
		}
		
		System.out.println(result);
		//System.out.println(multiply("15511210043330985984000000", "26"));
		
	}
}
