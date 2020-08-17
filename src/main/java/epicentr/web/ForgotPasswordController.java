package epicentr.web;

import epicentr.entities.Message;
import epicentr.entities.User;
import epicentr.helpers.EmailUtil;
import epicentr.models.ForgotPasswordUserViewModel;
import epicentr.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import epicentr.repositories.MessageRepository;
import epicentr.repositories.UserRepository;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Properties;

//import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_ENCODING;


/**
 * @author Ramesh Fadatare
 *
 */
@Controller
public class ForgotPasswordController
{

    static BASE64Encoder enc = new BASE64Encoder();
    static BASE64Decoder dec = new BASE64Decoder();
    @Autowired
    private UserRepository userRepository;


    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping("/changepassword")
    public String change( Model model)
    {
        return "changepassword";
    }
    @PostMapping("/changepassword")
    public String chPaswd(ForgotPasswordUserViewModel user)
    {
        String id = base64decode(user.getCode());
        Optional<User> usik = userRepository.findById(Integer.parseInt(id));
        usik.get().setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(usik.get());
        return "redirect:/login";
    }
    @GetMapping("/forgotpassword")
    public String forgot(Model model)
    {
        return "forgotpassword";
    }

    @PostMapping("/forgotpassword")
    public String sendCode(User user)
    {
        User usik = userRepository.findByEmail(user.getEmail());
        String c = base64encode(usik.getId().toString());

        String emailID = user.getEmail();

        Properties props = System.getProperties();

        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("epicentrik22@gmail.com", "Qwerty-1");
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, emailID,"Forgot password", "Link : http://localhost:8082/changepassword#/ your code :  "+c);
        return "redirect:/changepassword";
    }


    public static String base64encode(String text) {
            return text;
    }//base64encode

    public static String base64decode(String text) {
            return new String(text);
    }//base64decode
}