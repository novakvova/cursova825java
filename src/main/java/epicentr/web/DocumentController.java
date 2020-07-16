package epicentr.web;

import epicentr.entities.Message;
import epicentr.repositories.RoleRepository;
import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * @author Ramesh Fadatare
 *
 */
@Controller
public class DocumentController
{

    @Autowired
    ServletContext context;
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
    public String saveDocument(@RequestParam("images[]") MultipartFile[] files,
                               RedirectAttributes redirectAttributes)
    {
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];

            String name = UUID.randomUUID().toString()+"." +FilenameUtils.getExtension(file.getOriginalFilename());;
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath =  context.getRealPath("resources/");
                System.out.println("---------"+rootPath);
                File dir = new File(rootPath + File.separator + "uploads");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

//                logger.info("Server File Location="
//                        + serverFile.getAbsolutePath());

            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        }
        return "redirect:/home";
    }
}
