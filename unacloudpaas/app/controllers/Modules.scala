package controllers

import play.api.mvc._
import play.api.libs.json._
import models.PuppetModule
import com.avaje.ebean.Expr
import scala.collection.JavaConversions._
import play.db.ebean.Model
import play.data.DynamicForm

class Modules extends Controller{

  def search = Action{ request =>
    var ret = Json.arr()
    if(request.queryString.contains("query")){
      val query= request.queryString.get("query").get.head
      val lista = PuppetModule.find.where(Expr.like("name","%"+query+"%")).findList()
      lista.foreach{ i =>
        var params = Json.arr()
        i.puppetParams.foreach{ p => params = params:+Json.obj("id"->p.id,"name"->p.getName,"defaultValue"->p.defaultValue)}
        ret = ret:+(Json.obj("id" -> i.id,"name" -> i.name,"description" -> i.description, "params"->params))
      }
    }
    Ok(Json.stringify(ret))
  }

}
