package cn.cy.security.component;


import cn.cy.security.config.JwtConfig;
import cn.cy.security.util.JwtTokenUtil;
import cn.cy.security.web.model.SecurityUserDetails;
import cn.cy.security.web.service.IUserCacheService;
import cn.cy.web.exception.ApiAssert;
import cn.cy.web.response.ErrorCodeEnum;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>OncePerRequestFilter：过滤器基类，目的在于每一个请求进行一次过滤
 *
 * @Author: 友叔
 * @Date: 2020/11/26 21:41
 * @Description: JWT 身份验证令牌过滤器
 */
@Slf4j
@Component(value = "jwtAuthenticationTokenFilter")
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IUserCacheService userCacheService;

    /**
     * <b>过滤处理流程</b>
     * <p>从 request 中获取指定的请求头；从获取的请求头信息中解析出 token，
     * 从 token 中获取到 username；如果获取到 username 且当前用户没有凭证，那么进一步校验，先获取 userDetails，
     * 再一次 token 校验，若通过，进行用户凭证获取，并对 request 进行二次封装。</p>
     * <br>
     * <p>{@link WebAuthenticationDetailsSource} 可对 {@link HttpServletRequest} 进行二次封装，旨在让 request 拥有更丰富的 API</p>
     *
     * <br><b>注意：该方法的 token 校验前提是用户需要登陆，如果用户未登录，则需要前往登录获取 Security 的凭证 {@code filterChain.doFilter(request, response)}</b>
     *
     * @param request     请求对象
     * @param response    响应对象
     * @param filterChain 过滤器链
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(jwtConfig.getTokenHeader());
        if (authHeader != null && authHeader.startsWith(jwtConfig.getTokenHead())) {
            // The part after "Bearer " 从 token 中获取用户名
            String authToken = authHeader.substring(jwtConfig.getTokenHead().length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            log.info("checking username:{}", username);
            SecurityUserDetails userDetails = userCacheService.getUserInfo(username);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (username != null && ObjectUtil.isEmpty(authentication) && ObjectUtil.isNotNull(userDetails)) {
                // 直接向缓存数据库中获取用户信息，重构 UserDetails 对象
                ApiAssert.notNull(ErrorCodeEnum.USER_BAD_CREDENTIALS, userDetails);
                // 校验 token 的有效性
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    // 获取凭证
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // WebAuthenticationDetailsSource 对 request 进行二次封装，为了让 request 拥有更丰富的 API
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated user:{}", username);
                    // 设置当前用户的凭证
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
