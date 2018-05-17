package cn.dlbdata.dangjian.common.core.util;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.fileupload.util.mime.MimeUtility;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class MailUtil {
	// 日志器
	private static final Logger logger = Logger.getLogger(MailUtil.class);
	/** 发送邮件的服务器的IP */
	private static String mailHost = "smtp.meigsmart.com";
	/** 发送邮件的用户名（邮箱全名称） */
	private static String username = "";
	/** 发送邮件的密码 */
	private static String password = "";
	private static String fromName = "MeigLink";
	private static MailUtil sender = null;
	private Session session = null;

	public MailUtil() {
		session = getSession();
	}

	public static MailUtil getInstance() {
		if (sender == null) {
			sender = new MailUtil();
		}
		// mailHost = ConfigUtil.get("mail_host");
		username = ConfigUtil.get("mail_username");
		password = ConfigUtil.get("mail_password");
		fromName = ConfigUtil.get("mail_from_name");
		return sender;
	}

	public static MailUtil getInstance(String username, String password) {
		MailUtil.username = username;
		MailUtil.password = password;
		if (sender == null) {
			sender = new MailUtil();
		}
		return sender;
	}

	public Session getSession() {
		Properties props = new Properties(); // 参数配置
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", mailHost); // 发件人的邮箱的 SMTP 服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.port", "465");
		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		session = Session.getDefaultInstance(props);
		session.setDebug(false);

		return session;
	}

	/**
	 * 发送邮件
	 * 
	 * @param to
	 *            接收者（多个使用;分割）
	 * @param cc
	 *            抄送者（多个使用;分割）
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @throws Exception
	 */
	public void sendMail(String to, String cc, String subject, String content) throws Exception {
		sendMail(to, cc, null, subject, content, null);
	}

	public void sendMail(String to, String cc, String bcc, String subject, String content) throws Exception {
		sendMail(to, cc, bcc, subject, content, null);
	}

	/**
	 * 发送邮件
	 * 
	 * @param to
	 *            接收者（多个使用;分割）
	 * @param cc
	 *            抄送者（多个使用;分割）
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param filePath
	 *            附件路径
	 * @throws Exception
	 */
	public void sendMail(String to, String cc, String bcc, String subject, String content, String filePath)
			throws Exception {
		logger.info("发送邮件->" + to + "-cc>" + cc + "-bcc>" + bcc);
		if (StringUtils.isEmpty(to)) {
			return;
		}

		// 3. 创建一封邮件
		MimeMessage message = createMimeMessage(to, cc, bcc, subject, content, filePath);

		// 4. 根据 Session 获取邮件传输对象
		Transport transport = getSession().getTransport();

		// 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
		//
		// PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
		// 仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
		// 类型到对应邮件服务器的帮助网站上查看具体失败原因。
		//
		// PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
		// (1) 邮箱没有开启 SMTP 服务;
		// (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
		// (3) 邮箱服务器要求必须要使用 SSL 安全连接;
		// (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
		// (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
		//
		transport.connect(getUsername(), getPassword());

		// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人,
		// 密送人
		transport.sendMessage(message, message.getAllRecipients());

		// 7. 关闭连接
		transport.close();
		logger.info("邮件发送完成");
	}

	/**
	 * 创建一封只包含文本的简单邮件
	 * 
	 * @param to
	 *            发送人邮箱列表（;分割）
	 * @param cc
	 *            抄送人邮箱列表（;分割）
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param filePath
	 *            附件路径
	 * @return
	 * @throws Exception
	 */
	public MimeMessage createMimeMessage(String to, String cc, String bcc, String subject, String content,
			String filePath) throws Exception {
		// 1. 创建一封邮件
		MimeMessage message = new MimeMessage(session);
		Address from = new InternetAddress(getUsername(), fromName);
		// 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
		// 设置邮件消息的发送者
		message.setFrom(from);

		// 创建邮件的接收者地址 to：发送；cc：抄送
		Address[][] maillToArr = getMailToAddress(to, cc, bcc);

		// 设置邮件消息的接收者，发送，抄送
		if (maillToArr != null) {
			if (maillToArr[0] != null && maillToArr[0].length > 0) {
				message.setRecipients(MimeMessage.RecipientType.TO, maillToArr[0]);
			}
			if (maillToArr[1] != null && maillToArr[1].length > 0) {
				message.setRecipients(MimeMessage.RecipientType.CC, maillToArr[1]);
			}
			if (maillToArr[2] != null && maillToArr[2].length > 0) {
				message.setRecipients(MimeMessage.RecipientType.BCC, maillToArr[2]);
			}
		}

		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		// message.setRecipient(MimeMessage.RecipientType.TO, new
		// InternetAddress(receiveMail, receiveName, "UTF-8"));

		// 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
		message.setSubject(subject, "UTF-8");

		MimeBodyPart text = new MimeBodyPart();
		// 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
		text.setContent(content, "text/html;charset=UTF-8");
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text);
		// 6. 设置发件时间
		message.setSentDate(new Date());

		if (StringUtils.isNotEmpty(filePath)) {
			MimeBodyPart attachment = new MimeBodyPart();
			DataHandler dataHandler = new DataHandler(new FileDataSource(filePath)); // 读取本地文件
			attachment.setDataHandler(dataHandler); // 将附件数据添加到“节点”
			attachment.setFileName(MimeUtility.decodeText(dataHandler.getName())); // 设置附件的文件名（需要编码）

			mm.addBodyPart(attachment); // 如果有多个附件，可以创建多个多次添加
			mm.setSubType("mixed"); // 混合关系
		}
		message.setContent(mm);
		// 7. 保存设置
		message.saveChanges();

		return message;
	}

	/**
	 * 邮箱地址转换
	 * 
	 * @param sendTo
	 * @param sendCc
	 * @return
	 * @throws AddressException
	 */
	private Address[][] getMailToAddress(String sendTo, String sendCc, String sendBcc) throws AddressException {
		Address[] toAdds = null;
		Address[] ccAdds = null;
		Address[] bccAdds = null;
		if (StringUtils.isEmpty(sendTo)) {
			return null;
		}
		String[] toMails = sendTo.split(";");
		toAdds = new InternetAddress[toMails.length];
		for (int index = 0; index < toMails.length; index++) {
			toAdds[index] = new InternetAddress(toMails[index]);
		}
		if (StringUtils.isNotEmpty(sendCc)) {
			String[] ccMails = sendCc.split(";");
			ccAdds = new InternetAddress[ccMails.length];
			for (int index = 0; index < ccMails.length; index++) {
				ccAdds[index] = new InternetAddress(ccMails[index]);
			}
		}
		if (StringUtils.isNotEmpty(sendBcc)) {
			String[] ccMails = sendCc.split(";");
			bccAdds = new InternetAddress[ccMails.length];
			for (int index = 0; index < ccMails.length; index++) {
				bccAdds[index] = new InternetAddress(ccMails[index]);
			}
		}
		Address[][] result = { toAdds, ccAdds, bccAdds };
		return result;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		MailUtil.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		MailUtil.password = password;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		MailUtil.mailHost = mailHost;
	}

	public static void main(String[] args) {
		String to = "xiaowei@meigsmart.com";
		String cc = "support@meigsmart.com";
		MailUtil mailUtil = getInstance("support@meigsmart.com","~1qaz2wsx");
		try {
			mailUtil.sendMail(to, cc, "test email", "test email");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
