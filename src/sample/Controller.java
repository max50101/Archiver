package sample;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.EncryptionOfImages.Crypter;
import sample.ImageHandler.ImageOpener;
import sample.TextFiles.FileReader;

import javax.imageio.ImageIO;

public class Controller {
    private  String oops=null;
    private File file=null;
    private ImageOpener imageOpener=null;
    private Crypter crypter=null;
    @FXML
    private Button ssss;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Compress;

    @FXML
    private TextArea SourseText;

    @FXML
    private TextArea CompressesText;

    @FXML
    private Button Decompress;

    @FXML
    private Button OpenFile;
    @FXML
    private Button open_image;
    @FXML
    private javafx.scene.image.ImageView image_view;

    @FXML
    private Button bmp_header;
    @FXML
    private Button RGB;

    @FXML
    private Button image_image;
    @FXML
    private TextArea header_reader;
    @FXML
    private Button to_text;

    @FXML
    private TextField insert_arr;

    @FXML
    private Button hamming_button;

    @FXML
    private TextArea output_data_ham;
    @FXML
    private TextArea text_field_image_text;

    @FXML
    private Button compress_image;
    @FXML
    void initialize() {
        final FileChooser fileOpener=new FileChooser();
        OpenFile.setOnAction(event -> {

             file=fileOpener.showOpenDialog(new Stage());
            try {
                oops = FileReader.readFile(file);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            SourseText.setText(FileReader.addFileSize(oops,file));

        });
        Compress.setOnAction(event -> {
            try {
                Archiever.compress(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                oops=FileReader.readFile(file);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            CompressesText.setText(FileReader.addFileSize(oops,file));

        });
        Decompress.setOnAction(event -> {
            try {
                Archiever.decompress(file);
            }catch (IOException e){
                e.printStackTrace();;
            }
            try{
                oops=FileReader.readFile(file);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            CompressesText.setText(FileReader.addFileSize(oops,file));
        });
        open_image.setOnAction(event->{
            try{
                file=fileOpener.showOpenDialog(new Stage());
                imageOpener=new ImageOpener(file);
                image_view.setImage(imageOpener.openImage());
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        });
        bmp_header.setOnAction(event -> {
            File sourse=fileOpener.showOpenDialog(new Stage());
            imageOpener=new ImageOpener(sourse);
            File f=fileOpener.showOpenDialog(new Stage());
            imageOpener.readBMPHeader(f);
            oops=FileReader.readFile(f);
            header_reader.setText(oops);
        });
        RGB.setOnAction(event -> {
            imageOpener.RGBPalitre();
            imageOpener.BiteCuts();
        });
        compress_image.setOnAction(event -> {
            try {
                Archiever.compressImage(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ssss.setOnAction(event -> {
            try {
                Archiever.decompresImage(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        image_image.setOnAction(event -> {
             crypter=new Crypter(fileOpener.showOpenDialog(new Stage()).getPath());
            File f=new File("C:\\Users\\MaksPC\\Documents\\encoded.bmp");
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedImage image=null;
            try {
                image= ImageIO.read(fileOpener.showOpenDialog(new Stage()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            boolean b=crypter.encrypt(crypter.encodeToString(image,"bmp"),"secret.bmp");
//       boolean b=crypter.encrypt("StringForTest","secret.png");
            System.out.println(b);
        });
        to_text.setOnAction(event -> {
            //crypter=new Crypter(fileOpener.showOpenDialog(new Stage()).getPath());
            BufferedImage bufferedImage=crypter.encodeToImage(crypter.deCrypt(fileOpener.showOpenDialog(new Stage()).getPath()));
            File f=new File("sictom.bmp");
            try {
                f.createNewFile();
                ImageIO.write(bufferedImage,"bmp",f);

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        hamming_button.setOnAction(event -> {
            String s=insert_arr.getText();
            int size=s.length();
            char []charbuff=s.toCharArray();
            int []arr=new int[size];
            for(int i=0; i<size; i++) {
                arr[i] = Integer.parseInt(String.valueOf(charbuff[i]));
            }
            String result=Hamming.start(arr);
            output_data_ham.setText(result);
        });

    }
}

