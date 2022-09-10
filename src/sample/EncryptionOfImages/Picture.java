package sample.EncryptionOfImages;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

 final class Picture implements ActionListener
{
    private BufferedImage image;    // the rasterized image
    private JFrame frame;           // on-screen view
    private String filename;        // name of file

    public Picture(int w, int h)
    {
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        filename = w + "-by-" + h;
    }

    public String toString(){
        return filename;
    }
    /**
     * Create a picture by reading in a .png, .gif, or .jpg from
     * the given filename or URL name.
     */
    public Picture(String filename)
    {
        this.filename = filename;
        try {
            // try to read from file in working directory
            File file = new File(filename);
            if (file.isFile()) {
                image = ImageIO.read(file);
            }

            // now try to read from file in same directory as this .class file
            else {
                URL url = getClass().getResource(filename);
                if (url == null) { url = new URL(filename); }
                image = ImageIO.read(url);
            }
        }
        catch (IOException e) {
            // e.printStackTrace();
            throw new RuntimeException("Could not open file: " + filename);
        }

        // check that image was read in
        if (image == null) {
            throw new RuntimeException("Invalid image file: " + filename);
        }
    }

    /**
     * Create a picture by reading in a .png, .gif, or .jpg from a File.
     */
    public Picture(File file)
    {
        try { image = ImageIO.read(file); }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not open file: " + file);
        }
        if (image == null) {
            throw new RuntimeException("Invalid image file: " + file);
        }
    }





    /**
     * Return the height of the picture (in pixels).
     */
    public int height()
    {
        return image.getHeight(null);
    }

    /**
     * Return the width of the picture (in pixels).
     */
    public int width()
    {
        return image.getWidth(null);
    }

    /**
     * Return the Color of pixel (i, j).
     */
    public Color get(int i, int j)
    {
        return new Color(image.getRGB(i, j));
    }

    /**
     * Set the Color of pixel (i, j) to c.
     */
    public void set(int i, int j, Color c) {
        if (c == null) { throw new RuntimeException("can't set Color to null"); }
        image.setRGB(i, j, c.getRGB());
    }

    /**
     * Save the picture to a file in a standard image format.
     * The filetype must be .png or .jpg.
     */
    public void save(String name)
    {
        save(new File(name));
    }

    /**
     * Save the picture to a file in a standard image format.
     */
    public void save(File file)
    {
        this.filename = file.getName();
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        suffix = suffix.toLowerCase();
        if (suffix.equals("jpg") || suffix.equals("png")||suffix.equals("bmp"))
        {
            try { ImageIO.write(image, suffix, file); }
            catch (IOException e) { e.printStackTrace(); }
        }
        else
        {
            System.out.println("Error: filename must end in .jpg or .png");
        }
    }

    /**
     * Opens a save dialog box when the user selects "Save As" from the menu.
     */
    public void actionPerformed(ActionEvent e)
    {
        FileDialog chooser = new FileDialog(frame,
                "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        if (chooser.getFile() != null)
        {
            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }
}