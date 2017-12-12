package com.model2.mvc.web.purchase;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	
	//Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
//	
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping(value="addPurchase", method=RequestMethod.GET)
	public String addPurchase(@RequestParam("prodNo") int prodNo, Model model) 
			throws Exception{
		
		System.out.println("/purchase/addPurchaseView : GET");
		
		Product product= productService.getProduct(prodNo);
		System.out.println("=====> "+product);
		
		model.addAttribute("product",product);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@RequestMapping(value="addPurchase", method=RequestMethod.POST)
	public String addPurchase(
			@ModelAttribute("purchase") Purchase purchase,
			@ModelAttribute("product") Product product,
			@RequestParam("prodNo") int prodNo, Model model, 
			@RequestParam("userId") String userId) throws Exception{
		
		System.out.println("/purchase/addPurchase : POST");
		
		productService.getProduct(prodNo);
		product.setProTranCode("1");
		
		User user=userService.getUser(userId);
		
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setTranCode("1");
		
		purchaseService.addPurchase(purchase);
		
		
		System.out.println("/purchase/addPurchase : POST ==>"+purchase);
		
		//System.out.println(purchase);
		return "forward:/purchase/addPurchaseAction.jsp";
	}
	
	@RequestMapping(value="listPurchase")
	public String getPurchaseList(
			@ModelAttribute("search") Search search,
			Model model,
			HttpServletRequest request)throws Exception{
		
		
		System.out.println("/purchase/listPurchase : GET / POST");
		
		HttpSession session = request.getSession();
		String userId =((User)session.getAttribute("user")).getUserId();
		System.out.println("유저 아이디 ====>  "+userId);
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
			
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map =purchaseService.getPurchaseList(search,userId);	
		
		
		Page resultPage = new Page( search.getCurrentPage(), 
				((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("userId", map.get("userId"));
		
		return "forward:/purchase/listPurchase.jsp";
	}
	
	@RequestMapping(value="getPurchase", method=RequestMethod.GET)
	public String getPurchase2(@RequestParam("tranNo") int tranNo,Model model)
			throws Exception{
		
		System.out.println("/purchase/getPurchase2 : GET ");
		
		Purchase purchase = purchaseService.getPurchase2(tranNo);
				
		model.addAttribute("purchase",purchase);
		
		return "forward:/purchase/getPurchase.jsp?tranNo="+tranNo;
		
	}
	

	@RequestMapping(value="updatePurchase", method=RequestMethod.GET)
	public String updatePurchaseView(@RequestParam("tranNo") int tranNo, Model model )throws Exception{
		
		System.out.println("/purchase/updatePurchaseView : GET");
		
		Purchase purchase =purchaseService.getPurchase2(tranNo);
		
		model.addAttribute("purchase", purchase);
		
		
		return "forward:/purchase/updatePurchaseView.jsp";
		
	}
	
	@RequestMapping(value="updatePurchase",method=RequestMethod.POST)
	public String updatePurchase(@ModelAttribute("purchase") Purchase purchase)throws Exception{
		
		System.out.println("/purchase/updatePurchase : POST");
		
		purchaseService.updatePurchase(purchase);
	
		System.out.println("수정=====>  "+purchase);
		
		return "redirect:/purchase/getPurchase?tranNo="+purchase.getTranNo();
	}
	
	
	@RequestMapping(value="updateTranCode",method=RequestMethod.GET)
	public String updateTranCode(
			@ModelAttribute("purchase") Purchase purchase,
			@RequestParam("tranNo") int tranNo,
			@RequestParam("tranCode") String tranCode,
			Model model)throws Exception{
		
		System.out.println("/purchase/updateTranCode : GET");
		
		purchase = purchaseService.getPurchase2(tranNo);
		purchase.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purchase);
		
		System.out.println("updateTranCode===>"+purchase);
	
		
		model.addAttribute("tranCode", tranCode);
		
		return "redirect:/purchase/listPurchase?";
		
	}
	
	@RequestMapping(value="updateTranCodeByProd", method=RequestMethod.GET)
	public String updateTranCodeByProd(			
			@ModelAttribute("purchase") Purchase purchase,
			@ModelAttribute("product") Product product,
			@RequestParam("prodNo") int prodNo,
			@RequestParam("proTranCode") String proTranCode, Model model)throws Exception{
		
		System.out.println("::updateTranCode::");
		
		
		product = productService.getProduct(prodNo);
		System.out.println("Product====> "+product);
		product.setProTranCode(proTranCode);
		
		purchase = purchaseService.getPurchase(prodNo);
		System.out.println("Purchase ===> "+purchase);
		purchase.setTranCode(proTranCode);
		
		System.out.println("======>"+proTranCode);
		
		
		purchaseService.updateTranCode(purchase);
		
		
		
		model.addAttribute("proTranCode", proTranCode);
		model.addAttribute("product", product);
		model.addAttribute("purchase", purchase);
		
		return "forward:/product/listProduct?prodNo="+prodNo+"&menu=manage";
	}
	
	
}
