package com.ai.opt.uac.web.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.cache.factory.CacheClientFactory;
import com.ai.opt.sdk.util.RandomUtil;
import com.ai.opt.uac.web.constants.Constants.VerifyCode;
import com.ai.opt.uac.web.controller.common.VerifyCodeController;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;

public class VerifyCodeUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(VerifyCodeController.class);

	public static BufferedImage getImageVerifyCode(HttpServletRequest request, String namespace, String cacheKey) {
		int width = 60, height = 20;
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
		String verifyCode = RandomUtil.randomString(VerifyCode.VERIFY_SIZE_PICTURE);
		// 将认证码存入缓存
		ICacheClient cacheClient = CacheClientFactory.getCacheClient(namespace);
		cacheClient.setex(cacheKey, VerifyCode.VERIFY_OVERTIME_PICTURE, verifyCode);
		LOGGER.debug("cacheKey=" + cacheKey + ",verifyCode=" + verifyCode);
		// 将认证码显示到图象中
		g.setColor(Color.black);

		g.setFont(new Font("Atlantic Inline", Font.PLAIN, 18));
		String Str = verifyCode.substring(0, 1);
		g.drawString(Str, 8, 17);

		Str = verifyCode.substring(1, 2);
		g.drawString(Str, 20, 15);
		Str = verifyCode.substring(2, 3);
		g.drawString(Str, 35, 18);

		Str = verifyCode.substring(3, 4);
		g.drawString(Str, 45, 15);
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
}
