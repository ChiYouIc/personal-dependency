package cn.cy.mybatis.config;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: 友叔
 * @Date: 2020/11/24 18:09
 * @Description: Mybatis 配置
 */
@Configuration
@ComponentScan(basePackages = MybatisConfig.BASE_PACKAGE)
public class MybatisConfig {

    protected static final String BASE_PACKAGE = "cn.cy.mybatis";

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    @javax.annotation.Resource
    private MybatisProperties mybatisProperties;

    /**
     * <p>解析包别名，获取所有的 VO、domain 的包路径</p>
     * <p>传入配置好的 {@code typeAliasesPackage}，解析获取所有的 VO、domain 所在的包路径，提供给 myBatis 注入VO</p>
     *
     * @param typeAliasesPackage 配置的包别名
     * @return 包路径
     */
    public static String setTypeAliasesPackage(String typeAliasesPackage) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        List<String> allResult = new ArrayList<>();
        try {
            for (String aliasesPackage : typeAliasesPackage.split(",")) {
                List<String> result = new ArrayList<>();

                // 组装包路径
                aliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + ClassUtils.convertClassNameToResourcePath(aliasesPackage.trim())
                        + "/"
                        + DEFAULT_RESOURCE_PATTERN;

                // 获取 aliasesPackage 下的所有 class
                Resource[] resources = resolver.getResources(aliasesPackage);

                // 判断是否获取到 class
                if (resources != null && resources.length > 0) {
                    MetadataReader metadataReader;

                    for (Resource resource : resources) {
                        if (resource.isReadable()) {
                            metadataReader = metadataReaderFactory.getMetadataReader(resource);
                            try {
                                // 获取到 class 的全限定类名
                                result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                if (result.size() > 0) {
                    HashSet<String> hashResult = new HashSet<>(result);
                    allResult.addAll(hashResult);
                }
            }
            // 将所有获取到的全限定类型拼接返回，并赋值给 typeAliasesPackage 返回给 mybatis 的 SqlSessionFactoryBean 初始化
            if (allResult.size() > 0) {
                typeAliasesPackage = String.join(",", allResult.toArray(new String[0]));
            } else {
                throw new RuntimeException("mybatis typeAliasesPackage 路径扫描错误,参数 typeAliasesPackage:" + typeAliasesPackage + "未找到任何包对象");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return typeAliasesPackage;
    }

    /**
     * 手动配置 {@code SqlSessionFactory }<br>
     *
     * @param dataSource 数据源
     * @return SqlSessionFactory Object
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        // 获取指定搜索的类型别名路径
        String typeAliasesPackage = mybatisProperties.getTypeAliasesPackage();

        // 获取配置 mapper 的扫描，找到所有的 mapper.xml 映射文件
        Resource[] mapperLocations = mybatisProperties.resolveMapperLocations();

        // 获取 mybatis setting 配置文件
        String configLocation = mybatisProperties.getConfigLocation();

        // 解析包别名，扫描项目，获取所有的包含 VO 、domain 的包路径
        typeAliasesPackage = setTypeAliasesPackage(typeAliasesPackage);
        VFS.addImplClass(SpringBootVFS.class);

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        sessionFactory.setMapperLocations(mapperLocations);
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));

        return sessionFactory.getObject();
    }
}
