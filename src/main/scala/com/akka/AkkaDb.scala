package com.akka

import akka.actor.Actor
import akka.event.Logging
import scala.collection.mutable
import com.akka.messages.SetRequest

class AkkaDb extends Actor{
  val map = new mutable.HashMap[String, Object]()
  val log = Logging(context.system, this)
  override def receive = {
    case SetRequest(key, value) => {
      log.info("received SetRequest - key: {} value: {}", key,value)
      map.put(key,value)
    }
    case o => log.info("received unknown message: {}", o)
  }
}
