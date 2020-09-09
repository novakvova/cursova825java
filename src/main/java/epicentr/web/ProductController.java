package epicentr.web;

import epicentr.entities.Product;
import epicentr.entities.ProductImages;
import epicentr.repositories.CategoryRepository;
import epicentr.repositories.ProductRespository;
import epicentr.services.StorageService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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

    @GetMapping("/")
    public String list() {
        return "user/products/view";
    }

    //Old on Vue.js
//    @GetMapping("/product-view/{product_id}")
//    public String view(@PathVariable("product_id") int product_id,Model model){
//        model.addAttribute("product_id",product_id);
//        return "user/products/view_detail";
//    }
    //New on HTML
    @GetMapping("/product-view/{product_id}")
    public String view(@PathVariable("product_id") Long product_id, Model model) {
        model.addAttribute("product", productRespository.findById(product_id).get());
        return "user/products/view_detailHTML";
    }

    @GetMapping("/product-add")
    public String add(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/products/create";
    }

    @PostMapping("/product-add")
    public String addProduct(
            @RequestParam(value="images", required = false) String[] files,
            Product model) {

        //String f = files[0];
        Product pr = new Product();
        pr.setProductImages(model.getProductImages());
        pr.setName(model.getName());
        pr.setPrice(model.getPrice());
        pr.setDiscount(model.getDiscount());
        pr.setCategory(categoryRepository.findById(model.getCategory().getId()).get());
        pr.setDescription(model.getDescription());
        List<ProductImages> prImages = new ArrayList<ProductImages>();
        for (int i = 0; i < files.length; i++) {
            //Without cropper, simple input file
//            MultipartFile file = files[i];
//
//            String name = UUID.randomUUID().toString()+"." + FilenameUtils.getExtension(file.getOriginalFilename());;
//            try {
//                byte[] bytes = file.getBytes();
//
//                Path f = storageService.load("");
//                String rootPath= f.toUri().getPath();
//                //String rootPath =  context.getRealPath("resources/");
//                System.out.println("---------"+rootPath);
//                File dir = new File(rootPath + File.separator );
//                if (!dir.exists())
//                    dir.mkdirs();
//
//                // Create the file on server
//                File serverFile = new File(dir.getAbsolutePath()
//                        + File.separator + name);
//                BufferedOutputStream stream = new BufferedOutputStream(
//                        new FileOutputStream(serverFile));
//                stream.write(bytes);
//                stream.close();
//                ProductImages prImage = new ProductImages();
//                prImage.setImage_name(name);
//                prImage.setProduct(pr);
//                prImages.add(prImage);
//
//
//
////                logger.info("Server File Location="
////                        + serverFile.getAbsolutePath());
//
//            } catch (Exception e) {
//                return "You failed to upload " + name + " => " + e.getMessage();
//            }
            //base 64 saving (with cropper)
            try {
                byte[] imageByte = Base64.getMimeDecoder().decode(files[i].split(",")[1]);
                Path f = storageService.load("");
                String name = UUID.randomUUID().toString() + ".png";
                String rootPath = f.toUri().getPath();
                //String rootPath =  context.getRealPath("resources/");
                System.out.println("---------" + rootPath);
                File dir = new File(rootPath + File.separator);
                if (!dir.exists())
                    dir.mkdirs();
                File file = new File(rootPath, name);
                FileUtils.writeByteArrayToFile(file, imageByte);
                ProductImages prImage = new ProductImages();
                prImage.setImage_name(name);
                prImage.setProduct(pr);
                prImages.add(prImage);
            }catch (Exception e){
                return "redirect:/";
            }
        }
        pr.setProductImages(prImages);
        productRespository.save(pr);
        return "redirect:/";
    }
}

