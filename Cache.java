import java.io.*;
import java.util.*;
class PageReplacement{
static float fifo_ratio=(float)0;
static float opr_ratio=(float)0;
static float lru_ratio=(float)0;
static int arr[]=new int [4];
PageReplacement()
{for(int i=0;i<4;i++){arr[i]=4-i;}}

static void display() {
System.out.println("-----------------");
System.out.println("| "+arr[0]+" | "+arr[1]+" | "+arr[2]+" | "+arr[3]+" |");
System.out.println("-----------------\n\n");
try{
Thread.sleep(1000);}
catch(Exception e){}
}

static void opr(int req_pgs[]){
int miss=0;int hit=0;
ArrayList<Integer> user_data=new ArrayList<Integer>();
File file;
Scanner sc;
System.out.print("\nLearning form user data : ");
for(int i=0;i<=100;i++){
System.out.print(i+"%");
try{Thread.sleep(150);}catch(Exception e){}
if((i/10)>0&&((i/10)!=10)){System.out.print("\b\b\b");}
else if((i/10)==10){System.out.println("");}
else{System.out.print("\b\b");}
}
try{
 file = new File("C:\\Users\\Anjali\\Desktop\\user.data"); 
 sc = new Scanner(file); 
while (sc.hasNextLine()) {
      user_data.add(Integer.valueOf(sc.nextLine())); }  
}catch(Exception e){}
   

ArrayList<Integer> frequencies=new ArrayList<Integer>();
for(int i=0;i<8;i++){frequencies.add(0);}

for(int i:user_data){
int temp=frequencies.get(i-1);
temp++;
frequencies.set(i-1,temp);
}
System.out.println("Predictions using learned parameters: ");
for(int i=0;i<10;i++){
int req=frequencies.indexOf(Collections.max(frequencies))+1;
int req_index=frequencies.indexOf(Collections.max(frequencies));
System.out.print(req+" ");
try{Thread.sleep(150);}catch(Exception e){}
for (int j=0;j<8;j++){
	if(j!=req_index){
			int temp=frequencies.get(j);
			temp++;
			frequencies.set(j,temp);
			}
	}

}
System.out.println("");
ArrayList<Integer> req_list = new ArrayList<Integer>();
//Collections.addAll(req_list,req_pgs);
for(int i=0;i<req_pgs.length;i++){req_list.add(req_pgs[i]);}
for(int i=0;i<4;i++){arr[i]=4-i;}
for(int i=0;i<req_pgs.length;i++){
int contains=0;
System.out.println("Requested page is "+req_pgs[i]);

for(int j=0;j<4;j++){if(req_pgs[i]==arr[j]){contains=1;hit++;}}
if(contains==1){System.out.println("HIT!!!");}
if(contains==0){System.out.println("MISS!!!");
miss++;
int replace_index=0;
int max=0;
for(int k=0;k<4;k++){
	int temp=req_list.indexOf(arr[k]);
	if(temp==-1){
		replace_index=k;
		break;
		}
	else{
		if(temp>max){
			max=temp;
			replace_index=k;
			}
		}
	}
arr[replace_index]=req_pgs[i];
}		//contains=0
display();
}
opr_ratio=(float)hit/miss;
System.out.println("Hit to miss ratio is "+hit+":"+miss+" which is "+opr_ratio);
System.out.println("\n===================================================================================\n\n\n");
}		//opr

static void fifo(int req_pgs[]){
int miss=0;
int hit=0;
int first_in=3;
for(int i=0;i<req_pgs.length;i++){
int contains=0;
System.out.println("Requested page is "+req_pgs[i]);

for(int j=0;j<4;j++){if(req_pgs[i]==arr[j]){contains=1;hit++;}}
if(contains==1){System.out.println("HIT!!!");}
if(contains==0){System.out.println("MISS!!!");miss++;arr[first_in]=req_pgs[i];first_in--;if(first_in<0){first_in+=4;}}
display();

}
fifo_ratio=(float)hit/miss;
System.out.println("Hit to miss ratio is "+hit+":"+miss+" which is "+fifo_ratio);
System.out.println("\n===================================================================================\n\n\n");
}


static void lru(int req_pgs[]){
for(int i=0;i<4;i++){arr[i]=4-i;}
int miss=0;
int hit =0;
ArrayList<Integer> req_order =new ArrayList<Integer>();
for(int i=1;i<5;i++){req_order.add(i);}
for(int i=0;i<req_pgs.length;i++){req_order.add(req_pgs[i]);}
for(int i=4;i<req_order.size();i++){
int contains=0;
System.out.println("Requested page is "+req_order.get(i));
for(int j=0;j<4;j++){if(arr[j]==req_order.get(i)){contains=1;hit++;}}
if(contains==1){System.out.println("HIT!!!");}
if(contains==0){
System.out.println("MISS!!!");miss++;
ArrayList<Integer> subList =new ArrayList<Integer>(req_order.subList(0,i));
int min=14;
int replace_index=0;
for(int j=0;j<4;j++){
	int last_index=subList.lastIndexOf(arr[j]);
	if(last_index<min){
			min=last_index;
			replace_index=j;
		}
	}

arr[replace_index]=req_order.get(i);
}		// contains==0
display();
}		//for
lru_ratio=(float)hit/miss;
System.out.println("Hit to miss ratio is "+hit+":"+miss+" which is "+lru_ratio);
System.out.println("\n===================================================================================\n\n\n");
}

}	//PageReplacement


public class Cache{
public static void main(String args[]){
System.out.println("\n\t\t-----------------------------------");
System.out.println("\t\t\tSelf evolving cache");
System.out.println("\t\t-----------------------------------");
System.out.println("\nThis application is a page replacement algorithm for pages in the memory cache to improve the hit to miss ratio\n");

int req_pgs[]=new int[10]; 		//6 7 6 4 1 7 3 8 5 6
req_pgs[0]=2;
req_pgs[1]=3;
req_pgs[2]=1;
req_pgs[3]=2;
req_pgs[4]=3;
req_pgs[5]=5;
req_pgs[6]=8;
req_pgs[7]=1;
req_pgs[8]=2;
req_pgs[9]=3;

PageReplacement c=new PageReplacement();
System.out.println("Our cache initially contains the first four pages\n");
c.display();

System.out.println("Page Replacement algorithm used: FIFO (First in First out) \n");
c.fifo(req_pgs);
System.out.println("Page Replacement algorithm used: LRU (Least Recently Used) \n");
c.lru(req_pgs);
System.out.println("Page Replacement algorithm used: OPR (Optimal Page replacement using Machine LEarning) \n");
c.opr(req_pgs);

System.out.println("\n--------------------------------------------------------------------------");
System.out.println("|   Page Replacement algorithm \t\t |\t Hit to miss ratio\t |");		// 40chars before |
System.out.println("--------------------------------------------------------------------------");
System.out.println("|   First In First Out \t\t\t |\t "+ c.fifo_ratio +"\t\t\t |");
System.out.println("|   Least Recently Used \t\t |\t "+c.lru_ratio+"\t\t\t |");
System.out.println("|   Optimal Page Replacement \t\t |\t "+c.opr_ratio+"\t\t\t |");
System.out.println("--------------------------------------------------------------------------");
}
}
