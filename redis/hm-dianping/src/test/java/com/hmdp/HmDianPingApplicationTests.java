package com.hmdp;

import com.hmdp.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class HmDianPingApplicationTests {
	@Resource
	private ShopServiceImpl shopService;

	//逻辑过期 数据预热
	@Test
	public void testSaveShop() {
		shopService.saveShop2Redis(1L, 10L);
	}
}
