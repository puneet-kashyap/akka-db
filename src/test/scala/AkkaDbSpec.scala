import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import com.akka.AkkaDb
import com.akka.messages.SetRequest
import org.scalatest._

class AkkaDbSpec extends FunSpecLike with Matchers with BeforeAndAfterEach {
  implicit val system = ActorSystem()
  describe("akkaDB"){
    describe("given SetRequest") {
      it("should put key/value into map") {
        val actorRef = TestActorRef(new AkkaDb)
        actorRef ! SetRequest("key","value")
        val akkaDb = actorRef.underlyingActor
        akkaDb.map.get("key") shouldEqual(Some("value"))
      }
    }
  }
}
