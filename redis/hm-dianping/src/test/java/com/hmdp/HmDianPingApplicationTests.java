package com.hmdp;

import com.hmdp.service.impl.ShopServiceImpl;
import com.hmdp.utils.RedisIdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class HmDianPingApplicationTests {
	@Resource
	private ShopServiceImpl shopService;

	@Resource
	private RedisIdWorker redisIdWorker;

	private ExecutorService es = Executors.newFixedThreadPool(500);
	@Test
	public void testIDWorker() throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(300);

		Runnable task = () -> {
			for (int i = 0; i < 100; i++) {
				long id = redisIdWorker.nextId("order");
				System.out.println("id = " + id);
			}
			countDownLatch.countDown();
		};
		long start = System.currentTimeMillis();
		for (int i = 0; i < 300; i++) {
			es.submit(task);
		}
		countDownLatch.await();
		long end = System.currentTimeMillis();
		System.out.println("时间：" + (end - start));
	}

	//逻辑过期 数据预热
	@Test
	public void testSaveShop() {
		shopService.saveShop2Redis(1L, 10L);
	}
}
