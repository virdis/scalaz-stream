package fs2
package internal

import TestUtil._
import org.scalacheck.Prop._
import org.scalacheck._
import java.util.concurrent.CountDownLatch

object RefSpec extends Properties("Ref") {

  property("modify") = forAll(Gen.choose(1,100)) { n =>
    val ref = Ref(0)
    val latch = new CountDownLatch(2)
    S { (0 until n).foreach { _ => ref.modify(_ + 1) }; latch.countDown }
    S { (0 until n).foreach { _ => ref.modify(_ - 1) }; latch.countDown }
    latch.await
    ref.get ?= 0
  }
}
