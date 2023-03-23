package org.example.mufiye.microSpring.framework.event.impl.http.factory;

import org.example.mufiye.microSpring.framework.event.impl.http.message.RequestBean;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Date: 2023/1/14
 * @Author: mufiye
 * @Class: HttpFactory
 * @Description: description...
 */
@Slf4j
public class HttpFactory {
	private final static int MAX_REQUEST = 20;

	private final static Queue<RequestBean> REQUEST_QUEUE = new ConcurrentLinkedQueue<>();
	private final static Map<String, DefaultFullHttpResponse> RESPONSE_MAP = new HashMap<>();

	private final static Set<String> CLEAR_LIST = new HashSet<>();

	private final static ReentrantLock REQ_LOCK = new ReentrantLock();
	private final static Condition REQ_IN = REQ_LOCK.newCondition();
	private final static Condition REQ_OUT = REQ_LOCK.newCondition();
	private final static ReentrantLock RESP_LOCK = new ReentrantLock();
	private final static Condition RESP_IN = RESP_LOCK.newCondition();
	private final static Condition RESP_OUT = RESP_LOCK.newCondition();

	public static String putRequest(DefaultHttpRequest request){
		String id = UUID.randomUUID().toString();

		REQ_LOCK.lock();
		while (REQUEST_QUEUE.size() == MAX_REQUEST) {
			try {
				REQ_IN.await();
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}

		RequestBean requestBean = new RequestBean();
		requestBean.setId(id);
		requestBean.setRequest(request);
		REQUEST_QUEUE.offer(requestBean);
		REQ_OUT.signal();
		REQ_LOCK.unlock();

		return id;
	}

	public static RequestBean getRequest(){
		REQ_LOCK.lock();
		while (REQUEST_QUEUE.size() == 0) {
			try {
				REQ_OUT.await();
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}

		RequestBean requestBean = REQUEST_QUEUE.poll();
		REQ_IN.signal();
		REQ_LOCK.unlock();

		return requestBean;
	}

	public static void putResponse(String requestId, DefaultFullHttpResponse response){
		RESP_LOCK.lock();
		RESPONSE_MAP.put(requestId, response);
		RESP_OUT.signal();
		RESP_LOCK.unlock();
	}

	public static DefaultFullHttpResponse getResponse(String id, long timeout){
		RESP_LOCK.lock();
		long left = timeout;
		DefaultFullHttpResponse defaultHttpResponse;
		while ((defaultHttpResponse = RESPONSE_MAP.remove(id)) == null && left > 0L) {
			try {
				long start = System.currentTimeMillis();
				RESP_OUT.await(left, TimeUnit.MILLISECONDS);
				long end = System.currentTimeMillis();
				left -= end - start;
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}

		RESP_LOCK.unlock();
		//超时并且还是没拿到相应，需要之后手动清除这个响应消息。
		if (defaultHttpResponse == null) {
			synchronized (CLEAR_LIST) {
				CLEAR_LIST.add(id);
			}
		}

		return defaultHttpResponse;
	}
}
