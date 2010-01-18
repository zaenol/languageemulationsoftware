package modelEditor.model;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.util.Vector;

import javax.swing.JFrame;

import modelEditor.abstractClasses.AC_Distortion;
import modelEditor.abstractClasses.AC_Distortion_BubbleUp;
import modelEditor.abstractClasses.AC_Distortion_BubbleDown;
import modelEditor.interfaces.I_Element;

public abstract class Model_findDistortions {
	
	//Vector<Constructor> distortionConstructors = new Vector<Constructor>();
	//Vector<I_Distortion> distortionInstances = new Vector<I_Distortion>();
	
	Classification anomicDist;
	Classification agramaticDist;
	
	
	FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.contains(".java");
        }
    };
    
    public Model_findDistortions(){
    	anomicDist = new Classification("Anomic Distortions");
    	agramaticDist = new Classification("Agrammatic Distortions");
    }
    
    /**
     * Looks through the aMic directory and determines if there are any Properties and loads them
     */
    protected void find_distortions(){
    	String dirName = "modelEditor/distortions";
        File dir = new File("./"+dirName+"/");
        
        for(String c:dir.list(filter)){
        	try {
        		String forName = dirName.replace("/", ".")+"."+c.substring(0, c.length()-5);
        		//System.out.println(forName);
				Class cls = Class.forName(forName);
				boolean matchI = matchInterface(cls,I_Element.class,10);
				if(matchI){
					
					Constructor distConstructor = cls.getConstructor();
					AC_Distortion distInstance = this.createInstanceOfDistortion(distConstructor);
					
					boolean isAnomic = distInstance.isANOMIC();
					boolean isAgramatic = distInstance.isAGRAMMATIC();
					
					if(isAnomic)
						anomicDist.addDistortion(distConstructor, distInstance);
					if(isAgramatic)
						agramaticDist.addDistortion(distConstructor, distInstance);
		
				}else{
					System.out.println("-"+cls);
				}
				
			} catch (Exception e) {
				System.out.println("DRIVER - ELEMENT DISTORTIONS");
				e.printStackTrace();
			}
  
        	
        }
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


    
    

}
