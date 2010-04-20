package modelEditor.model;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JFrame;

import modelEditor.abstractClasses.AC_Distortion;
import modelEditor.abstractClasses.AC_Distortion_BubbleUp;
import modelEditor.abstractClasses.AC_Distortion_BubbleDown;
import modelEditor.interfaces.I_Element;

public abstract class Model_findDistortions {
	
	//Vector<Constructor> distortionConstructors = new Vector<Constructor>();
	//Vector<I_Distortion> distortionInstances = new Vector<I_Distortion>();
	
	Classification wordDist;
	Classification inflectionDist;
	Classification functionDist;
	Classification nonFluencyDist;
	Classification otherDist;
	
	FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.contains(".java");
        }
    };
    

	public Model_findDistortions(){
    	wordDist = new Classification("Word Distortions");
    	inflectionDist = new Classification("Inflection Distortions");
    	functionDist = new Classification("Function Word Distortions");
    	nonFluencyDist = new Classification("Non Fluency Distortions");
    	otherDist = new Classification("Other Distortions");
    }
    
    /**
     * Looks through the aMic directory and determines if there are any Properties and loads them
     */
    protected void find_distortions(){
    	String dirName = "modelEditor/distortions";
    	ArrayList<String> list = new ArrayList<String>();
    	try {
			String[] resources = getResourceListing(getClass(),dirName+"/");
			for(String s: resources){
				//System.out.println(">> "+s);
				if(s.contains(".java"))
					list.add(s);
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
    	/*
    	String dirName = "modelEditor/distortions";
        File dir = new File("./"+dirName+"/");
        
		
        for(String c:dir.list(filter)){*/
		for(String c:list){
        	try {
        		String forName = dirName.replace("/", ".")+"."+c.substring(0, c.length()-5);
        		//System.out.println(forName);
				Class cls = Class.forName(forName);
				boolean matchI = matchInterface(cls,I_Element.class,10);
				if(matchI){
					
					Constructor distConstructor = cls.getConstructor();
					AC_Distortion distInstance = this.createInstanceOfDistortion(distConstructor);
					
					boolean isWordDist = distInstance.isDISTORTION_WORD();
					boolean isInflDist = distInstance.isDISTORTION_INFLECTION();
					boolean isNFluDist = distInstance.isDISTORTION_NONFLUENCY();
					boolean isFuncDist = distInstance.isDISTORTION_FUNCTION();
					boolean isOtheDist = distInstance.isDISTORTION_OTHER();
					
					if(isWordDist)
						wordDist.addDistortion(distConstructor, distInstance);
					if(isInflDist)
						inflectionDist.addDistortion(distConstructor, distInstance);
					if(isNFluDist)
						nonFluencyDist.addDistortion(distConstructor, distInstance);
					if(isFuncDist)
						functionDist.addDistortion(distConstructor, distInstance);
					if(isOtheDist)
						otherDist.addDistortion(distConstructor, distInstance);
		
				}else{
					System.out.println("-"+cls);
				}
				
			} catch (Exception e) {
				System.out.println("DRIVER - ELEMENT DISTORTIONS");
				e.printStackTrace();
			}
  
        	
        }
        //System.out.println("debug");
    }
    
    private AC_Distortion createInstanceOfDistortion(Constructor distortionToCreate) {
		Object arglist[] = new Object[0];
		try {
			AC_Distortion dist=(AC_Distortion) distortionToCreate.newInstance(arglist);
			//app.setMic(getActiveInputs());
			//app.start();
			return dist;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		
	}
    
    private boolean matchInterface(Class source,Class target,int depth){
    	for(Class c : source.getInterfaces())
    		if(c.equals(target))
    			return true;   	 	
    	if ( source.getSuperclass() != null )
    		return matchInterface(source.getSuperclass(),target,depth+1);
    	return false;
    }


    
    /**
     * List directory contents for a resource folder. Not recursive.
     * This is basically a brute-force implementation.
     * Works for regular files and also JARs.
     * 
     * @author Greg Briggs
     * @param clazz Any java class that lives in the same place as the resources you want.
     * @param path Should end with "/", but not start with one.
     * @return Just the name of each member item, not the full paths.
     * @throws URISyntaxException 
     * @throws IOException 
     */
    String[] getResourceListing(Class clazz, String path) throws URISyntaxException, IOException {
        URL dirURL = clazz.getClassLoader().getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
          /* A file path: easy enough */
          return new File(dirURL.toURI()).list();
        } 

        if (dirURL == null) {
          /* 
           * In case of a jar file, we can't actually find a directory.
           * Have to assume the same jar as clazz.
           */
          String me = clazz.getName().replace(".", "/")+".class";
          dirURL = clazz.getClassLoader().getResource(me);
        }
        
        if (dirURL.getProtocol().equals("jar")) {
          /* A JAR path */
          String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
          JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
          Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
          Set<String> result = new HashSet<String>(); //avoid duplicates in case it is a subdirectory
          while(entries.hasMoreElements()) {
            String name = entries.nextElement().getName();
            if (name.startsWith(path)) { //filter according to the path
              String entry = name.substring(path.length());
              int checkSubdir = entry.indexOf("/");
              if (checkSubdir >= 0) {
                // if it is a subdirectory, we just return the directory name
                entry = entry.substring(0, checkSubdir);
              }
              result.add(entry);
            }
          }
          return result.toArray(new String[result.size()]);
        } 
          
        throw new UnsupportedOperationException("Cannot list files for URL "+dirURL);
    }

}
