package sample;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Print implements Printable, ActionListener {


    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Now we perform our rendering */
        BufferedImage img = null;
        BufferedImage img_watermark = null;
        try {
            img = ImageIO.read(new File("E:\\Temp\\Ajith\\0120\\Ajith Motors\\sample\\sample\\images\\logo.png"));
            img_watermark = ImageIO.read(new File("E:\\Temp\\Ajith\\0120\\Ajith Motors\\sample\\sample\\images\\customer-copy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.drawImage(img,10,10,50,50,Color.WHITE,null);
        //g.drawImage(img_watermark,100,159,50,50,Color.WHITE,null);
        g.drawImage(img,10,10,50,50,Color.WHITE,null);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Ajith Motors (Pvt) Ltd.", 70, 25);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        g.drawString("No. 59/A T. B. Jayah Mawatha, Colombo 10.", 70, 40);
        g.drawString("Phone No : 0112123123", 70, 55);
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.drawString("BILL TO",20,80);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        if(!Cashier.cus_name.equals("Mr. ")){g.drawString("Name : "+Cashier.cus_name,20,100);}
        if(!Cashier.mobile.equals("")){g.drawString("Mobile : "+Cashier.mobile,20,115);}
        if(!Cashier.vehicle.equals("")){g.drawString("Vehicle No : "+Cashier.vehicle,120,115);}

        g.drawString("INVOICE",450,25);
        g.drawString("________",450,27);

        g.drawString("Date : "+getDateTime(),390,45);

        g.drawString("Invoice No : "+Cashier.transaction_no,390,60);

        //Table
        for(int i=20;i<=555;){
            g.drawString("_",i,130);
            i+=5;
        }
        for(int i=139;i<=310;){
            g.drawString("|",19,i);
            i+=9;
        }
        g.drawString("Item Code",40,145);
        for(int i=139;i<=310;){
            g.drawString("|",100,i);
            i+=9;
        }
        g.drawString("Description",150,145);
        for(int i=139;i<=310;){
            g.drawString("|",250,i);
            i+=9;
        }
        g.drawString("Quantity",265,145);
        for(int i=139;i<=310;){
            g.drawString("|",310,i);
            i+=9;
        }
        g.drawString("Rate (Rs.)",335,145);
        for(int i=139;i<=310;){
            g.drawString("|",390,i);
            i+=9;
        }
        g.drawString("Amount (Rs.)",415,145);
        for(int i=139;i<=310;){
            g.drawString("|",490,i);
            i+=9;
        }
        g.drawString("Warranty",505,145);
        for(int i=20;i<=555;){
            g.drawString("_",i,150);
            i+=5;
        }
        ObservableList<Cart> carts=Cashier.tableCart.getItems();
        int gap=165;
        for(int i=0;i<carts.size();i++){
            g.drawString(carts.get(i).getItem_code(),50,gap);
            g.drawString(carts.get(i).getDescription(),110,gap);
            g.drawString(carts.get(i).getQuantity(),265,gap);
            printPrice(carts.get(i).getPrice(),380,gap,g);
            printPrice(carts.get(i).getNetprice(),480,gap,g);
            //g.drawString(String.format("%.2f",carts.get(i).getPrice()),320,gap);
            //g.drawString(String.valueOf(String.format("%.2f",carts.get(i).getNetprice())),400,gap);
            gap+=15;
        }
        double sum=0;
        for(int i=0;i<carts.size();i++){
            sum+=carts.get(i).getNetprice();
        }
        for(int i=139;i<=310;){
            g.drawString("|",559,i);
            i+=9;
        }
        for(int i=20;i<=555;){
            g.drawString("_",i,310);
            i+=5;
        }


        g.drawString("Total :",310,326);
        for(int i=319;i<=328;){
            g.drawString("|",390,i);
            i+=9;
        }
        printPrice(sum,480,326,g);
        //g.drawString(String.valueOf(String.format("%.2f",sum)),420,310);
        for(int i=391;i<=486;){
            g.drawString("_",i,328);
            i+=5;
        }
        for(int i=319;i<=328;){
            g.drawString("|",490,i);
            i+=9;
        }
        //g.drawString("Discount :",310,326);
        /*for(int i=321;i<=330;){
            g.drawString("|",390,i);
            i+=9;
        }
        for(int i=391;i<=486;){
            g.drawString("_",i,330);
            i+=5;
        }
        for(int i=321;i<=330;){
            g.drawString("|",490,i);
            i+=9;
        }

        for(int i=339;i<=348;){
            g.drawString("|",390,i);
            i+=9;
        }
        for(int i=391;i<=486;){
            g.drawString("_",i,348);
            i+=5;
        }
        g.drawString("Total :",310,343);
        for(int i=339;i<=348;){
            g.drawString("|",490,i);
            i+=9;
        }*/
        for(int i=391;i<=476;){
            g.setFont(new Font("Times New Roman",Font.BOLD,25));
            g.drawString("_",i,328);
            i+=5;
        }
        g.setFont(new Font("Times New Roman",Font.PLAIN,10));
        g.drawString("Payment Mode(s) :",310,347);
        String payment_method="";
        for(int i=0;i<Payment.payment_methods.size();i++){
            if(i==Payment.payment_methods.size()-1){
                payment_method += Payment.payment_methods.get(i);
            }else {
                payment_method += Payment.payment_methods.get(i) + ",";
            }
        }
        g.drawString(payment_method,395,347);
        g.drawString("_",480,328);
        g.setFont(new Font("Arial",Font.BOLD,7));
        g.drawString("INVOICE IS REQUIRED FOR WARRANTY",220,380);
        g.setFont(new Font("Arial",Font.BOLD,10));
        g.drawString("THANK YOU. COME AGAIN !",220,400);
        /*g.drawString("Total :",310,361);
        for(int i=357;i<=366;){
            g.drawString("|",390,i);
            i+=9;
        }
        for(int i=391;i<=486;){
            g.drawString("_",i,366);
            i+=5;
        }*/
        /*for(int i=391;i<=476;){
            g.setFont(new Font("Times New Roman",Font.BOLD,25));
            g.drawString("_",i,367);
            i+=5;
        }
        g.drawString("_",480,367);*/
        g.setFont(new Font("Times New Roman",Font.PLAIN,10));

        /*for(int i=357;i<=366;){
            g.drawString("|",490,i);
            i+=9;
        }*/
        //END OF TABLE

        g.drawString(".............................",19,342);
        g.drawString("Issued by",34,356);

        g.drawString(Login.uname,110,336);
        g.drawString(".............................",100,342);
        g.drawString("Cashier",116,356);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    public void actionPerformed(ActionEvent e) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                // The job did not successfully complete
            }
        }
    }

    public static void printReceipt() {
        Print print=new Print();
        print.actionPerformed(null);
        /*UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame f = new JFrame("Hello World Printer");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JButton printButton = new JButton("Print Hello World");
        printButton.addActionListener(new Print());
        f.add("Center", printButton);
        f.pack();
        f.setVisible(true);*/
    }

    public String getDateTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a");
        LocalDateTime now = LocalDateTime.now();
        String date=dtf.format(now);
        return date;
    }
    private void printPrice(double price,int x,int y,Graphics g){
        String s=String.format("%.2f",price);
        for(int i=s.length()-1;i>=0;i--){
            g.drawString(String.valueOf(s.charAt(i)),x,y);
            x-=5;
        }
    }
}
