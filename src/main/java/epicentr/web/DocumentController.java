package epicentr.web;

import epicentr.entities.Message;
import epicentr.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import epicentr.repositories.MessageRepository;
import epicentr.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Ramesh Fadatare
 *
 */
@Controller
public class DocumentController
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping("/document")
    public String document()
    {
        return "documents";
    }

    @PostMapping("/document")
    public String saveDocument(@RequestParam("images[]") MultipartFile[] images,
                               RedirectAttributes redirectAttributes)
    {
        return "redirect:/home";
    }
}
