package cn.cy.mybatis.config;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
public class MybatisConfig {

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    @Autowired
    private Environment env;

    /**
     * <p>解析包别名，获取所有的 VO、domain 的包路径</p>
     * <p>传入配置好的 {@code typeAliasesPackage}，解析获取所有的 VO、domain 所在的包路径，提供给 myBatis 注入VO</p>
     *
     * @param typeAliasesPackage 配置的包别名
     * @return 包路径
     */
    public static String setTypeAliasesPackage(String typeAliasesPackage) {
        ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        List<String> allResult = new ArrayList<String>();
        try {
            for (String aliasesPackage : typeAliasesPackage.split(",")) {
                List<String> result = new ArrayList<String>();
                aliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + ClassUtils.convertClassNameToResourcePath(aliasesPackage.trim()) + "/" + DEFAULT_RESOURCE_PATTERN;
                Resource[] resources = resolver.getResources(aliasesPackage);
                if (resources != null && resources.length > 0) {
                    MetadataReader metadataReader = null;
                    for (Resource resource : resources) {
                        if (resource.isReadable()) {
                            metadataReader = metadataReaderFactory.getMetadataReader(resource);
                            try {
                                result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (result.size() > 0) {
                    HashSet<String> hashResult = new HashSet<String>(result);
                    allResult.addAll(hashResult);
                }
            }
            if (allResult.size() > 0) {
                typeAliasesPackage = String.join(",", (String[]) allResult.toArray(new String[0]));
            } else {
                throw new RuntimeException("mybatis typeAliasesPackage 路径扫描错误,参数typeAliasesPackage:" + typeAliasesPackage + "未找到任何包");
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
        // 获取指定搜索的包别名
        String typeAliasesPackage = env.getProperty("mybatis.type-aliases-package");
        // 获取配置 mapper 的扫描，找到所有的 mapper.xml 映射文件
        String mapperLocations = env.getProperty("mybatis.mapper-locations");
        // 获取加载全局的配置文件
        String configLocation = env.getProperty("mybatis.configLocation");
        // 解析包别名，扫描项目，获取所有的包含 VO 、domain 的包路径
        assert typeAliasesPackage != null;
        typeAliasesPackage = setTypeAliasesPackage(typeAliasesPackage);
        VFS.addImplClass(SpringBootVFS.class);

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        assert mapperLocations != null;
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        assert configLocation != null;
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        return sessionFactory.getObject();
    }
}
