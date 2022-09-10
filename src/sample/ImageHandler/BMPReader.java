package sample.ImageHandler;

import sample.TextFiles.FileReader;

import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;
import java.io.IOException;
import java.io.InputStream;

class BMPDecoder {
    InputStream is;
    int curPos = 0;

    int bitmapOffset;               // starting position of image data

    int width;                              // image width in pixels
    int height;                             // image height in pixels
    short bitsPerPixel;             // 1, 4, 8, or 24 (no color map)
    int compression;                // 0 (none), 1 (8-bit RLE), or 2 (4-bit RLE)
    int actualSizeOfBitmap;
    int scanLineSize;
    int actualColorsUsed;
    StringBuilder sbHeader=new StringBuilder();
    StringBuilder rgbPalitre=new StringBuilder();

    byte r[], g[], b[];             // color palette
    int noOfEntries;

    byte[] byteData;                // Unpacked data
    int[] intData;                     // Unpacked data
    boolean topDown;


    private int readInt() throws IOException {
        int b1 = is.read();
        int b2 = is.read();
        int b3 = is.read();
        int b4 = is.read();
        curPos += 4;
        return ((b4 << 24) + (b3 << 16) + (b2 << 8) + (b1 << 0));
    }


    private short readShort() throws IOException {
        int b1 = is.read();
        int b2 = is.read();
        curPos += 2;
        return (short)((b2 << 8) + b1);
    }


    void getFileHeader()  throws IOException, Exception {
        // Actual contents (14 bytes):
        short fileType = 0x4d42;// always "BM"
        int fileSize;                   // size of file in bytes
        short reserved1 = 0;    // always 0
        short reserved2 = 0;    // always 0

        fileType = readShort();
        if (fileType != 0x4d42)
            throw new Exception("Not a BMP file");  // wrong file type
        fileSize = readInt();
        reserved1 = readShort();
        reserved2 = readShort();
        bitmapOffset = readInt();
    }

    void getBitmapHeader() throws IOException {

        // Actual contents (40 bytes):
        int size;                               // size of this header in bytes
        short planes;                   // no. of color planes: always 1
        int sizeOfBitmap;               // size of bitmap in bytes (may be 0: if so, calculate)
        int horzResolution;             // horizontal resolution, pixels/meter (may be 0)
        int vertResolution;             // vertical resolution, pixels/meter (may be 0)
        int colorsUsed;                 // no. of colors in palette (if 0, calculate)
        int colorsImportant;    // no. of important colors (appear first in palette) (0 means all are important)
        int noOfPixels;

        size = readInt();
        width = readInt();
        height = readInt();
        planes = readShort();
        bitsPerPixel = readShort();
        compression = readInt();
        sizeOfBitmap = readInt();
        horzResolution = readInt();
        vertResolution = readInt();
        colorsUsed = readInt();
        colorsImportant = readInt();
        if (bitsPerPixel==24)
            colorsUsed = colorsImportant = 0;

        topDown = (height < 0);
        if (topDown) height = -height;
        noOfPixels = width * height;

        // Scan line is padded with zeroes to be a multiple of four bytes
        scanLineSize = ((width * bitsPerPixel + 31) / 32) * 4;

        actualSizeOfBitmap = scanLineSize * height;

        if (colorsUsed != 0)
            actualColorsUsed = colorsUsed;
        else
            // a value of 0 means we determine this based on the bits per pixel
            if (bitsPerPixel < 16)
                actualColorsUsed = 1 << bitsPerPixel;
            else
                actualColorsUsed = 0;   // no palette
            sbHeader.append("BMP_Reader")
                    .append("\n size: "+size)
                    .append("\n width: ")
                    .append(width)
                    .append("\n height: "+height)
                    .append("\n planec: "+planes)
                    .append("\nbitsPerPixel: "+bitsPerPixel)
                    .append("\ncompression: "+compression)
                    .append("\n size of BMP: "+sizeOfBitmap)
                    .append("\n horzResolution: "+horzResolution)
                    .append("\n vertResolution: "+vertResolution)
                    .append("\n colorUsed: "+colorsUsed)
                    .append("\n colorsImportant: "+colorsImportant);
    }

    void getPalette() throws IOException {
        noOfEntries = actualColorsUsed;
        if (noOfEntries>0) {
            r = new byte[noOfEntries];
            g = new byte[noOfEntries];
            b = new byte[noOfEntries];

            int reserved;
            for (int i = 0; i < noOfEntries; i++) {
                b[i] = (byte) is.read();
                g[i] = (byte) is.read();
                r[i] = (byte) is.read();
                reserved = is.read();
                curPos += 4;
            }
        }

    }



    void unpack(byte[] rawData, int rawOffset, int bpp, byte[] byteData, int byteOffset, int w) throws Exception {
        int j = byteOffset;
        int k = rawOffset;
        byte mask;
        int pixPerByte;

        switch (bpp) {
            case 1: mask = (byte)0x01; pixPerByte = 8; break;
            case 4: mask = (byte)0x0f; pixPerByte = 2; break;
            case 8: mask = (byte)0xff; pixPerByte = 1; break;
            default:
                throw new Exception("Unsupported bits-per-pixel value: " + bpp);
        }

        for (int i = 0;;) {
            int shift = 8 - bpp;
            for (int ii = 0; ii < pixPerByte; ii++) {
                byte br = rawData[k];
                br >>= shift;
                byteData[j] = (byte)(br & mask);
                //System.out.println("Setting byteData[" + j + "]=" + Test.byteToHex(byteData[j]));
                j++;
                i++;
                if (i == w) return;
                shift -= bpp;
            }
            k++;
        }
    }

    void unpack24(byte[] rawData, int rawOffset, int[] intData, int intOffset, int w) {
        int j = intOffset;
        int k = rawOffset;
        int mask = 0xff;
        for (int i = 0; i < w; i++) {
            int b0 = (((int)(rawData[k++])) & mask);
            int b1 = (((int)(rawData[k++])) & mask) << 8;
            int b2 = (((int)(rawData[k++])) & mask) << 16;
            intData[j] = 0xff000000 | b0 | b1 | b2;
            j++;
        }
    }

    void unpack32(byte[] rawData, int rawOffset, int[] intData, int intOffset, int w) {
        int j = intOffset;
        int k = rawOffset;
        int mask = 0xff;
        for (int i = 0; i < w; i++) {
            int b0 = (((int)(rawData[k++])) & mask);
            int b1 = (((int)(rawData[k++])) & mask) << 8;
            int b2 = (((int)(rawData[k++])) & mask) << 16;
            int b3 = (((int)(rawData[k++])) & mask) << 24; // this gets ignored!
            intData[j] = 0xff000000 | b0 | b1 | b2;
            j++;
        }
    }

    void getPixelData() throws IOException, Exception {
        byte[] rawData;                 // the raw unpacked data

        // Skip to the start of the bitmap data (if we are not already there)
        long skip = bitmapOffset - curPos;
        if (skip > 0) {
            is.skip(skip);
            curPos += skip;
        }

        int len = scanLineSize;
        if (bitsPerPixel > 8)
            intData = new int[width * height];
        else
            byteData = new byte[width * height];
        rawData = new byte[actualSizeOfBitmap];
        int rawOffset = 0;
        int offset = (height - 1) * width;
        for (int i = height - 1; i >= 0; i--) {
            int n = is.read(rawData, rawOffset, len);
            if (n < len) throw new Exception("Scan line ended prematurely after " + n + " bytes");
            if (bitsPerPixel==24)
                unpack24(rawData, rawOffset, intData, offset, width);
            else if (bitsPerPixel==32)
                unpack32( rawData, rawOffset, intData, offset, width);
            else // 8-bits or less
                unpack(rawData, rawOffset, bitsPerPixel, byteData, offset, width);
            rawOffset += len;
            offset -= width;
        }
    }


    public void read(InputStream is) throws IOException, Exception {
        this.is = is;
        getFileHeader();
        getBitmapHeader();
        if (compression!=0)
            throw new Exception("Compression not supported");
        getPalette();
        getPixelData();
    }


    public MemoryImageSource makeImageSource(int i) {
        ColorModel cm=null ;
        MemoryImageSource mis;

        if (noOfEntries==0 && bitsPerPixel==24) {
            if(i==1)
            // There is a color palette; create an IndexColorModel
            cm = new IndexColorModel(8, 20, r, new byte[]{0}, new byte[]{0});
            else if(i==2){
                cm = new IndexColorModel(8, 20, new byte[]{0}, g, new byte[]{0});
            }else if(i==3){
                cm = new IndexColorModel(8, 20, new byte[]{0}, new byte[]{0}, b);

            }
        } else {
            // There is no palette; use the default RGB color model
            cm = ColorModel.getRGBdefault();
        }

        // Create MemoryImageSource

        if (bitsPerPixel > 8) {
            // use one int per pixel
            mis = new MemoryImageSource(width,
                    height, cm, intData, 0, width);
        } else {
            // use one byte per pixel
            mis = new MemoryImageSource(width,
                    height, cm, byteData, 0, width);
        }

        return mis;      // this can be used by Component.createImage()
    }
    public  String getBitMapHeader(){
        return sbHeader.toString();
    }
}