package cn.cy.security.util;

import cn.cy.security.config.JwtConfig;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>JwtToken生成的工具类
 * <p>JWT token的格式：header.payload.signature
 * <p>header 的格式（算法、token的类型）：{@code {"alg": "HS512","typ": "JWT"}}
 * <p>payload 的格式（用户名、创建时间、生成时间）：{@code {"sub":"wang","created":1489079981393,"exp":1489684781}}
 * <p>signature 的生成算法：{@code HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)}
 *
 * @Author: 友叔
 * @Date: 2020/11/26 21:50
 * @Description: JwtToken 生成的工具类
 */
public class JwtTokenUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 根据 claims 生成 token
     *
     * @param claims 用户信息(payload)
     * @return token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();

    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000);
    }

    /**
     * 从 token 中获取登陆用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从 token 中获取 JWT 中的负载（payload）
     *
     * @param token token
     * @return token 中的负载
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.info("JWT 格式校验失败:{}", token);
        }

        return claims;
    }


    /**
     * 验证token是否还有效
     *
     * @param authToken   客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String authToken, UserDetails userDetails) {
        String username = getUserNameFromToken(authToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(authToken);
    }

    /**
     * 判断token是否已经失效
     *
     * @param token token
     * @return true token 已过期
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }


    /**
     * 从token中获取过期时间
     *
     * @param token token
     * @return token 过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 当原来的 token 没有过期时，是可以进行刷新的
     *
     * @param oldToken 旧的 token
     * @return 新的 token
     */
    public String refreshHeadToken(String oldToken) {
        if (StrUtil.isEmpty(oldToken)) {
            return null;
        }
        String token = oldToken.substring(jwtConfig.getTokenHead().length());
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        // token 校验不通过
        Claims claims = this.getClaimsFromToken(token);
        if (ObjectUtil.isEmpty(claims)) {
            return null;
        }
        // 如果 token 已经过期，不给予刷新
        if (this.isTokenExpired(token)) {
            return null;
        }
        // 如果 token 在 30 分钟之内刚刷新过，返回原 token
        if (this.tokenRefreshJustBefore(token)) {
            return token;
        } else {
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }
    }

    /**
     * 判断 token 在指定时间内是否刚刚刷新过
     *
     * @param token token
     * @return true  刚刚刷新过
     */
    private boolean tokenRefreshJustBefore(String token) {
        Claims claims = this.getClaimsFromToken(token);
        Date createDate = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        // 刷新时间在创建时间的指定时间内
        return refreshDate.after(createDate) && refreshDate.before(DateUtil.offsetSecond(createDate, jwtConfig.getTokenRefreshInterval()));
    }

}
