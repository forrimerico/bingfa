import Mutil.BankServiceWindows;

public class BankServiceWindowTest {
    public static void main(String[] args){
        // 独占锁

//        final BankServiceWindow bankServiceWindow=new BankServiceWindow();
        // 共享锁
        // 多个线程竞争这两把锁，先竞争到的先执行，另外的等候着
        final BankServiceWindows bankServiceWindow=new BankServiceWindows(2);

        Thread tom=new Thread(){
            public void run(){
                bankServiceWindow.handle();
                System.out.println("tom开始办理业务");
                try {
                    this.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("tom结束办理业务");
                bankServiceWindow.unhandle();
            }
        };
        Thread jim=new Thread(){
            public void run(){
                bankServiceWindow.handle();
                System.out.println("jim开始办理业务");
                try {
                    this.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("jim结束办理业务");
                bankServiceWindow.unhandle();
            }
        };
        Thread jay=new Thread(){
            public void run(){
                bankServiceWindow.handle();
                System.out.println("jay开始办理业务");
                try {
                    this.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("jay结束办理业务");
                bankServiceWindow.unhandle();
            }
        };
        tom.start();
        jim.start();
        jay.start();
    }
}