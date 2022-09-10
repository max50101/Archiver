package sample.ImageHandler;

import javafx.scene.image.Image;
import sample.TextFiles.FileReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageOpener {
    private File sourseFile;
    private InputStream is;
    private BMPDecoder bmpDecoder;
    private FileReader fileReader;
    public ImageOpener(File file){
        sourseFile=file;
        try {
            is=new FileInputStream(file.getPath());
            fileReader=new FileReader(file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bmpDecoder=new BMPDecoder();
    }
    public Image openImage(){
        return new Image(sourseFile.toURI().toString());
    }
    public void readBMPHeader(File fileName){
        try {
            bmpDecoder.read(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fileReader.writeFile(fileName.getPath(),bmpDecoder.getBitMapHeader());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void BiteCuts() {
        BufferedImage img0 = null;
        BufferedImage img1 = null;
        BufferedImage img2 = null;
        BufferedImage img3 = null;
        BufferedImage img4 = null;
        BufferedImage img5 = null;
        BufferedImage img6 = null;
        BufferedImage img7 = null;
        try {
            img0 = ImageIO.read(sourseFile);
            img1 = ImageIO.read(sourseFile);
            img2 = ImageIO.read(sourseFile);
            img3 = ImageIO.read(sourseFile);
            img4 = ImageIO.read(sourseFile);
            img5 = ImageIO.read(sourseFile);
            img6 = ImageIO.read(sourseFile);
            img7 = ImageIO.read(sourseFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = img0.getWidth();
        int height = img0.getHeight();
        for (int y= 0; y < height; y++) {
            for (int x = 0; x < width;x++) {
                int p = img0.getRGB(x, y);
                int p1 = img1.getRGB(x, y);
                int p2 = img2.getRGB(x, y);
                int p3 = img3.getRGB(x, y);
                int p4 = img4.getRGB(x, y);
                int p5 = img5.getRGB(x, y);
                int p6 = img6.getRGB(x, y);
                int p7 = img7.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = ((p >> 16) & 0xff) & 0b10000000;int g = ((p >> 8) & 0xff) & 0b10000000;int b = (p & 0xff) & 0b10000000;
                int r1 = ((p1 >> 16) & 0xff)&0b01000000;int g1 = ((p1 >> 8) & 0xff)*0b01000000;int b1 = p1 & 0xff&0b01000000;
                int r2 = ((p >> 16) & 0xff)&0b00100000;int g2 = (p >> 8) & 0xff&0b00100000;int b2 = p & 0xff&0b00100000;
                int r3 = ((p >> 16) & 0xff)&0b00010000;int g3 = (p >> 8) & 0xff&0b00010000;int b3 = p & 0xff&0b00010000;
                int r4 = ((p >> 16) & 0xff)&0b00001000;int g4 = (p >> 8) & 0xff&0b00001000;int b4 = p & 0xff&0b00001000;
                int r5 = ((p >> 16) & 0xff)&0b00000100;int g5 = (p >> 8) & 0xff&0b00000100;int b5 = p & 0xff&0b00000100;
                int r6 = ((p >> 16) & 0xff)&0b00000010;int g6 = (p >> 8) & 0xff&0b00000010;int b6 = p & 0xff&0b00000010;
                int r7 = ((p >> 16) & 0xff)&0b00000001;int g7 = (p >> 8) & 0xff&0b00000001;int b7 = p & 0xff&0b00000001;
                p = (a << 24) | (r << 16) | (g << 8) | b;
                p1 = (a << 24) | (r1 << 16) | (g1 << 8) | b1;
                p2 = (a << 24) | (r2 << 16) | (g2 << 8) | b2;
                p3 = (a << 24) | (r3 << 16) | (g3 << 8) | b3;
                p4 = (a << 24) | (r4 << 16) | (g4 << 8) | b4;
                p5 = (a << 24) | (r5 << 16) | (g5 << 8) | b5;
                p6 = (a << 24) | (r6 << 16) | (g6 << 8) | b6;
                p7 = (a << 24) | (r7 << 16) | (g7 << 8) | b7;
                img0.setRGB(x, y, p);
                img1.setRGB(x,y,p1);
                img2.setRGB(x,y,p2);
                img3.setRGB(x,y,p3);
                img4.setRGB(x,y,p4);
                img5.setRGB(x,y,p5);
                img6.setRGB(x,y,p6);
                img7.setRGB(x,y,p7);

            }
        }
        writeFile(img0,1);
        writeFile(img1,2);
        writeFile(img2,3);
        writeFile(img3,4);
        writeFile(img4,5);
        writeFile(img5,6);
        writeFile(img6,7);
        writeFile(img7,8);
        }
        public void  RGBPalitre() {
        BufferedImage img0 = null;
        BufferedImage img1 = null;
        BufferedImage img2 = null;


        try {
            img0 = ImageIO.read(sourseFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            img1 = ImageIO.read(sourseFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            img2 = ImageIO.read(sourseFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = img0.getWidth();
        int height = img0.getHeight();
        File f = null;
        for (int i = 0; i < 3; i++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if(i==0) {
                        int p = img0.getRGB(x, y);
                        int a = (p >> 24) & 0xff;
                        int r = (p >> 16) & 0xff;

                        // set new RGB
                        // keeping the r value same as in original
                        // image and setting g and b as 0.
                        p = (a << 24) | (r << 16) | (0 << 8) | 0;

                        img0.setRGB(x, y, p);
                    }
                    if(i==1){

                        int p = img1.getRGB(x,y);

                        int a = (p>>24)&0xff;
                        int g = (p>>8)&0xff;

                        // set new RGB
                        // keeping the g value same as in original
                        // image and setting r and b as 0.
                        p = (a<<24) | (0<<16) | (g<<8) | 0;

                        img1.setRGB(x, y, p);
                    }
                    if(i==2){

                        int p = img2.getRGB(x,y);

                        int a = (p>>24)&0xff;
                        int b = p&0xff;

                        // set new RGB
                        // keeping the b value same as in original
                        // image and setting r and g as 0.
                        p = (a<<24) | (0<<16) | (0<<8) | b;

                        img2.setRGB(x, y, p);
                    }
                }
                try {
                    f = new File("C:\\Users\\MaksPC\\Documents\\image"+i+".bmp");
                    f.createNewFile();
                    if(i==0) {
                        ImageIO.write(img0 , "bmp", f);
                    }
                    if(i==1){
                        ImageIO.write(img1,"bmp",f);
                    }
                    if(i==2){
                        ImageIO.write(img2,"bmp",f);
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            }

        }
    }

    private void writeFile(BufferedImage img,int i){

        File f = null;
        try {
            f = new File("C:\\Users\\MaksPC\\Documents\\imageByteCuts"+i+".bmp");
            f.createNewFile();
            ImageIO.write(img, "bmp", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}





