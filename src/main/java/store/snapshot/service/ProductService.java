package store.snapshot.service;

import java.util.HashSet;
import java.util.Set;

import store.snapshot.dao.IGenericDao;
import store.snapshot.model.Product;

import org.springframework.web.multipart.MultipartFile;

public class ProductService implements IProductService {
	@Autowired 
	private IGenericDao<Product> productDao;
	@Autowired 
	private IImageService imageService;
	
	public List<Product> getAllProducts() {
		return productDao.getProductList();
	}

	public boolean addProduct(Product product, MultipartFile img) {
		boolean res = false; 
		if (!img.isEmpty()) {
			Set<String> allowedImageExtensions = new HashSet<String>();
			allowedImageExtensions.add("png");
			allowedImageExtensions.add("jpg");
			allowedImageExtensions.add("JPG");
			allowedImageExtensions.add("jpeg");
			allowedImageExtensions.add("JPEG");
			allowedImageExtensions.add("gif");
			imageService.setImgSizeToDefault();
			res = imageService.saveImg(img, IMG_PATH, allowedImageExtensions);
		}
		res = productDao.create(product);
		return res;
	}

	public void removeProduct(Product product) {
		productDao.delete(product);
	}

	public Product getProduct(int prodId) {
		return productDao.getById(prodId);
	}
}
