/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools.SceneManagment;


import java.util.HashMap;

/**
 * All scenes are loaded into the scenemanager and can be delivered by useage.
 * SINGLETON
 * @author Steve
 */
public class SceneManager {

    private static SceneManager instance = new SceneManager();
    /**
     * returns the SINGLETON SceneManager
     * @return SceneManager
     */
    public static SceneManager get(){
        return instance;
    }
    
    
    
    private HashMap<String,Scene> scenes;
    
    private SceneManager(){
        scenes = new HashMap();
    }
    

    /**
     * Saves anew scene into the SceneManager
     * @param key key to find the scene later
     * @param scene the scene
     * @return returns false if key is already in use
     */
    public boolean addScene(String key, Scene scene){
        if(scenes.containsKey(key)){
            return false;
        }else{
            scenes.put(key, scene);
            return true;
        }
    }
    
    /**
     * return the specified scene;
     * @param key name of the scene
     * @return null if scene is not loaded into manager
     */
    public Scene getScene(String key){
        return scenes.get(key);
    }
    
    /**
     * Checks if the specified scenes has been saved to the SceneManager already
     * @param key name of the scene
     * @return true if already loaded into sceneManager
     */
    public boolean hasScene(String key){
        return scenes.containsKey(key);
    }
    
    

}
