//String 타입의 null은 빈 문자열, int 타입의 null은 일단 0으로 합니다.
//추후 SQL상에서 수정이 필요합니다.

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

class Menu{
	static ArrayList<Menu> menuList= new ArrayList<Menu>();
	int menuNum;
	int price;
	Menu(int menuNum, int price){
		this.menuNum=menuNum;
		this.price=price;
		menuList.add(this);
	}
}

class Order{
	static ArrayList<Order> orderList = new ArrayList<Order>();
	int orderNum;
	String orderState;
	int totalCost;
	int cusNum;
	String orderTime;
	String orderType;
	int resNum;
	
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append(orderNum+","+orderState+","+totalCost+",");
		if (cusNum!=0) sb.append(cusNum); //비회원주문이면 공백문자열 반환
		sb.append(","+orderTime+","+orderType+",");
		if (resNum!=0) sb.append(resNum);
		
		return sb.toString();
	}
	
	Order(String resTime, int resNum){
		orderNum=Order.orderList.size()+1; //오더 번호
		totalCost=makeRandomDetailOrder(); //디테일 주문 랜덤 생성 후, 총액 담기
		
		//고객 등록되지 않은 손님일 경우
		if(Customer.custotal==0 || 
				Math.random()<StoreSimulationWithNoCustomer.온손님이고객이아닐확률) {
			if(Math.random()<StoreSimulationWithNoCustomer.고객이아닌손님이고객으로등록할확률) 
			{
				//새로운 고객 등록
				Customer c= new Customer();
				cusNum=Customer.custotal;
			}
			else cusNum=0; //비회원 주문
			
		}
		//고객 등록된 손님인 경우
		else {
			//기존 고객 중 랜덤으로 주문 매칭
			cusNum=(int) (Math.random()*Customer.custotal+1);
		}
		
		//오더 타입 정하기
		if(Math.random()<StoreSimulationWithNoCustomer.테이크아웃확률) orderType="테이크아웃";
		else orderType="매장";
		
		//날짜 지정하기
		orderTime=resTime;
		
		//주문 상태
		if(Math.random()<0.05f) orderState="취소";
		else orderState="완료";
		
		this.resNum=resNum; //예약아님
		
		orderList.add(this);
	}
	
	//예약 없는 주문
	Order(){
		orderNum=Order.orderList.size()+1; //오더 번호
		totalCost=makeRandomDetailOrder(); //디테일 주문 랜덤 생성 후, 총액 담기
		
		//고객 등록되지 않은 손님일 경우
		if(Customer.custotal==0 || 
				Math.random()<StoreSimulationWithNoCustomer.온손님이고객이아닐확률) {
			if(Math.random()<StoreSimulationWithNoCustomer.고객이아닌손님이고객으로등록할확률) 
			{
				//새로운 고객 등록
				Customer c= new Customer();
				cusNum=Customer.custotal;
			}
			else cusNum=0; //비회원 주문
			
		}
		//고객 등록된 손님인 경우
		else {
			//기존 고객 중 랜덤으로 주문 매칭
			cusNum=(int) (Math.random()*Customer.custotal+1);
		}
		
		//오더 타입 정하기
		if(Math.random()<StoreSimulationWithNoCustomer.테이크아웃확률) orderType="테이크아웃";
		else orderType="매장";
		
		//날짜 지정하기
		orderTime=RandomData.RandomDate();
		
		//주문 상태
		if(Math.random()<0.05f) orderState="취소";
		else orderState="완료";
		
		resNum=0; //예약아님
		
		orderList.add(this);
	}
	
	int makeRandomDetailOrder() {
		int total=0;
		
		//주문할 항목(들)을 랜덤으로 선택하기
		int 항목개수;
		if (Math.random()<0.7f) 항목개수=1;
		else 항목개수=(int)(Math.random()*3+1); //한 주문에 대해 랜덤으로 1~4개의 항목
		Set <Integer> set= new HashSet<Integer>();
		while(set.size()!=항목개수)
			//모든 메뉴증에 항목개수만큼 고름.
			set.add((int)(Math.random()*Menu.menuList.size()+1));
		
		//각 항목 별로 DetailOrder에 넣기
		Iterator it=set.iterator();
		while (it.hasNext()){
			DetailOrder d=new DetailOrder(orderNum, (int)it.next());
			total+=Menu.menuList.get(d.menuNum-1).price*d.orderQuan;
		}
		//주문의 총액 반환
		return total;
	}
}

class DetailOrder{
	static ArrayList<DetailOrder> detailOrderList= new ArrayList<DetailOrder>();
	int orderNum;
	int menuNum;
	int orderQuan;
	
	public String toString(){
		return orderNum+","+menuNum+","+orderQuan;
	}
	
	DetailOrder(int orderNum,int menuNum){
		this.menuNum=menuNum;
		this.orderNum=orderNum;
		orderQuan=(int)(Math.random()*3+1); //1~5개 중 랜덤으로 주문
		detailOrderList.add(this); //자동으로 리스트에 넣어줌
	}
}

class Reservation{
	static ArrayList<Reservation> resList= new ArrayList<Reservation>();
	int resNum;
	String resTime;
	int people;
	String resState;
	int cusNum;
	
	public String toString(){
		return resNum+","+resTime+","+people+","+cusNum;
	}
	
	Reservation(){
		resNum=resList.size()+1;
		resTime=RandomData.RandomDate();
		people=RandomData.RandomPeople();
		resState="완료";
		
		//고객 등록되지 않은 손님일 경우
		if(Customer.custotal==0 || 
				Math.random()<StoreSimulationWithNoCustomer.온손님이고객이아닐확률) {
			Customer c= new Customer();
			cusNum=Customer.custotal;
		}
		//이미 고객인 손님인 경우
		else {
			cusNum=(int) (Math.random()*Customer.custotal+1);
		}
		resList.add(this);
		new Order(resTime,resNum);	//새로운 주문
		}
}

class RandomData {   
	//랜덤으로 세글자 한글 이름을 생성하는 메서드
    public static String RandomName()
    {
        StringBuffer Name = new StringBuffer();
        for(int i = 0; i <3; i++){
            char ch = (char)((Math.random() * 11172) + 0xAC00);
            Name.append(ch);
        }
        return Name.toString();
    }
    
    //랜덤으로 전화번호 생성하는 메서드
    public static String RandomTel() {
    	return "010"+
    	String.format("%04d", (int)(Math.random()*10000))
    	+ String.format("%04d", (int)(Math.random()*10000));
    }
    
    //랜덤 나이 생성
    public static int RandomAge() {
    	//age를 가중치로 바꾸자
    	return (int)(Math.random()*80+10);
    }
    
    //랜덤 주소 생성
    public static String RandomAdd() {
    	//10퍼 확률로 null 반환
    	if(Math.random()<0.1f) return "";
    	 StringBuffer Name = new StringBuffer();
         for(int i = 0; i <6; i++){
             char ch = (char)((Math.random() * 11172) + 0xAC00);
             Name.append(ch);
         }
         return Name.toString();
    }
    
    //랜덤 성별 생성
    public static String RandomGender() {
    	//10퍼 확률로 null 반환
    	if(Math.random()<0.01f) return "";
    	if(Math.random()<0.5f) return "여성";
    	return "남성";
    }
    
    //요일별 건수 비율
    static double DayRatio[]= {806.0/5794, 873.0/5794, 911.0/5794, 683.0/5794, 664/5794,
    		971.0/5794, 886.0/5794};
    static double TimeRatio[]= {20.0/5795, 530.0/5795, 1349.0/5795,
    		1719.0/5795, 1772.0/5795, 405/5795};
    static int TimeRange[][]= {{0,6},{6,5},{11,3},{14,3},{17,4},{21,3}};
    
    public static String RandomDate() {
    	//2019년 5월 20일~27일 사이의 날짜와 시간 생성.
    	StringBuffer sb= new StringBuffer();
    	sb.append("2019-05-");
    	
    	//날짜 생성(통계자료 반영)
    	int i;
    	double ranDate=Math.random();
    	for(i=0;i<7;i++) {
    		if(ranDate<DayRatio[i]) {
    			sb.append(20+i); //날짜
    			break;
    		}else ranDate-=DayRatio[i];
    	}
    	if(i==7) sb.append(26);
    	
    	sb.append(" ");
    	
    	//시간 생성(통계자료 반영)
    	ranDate=Math.random();
    	for(i=0;i<5;i++) {
    		if(ranDate<TimeRatio[i]) {
    			sb.append(String.format("%02d", 
    					(int)(Math.random()*TimeRange[i][1])+TimeRange[i][0])+":");
    			break;
    		}else ranDate-=DayRatio[i];
    	}
    	if(i==5) sb.append(String.format("%02d", 
				(int)(Math.random()*TimeRange[5][1])+TimeRange[5][0])+":");

    	sb.append(String.format("%02d", (int)(Math.random()*60))+":");
    	sb.append(String.format("%02d", (int)(Math.random()*60)));
    	return sb.toString();
    }
    
    public static int RandomPeople() {
    	return (int) (Math.random()*10+1); //1~10명 사이 랜덤 예약
    }
}

/*
class Customer{
	static ArrayList<Customer> customerList = new ArrayList<Customer>();
	int cusNum;
	String cusName;
	int cusAge;
	String cusGender;
	String cusTel;
	String cusAdd;
	
	public String toString(){
		return cusNum+","+cusName+","+cusAge+","+cusGender
				+","+cusTel+","+cusAdd;
	}
	
	//새로운 고객 랜덤 생성
	Customer(){
		cusNum=customerList.size()+1;
		cusName= RandomData.RandomName();
		cusTel=RandomData.RandomTel();
		cusAge=RandomData.RandomAge();
		cusGender=RandomData.RandomGender();
		cusAdd=RandomData.RandomAdd();
		customerList.add(this);
	}
}
*/

class Customer{
	static int custotal=0;
	Customer(){
		custotal++;
	}
}

public class StoreSimulationWithNoCustomer {
	static float 온손님이고객이아닐확률=0.4f;
	static float 고객이아닌손님이고객으로등록할확률=0.2f;
	static float 테이크아웃확률 = 0.7f;

	public static void main(String[] args) throws IOException {
		StringTokenizer st;
		BufferedOutputStream bs = null;
		String temp;
		
		//메뉴 텍스트파일 읽어와서 저장하기
		try {
			File file = new File("Menu.txt");
			FileReader fr=new FileReader(file);
			BufferedReader br= new BufferedReader(fr);
			
			String line = "";
            while((line = br.readLine()) != null){
                st= new StringTokenizer(line, " ");
                new Menu(Integer.parseInt(st.nextToken()),
                		Integer.parseInt(st.nextToken()));
            }
            br.close();
		}catch (Exception e) {
            System.out.println(e.getMessage());
        }
		
		//주문 1000번 들어왔다고 생각하기
		for(int i=0;i<8580;i++) new Order();
		for(int i=0;i<20;i++) new Reservation();
		
		//결과 파일로 저장하기
		try {
			bs = new BufferedOutputStream(new FileOutputStream("Order.txt"));
			for(int i=0;i<Order.orderList.size();i++) {
				temp=Order.orderList.get(i).toString()+"\n";
				bs.write(temp.getBytes());
			}
		} catch (Exception e) {
	                e.getStackTrace();
		}finally {
			bs.close();
		} 
		
		try {
			bs = new BufferedOutputStream(new FileOutputStream("Reservation.txt"));
			for(int i=0;i<Reservation.resList.size();i++) {
				temp=Reservation.resList.get(i).toString()+"\n";
				bs.write(temp.getBytes());
			}
		} catch (Exception e) {
	                e.getStackTrace();
		}finally {
			bs.close();
		} 
		
		try {
			bs = new BufferedOutputStream(new FileOutputStream("DetailOrder.txt"));
			for(int i=0;i<DetailOrder.detailOrderList.size();i++) {
				temp=DetailOrder.detailOrderList.get(i).toString()+"\n";
				bs.write(temp.getBytes());
			}
		} catch (Exception e) {
	                e.getStackTrace();
		}finally {
			bs.close();
		} 
		
		/*
		try {
			bs = new BufferedOutputStream(new FileOutputStream("Customer.txt"));
			for(int i=0;i<Customer.customerList.size();i++) {
				temp=Customer.customerList.get(i).toString()+"\n";
				bs.write(temp.getBytes());
			}
		} catch (Exception e) {
	                e.getStackTrace();
		}finally {
			bs.close();
		} 
		*/
		
		System.out.println("생성 완료");
		System.out.println("생성된 고객 수:"+Customer.custotal);
	}

}
