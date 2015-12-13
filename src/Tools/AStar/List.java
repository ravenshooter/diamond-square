/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools.AStar;

/**
 *
 * @author Steve
 */
public class List {
    Element start;
    Element end;
    private int counter;
    public List(){
        
    }
    
    public void add(Knoten k){
        if(start == null){
            start = new Element(k);
            end = start;
        }else{
            if(k.getF() < start.getData().getF()){
                Element e = new Element(k);
                e.setFollower(start);
                start = e;
            }
            Element e = start;
            boolean done=false;
            while(e!= null){ //solange noch element in der schlange sind
                if(e.getFollower()!=null){ //wenn das gegenwärtige element noch nicht das letzte ist
                    if(e.getFollower().getData().getF() > k.getF()){ //wenn der f wert von k kleiner ist als vom listenelement
                        Element newElement = new Element(k); //dann vor dem listenelement einfügen
                        newElement.setFollower(e.getFollower());
                        e.setFollower(newElement);
                        e = null;
                    }else{ //wenn der f wert des listenelements kleiner ist als der von k dann das nächste listenelement betrachten
                        e= e.getFollower();
                    }
                }else{ //wenn e das letzte listenelemet ist dann wird k hinten angehängt
                    e.setFollower(new Element(k));
                    e= null;
                }
            }
        }
        counter++;
    }
    
    public boolean hasElement(int x, int y){
        if(start != null){
            Element e = start;
            while(e!=null){
                if(e.getData().x == x && e.getData().y==y){
                    return true;
                }
                e= e.getFollower();
            }
            return false;
        }
        return false; 
    }
    
    public Element getElement(int x, int y){
        if(start != null){
            Element e = start;
            while(e!=null){
                if(e.getData().x == x && e.getData().y==y){
                    return e;
                }
                e = e.getFollower();
            }
            return null;
        }
        return null; 
    }
    
    public Element getElement(Knoten k){
        return getElement(k.x,k.y);
    }
    
    public Knoten getKnoten(int x, int y){
        return getElement(x,y).getData();
    }
    
    public Knoten getKnoten(Knoten k){
        return getElement(k.x,k.y).getData();
    }
    
    public Element getElement(int ID){
        if(ID >= 0 && ID < counter){
            Element e = start;
            for(int i = 0; i < ID; i++){
                e = e.getFollower();
            }
            return e;
        }
        return null;
    }
    
    public Knoten removeElement(int x, int y){
        if(start != null){
            Element buffer = start;
            if(start.getData().getX()==x&&start.getData().getY()==y){
                buffer = start;
                start = start.getFollower();
                return buffer.getData();
            }
            Element e = start;
            while(e!=null){
                if(e.getData().x == x && e.getData().y==y){
                    buffer.setFollower(e.getFollower());
                    counter--;
                    if(e==start){
                        start = end = null;
                    }
                    return e.getData();
                }
                buffer = e;
                e= e.getFollower();
            }
            return null;
        }
        return null; 
    }
    
    public Knoten removeElement(Knoten k){
        return removeElement(k.x,k.y);
    }
    
    public int getCounter(){
        return counter;
    }
    
    public Knoten removeBest(){
        Element buffer = start;
        if(start!=null){
            start = start.getFollower();
            counter--;
        }
        
        return buffer.getData();
    }
    
    
    class Element{
        Element follower;
        Knoten data;
        public Element(Knoten k){
            data = k;
        }
        
        public Element setFollower(Element e){
            follower = e;
            return follower;
        }
        
        public Element setFollower(Knoten k){
            follower = new Element(k);
            return follower;
        }
        
        public Knoten getData(){
            return data;
        }
        
        public Element getFollower(){
            return follower;
        }
    } 
}
