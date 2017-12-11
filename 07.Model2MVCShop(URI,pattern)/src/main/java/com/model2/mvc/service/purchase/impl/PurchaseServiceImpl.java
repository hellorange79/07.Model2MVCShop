package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;;

@Repository("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {
	
	
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	
	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao=purchaseDao;
	}
	
	
	public PurchaseServiceImpl() {
		System.out.println(this.getClass());
	}

	// ±¸¸Å
	public void addPurchase(Purchase purchase) throws Exception {
		System.out.println("::PurchaseService addPurchase :: ");
		
		purchaseDao.insertPurchase(purchase);

	}

	@Override
	public Purchase getPurchase2(int tranNo) throws Exception {

		return purchaseDao.findPurchase2(tranNo);
	}

	@Override
	public Purchase getPurchase(int prodNo) throws Exception {

		return purchaseDao.findPurchase(prodNo);
		
	}

	@Override
	public Map<String,Object> getPurchaseList(Search search,String buyerId) throws Exception {
		
		List<Purchase> list = purchaseDao.getPurchaseList(search,buyerId);
		int totalCount= purchaseDao.getTotalCount(buyerId);
		System.out.println("====>"+list);
		HashMap<String, Object> map= new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}

	@Override
	public HashMap<String, Object> getSaleList(Search search) throws Exception {

		return null;
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {

		purchaseDao.updatePurchase(purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	}


	

}
