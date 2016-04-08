package com.ai.opt.uac.web.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.cache.factory.CacheClientFactory;
import com.ai.opt.sdk.configcenter.factory.ConfigCenterFactory;
import com.ai.opt.sdk.mail.EmailFactory;
import com.ai.opt.sdk.mail.EmailTemplateUtil;
import com.ai.opt.sdk.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.RandomUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.uac.api.seq.interfaces.ICreateSeqSV;
import com.ai.opt.uac.api.seq.param.PhoneMsgSeqResponse;
import com.ai.opt.uac.api.sso.interfaces.ILoginSV;
import com.ai.opt.uac.api.sso.param.UserLoginResponse;
import com.ai.opt.uac.web.constants.Constants;
import com.ai.opt.uac.web.constants.VerifyConstants;
import com.ai.opt.uac.web.constants.VerifyConstants.PictureVerifyConstants;
import com.ai.opt.uac.web.constants.VerifyConstants.ResultCodeConstants;
import com.ai.opt.uac.web.model.email.SendEmailRequest;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.runner.center.mmp.api.manager.interfaces.SMSServices;
import com.ai.runner.center.mmp.api.manager.param.SMDataInfoNotify;

public class VerifyUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(VerifyUtil.class);

	public static BufferedImage getImageVerifyCode(HttpServletRequest request, String namespace, String cacheKey) {
		int width = 100, height = 38;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 设定背景色
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);

		// 画边框
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);

		// 取随机产生的认证码
		String verifyCode = RandomUtil.randomString(PictureVerifyConstants.VERIFY_SIZE);
		// 将认证码存入缓存
		ICacheClient cacheClient = CacheClientFactory.getCacheClient(namespace);
		String overTimeStr = ConfigCenterFactory.getConfigCenterClient().get(PictureVerifyConstants.VERIFY_OVERTIME_KEY);
		cacheClient.setex(cacheKey, Integer.valueOf(overTimeStr), verifyCode);
		LOGGER.debug("cacheKey=" + cacheKey + ",verifyCode=" + verifyCode);
		// 将认证码显示到图象中
		g.setColor(Color.black);

		g.setFont(new Font("Atlantic Inline", Font.PLAIN, 30));
		String Str = verifyCode.substring(0, 1);
		g.drawString(Str, 8, 25);

		Str = verifyCode.substring(1, 2);
		g.drawString(Str, 28, 30);
		Str = verifyCode.substring(2, 3);
		g.drawString(Str, 48, 27);

		Str = verifyCode.substring(3, 4);
		g.drawString(Str, 68, 32);
		// 随机产生88个干扰点，使图象中的认证码不易被其它程序探测到
		Random random = new Random();
		for (int i = 0; i < 30; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			g.drawOval(x, y, 0, 0);
		}

		// 图象生效
		g.dispose();
		return image;

	}

	/**
	 * 发送邮件
	 * @param emailRequest
	 * @return
	 */
	public static boolean sendEmail(SendEmailRequest emailRequest) {
		boolean success = true;
		String htmlcontext = EmailTemplateUtil.buildHtmlTextFromTemplate(emailRequest.getTemplateRUL(), emailRequest.getData());
		try {
			EmailFactory.SendEmail(emailRequest.getTomails(), emailRequest.getCcmails(), emailRequest.getSubject(), htmlcontext);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * 发送手机信息
	 * @param smDataInfoNotify
	 * @return
	 */
	public static boolean sendPhoneInfo(SMDataInfoNotify smDataInfoNotify){
		SMSServices smsService = DubboConsumerFactory.getService("sMSServices");
		boolean isSuccess = true;
		try {
			smsService.dataInput(smDataInfoNotify);
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
		}
		return isSuccess;
	}

	/**
	 * 创建短信信息seq
	 * @return
	 */
	public static String createPhoneMsgSeq() {
		ICreateSeqSV service = DubboConsumerFactory.getService("iCreateSeqSV");
		PhoneMsgSeqResponse msgSeqResponse = service.createPhoneMsgSeq();
		if (msgSeqResponse != null) {
			ResponseHeader responseHeader = msgSeqResponse.getResponseHeader();
			String resultCode = responseHeader.getResultCode();
			if (Constants.ResultCode.SUCCESS_CODE.equals(resultCode)) {
				return msgSeqResponse.getMsgSeqId();
			}
		}
		return null;
	}
	
	/**
	 * 检查图片验证码
	 * @param verifyCode
	 * @param cacheVerifyCode
	 * @return
	 */
	public static ResponseData<String> checkPictureVerifyCode(String verifyCode, String cacheVerifyCode) {
		ResponseData<String> responseData = null;
		ResponseHeader responseHeader = null;
		if (cacheVerifyCode == null) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "图形验证码已失效", null);
			responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.REGISTER_PICTURE_ERROR, "图形验证码已失效");
		} else if (cacheVerifyCode.compareToIgnoreCase(verifyCode) != 0) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "图形验证码错误", null);
			responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.REGISTER_PICTURE_ERROR, "图形验证码错误");
		} else {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "图形验证码正确", null);
			responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "图形验证码正确");
		}
		responseData.setResponseHeader(responseHeader);
		return responseData;
	}
	
	/**
	 * 检查邮箱验证码
	 * @param verifyCode
	 * @param cacheVerifyCode
	 * @return
	 */
	public static ResponseData<String> checkPhoneVerifyCode(String verifyCode, String cacheVerifyCode) {
		ResponseData<String> responseData = null;
		ResponseHeader responseHeader = null;
		if (cacheVerifyCode == null) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "验证码已失效", null);
			responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.REGISTER_VERIFY_ERROR, "短信验证码已失效");
		} else if (!cacheVerifyCode.equals(verifyCode)) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "短信验证码错误", null);
			responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.REGISTER_VERIFY_ERROR, "短信验证码错误");
		} else {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "手机校验码正确", null);
			responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "手机校验码正确");
		}
		responseData.setResponseHeader(responseHeader);
		return responseData;
	}

	/**
	 * 检查邮箱验证码
	 * 
	 * @param safetyConfirmData
	 * @param cacheClient
	 * @param sessionId
	 * @return
	 */
	public static ResponseData<String> checkEmailVerifyCode(String verifyCode, String cacheVerifyCode) {
		ResponseData<String> responseData = null;
		ResponseHeader responseHeader = null;
		if (cacheVerifyCode == null) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "邮箱校验码已失效", null);
			responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.REGISTER_VERIFY_ERROR, "邮箱校验码已失效");
		} else if (!cacheVerifyCode.equals(verifyCode)) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "邮箱校验码已错误", null);
			responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.REGISTER_VERIFY_ERROR, "邮箱校验码错误");
		} else {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "邮箱校验码正确", null);
			responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "邮箱校验码正确");
		}
		responseData.setResponseHeader(responseHeader);
		return responseData;
	}
	
	/**
	 * 检测手机号码唯一性
	 * @param phone
	 * @return
	 */
    public static ResponseData<String> checkPhoneOnly(String phone) {
        ResponseData<String> responseData = null;
        ResponseHeader header = null;
        try {
            ILoginSV loginService = DubboConsumerFactory.getService("iLoginSV");
            UserLoginResponse userLoginResponse = loginService.queryAccountByUserName(phone);
            if(userLoginResponse!=null){
                String resultCode = userLoginResponse.getResponseHeader().getResultCode();
				if(resultCode.equals(ResultCodeConstants.SUCCESS_CODE)){
					header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.PHONE_ERROR, "该手机号码已经注册");
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "该手机号码已经注册", null);
                    responseData.setResponseHeader(header);
                } else{
                	header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "成功");
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "成功", null);
                    responseData.setResponseHeader(header);
                }
            }
        } catch (Exception e) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "手机校验失败",null);
        }
        return responseData;
    }
    
    /**
     * 检测邮箱唯一性 
     * @param email
     * @return
     */
    public static ResponseData<String> checkEmialOnly(String email) {
    	 ResponseData<String> responseData = null;
         ResponseHeader header = null;
         try {
             ILoginSV loginService = DubboConsumerFactory.getService("iLoginSV");
             UserLoginResponse userLoginResponse = loginService.queryAccountByUserName(email);
             if(userLoginResponse!=null){
                 String resultCode = userLoginResponse.getResponseHeader().getResultCode();
 				if(resultCode.equals(ResultCodeConstants.SUCCESS_CODE)){
 					header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.EMAIL_ERROR, "该邮箱已经注册");
                     responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "该邮箱已经注册", null);
                     responseData.setResponseHeader(header);
                 } else{
                 	header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "成功");
                     responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "成功", null);
                     responseData.setResponseHeader(header);
                 }
             }
         } catch (Exception e) {
             responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "邮箱校验失败",null);
         }
         return responseData;
    }  
}
