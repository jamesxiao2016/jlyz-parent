package cn.dlbdata.dj.common.core.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import cn.dlbdata.dj.common.core.util.security.Base64;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil {
	static Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

	public final static Cache<String, String> USER_TICKET_CACHE = CacheBuilder.newBuilder()
			// 设置cache的初始大小为10，要合理设置该值
			.initialCapacity(10)
			// 设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
			.concurrencyLevel(5)
			// 设置cache中的数据在写入之后的存活时间为10秒
			.expireAfterWrite(7, TimeUnit.DAYS)
			// 构建cache实例
			.build();
	/**
	 * KEY-用户ID
	 */
	public static String KEY_UID = "UID";
	/**
	 * KEY-用户名
	 */
	public static String KEY_UNAME = "UNAME";
	/**
	 * KEY-部门ID
	 */
	public static String KEY_CID = "CID";
	public static String KEY_UTYPE = "UTYPE";

	/**
	 * 生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。<br/>
	 * 它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret,那就意味着客户端是可以自我签发jwt了。
	 */
	static SecretKey key = generalKey();

	static JwtTokenUtil instance = new JwtTokenUtil();

	private JwtTokenUtil() {

	}

	public static JwtTokenUtil getInstance() {
		return instance;
	}

	/**
	 * 创建token
	 * 
	 * @param uid
	 *            用户ID
	 * @param uname
	 *            用户姓名
	 * @param cid
	 *            部门ID
	 * @param ttlMillis
	 * @return
	 */
	public static String createToken(String uid, String uname, String cid, String utype, long ttlMillis) {
		try {
			SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
			long nowMillis = System.currentTimeMillis();// 生成JWT的时间
			Date now = new Date(nowMillis);
			Map<String, Object> claims = new HashMap<String, Object>();// 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
			claims.put(KEY_UID, uid);
			claims.put(KEY_UNAME, uname);
			claims.put(KEY_CID, cid);
			claims.put(KEY_UTYPE, utype);

			// 下面就是在为payload添加各种标准声明和私有声明了
			JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
					.setClaims(claims) // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
					.setId(uid) // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
					.setIssuedAt(now) // iat: jwt的签发时间
					.setSubject(uname) // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
					.signWith(signatureAlgorithm, key);// 设置签名使用的签名算法和签名使用的秘钥
			if (ttlMillis > 0) {
				long expMillis = nowMillis + ttlMillis;
				Date exp = new Date(expMillis);
				builder.setExpiration(exp); // 设置过期时间
			}
			return builder.compact(); // 就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
		} catch (Exception e) {
			logger.error("createJWT", e);
			return "";
		}
	}

	/**
	 * 根据token获取
	 * 
	 * @param token
	 * @return
	 */
	public static Map<String, String> getTokenInfo(String token) {
		Map<String, String> result = new HashMap<String, String>();
		if (StringUtils.isEmpty(token)) {
			logger.error("token is null");
			return result;
		}

		Claims claims = null;
		try {
			claims = parseJWT(token);
		} catch (Exception e) {
			logger.error("解析token失败", e);
			return result;
		}
		if (claims != null) {
			result.put(KEY_UID, (String) claims.get(KEY_UID));
			result.put(KEY_UNAME, (String) claims.get(KEY_UNAME));
			result.put(KEY_CID, (String) claims.get(KEY_CID));
		}
		return result;
	}

	/**
	 * 解析token中的内容
	 * 
	 * @param jwt
	 * @return
	 */
	public static Claims parseJWT(String jwt) {
		SecretKey key = generalKey(); // 签名秘钥，和生成的签名的秘钥一模一样
		Claims claims = Jwts.parser() // 得到DefaultJwtParser
				.setSigningKey(key) // 设置签名的秘钥
				.parseClaimsJws(jwt).getBody();// 设置需要解析的jwt
		return claims;
	}

	/**
	 * 生成Key
	 * 
	 * @return
	 */
	public static SecretKey generalKey() {
		byte[] encodedKey = Base64.decode(JWT_SECERT);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}

	/**
	 * jwt
	 */
	static final String JWT_ID = "dlbdata"; // jwtid
	static final String JWT_SECERT = "7786df7fc3a34e26a61c034d5ec8245d"; // 密匙
	public static final long JWT_TTL = 1 * 5 * 1000; // token有效时间
	public static final int MINUTE_TTL = 60 * 1000; // millisecond
	public static final int HOURS_TTL = 60 * 60 * 1000; // millisecond
	public static final int DAY_TTL = 12 * 60 * 60 * 1000; // millisecond

	public static void main(String[] args) {
		String name = "test";
		String userId = "0";
		long TTLMillis = 10000L;
		String cid = "c001";
		try {
			String token = createToken(userId, name, cid, "1", TTLMillis);
			System.out.println(token);
			Claims claims = parseJWT(token);
			System.out.println(claims.get(KEY_UID));
			System.out.println(claims.get(KEY_UNAME));
			System.out.println(claims.get(KEY_CID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
