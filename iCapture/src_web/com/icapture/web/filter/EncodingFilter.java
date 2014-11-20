package com.icapture.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.icapture.util.GlobalLogger;
import com.util.LogsUtil;
import com.util.StringUtil;

/**
 * <pre>
 * <b>字符集过滤器.</b>
 * <b>Description:</b> 针对HTTP请求以及相应数据或资源进行统一字符过滤, 
 *     默认系统采用UTF-8 字符集编码, 同时支持对HTTP相应的内容页进行字符
 *      编码过滤处理.
 *   
 * <b>Author:</b> liuuhong@yeah.net
 * <b>Date:</b> 2014-1-1 上午10:00:01
 * <b>Copyright:</b> Copyright &copy;2006-2015 firefly.org Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2014-01-01 10:00:01    liuuhong@yeah.net
 *         new file.
 * </pre>
 */
public class EncodingFilter extends CharacterEncodingFilter {

    // 过滤器字符集编码, 默认: UTF-8.
    private String encoding = "UTF-8";

    // 强制对 response 进行编码输出, 默认: true.
    private boolean forceEncoding = true;

    // 需要排除过滤的URL路径, 默认:"".
    private String excludePath = "";

    // 日志记录器.
    public static final Logger logger = GlobalLogger.init_filter;

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        if (logger.isInfoEnabled()) {
            logger.info(LogsUtil.LINE);
            logger.info(LogsUtil.PREFIX2 + "EncodingFilter init ...");
            logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("encoding", this.encoding));
            logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("excludePath", this.excludePath));
            logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("forceEncoding", this.forceEncoding));
            logger.info(LogsUtil.PREFIX3 + "EncodingFilter init is complete");
        }
    }

    /**
     * 字符处理处理流程引擎.
     * 
     * @param request
     * @param response
     * @param filter
     * @throws IOException, ServletException.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 获取HTTP请求的地址.
        // String _path = ((HttpServletRequest) request).getServletPath();
        // 判断当前是否设置有效的字符集编码
        if (StringUtil.hasText(this.encoding) && (this.forceEncoding || null == request.getCharacterEncoding())) {
            request.setCharacterEncoding(this.encoding);
            if (this.forceEncoding) {
                response.setCharacterEncoding(this.encoding);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 设置 过滤器字符集编码.
     * 
     * @param encoding 过滤器字符集编码.
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 设置 强制对 response 进行编码输出.
     * 
     * @param forceEncoding 强制对 response 进行编码输出.
     */
    public void setForceEncoding(boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
    }

    /**
     * 设置 需要排除过滤的URL路径.
     * 
     * @param excludePath 需要排除过滤的URL路径.
     */
    public void setExcludePath(String excludePath) {
        this.excludePath = excludePath;
    }

}
