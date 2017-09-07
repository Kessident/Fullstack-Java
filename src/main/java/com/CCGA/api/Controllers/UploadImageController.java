package com.CCGA.api.Controllers;


//import com.CCGA.api.Repositorys.BookRepo;
//import com.CCGA.api.Repositorys.UserRepo;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.imageio.ImageIO;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.awt.image.BufferedImage;
//import java.awt.image.RenderedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//
//@RestController
//public class UploadImageController {
//
//    @Autowired
//    BookRepo books;
//
//    @Autowired
//    UserRepo users;
//
//    @RequestMapping(value ="/uploadimage", method=RequestMethod.POST)
//    protected void getImage(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
//        //encode image to Base64 String
//        File f = new File("/Users/aprilross/Desktop/bookpic.png");		//change path of image according to you
//        FileInputStream fis = new FileInputStream(f);
//        byte byteArray[] = new byte[(int)f.length()];
//        fis.read(byteArray);
//        String imageString = Base64.encodeBase64String(byteArray);
//
//
//
//
//    }
//
//
//
//}
