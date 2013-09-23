package unacloud.paas.web.utils;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author G
 */
public abstract class AbstractEntityEditor<T>{
   public final int NORMAL=0, ADD_NEW=1, UPDATE=2;
   private int currentState=NORMAL;
   private T object;
   private List<T> lista;
   public abstract String addNew();
   public abstract void fillList();
   public String edit(T value){
      object=value;
      currentState=UPDATE;
      return null;
   }
   public int getCurrentState(){
      return currentState;
   }
   public void setCurrentState(int currentState){
      this.currentState=currentState;
   }
   public T getObject(){
      return object;
   }
   public void setObject(T object){
      this.object=object;
   }
   public List<T> getLista(){
      if(lista==null){
         lista=new ArrayList<>();
         fillList();
      }
      return lista;
   }
   public void setLista(List<T> lista){
      this.lista=lista;
   }
}