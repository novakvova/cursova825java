package epicentr.web;

import epicentr.entities.Document;
import epicentr.entities.DocumentFile;
import epicentr.entities.Message;
import epicentr.repositories.*;
import epicentr.services.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Ramesh Fadatare
 *
 */
import java.util.List;

@Controller
public class DocumentController
{

    @Autowired
    ServletContext context;
    private final StorageService storageService;

    @Autowired
    public DocumentController(StorageService storageService) {
        this.storageService = storageService;
    }
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentFileRepository documentFileRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping("/document/create")
    public String document()
    {
        return "documents";
    }
    @GetMapping("/documents")
    public String documents(Model model)
    {
        model.addAttribute("documents",documentRepository.findAll());
        return "allDocuments";
    }

    @PostMapping("/document")
    public String saveDocument(@RequestParam("images[]") MultipartFile[] files,
                               RedirectAttributes redirectAttributes, Document model)
    {
        Document doc = new Document();
        doc.setName(model.getName());
        List <DocumentFile> docFiles = new ArrayList<DocumentFile>();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];

            String name = UUID.randomUUID().toString()+"." +FilenameUtils.getExtension(file.getOriginalFilename());;
            try {
                byte[] bytes = file.getBytes();

                Path f = storageService.load("");
                String rootPath= f.toUri().getPath();
                //String rootPath =  context.getRealPath("resources/");
                System.out.println("---------"+rootPath);
                File dir = new File(rootPath + File.separator );
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                DocumentFile docFile = new DocumentFile();
                docFile.setFile_name(name);
                docFile.setDocument(doc);
                docFiles.add(docFile);



//                logger.info("Server File Location="
//                        + serverFile.getAbsolutePath());

            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        }
        doc.setDocumentFiles(docFiles);
        documentRepository.save(doc);
        return "redirect:/home";
    }
}
