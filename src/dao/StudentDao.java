package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import dto.StudentDto;

// Data Access Object : 데이터를 취급하는 클래스
public class StudentDao {
	Scanner sc = new Scanner(System.in);

	// 학생 데이터 관리 배열
	private StudentDto student[];
	
	private int count;
	
	// 추가, 삭제, 검색, 수정 (CRUD)
	public StudentDao() {
		count = 0;
		
		student = new StudentDto[10];	// 변수만 생성
		// StudentDto student1, student2, student3, ...;
				
		// dto를 생성
//		for (int i = 0; i < student.length; i++) {
//			student[i] = new StudentDto();
//		}
	}	
	
	public void insert() {
		System.out.println("학생 정보 입력입니다");	
		
		System.out.print("이름 >> ");
		String name = sc.next();
		
		System.out.print("나이 >> ");
		int age = sc.nextInt();
		
		System.out.print("신장 >> ");
		double height = sc.nextDouble();
		
		System.out.print("주소 >> ");
		String address = sc.next();
		
		System.out.print("국어 >> ");
		int kor = sc.nextInt();
		
		System.out.print("영어 >> ");
		int eng = sc.nextInt();
		
		System.out.print("수학 >> ");
		int math = sc.nextInt();
		
		student[count] = new StudentDto(name, age, height, address, kor, eng, math);		
		count++;	// 배열의 다음으로 이동
	}
	public void delete() {
		boolean flag = true;
		Scanner sc = new Scanner(System.in);
		// 이름입력
		while(flag) {
		System.out.print("삭제하고 싶은 학생의 이름 >> ");
		String name = sc.next();
		
		// 검색
		int findIndex = 0;
		for (int i = 0; i < count; i++) {
			if(student[i].getName().equals(name)) { // 찾았다
				findIndex = i;
				flag = false;
				break;
			}
		}
		student[findIndex].setName("");
		student[findIndex].setAge(0);
		student[findIndex].setHeight(0.0);
		student[findIndex].setAddress("");
		student[findIndex].setKor(0);
		student[findIndex].setEng(0);
		student[findIndex].setMath(0);
		System.out.println("학생데이터를 삭제하였습니다.");
		
		if(flag) {
			System.out.println("해당하는 학생이 존재하지 않습니다. 다시 입력해주세요");
		}
	    }
	}
	public void select() {
		boolean flg = true;
		while(flg) {
		System.out.print("검색하고 싶은 학생의 이름 >> ");
		String name = sc.next();
		
		for (int i = 0; i < student.length; i++) {
			StudentDto dto = student[i];
			if(dto != null && dto.getName().equals("") == false) {
				if(name.equals(dto.getName())) {
					dto.print();
					flg = false;
				}
			}
			
		}
		if(flg) {
			System.out.println("해당하는 학생이 존재하지 않습니다. 다시 입력해주세요");
		}
	}
	   
	}
	public void update() {
		boolean flag = true;
		Scanner sc = new Scanner(System.in);
		// 이름입력
		while(flag) {
		System.out.print("수정하고 싶은 학생의 이름 >> ");
		String name = sc.next();
		
		// 검색
		int findIndex = -1;
		for (int i = 0; i < count; i++) {
			if(student[i].getName().equals(name)) { // 찾았다
				findIndex = i;
				flag = false;
				System.out.println("수정할 데이터를 찾았습니다");
    			
    			System.out.print("국어 >> ");
    			int lang = sc.nextInt();
    			
    			System.out.print("영어 >> ");
    			int eng = sc.nextInt();
    			
    			System.out.print("수학 >> ");
    			int math = sc.nextInt();
    			
    			StudentDto dto = student[findIndex];
    			dto.setKor(lang);
    			dto.setEng(eng);
    			dto.setMath(math);
    			System.out.println("수정이 완료되었습니다!!");
    			
				break;
			}
		
		}
		
		if(flag) {
			System.out.println("해당하는 학생이 존재하지 않습니다. 다시 입력해주세요");
		}
	    }
	}
	
	public void allData() {
	    for (int i = 0; i < count; i++) {
	        StudentDto dto = student[i];
	        if (dto != null) {
	            System.out.println(dto.toString());
	        }
	    }
	}
	
	public void save() {
		File f = new File("d:\\tmp\\student.txt");
		
		String strLine[] = new String[student.length];
		
		for (int i = 0; i < student.length; i++) {
			if(student[i] != null && !student[i].getName().equals("")) {
				strLine[i] = student[i].getName() + "-" + student[i].getAge() + "-" + student[i].getHeight()+"-"
				+student[i].getAddress() + "-" + student[i].getKor()+ "-" + student[i].getEng() + "-" + student[i].getMath();
				
							
			}else {
				strLine[i] = "";
			}
		}		
		
//		for (String s : strLine) {
//			System.out.println(s);
//		}
		
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
			
			for (String s : strLine) {
				if(s != null && !s.equals("")) {
					pw.println(s);
				}
			}
			
			pw.close();			
		} catch (IOException e) {			
			System.out.println("파일에 저장되지 않았습니다");
			return;
		}		
		
		System.out.println("정상적으로 저장되었습니다");
	}
	public void load() {
		
		File f = new File("d:\\tmp\\student.txt");
		
		try {			
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			String str = "";
			count = 0;			
			while((str = br.readLine()) != null) {
				String data[] = str.split("-");	
				//System.out.println(str);
				//System.out.println(Arrays.toString(data));
				
				 StudentDto dto = new StudentDto();
				 dto.setName(data[0]);							// 이름
		         dto.setAge(Integer.parseInt(data[1])); 		// 나이
		         dto.setHeight(Double.parseDouble(data[2]));	// 신장
		         dto.setAddress(data[3]);						// 주소
		         dto.setKor(Integer.parseInt(data[4])); 		// 국어
		         dto.setEng(Integer.parseInt(data[5])); 		// 영어
		         dto.setMath(Integer.parseInt(data[6])); 		// 수학
				
		         student[count] = dto;		
		         count++;
			}			
			br.close();
			System.out.println("파일을 불러왔습니다!!");
			
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
}
	
	






