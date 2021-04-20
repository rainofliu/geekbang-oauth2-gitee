package com.ajin.gitee.servlet;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * https://blog.csdn.net/qq_40565265/article/details/105103701
 *
 * @author ajin
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = -8831608179560615136L;

    private static final String CLIENT_ID     = "5ebea7798f0c6cd92c7d122ed80e8a477acef22da94b36f582e34d904211d72b";
    private static final String CLIENT_SECRET = "0e888866a5f8a0cb6aa8b3e49f81b1658eac5c1e1a14d0197db184189c154612";
    private static final String REDIRECT_URI  = "http://localhost:8080/login?actionName=giteeCode";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String actionName = request.getParameter("actionName");
        if ("giteeLogin".equals(actionName)) {
            giteeLogin(request, response);
        } else if ("giteeCode".equals(actionName)) {
            giteeCode(request, response);
        }
    }

    private void giteeLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 跳转授权页面
        AuthRequest authRequest = new AuthGiteeRequest(AuthConfig.builder().clientId(CLIENT_ID).clientSecret(
            CLIENT_SECRET).redirectUri(REDIRECT_URI).build());
        String authorizeUrl = authRequest.authorize();
        response.sendRedirect(authorizeUrl);
    }

    private void giteeCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // http://localhost:8080/login?actionName=giteeCode&code=46a99825f96824a83a93cea054eea829c90bcad3be66e847538297476553cb62
        String code = request.getParameter("code");
        AuthRequest authRequest = new AuthGiteeRequest(AuthConfig.builder().clientId(CLIENT_ID).clientSecret(
            CLIENT_SECRET).redirectUri(REDIRECT_URI).build());
        AuthResponse authResponse = authRequest.login(code);
        // AuthResponse(code=0, msg=null, data=AuthUser(username=tianba985, avatar=https://gitee.com/assets/no_portrait.png, blog=null, nickname=����ʱ������, company=null, location=null, email=null, remark=null, gender=null, source=GITEE, token=AuthToken(accessToken=44539d9f7c573142f06e5cb1e9a7df54, expireIn=0, refreshToken=null, uid=null, openId=null, accessCode=null, scope=null, tokenType=null, idToken=null)))
        System.out.println(authResponse);
    }
}
