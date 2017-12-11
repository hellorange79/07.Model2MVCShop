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
	
	@RequestMapping("/addPurchaseView.do")
	public String addPurchaseView(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
		
		System.out.println("/addPurchaseView.do");
		
		Product product= productService.getProduct(prodNo);
		
		model.addAttribute("product",product);
		
		return "forward:/purchase/addPurchaseViewAction.jsp";
	}
	
	@RequestMapping("/addPurchase.do")
	public String addPurchase(
			@ModelAttribute("purchase") Purchase purchase,
			@ModelAttribute("product") Product product,
			@RequestParam("prodNo") int prodNo, Model model, 
			@RequestParam("userId") String userId) throws Exception{
		
		System.out.println("/addPurchase.do");
		
		productService.getProduct(prodNo);
		product.setProTranCode("1");
		
		User user=userService.getUser(userId);
		
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setTranCode("1");
		
		purchaseService.addPurchase(purchase);
		
		
		System.out.println("addPurchase.do ==>"+purchase);
		
		//System.out.println(purchase);
		return "forward:/purchase/addPurchaseAction.jsp";
	}
	
	@RequestMapping("/listPurchase.do")
	public String getPurchaseList(
			@ModelAttribute("search") Search search,
			Model model,
			HttpServletRequest request)throws Exception{
		
		
		System.out.println("/listPurchase.do");
		
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
	
	@RequestMapping("/getPurchase.do")
	public String getPurchase2(@RequestParam("tranNo") int tranNo,Model model)
			throws Exception{
		
		System.out.println("/getPurchase2.do");
		
		Purchase purchase = purchaseService.getPurchase2(tranNo);
				
		model.addAttribute("purchase",purchase);
		
		return "forward:/purchase/getPurchase.jsp?tranNo="+tranNo;
		
	}
	

	@RequestMapping("/updatePurchaseView.do")
	public String updatePurchaseView(@RequestParam("tranNo") int tranNo, Model model )throws Exception{
		
		System.out.println("/updatePurchaseView.do");
		
		Purchase purchase =purchaseService.getPurchase2(tranNo);
		
		model.addAttribute("purchase", purchase);
		
		
		return "forward:/purchase/updatePurchaseView.jsp";
		
	}
	
	@RequestMapping("/updatePurchase.do")
	public String updatePurchase(@ModelAttribute("purchase") Purchase purchase)throws Exception{
		
		System.out.println("/updatePurchase.do");
		
		purchaseService.updatePurchase(purchase);
	
		System.out.println("수정=====>  "+purchase);
		
		return "redirect:/getPurchase.do?tranNo="+purchase.getTranNo();
	}
	
	
	@RequestMapping("/updateTranCode.do")
	public String updateTranCode(
			@ModelAttribute("purchase") Purchase purchase,
			@RequestParam("tranNo") int tranNo,
			@RequestParam("tranCode") String tranCode,
			Model model)throws Exception{
		
		System.out.println("/updateTranCode.do");
		
		purchase = purchaseService.getPurchase2(tranNo);
		purchase.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purchase);
		
		System.out.println("updateTranCode===>"+purchase);
	
		
		model.addAttribute("tranCode", tranCode);
		
		return "redirect:/listPurchase.do?";
		
	}
	
	@RequestMapping("/updateTranCodeByProd.do")
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
		
		return "redirect:/listProduct.do?prodNo="+prodNo;
	}
	
	
}
