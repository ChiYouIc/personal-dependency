package cn.cy.sso.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author: 开水白菜
 * @description: 自定义的 ResponseErrorHandler
 * @create: 2021-12-14 22:52
 * @see org.springframework.web.client.DefaultResponseErrorHandler
 **/
public class RestResponseErrorHandler implements ResponseErrorHandler {
    /**
     * 将响应状态代码委托给hasError(HttpStatus) （对于标准状态枚举值）或hasError(int) （对于未知状态代码）。
     *
     * @param response 对检查的响应
     * @return 如果响应指示错误，则为true ； 否则为false
     * @throws IOException 在 I/O 错误的情况下
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        int rawStatusCode = response.getRawStatusCode();
        HttpStatus statusCode = HttpStatus.resolve(rawStatusCode);
        return (statusCode != null ? hasError(statusCode) : hasError(rawStatusCode));
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
        if (statusCode == null) {
            byte[] body = getResponseBody(response);
            String message = getErrorMessage(response.getRawStatusCode(), response.getStatusText(), body, getCharset(response));
            throw new UnknownHttpStatusCodeException(message, response.getRawStatusCode(), response.getStatusText(), response.getHeaders(), body, getCharset(response));
        }
        handleError(response, statusCode);
    }

    /**
     * 从hasError(ClientHttpResponse)调用的模板方法。
     * 默认实现检查HttpStatus.isError() 。 可以在子类中覆盖。
     *
     * @param statusCode 作为枚举值的 HTTP 状态代码
     * @return 如果响应指示错误，则为true； 否则为false
     */
    protected boolean hasError(HttpStatus statusCode) {
        return statusCode.isError();
    }

    /**
     * 从hasError(ClientHttpResponse)调用的模板方法。
     * 默认实现检查给定的状态代码是CLIENT_ERROR还是SERVER_ERROR 。 可以在子类中覆盖。
     *
     * @param unknownStatusCode 作为原始值的 HTTP 状态代码
     * @return 如果响应指示错误，则为true； 否则为false
     */
    protected boolean hasError(int unknownStatusCode) {
        HttpStatus.Series series = HttpStatus.Series.resolve(unknownStatusCode);
        return (series == HttpStatus.Series.CLIENT_ERROR || series == HttpStatus.Series.SERVER_ERROR);
    }


    /**
     * 返回带有响应正文详细信息的错误消息，可能会被截断：
     * <pre>
     * 404 Not Found: [{'id': 123, 'message': 'my very long... (500 bytes)]
     * </pre>
     *
     * @param rawStatusCode HTTP 状态
     * @param statusText    HTTP 状态文本
     * @param responseBody  响应正文作为字节数组
     * @param charset       关联的字符集
     */
    private String getErrorMessage(int rawStatusCode, String statusText, @Nullable byte[] responseBody, @Nullable Charset charset) {

        String preface = rawStatusCode + " " + statusText + ": ";
        if (ObjectUtils.isEmpty(responseBody)) {
            return preface + "[no body]";
        }

        if (charset == null) {
            charset = StandardCharsets.UTF_8;
        }
        int maxChars = 200;

        if (responseBody.length < maxChars * 2) {
            return preface + "[" + new String(responseBody, charset) + "]";
        }

        try {
            Reader reader = new InputStreamReader(new ByteArrayInputStream(responseBody), charset);
            CharBuffer buffer = CharBuffer.allocate(maxChars);
            reader.read(buffer);
            reader.close();
            buffer.flip();
            return preface + "[" + buffer + "... (" + responseBody.length + " bytes)]";
        } catch (IOException ex) {
            // 永远不应该发生
            throw new IllegalStateException(ex);
        }
    }

    /**
     * 根据解析的状态码处理错误。
     *
     * <p>
     * 默认实现委托 {@link HttpClientErrorException#create} 处理 4xx 范围内的错误,
     * 委托 {@link HttpServerErrorException#create} 处理 5xx 范围内的错误,
     * 其它方式引发 {@link UnknownHttpStatusCodeException}.
     *
     * @param response   对检查的响应
     * @param statusCode 作为枚举值的 HTTP 状态代码
     * @see HttpClientErrorException#create
     * @see HttpServerErrorException#create
     */
    protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        String statusText = response.getStatusText();
        HttpHeaders headers = response.getHeaders();
        byte[] body = getResponseBody(response);
        Charset charset = getCharset(response);
        String message = getErrorMessage(statusCode.value(), statusText, body, charset);

        switch (statusCode.series()) {
            case CLIENT_ERROR:
                throw HttpClientErrorException.create(message, statusCode, statusText, headers, body, charset);
            case SERVER_ERROR:
                throw HttpServerErrorException.create(message, statusCode, statusText, headers, body, charset);
            default:
                throw new UnknownHttpStatusCodeException(message, statusCode.value(), statusText, headers, body, charset);
        }
    }

    /**
     * 确定给定响应的 HTTP 状态。
     *
     * @param response 对检查的响应
     * @return 关联的 HTTP 状态
     * @throws IOException                    在 I/O 错误的情况下
     * @throws UnknownHttpStatusCodeException 如果出现无法用 {@link HttpStatus} 枚举表示的未知状态代码
     */
    @Deprecated
    protected HttpStatus getHttpStatusCode(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
        if (statusCode == null) {
            throw new UnknownHttpStatusCodeException(response.getRawStatusCode(), response.getStatusText(), response.getHeaders(), getResponseBody(response), getCharset(response));
        }
        return statusCode;
    }

    /**
     * 读取给定响应的正文（以包含在状态异常中）。
     *
     * @param response 对检查的响应
     * @return 响应正文作为字节数组，如果无法读取正文，则为空字节数组
     */
    protected byte[] getResponseBody(ClientHttpResponse response) {
        try {
            return FileCopyUtils.copyToByteArray(response.getBody());
        } catch (IOException ex) {
            // 忽略
        }
        return new byte[0];
    }

    /**
     * 确定响应的字符集（用于包含在状态异常中）。
     *
     * @param response 对检查的响应
     * @return 关联的字符集，如果没有，则为 {@code null}
     */
    @Nullable
    protected Charset getCharset(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        MediaType contentType = headers.getContentType();
        return (contentType != null ? contentType.getCharset() : null);
    }
}
