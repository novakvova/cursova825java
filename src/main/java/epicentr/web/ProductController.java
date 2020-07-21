package epicentr.web;

import epicentr.entities.Product;
import epicentr.repositories.RoleRepository;
import epicentr.repositories.UserRepository;
import epicentr.services.ProductService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Ramesh Fadatare
 *
 */
@Controller
public class ProductController
{
    @GetMapping("/product")
    public String product()
    {
        return "products";
    }
}
