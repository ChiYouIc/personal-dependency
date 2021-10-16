package cn.cy.security.web.controller;

import cn.cy.security.web.model.Captcha;
import cn.cy.security.web.service.ICaptchaCacheService;
import cn.cy.web.properties.Website;
import cn.cy.web.util.uuid.IdUtils;
import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.Producer;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/30 15:14
 * @Description: 验证码操作处理Controller
 */
@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource
    private ICaptchaCacheService captchaCacheService;

    @Resource
    private Website website;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public Captcha getCode(HttpServletResponse response) throws IOException {
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(website.getCaptcha().getCaptchaType())) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(website.getCaptcha().getCaptchaType())) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        captchaCacheService.setCaptchaInfo(uuid, code);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        assert image != null;
        ImageIO.write(image, "jpg", os);
        return new Captcha(uuid, Base64.encode(os.toByteArray()));
    }
}
