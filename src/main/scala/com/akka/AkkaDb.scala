package com.akka

import akka.actor.{Actor, ActorSystem, Props, Status}
import akka.event.Logging

import scala.collection.mutable
import com.akka.messages.{GetRequest, KeyNotFoundException, SetRequest}

class AkkaDb extends Actor{
  val map = new mutable.HashMap[String, Object]()
  val log = Logging(context.system, this)
  override def receive = {
    case SetRequest(key, value) => {
      log.info("received SetRequest - key: {} value: {}", key,value)
      map.put(key,value)
      sender() ! Status.Success
    }
    case GetRequest(key) => {
      log.info("received GetRequest - key: {}", key)
      val response: Option[Object] = map.get(key)
      response match {
        case Some(x) => sender() ! x
        case None => sender() ! Status.Failure(new KeyNotFoundException(key))
      }

    }
    case o => Status.Failure(new ClassNotFoundException())
  }
}

object Main extends App {
  val system = ActorSystem("akkaDB")
  val helloActor = system.actorOf(Props[AkkaDb], name = "akka-db")
}