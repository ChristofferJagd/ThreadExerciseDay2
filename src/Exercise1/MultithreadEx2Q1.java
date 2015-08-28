/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christoffer
 */
public class MultithreadEx2Q1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Avilable Processors: " + Runtime.getRuntime().availableProcessors());

        int result = 0;
        MultithreadEx2Q1 task1 = new MultithreadEx2Q1();
        myThreads t1 = new myThreads("https://fronter.com/cphbusiness/design_images/images.php/Classic/login/fronter_big-logo.png");
        myThreads t2 = new myThreads("https://fronter.com/cphbusiness/design_images/images.php/Classic/login/folder-image-transp.png");
        myThreads t3 = new myThreads("https://fronter.com/volY12-cache/cache/img/design_images/Classic/other_images/button_bg.png");
        
        long start = System.nanoTime();
        ExecutorService exe = Executors.newFixedThreadPool(3);
        exe.execute(t1);
        exe.execute(t2);
        exe.execute(t3);

        exe.shutdown();
        exe.awaitTermination(10, TimeUnit.SECONDS);

        
         long end = System.nanoTime();
        System.out.println("Time parallel: "+(end-start));
        
        
        System.out.println("Sum 1: " + t1.getSum());
        System.out.println("Sum 2: " + t2.getSum());
        System.out.println("Sum 3: " + t3.getSum());
        int i = t1.getSum() + t2.getSum() + t3.getSum();
        System.out.println("Total sum: " + i);
        

        long start2 = System.nanoTime();
        t1.run();
        t2.run();
        t3.run();

       

        long end2 = System.nanoTime();
        System.out.println("Time Sequental: "+(end2-start2));
       

    }

    protected static byte[] getBytesFromUrl(String url) {

        ByteArrayOutputStream bis = new ByteArrayOutputStream();
        try {
            InputStream is = new URL(url).openStream();
            byte[] bytebuff = new byte[4096];
            int read;
            while ((read = is.read(bytebuff)) > 0) {
                bis.write(bytebuff, 0, read);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return bis.toByteArray();
    }

    public static class myThreads implements Runnable {

        private String url;
        public int sum;
        byte[] ba;
        InputStream input;

        public myThreads(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            ba = getBytesFromUrl(url);
            for (int i = 0; i < ba.length; i++) {
                sum += ba[i];
            }

        }

        public int getSum() {
            return sum;
        }
    }

}
