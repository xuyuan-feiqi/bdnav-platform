package com.bdxh.task.api.service;

import com.bdxh.common.feigin.feign.OAuth2FeignAutoConfiguration;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.task.api.model.dto.TpcMqMessageDto;
import com.bdxh.task.api.service.hystrix.TpcMqMessageFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface Tpc mq message feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-tpc", configuration = OAuth2FeignAutoConfiguration.class, fallback = TpcMqMessageFeignApiHystrix.class)
public interface TpcMqMessageFeignApi {

	/**
	 * 预存储消息.
	 *
	 * @param mqMessageDto the mq message dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/saveMessageWaitingConfirm")
	Wrapper saveMessageWaitingConfirm(@RequestBody TpcMqMessageDto mqMessageDto);

	/**
	 * 确认并发送消息.
	 *
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/confirmAndSendMessage")
	Wrapper confirmAndSendMessage(@RequestParam("messageKey") String messageKey);

	/**
	 * 存储并发送消息.
	 *
	 * @param mqMessageDto the mq message dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/saveAndSendMessage")
	Wrapper saveAndSendMessage(@RequestBody TpcMqMessageDto mqMessageDto);

	/**
	 * 直接发送消息.
	 *
	 * @param mqMessageDto the mq message dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/directSendMessage")
	Wrapper directSendMessage(@RequestBody TpcMqMessageDto mqMessageDto);

	/**
	 * 根据messageKey删除消息记录.
	 *
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/deleteMessageByMessageKey")
	Wrapper deleteMessageByMessageKey(@RequestParam("messageKey") String messageKey);

	/**
	 * Confirm receive message wrapper.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/confirmReceiveMessage")
	Wrapper confirmReceiveMessage(@RequestParam("cid") final String cid, @RequestParam("messageKey") final String messageKey);

	/**
	 * Save and confirm finish message wrapper.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/saveAndConfirmFinishMessage")
	Wrapper confirmConsumedMessage(@RequestParam("cid") final String cid, @RequestParam("messageKey") final String messageKey);
}
