package com.gjyxfs.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**    
 *     
 * 项目名称：SmartTMS_S3
 * 类名称：PathUtil
 * 类描述：定义所有SNMP有关的路径
 * 创建人：yangf
 * 创建时间：2016年3月7日 下午3:30:17
 * 修改人：yangf
 * 修改时间：2016年3月7日 下午3:30:17
 * 修改备注：
 * @version 
 *     
 */
public class PathUtil {

    /**
     * 获得当前工程的发布的classes路径
     * @return
     */
    private static String projectClassPath = PathUtil.getPojectClassesPath();

    private static final String separator = File.separator;

    //Snmp--------------------------start
    //Mib包加载目录
    public final static String MIB_PATH = projectClassPath + "BS_mibs";

    //校验文件目录
    public final static String VALIDATE_PATH = projectClassPath + "validate" + separator;

    //orderXML生成目录
    public final static String ORDER_MIBXML_PATH = projectClassPath + "MibAnalyze" + separator + "orderMibXml" + separator;

    //orderXML生成名称
    public final static String ORDER_MIBXML_SUCCESS = "orderMib.xml";

    //orderXML生成名称
    public final static String ORDER_MIBXML_ERROR = "orderMibErr.xml";

    //MibProperties生成目录
    public final static String MIB_PROPERTIES_PATH = projectClassPath + "MibAnalyze" + separator + "MibProperties" + separator;

    //MibXML生成目录
    public final static String MIBXML_PATH = projectClassPath + "MibAnalyze" + separator + "Mib_Xml" + separator;

    //MibProperties生成名称
    public final static String ENPRO_FILE_NAME = "EN_MibPropertie.properties"; //英文

    public final static String CNPRO_FILE_NAME = "CN_MibPropertie.properties"; //中文

    //vlc脚本的路径
    public final static String VLCSH_PATH = projectClassPath + "Linux_sh" + separator;

    //Snmp--------------------------end

    //文件下载，上传路径
    public final static String DOWN_PATH = tomcatPath() + "fileManager" + separator;

    public final static String SETTING_CONFIGURE = "settingConfigure" + separator;

    public final static String LOG_FILE = "logFile" + separator;

    public final static String VNC_PATH = webappPath() + separator + "webapps" + separator;

    public static String getPojectClassesPath() {
        String projectClassPath = PathUtil.class.getClassLoader().getResource("").getPath();
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("windows") > -1 && (projectClassPath.startsWith("/") || projectClassPath
            .startsWith("\\")))
            projectClassPath = projectClassPath.substring(1, projectClassPath.length());
        return projectClassPath;
    }

    public static String tomcatPath() {
        if (getPojectClassesPath().indexOf("apache-tomcat") > 0) {
            return getPojectClassesPath().substring(0, getPojectClassesPath().indexOf("apache-tomcat"));
        }
        return getPojectClassesPath();
    }

    public static String webappPath() {
        // /home/smart/.tms3/tomcat_xx/webapps
        // /home/SmartTMS_S3/target/SmartTMS_S3/WEB-INF/classes
        String webPath = "";
        if (getPojectClassesPath().indexOf("webapps") > 0) {
            webPath =getPojectClassesPath().substring(0, getPojectClassesPath().indexOf("webapps"));
        }else{
            webPath = getPojectClassesPath();
        }

        try {
            webPath = URLDecoder.decode(webPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return webPath;
    }

    public static void isDirectory(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static boolean isFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }
}
