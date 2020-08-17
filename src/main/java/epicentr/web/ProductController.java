package epicentr.web;

import epicentr.entities.Document;
import epicentr.entities.DocumentFile;
import epicentr.entities.Product;
import epicentr.entities.ProductImages;
import epicentr.repositories.CategoryRepository;
import epicentr.repositories.ProductRespository;
import epicentr.repositories.UserRepository;
import epicentr.services.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class ProductController {
    @Autowired
    private ProductRespository productRespository;
    @Autowired
    private CategoryRepository categoryRepository;
    private final StorageService storageService;

    @Autowired
    public ProductController(StorageService storageService) {
        this.storageService = storageService;
    }
    @GetMapping("/products")
    public String list(){
        return "products";
    }
    //Old on Vue.js
//    @GetMapping("/product-view/{product_id}")
//    public String view(@PathVariable("product_id") int product_id,Model model){
//        model.addAttribute("product_id",product_id);
//        return "productview";
//    }
    //New on HTML
    @GetMapping("/product-view/{product_id}")
    public String view(@PathVariable("product_id") Long product_id,Model model){
        model.addAttribute("product",productRespository.findById(product_id).get());
        return "productviewHTML";
    }
    @GetMapping("/product-add")
    public String add(Model model){
        model.addAttribute("categories",categoryRepository.findAll());
        return "addproduct";
    }
    @PostMapping("/product-add")
    public String addProduct(@RequestParam("images[]") MultipartFile[] files,
                             RedirectAttributes redirectAttributes, Product model){
        Product pr = new Product();
        pr.setName(model.getName());
        pr.setPrice(model.getPrice());
        pr.setDiscount(model.getDiscount());
        pr.setCategory(categoryRepository.findById(model.getCategory().getId()).get());
        pr.setDescription(model.getDescription());
        List<ProductImages> prImages = new ArrayList<ProductImages>();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];

            String name = UUID.randomUUID().toString()+"." + FilenameUtils.getExtension(file.getOriginalFilename());;
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
                ProductImages prImage = new ProductImages();
                prImage.setImage_name(name);
                prImage.setProduct(pr);
                prImages.add(prImage);



//                logger.info("Server File Location="
//                        + serverFile.getAbsolutePath());

            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        }
        pr.setProductImages(prImages);
        productRespository.save(pr);
        return "redirect:/products";
    }
}

