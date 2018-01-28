package sbitneva;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

/**
 * Main class.
 */
public class Main {

    /**
     * Entry point.
     *
     * @param args command line arguments
     * @throws Exception Unhandled exceptions
     */
    public static void main(final String[] args) throws Exception {

        String webappDirLocation = "src/main/webapp";
        Tomcat tomcat = new Tomcat();

        tomcat.setPort(8080);

        StandardContext ctx = (StandardContext) tomcat.addWebapp(
                "",
                new File(webappDirLocation).getAbsolutePath()
        );

        ctx.setParentClassLoader(Main.class.getClassLoader());
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}

