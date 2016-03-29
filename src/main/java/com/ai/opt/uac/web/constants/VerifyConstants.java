package com.ai.opt.uac.web.constants;

public final class VerifyConstants {
	private VerifyConstants(){}
	
	public final class PhoneVerifyConstants{
		private PhoneVerifyConstants(){}
		
		/**手机验证码长度*/
		public static final int VERIFY_SIZE = 6;
		
		/**手机验证码超时时间*/
		public static final int VERIFY_OVERTIME = 300;
		
		/** 短信注册模板ID */
        public static final String TEMPLATE_REGISTER_ID = "1";
        /** 短信修改密码模板ID */
        public static final String TEMPLATE_UPDATE_PASSWORD_ID = "2";
        /** 短信修改手机模板ID */
        public static final String TEMPLATE_UPDATE_PHONE_ID = "3";
        /** 短信修改邮箱模板ID */
        public static final String TEMPLATE_UPDATE_EMAIL_ID = "4";
        /** 短信修改邮箱模板ID */
        public static final String TEMPLATE_SET_PASSWORD_ID = "5";
        /** 短信找回密码模板ID */
        public static final String TEMPLATE_RETAKE_PASSWORD_ID = "6";
        
        public static final String SERVICE_TYPE = "1";
		
	}
	
	public final class EmailVerifyConstants{
		private EmailVerifyConstants(){}
		
		/** 邮箱主题 */
        public static final String EMAIL_SUBJECT = "亚信云计费";
		
		/**邮箱验证码长度*/
		public static final int VERIFY_SIZE = 6;
    	
    	/**邮件验证码超时时间*/
		public static final int VERIFY_OVERTIME = 1800;
		
		/**邮箱模板路径*/
		public static final String TEMPLATE_URL = "email/template/uac-retakepassword-mail.xml";
	}
	
	public final class PictureVerifyConstants{
		private PictureVerifyConstants(){}
		
		/**图片验证码长度*/
		public static final int VERIFY_SIZE = 4;
		/**图片验证码超时时间*/
		public static final int VERIFY_OVERTIME = 180;
	}
}
