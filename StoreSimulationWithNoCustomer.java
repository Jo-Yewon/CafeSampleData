//String Ÿ���� null�� �� ���ڿ�, int Ÿ���� null�� �ϴ� 0���� �մϴ�.
//���� SQL�󿡼� ������ �ʿ��մϴ�.

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
		if (cusNum!=0) sb.append(cusNum); //��ȸ���ֹ��̸� ���鹮�ڿ� ��ȯ
		sb.append(","+orderTime+","+orderType+",");
		if (resNum!=0) sb.append(resNum);
		
		return sb.toString();
	}
	
	Order(String resTime, int resNum){
		orderNum=Order.orderList.size()+1; //���� ��ȣ
		totalCost=makeRandomDetailOrder(); //������ �ֹ� ���� ���� ��, �Ѿ� ���
		
		//�� ��ϵ��� ���� �մ��� ���
		if(Customer.custotal==0 || 
				Math.random()<StoreSimulationWithNoCustomer.�¼մ��̰��̾ƴ�Ȯ��) {
			if(Math.random()<StoreSimulationWithNoCustomer.���̾ƴѼմ��̰����ε����Ȯ��) 
			{
				//���ο� �� ���
				Customer c= new Customer();
				cusNum=Customer.custotal;
			}
			else cusNum=0; //��ȸ�� �ֹ�
			
		}
		//�� ��ϵ� �մ��� ���
		else {
			//���� �� �� �������� �ֹ� ��Ī
			cusNum=(int) (Math.random()*Customer.custotal+1);
		}
		
		//���� Ÿ�� ���ϱ�
		if(Math.random()<StoreSimulationWithNoCustomer.����ũ�ƿ�Ȯ��) orderType="����ũ�ƿ�";
		else orderType="����";
		
		//��¥ �����ϱ�
		orderTime=resTime;
		
		//�ֹ� ����
		if(Math.random()<0.05f) orderState="���";
		else orderState="�Ϸ�";
		
		this.resNum=resNum; //����ƴ�
		
		orderList.add(this);
	}
	
	//���� ���� �ֹ�
	Order(){
		orderNum=Order.orderList.size()+1; //���� ��ȣ
		totalCost=makeRandomDetailOrder(); //������ �ֹ� ���� ���� ��, �Ѿ� ���
		
		//�� ��ϵ��� ���� �մ��� ���
		if(Customer.custotal==0 || 
				Math.random()<StoreSimulationWithNoCustomer.�¼մ��̰��̾ƴ�Ȯ��) {
			if(Math.random()<StoreSimulationWithNoCustomer.���̾ƴѼմ��̰����ε����Ȯ��) 
			{
				//���ο� �� ���
				Customer c= new Customer();
				cusNum=Customer.custotal;
			}
			else cusNum=0; //��ȸ�� �ֹ�
			
		}
		//�� ��ϵ� �մ��� ���
		else {
			//���� �� �� �������� �ֹ� ��Ī
			cusNum=(int) (Math.random()*Customer.custotal+1);
		}
		
		//���� Ÿ�� ���ϱ�
		if(Math.random()<StoreSimulationWithNoCustomer.����ũ�ƿ�Ȯ��) orderType="����ũ�ƿ�";
		else orderType="����";
		
		//��¥ �����ϱ�
		orderTime=RandomData.RandomDate();
		
		//�ֹ� ����
		if(Math.random()<0.05f) orderState="���";
		else orderState="�Ϸ�";
		
		resNum=0; //����ƴ�
		
		orderList.add(this);
	}
	
	int makeRandomDetailOrder() {
		int total=0;
		
		//�ֹ��� �׸�(��)�� �������� �����ϱ�
		int �׸񰳼�;
		if (Math.random()<0.7f) �׸񰳼�=1;
		else �׸񰳼�=(int)(Math.random()*3+1); //�� �ֹ��� ���� �������� 1~4���� �׸�
		Set <Integer> set= new HashSet<Integer>();
		while(set.size()!=�׸񰳼�)
			//��� �޴����� �׸񰳼���ŭ ��.
			set.add((int)(Math.random()*Menu.menuList.size()+1));
		
		//�� �׸� ���� DetailOrder�� �ֱ�
		Iterator it=set.iterator();
		while (it.hasNext()){
			DetailOrder d=new DetailOrder(orderNum, (int)it.next());
			total+=Menu.menuList.get(d.menuNum-1).price*d.orderQuan;
		}
		//�ֹ��� �Ѿ� ��ȯ
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
		orderQuan=(int)(Math.random()*3+1); //1~5�� �� �������� �ֹ�
		detailOrderList.add(this); //�ڵ����� ����Ʈ�� �־���
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
		resState="�Ϸ�";
		
		//�� ��ϵ��� ���� �մ��� ���
		if(Customer.custotal==0 || 
				Math.random()<StoreSimulationWithNoCustomer.�¼մ��̰��̾ƴ�Ȯ��) {
			Customer c= new Customer();
			cusNum=Customer.custotal;
		}
		//�̹� ���� �մ��� ���
		else {
			cusNum=(int) (Math.random()*Customer.custotal+1);
		}
		resList.add(this);
		new Order(resTime,resNum);	//���ο� �ֹ�
		}
}

class RandomData {   
	//�������� ������ �ѱ� �̸��� �����ϴ� �޼���
    public static String RandomName()
    {
        StringBuffer Name = new StringBuffer();
        for(int i = 0; i <3; i++){
            char ch = (char)((Math.random() * 11172) + 0xAC00);
            Name.append(ch);
        }
        return Name.toString();
    }
    
    //�������� ��ȭ��ȣ �����ϴ� �޼���
    public static String RandomTel() {
    	return "010"+
    	String.format("%04d", (int)(Math.random()*10000))
    	+ String.format("%04d", (int)(Math.random()*10000));
    }
    
    //���� ���� ����
    public static int RandomAge() {
    	//age�� ����ġ�� �ٲ���
    	return (int)(Math.random()*80+10);
    }
    
    //���� �ּ� ����
    public static String RandomAdd() {
    	//10�� Ȯ���� null ��ȯ
    	if(Math.random()<0.1f) return "";
    	 StringBuffer Name = new StringBuffer();
         for(int i = 0; i <6; i++){
             char ch = (char)((Math.random() * 11172) + 0xAC00);
             Name.append(ch);
         }
         return Name.toString();
    }
    
    //���� ���� ����
    public static String RandomGender() {
    	//10�� Ȯ���� null ��ȯ
    	if(Math.random()<0.01f) return "";
    	if(Math.random()<0.5f) return "����";
    	return "����";
    }
    
    //���Ϻ� �Ǽ� ����
    static double DayRatio[]= {806.0/5794, 873.0/5794, 911.0/5794, 683.0/5794, 664/5794,
    		971.0/5794, 886.0/5794};
    static double TimeRatio[]= {20.0/5795, 530.0/5795, 1349.0/5795,
    		1719.0/5795, 1772.0/5795, 405/5795};
    static int TimeRange[][]= {{0,6},{6,5},{11,3},{14,3},{17,4},{21,3}};
    
    public static String RandomDate() {
    	//2019�� 5�� 20��~27�� ������ ��¥�� �ð� ����.
    	StringBuffer sb= new StringBuffer();
    	sb.append("2019-05-");
    	
    	//��¥ ����(����ڷ� �ݿ�)
    	int i;
    	double ranDate=Math.random();
    	for(i=0;i<7;i++) {
    		if(ranDate<DayRatio[i]) {
    			sb.append(20+i); //��¥
    			break;
    		}else ranDate-=DayRatio[i];
    	}
    	if(i==7) sb.append(26);
    	
    	sb.append(" ");
    	
    	//�ð� ����(����ڷ� �ݿ�)
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
    	return (int) (Math.random()*10+1); //1~10�� ���� ���� ����
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
	
	//���ο� �� ���� ����
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
	static float �¼մ��̰��̾ƴ�Ȯ��=0.4f;
	static float ���̾ƴѼմ��̰����ε����Ȯ��=0.2f;
	static float ����ũ�ƿ�Ȯ�� = 0.7f;

	public static void main(String[] args) throws IOException {
		StringTokenizer st;
		BufferedOutputStream bs = null;
		String temp;
		
		//�޴� �ؽ�Ʈ���� �о�ͼ� �����ϱ�
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
		
		//�ֹ� 1000�� ���Դٰ� �����ϱ�
		for(int i=0;i<8580;i++) new Order();
		for(int i=0;i<20;i++) new Reservation();
		
		//��� ���Ϸ� �����ϱ�
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
		
		System.out.println("���� �Ϸ�");
		System.out.println("������ �� ��:"+Customer.custotal);
	}

}
