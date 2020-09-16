package epicentr.web;

import epicentr.entities.User;
import epicentr.helpers.EmailUtil;
import epicentr.models.ForgotPasswordUserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import epicentr.repositories.UserRepository;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
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
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping("/changepassword")
    public String change( Model model)
    {
        return "user/auth/changepassword";
    }
    @PostMapping("/changepassword")
    public String chPaswd(ForgotPasswordUserViewModel user)
    {
        Integer id = Integer.parseInt(user.getCode());
        User usik = userRepository.getOne(id);
        usik.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(usik);
        return "redirect:/login";
    }
    @GetMapping("/forgotpassword")
    public String forgot(Model model)
    {
        return "user/auth/forgotpassword";
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