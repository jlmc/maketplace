package org.xine.marketplace.frontend.views.controller;

import org.xine.marketplace.business.services.ProductService;
import org.xine.marketplace.frontend.views.util.jsf.FacesUtil;
import org.xine.marketplace.model.entities.Category;
import org.xine.marketplace.model.entities.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The Class ProductSaverBean.
 */
@Named
@ViewScoped
public class ProductSaverBean implements Serializable {

	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The product. */
    private Product product;

    /** The category master. */
    private Category categoryMaster;

    /** The root categorys. */
    private List<Category> rootCategorys;

    /** The categories. */
    private List<Category> categories;

    /** The service. */
    @Default
    @Inject
    private ProductService service;

    /**
     * Initialize.
     */
    @PostConstruct
    public void initialize() {
    	 System.out.println("\n-----------------------------------------------------------------------");
    	 System.out.println("initialize @PostConstruct");
    	 System.out.println("-----------------------------------------------------------------------");
    	clean();
        
        this.rootCategorys = this.service.getRootCategorys();

    }

    /**
     * Inits the.
     */
    @SuppressWarnings("static-method")
    public void init() {
         if (FacesUtil.isNotPostback()) {
        	 System.out.println("\n-----------------------------------------------------------------------");
        	 System.out.println("INIT");
         // do the operation here
        	 if (this.categoryMaster != null) {
        		 System.out.println(">>>> LOAD CHILD CATEGORIES");
        		 this.loadChildCategorys();
 			}
        	 System.out.println("-----------------------------------------------------------------------");
         }
    }

    /**
     * Save.
     */
    public void save() {

        this.product = this.service.save(this.product);

        FacesUtil.addInfoMessage("Produto savo com sucesso");

        clean();
    }

    /**
     * Clean.
     */
    private void clean() {
        this.product = new Product();
        this.categoryMaster = null;
        this.categories = new ArrayList<>();
    }

    /**
     * Load child categorys.
     */
    public void loadChildCategorys() {
        this.categories = this.service.getChildsCategories(this.categoryMaster);
    }

    /**
     * Gets the product.
     * @return the product
     */
    public Product getProduct() {
        return this.product;
    }

    /**
     * Gets the categories.
     * @return the categories
     */
    public List<Category> getCategories() {
        return this.categories;
    }

    /**
     * Gets the category master.
     * @return the category master
     */
    public Category getCategoryMaster() {
        return this.categoryMaster;
    }

    /**
     * Sets the category master.
     * @param categoryMaster
     *            the new category master
     */
    public void setCategoryMaster(final Category categoryMaster) {
        this.categoryMaster = categoryMaster;
    }

    /**
     * Gets the root categorys.
     * @return the root categorys
     */
    public List<Category> getRootCategorys() {
        return this.rootCategorys;
    }

    
    /**
     * Sets the product.
     *
     * @param product the new product
     */
    public void setProduct(Product product) {
		this.product = product;
		
		if(this.product != null){
			//load the category
			System.out.println("\n-------------------------------------------------------------");
			System.out.println("setProduct load subcategorias e categorias");
			System.out.println("--------------------------------------------------------------");
			if(this.product.getCategory() != null){
				this.categoryMaster = this.product.getCategory().getMasterCategory();
				
				
			}
		}
	}
    
    /**
     * Checks if is edits Mode the.
     * is edite mode if the using product have already a ID defined 
     * @return true, if is , false otherwise 
     */
    public boolean isEdit(){
    	return this.product != null && this.product.getId() != null;
    }
}
