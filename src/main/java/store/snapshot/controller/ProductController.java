package store.snapshot.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import store.snapshot.model.UserDetailsAdapter;
import store.snapshot.service.IProductService;
import store.snapshot.service.IValidationService;
import store.snapshot.model.Product;

@Controller
@RequestMapping("products")
@SessionAttributes("country_code")
public class ProductController {
	private static final String P_OK = "main";
	
	@Autowired 
	private IProductService productService;
	@Autowired
	private IValidationService validationService;
	//This method is called by AJAX
	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	public @ResponseBody ResponseCode addNewProduct(@ModelAttribute("product") @Valid AddProductForm form, 
													  BindingResult result, MultipartHttpServletRequest request, 
													  HttpServletResponse response, Principal principal) 
													 
	{
		final UserDetailsAdapter currentUser = (UserDetailsAdapter) ((Authentication) principal).getPrincipal();
		
		ResponseCode res = new ResponseCode();
		Map<String, String> validationErrors = validationService.loadValidationErrors();
		ValidationUtils.rejectIfEmpty(result, ProductEnum.TITLE, validationErrors.get(ProductEnum.TITLE));
		ValidationUtils.rejectIfEmpty(result, ProductEnum.DESRIPTION, validationErrors.get(ProductEnum.DESRIPTION));
		String countryCode = request.getSession().getAttribute(ProductEnum.COUNTRY_CODE).toString();
		MultipartFile imgFile = form.getImg_file();
		String title = form.getTitle();
		String description = form.getDescription();
		boolean successfullyCreated = false;
		
		if(!result.hasErrors()) {
			Product product = new Product(); 		
			product.setDescription(description);
			product.setTitle(title);
			product.setImg_url(imgFile.getOriginalFilename());
			product.setCountry_code(countryCode);
			product.setUserId(currentUser.getId());
			successfullyCreated = productService.addProduct(product, imgFile);
		}
		if (successfullyCreated) {
	 			res.setStatus("SUCCESS");
	 			res.setResult(P_OK);
		} else {
			res.setStatus("ERROR");
			res.setResult(result.getAllErrors());
		}		
		return res;
	}
	
	@RequestMapping("removeProduct")
	public String singUp(@RequestParam("productId") int productId, HttpServletRequest request) {
		Product product = productService.getProduct(productId);
		productService.removeProduct(product);				
		return (P_OK);
	}

	...
}
