package android.app.wolf.household.http.httpconstant;

/**
 * Created by lh on 2017/10/16.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class HttpHost {

    private static boolean isDebug = false;

    public HttpHost() {
    }

    public String debugHost = "http://101.132.115.12:8080/";

    public String formalHost = "https://public.jiafeicat.com/";

    public static String getHttpHost(){
        if (isDebug){
            return new HttpHost().debugHost;
        }else {
            return new HttpHost().formalHost;
        }
    }

}
