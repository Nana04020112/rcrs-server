public  class HelloWorld{
public static void main(String[] args) {
    int age =18;
    double score=100.0;
    boolean isStudent=true;
    String name="Nana";
    if(age>=18){
        System.out.println(name+"是成年人");
    }else{
        System.out.println(name+"不是成年人");
    }
    switch((int)score/10){
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
            System.out.println("不及格");
            break;
        case 6:
        case 7:
        case 8:
            System.out.println("良好");
            break;
        case 9:
        case 10:
            System.out.println("优秀");
            break;
    }
    System.out.println("循环输出1~5");
    for(int i=1;i<=5;i++){
        System.out.println(i+" ");
    }
    double avg=calculate(88,99);
    System.out.println("这两节课程平均分："+avg);

}
public static double calculate(int a,int b){
    return (a+b)/2;
}
}