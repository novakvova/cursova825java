package epicentr.web;


import epicentr.entities.User;
import epicentr.repositories.RoleRepository;
import epicentr.services.StorageService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import epicentr.repositories.UserRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

/**
 * @author Ramesh Fadatare
 *
 */
@Controller
public class AccountConroller
{
    private final StorageService storageService;

    @Autowired
    public AccountConroller(StorageService storageService) {
        this.storageService = storageService;
    }
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String GetRegister()
    {
        return "register";
    }
    @GetMapping("/profile")
    public String GetProfile(Model model)
    {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String u =  a.getName();
        User user = userRepository.findByEmail(u);
        model.addAttribute("email",user.getEmail());
        model.addAttribute("firstName",user.getName());
        model.addAttribute("lastName",user.getLastName());
        model.addAttribute("city",user.getCity());
        model.addAttribute("postOffice",user.getPostOffice());
        model.addAttribute("image",user.getImage());
        return "profile";
    }
    @PostMapping("/profile")
    public String UpdateProfile(User usik)
    {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String u =  a.getName();
        User user = userRepository.findByEmail(u);
        user.setEmail(usik.getEmail());
        user.setName(usik.getName());
        user.setLastName(usik.getLastName());
        user.setCity(usik.getCity());
        user.setPostOffice(usik.getPostOffice());
        userRepository.save(user);
        return "redirect:/profile";
    }
    @PostMapping("/save-image")
    public String UpdateImage(User usik)
    {
        try {
            Authentication a = SecurityContextHolder.getContext().getAuthentication();
            String u =  a.getName();
            User user = userRepository.findByEmail(u);
            byte[] imageByte= Base64.getMimeDecoder().decode(usik.getImage().split(",")[1]);
            Path f = storageService.load("");
            String name = UUID.randomUUID().toString()+".png";
            String rootPath= f.toUri().getPath();
            //String rootPath =  context.getRealPath("resources/");
            System.out.println("---------"+rootPath);
            File dir = new File(rootPath + File.separator );
            if (!dir.exists())
                dir.mkdirs();
            File file = new File(rootPath, name);
            FileUtils.writeByteArrayToFile(file, imageByte);
            user.setImage(name);
            //new FileOutputStream(rootPath+name).write(imageByte);
            userRepository.save(user);
            return "redirect:/profile";
        }catch (Exception e){
            return "redirect:/profile";
        }

    }
    @PostMapping("/register")
    public String Register(User user)
    {
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        User u = new User();
        u.setPassword(pass);
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setLastName(user.getLastName());
        u.setCity(user.getCity());
        u.setPostOffice(user.getPostOffice());
        //Role r = roleRepository.findByName("ROLE_USER");
//        Role r = new Role();
//        r.setId(3);

        u.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        userRepository.save(u);
        //messageRepository.save(message);
        return "redirect:/home";
    }
}