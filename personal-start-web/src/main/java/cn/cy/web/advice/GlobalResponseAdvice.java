package cn.cy.web.advice;

import cn.cy.framework.ResponseType;
import cn.cy.web.response.ApiResponse;
import cn.cy.web.response.UnifiedReturn;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.net.URI;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/3 18:05
 * @Description: 统一 restful 数据格式处理， 主要针对 RestController 与 Controller
 */
@Slf4j
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    private final static String SWAGGER = "swagger";
    private final static String V2 = "v2";
    private final static String SWAGGER_RESOURCES = "swagger-resources";
    private final static String API_DOCS = "api-docs";

    /**
     * 这个方法表示对哪些请求需要执行 beforeBodyWrite, 返回 true 执行，false 不执行
     *
     * @param returnType    返回类型，
     *                      MethodParameter 对象是一个封装方法参数规范的帮助程序类，即{Method}或{Constructor}以及参数索引和已声明泛型类型的嵌套类型索引。用作传递的规范对象。
     * @param converterType 选中的转换器类型
     * @return boolean
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 从 returnType 上获取 Controller、RestController、ResponseBody 等注解信息。
        Controller controller = returnType.getDeclaringClass().getAnnotation(Controller.class);
        RestController restController = returnType.getDeclaringClass().getAnnotation(RestController.class);
        ResponseBody responseBody = returnType.getDeclaringClass().getAnnotation(ResponseBody.class);

        // 是否包装返回
        boolean isWrapper = true;
        // 从 returnType 中获取包装类的可执行信息，并从中获取表示类或接口的{Class}对象，然后获取其包含的注解 UnifiedReturn
        UnifiedReturn unifiedReturn = returnType.getDeclaringClass().getAnnotation(UnifiedReturn.class);
        // unifiedReturn 不为空时，获取其它方法返回值是否需要封装信息
        if (ObjectUtil.isNotEmpty(unifiedReturn)) {
            isWrapper = unifiedReturn.wrapper();
        }
        // 如果 returnType 来自 RestController 或是 Controller、ResponseBody，则需要包装
        return ObjectUtil.isNotEmpty(restController) || (ObjectUtil.isNotEmpty(controller) && ObjectUtil.isNotEmpty(responseBody)) || isWrapper;
    }

    /**
     * 对数据 response 响应信息进行二次处理
     *
     * @param body                  可重写的responseBody
     * @param returnType            控制器返回的数据类型
     * @param selectedContentType   通过内容协商选择的内容类型
     * @param selectedConverterType 选择写入响应的转换器类型
     * @param request               当前请求
     * @param response              当前响应
     * @return RestResult
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 获取执行器方法上的注解
        UnifiedReturn unifiedReturn = returnType.getMethodAnnotation(UnifiedReturn.class);
        int status = HttpStatus.HTTP_OK;
        // unifiedReturn 不为空时，获取其其方法执行后返回的状态码
        if (ObjectUtil.isNotEmpty(unifiedReturn)) {
            status = unifiedReturn.status().value();
        }
        // ResponseStatus	获取被 @ResponseStatus 注解标注时，声明的 状态值
        ResponseStatus responseStatus = returnType.getMethodAnnotation(ResponseStatus.class);
        if (ObjectUtil.isNotEmpty(responseStatus)) {
            status = responseStatus.code().value();
        }
        // 当 unifiedReturn 不为空 且 不需要包装时，直接返回对象
        if (ObjectUtil.isNotEmpty(unifiedReturn) && !unifiedReturn.wrapper()) {
            return body;
        }

        // swagger 的响应信息不做处理
        URI uri = request.getURI();
        if (ObjectUtil.isNotNull(uri) && StrUtil.containsAny(uri.getPath(), SWAGGER, V2, SWAGGER_RESOURCES, API_DOCS)) {
            log.warn("[ResponseBodyAdvice] swagger-ui 的响应.");
            return body;
        }

        // 如果是 ResponseType
        if (body instanceof ResponseType) {
            return body;
        }

        return ApiResponse.success(status, body);
    }
}
