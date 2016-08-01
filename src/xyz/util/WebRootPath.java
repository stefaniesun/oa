package xyz.util;

public class WebRootPath {
	private static WebRootPath instance;
	private static String path;
	private WebRootPath(){
		path = webRootPath();
	}
	public static synchronized WebRootPath getInstance(){
		if(instance==null){
			instance = new WebRootPath();
		}
		return instance;
	}
	public String getPath(){
		if(path==null){
			path = webRootPath();
		}
		return path;
	}
	
	private String webRootPath(){
		String webroot = this.getClass().getClassLoader().getResource("/").getPath();
		System.out.println("webroot="+webroot);
		String webroot1 = this.getClass().getClassLoader().getResource("/").getPath().replaceAll("%20", " ").replace("ebmb2b/WEB-INF/classes", "ebmb2b");
		System.out.println("webroot1="+webroot1);
		//本机路径
		String webroot2 = this.getClass().getClassLoader().getResource("/").getPath().replace(".metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/ebmb2b/WEB-INF/classes", "ebmb2b/WebRoot");
		System.out.println("webroot2="+webroot2);
		System.out.println(webroot.indexOf("file:/"));
		if(webroot.indexOf("file:/")==0){
			webroot = webroot.replaceFirst("file:/", "");
		}else if(webroot.indexOf("/")==0){
			webroot = webroot.replaceFirst("/", "");
		}
		if(webroot.indexOf("/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps")>-1){
			String[] str = webroot.split("/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps");
			String[] str2 = str[1].split("WEB-INF/classes");
			webroot = str[0]+str2[0]+"WebRoot/";
			return webroot;
		}else if(webroot.indexOf("%20")>-1){
			webroot = webroot.replaceAll("%20", " ");
			webroot = webroot.replace("/WEB-INF/classes", "");
			return webroot;
		}
		String[] webrootstr = webroot.split("/WebRoot/");
		
		return webrootstr[0]+"/WebRoot/";
	}
}
